package patientmanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patientmanager.entities.PatientStayPeriod;
import patientmanager.services.impl.PatientStayPeriodServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/patient-stay-period")
public class PatientStayPeriodController {

    private final PatientStayPeriodServiceImpl patientStayPeriodService;

    @Autowired
    public PatientStayPeriodController(PatientStayPeriodServiceImpl patientStayPeriodService) {
        this.patientStayPeriodService = patientStayPeriodService;
    }

    @GetMapping("/{passportId}")
    public ResponseEntity<PatientStayPeriod> getPatientStayPeriod(@PathVariable String passportId) {
        PatientStayPeriod stayPeriod = patientStayPeriodService.showPatientStayPeriod(passportId);
        if (stayPeriod == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println(stayPeriod);
        return ResponseEntity.ok(stayPeriod);
    }

    @GetMapping("/all/{passportId}")
    public ResponseEntity<List<PatientStayPeriod>> getPatientStayPeriodList(@PathVariable String passportId) {
        List<PatientStayPeriod> periodList = patientStayPeriodService.showAllPeriods(passportId);
        if (periodList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(periodList);
    }

    @PostMapping("/initial-diagnosis")
    public ResponseEntity<PatientStayPeriod> setInitialDiagnosis(@RequestParam String passportId, @RequestParam String diagnosis) {
        PatientStayPeriod stayPeriod = patientStayPeriodService.addInitialDiagnosis(diagnosis, passportId);
        if (stayPeriod == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stayPeriod);
    }

    @PostMapping("/discharge-summary")
    public ResponseEntity<PatientStayPeriod> setDischargeSummary(@RequestParam String passportId, @RequestParam String summary) {
        PatientStayPeriod stayPeriod = patientStayPeriodService.addDischargeSummary(summary, passportId);
        if (stayPeriod == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stayPeriod);
    }
}
