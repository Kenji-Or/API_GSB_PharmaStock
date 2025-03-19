package com.cours.api_gsbpharmastock.repository;

import com.cours.api_gsbpharmastock.model.Medicament;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicamentRepository extends CrudRepository<Medicament, Long> {
    Iterable<Medicament> findMedicamentsByCategory(final int category);
    // Trouver les médicaments dont la quantité est inférieure à l'alerte_stock
    @Query("SELECT m FROM Medicament m WHERE m.quantity < m.alerte_stock")
    List<Medicament> findMedicamentsEnRupture();
}
