package com.cours.api_gsbpharmastock.service;


import com.cours.api_gsbpharmastock.model.Category;
import com.cours.api_gsbpharmastock.model.Medicament;
import com.cours.api_gsbpharmastock.repository.MedicamentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class MedicamentService {
    @Autowired
    private MedicamentRepository medicamentRepository;

    public Iterable<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }

    public Optional<Medicament> getMedicamentById(final Long id) {
        return medicamentRepository.findById(id);
    }

    public List<Medicament> getMedicamentsExpiringInOneMonth() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthLater = today.plusMonths(1);

        List<Medicament> allMedicaments = (List<Medicament>) medicamentRepository.findAll();

        return allMedicaments.stream()
                .filter(med -> {
                    try {
                        LocalDate expirationDate = parseExpirationDate(med.getDate_expiration());
                        return expirationDate != null &&
                                (expirationDate.isAfter(today) || expirationDate.isEqual(today)) &&
                                expirationDate.isBefore(oneMonthLater.plusDays(1));
                    } catch (Exception e) {
                        return false; // Ignorer si la date est invalide
                    }
                })
                .collect(Collectors.toList());
    }

    // Méthode pour détecter et parser les formats de date
    private LocalDate parseExpirationDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        try {
            // Format "dd/MM/yyyy"
            DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, fullFormatter);
        } catch (DateTimeParseException ignored) {}

        try {
            // Format "MM/yyyy" → On considère le dernier jour du mois
            DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
            return LocalDate.parse("01/" + dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .withDayOfMonth(LocalDate.parse("01/" + dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")).lengthOfMonth());
        } catch (DateTimeParseException ignored) {}

        return null; // Si aucun format ne correspond
    }

    public Iterable<Medicament> getMedicamentsByCategory(final Category category) {
        return medicamentRepository.findMedicamentsByCategory(category);
    }

    public Medicament saveMedicament(final Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public void deleteMedicament(final Medicament medicament) {
        medicamentRepository.delete(medicament);
    }
}
