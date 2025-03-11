package com.cours.api_gsbpharmastock.service;


import com.cours.api_gsbpharmastock.model.Medicament;
import com.cours.api_gsbpharmastock.repository.MedicamentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Medicament saveMedicament(final Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public void deleteMedicament(final Medicament medicament) {
        medicamentRepository.delete(medicament);
    }
}
