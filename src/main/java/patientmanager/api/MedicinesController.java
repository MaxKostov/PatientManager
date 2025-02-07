package patientmanager.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patientmanager.entities.Medicine;
import patientmanager.services.MedicineService;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicinesController {
    private final MedicineService medicineService;

    public MedicinesController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineService.createMedicine(medicine));
    }

    @PutMapping
    public ResponseEntity<Medicine> updateMedicine(@RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineService.updateMedicine(medicine));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicine(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicine(id));
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicine() {
        return ResponseEntity.ok(medicineService.getAllMedicine());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.deleteMedicine(id));
    }

    @PutMapping("/{id}/quantity")
    public ResponseEntity<String> updateQuantity(@PathVariable Long id, @RequestParam int quantityChange) {
        medicineService.updateQuantity(id, quantityChange);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    @PostMapping("/{medicineId}/assign/{patientStayPeriodId}")
    public ResponseEntity<String> assignMedicineToPatientStayPeriod(
            @PathVariable Long medicineId, @PathVariable Long patientStayPeriodId, @RequestParam int quantity) {
        return ResponseEntity.ok(medicineService.assignMedicineToPatientStayPeriod(medicineId, patientStayPeriodId, quantity));
    }
}
