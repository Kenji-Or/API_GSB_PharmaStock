package com.cours.api_gsbpharmastock.controller;

import com.cours.api_gsbpharmastock.model.Medicament;
import com.cours.api_gsbpharmastock.service.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MedicamentController {

    @Autowired
    private MedicamentService medicamentService;

    @GetMapping("/medicaments")
    public Iterable<Medicament> getAllMedicaments() {
        return medicamentService.getAllMedicaments();
    }

    @GetMapping("/medicament/{id}")
    public Optional<Medicament> getMedicamentById(@PathVariable Long id) {
        return medicamentService.getMedicamentById(id);
    }

    @GetMapping("/medicament/dateexpiration")
    public List<Medicament> getMedicamentDateExpiration() {
        return medicamentService.getMedicamentsExpiringInOneMonth();
    }

    @GetMapping("/medicament/categorie/{category}")
    public Iterable<Medicament> getMedicamentByCategory(@PathVariable int category) {
        return medicamentService.getMedicamentsByCategory(category);
    }

    @GetMapping("medicament/alertstock")
    public List<Medicament> getMedicamentEnAlerteStock() {
        return medicamentService.getMedicamentsEnRupture();
    }

    @PostMapping("/medicament/create")
    public Medicament createMedicament(@RequestBody Medicament medicament) {
        return medicamentService.saveMedicament(medicament);
    }

    @PatchMapping("/medicament/{id}")
    public ResponseEntity<?> editMedicament(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Medicament> medicamentOptional = medicamentService.getMedicamentById(id);
        if (medicamentOptional.isPresent()) {
            Medicament currentMedicament = medicamentOptional.get();

            if (updates.containsKey("name")) {
                currentMedicament.setName((String) updates.get("name"));
            }
            if (updates.containsKey("quantity")) {
                currentMedicament.setQuantity((Integer) updates.get("quantity"));
            }
            if (updates.containsKey("category")) {
                currentMedicament.setCategory((Integer) updates.get("category"));
            }
            if (updates.containsKey("date_expiration")) {
                currentMedicament.setDate_expiration((String) updates.get("date_expiration"));
            }
            if (updates.containsKey("alerte_stock")) {
                currentMedicament.setAlerte_stock((Integer) updates.get("alerte_stock"));
            }
            medicamentService.saveMedicament(currentMedicament);

            // Construire la réponse JSON
            Map<String, Object> response = new HashMap<>();
            response.put("id", currentMedicament.getId());
            response.put("name", currentMedicament.getName());
            response.put("quantity", currentMedicament.getQuantity());
            response.put("category", currentMedicament.getCategory());
            response.put("date_expiration", currentMedicament.getDate_expiration());
            response.put("alerte_stock", currentMedicament.getAlerte_stock());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/medicament/{id}")
    public void deleteMedicament(@PathVariable Long id) {
        Optional<Medicament> medicamentOptional = medicamentService.getMedicamentById(id);
        if (medicamentOptional.isPresent()) {
            Medicament currentMedicament = medicamentOptional.get();
            medicamentService.deleteMedicament(currentMedicament);
        }
    }
}
