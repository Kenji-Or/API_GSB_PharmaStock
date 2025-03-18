package com.cours.api_gsbpharmastock.repository;

import com.cours.api_gsbpharmastock.model.Category;
import com.cours.api_gsbpharmastock.model.Medicament;
import org.springframework.data.repository.CrudRepository;

public interface MedicamentRepository extends CrudRepository<Medicament, Long> {
    Iterable<Medicament> findMedicamentsByCategory(final Category category);
}
