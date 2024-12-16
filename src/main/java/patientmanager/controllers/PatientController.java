package patientmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import patientmanager.objects.Patient;
import patientmanager.services.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Validated
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(patientService.getPatientById(id));
    }

    @PutMapping("/")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        return ResponseEntity.ok().body(patientService.updatePatient(patient));
    }

    @PostMapping("/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok().body(patientService.savePatient(patient));
    }

    @PutMapping("/discharge/{passportID}")
    public ResponseEntity<Patient> dischargePatient(@PathVariable("passportID") String passportID) {
        Patient dischargedPatient = patientService.dischargePatient(passportID);
        return ResponseEntity.ok().body(dischargedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().body("Deleted patient successfully");
    }
}
