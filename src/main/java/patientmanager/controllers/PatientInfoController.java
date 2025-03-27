package patientmanager.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import patientmanager.entities.Medicine;
import patientmanager.entities.Patient;
import patientmanager.entities.PatientStayPeriod;
import patientmanager.entities.Procedure;
import patientmanager.services.MedicineService;
import patientmanager.services.PatientService;
import patientmanager.services.ProcedureService;
import patientmanager.services.impl.PatientStayPeriodServiceImpl;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/patient")
public class PatientInfoController {
    private final PatientService patientService;
    private final PatientStayPeriodServiceImpl patientStayPeriodService;
    private final MedicineService medicineService;
    private final ProcedureService procedureService;

    @Autowired
    public PatientInfoController(PatientService patientService, PatientStayPeriodServiceImpl patientStayPeriodService, MedicineService medicineService, ProcedureService procedureService) {
        this.patientService = patientService;
        this.patientStayPeriodService = patientStayPeriodService;
        this.medicineService = medicineService;
        this.procedureService = procedureService;
    }

    @GetMapping
    public String getPatientId(Model model) {
        model.addAttribute("type", "patientInfo");
        model.addAttribute("displayText", "Search for a patient by passport ID: ");
        return "passportIn";
    }

    @PostMapping("/submit")
    public String submitPassportId(@RequestParam("input") String passportID, HttpSession session) {
        session.setAttribute("passportID", passportID);
        System.out.println("passportID: " + passportID);
        return "redirect:/patient/info";
    }

    @GetMapping("/info")
    public String showPatientInfo(HttpSession session, Model model) {
        String passportID = (String) session.getAttribute("passportID");
        Patient patient = patientService.getPatientByPassportId(passportID);
        PatientStayPeriod lastPatientStayPeriod = patient.getLastPeriod();
        List<Medicine> medicines = medicineService.getAllMedicine();
        List<Procedure> procedures = procedureService.getAllProcedure();
        List<PatientStayPeriod> periodList = patientStayPeriodService.showAllPeriods(passportID);
        if (lastPatientStayPeriod.getDischargeDate() == null) {
            model.addAttribute("lastPatientStayPeriod", lastPatientStayPeriod);
            periodList.removeLast();
        }
        model.addAttribute("patient", patient);
        model.addAttribute("periodList", periodList.reversed());
        model.addAttribute("medicines", medicines);
        model.addAttribute("procedures", procedures);
        return "fullPatientInfo";
    }

    @PostMapping("/submit-initial-diagnosis")
    public String submitInitialDiagnosis(@RequestParam("initialDiagnosis") String initialDiagnosis, HttpSession session, Model model) {
        System.out.println("Initial Diagnosis: " + initialDiagnosis);
        String passportID = (String) session.getAttribute("passportID");
        patientStayPeriodService.addInitialDiagnosis(initialDiagnosis, passportID);

        model.addAttribute("message", "Initial diagnosis submitted successfully!");
        return "redirect:info";
    }

    @PostMapping("/submit-discharge-summary")
    public String submitDischargeSummary(@RequestParam("dischargeSummary") String dischargeSummary, HttpSession session, Model model) {
        System.out.println("Discharge Summary: " + dischargeSummary);
        String passportID = (String) session.getAttribute("passportID");
        patientStayPeriodService.addDischargeSummary(dischargeSummary, passportID);

        model.addAttribute("message", "Discharge summary submitted successfully!");
        return "redirect:info";
    }

    @PostMapping("/submit-medicines")
    public String submitMedicines(@RequestParam Long medicineId, @RequestParam int quantity,HttpSession session, Model model) {
        String passportID = (String) session.getAttribute("passportID");
        PatientStayPeriod patientStayPeriod = patientStayPeriodService.showPatientStayPeriod(passportID);
        medicineService.assignMedicineToPatientStayPeriod(medicineId, patientStayPeriod.getId(), quantity);

        model.addAttribute("message", "Medicine assigned successfully!");
        return "redirect:info";
    }

    @PostMapping("/submit-procedures")
    public String submitProcedure(@RequestParam Long procedure_id, HttpSession session, Model model) {
        String passportID = (String) session.getAttribute("passportID");
        PatientStayPeriod patientStayPeriod = patientStayPeriodService.showPatientStayPeriod(passportID);
        procedureService.assignProcedureToStayPeriod(procedure_id, patientStayPeriod);
        return "redirect:info";
    }

    @PostMapping("/delete-procedure")
    public ResponseEntity<?> deleteProcedure(@RequestParam("procedure_id") Long procedure_id, HttpSession session) {
        try {
            String passportID = (String) session.getAttribute("passportID");
            PatientStayPeriod patientStayPeriod = patientStayPeriodService.showPatientStayPeriod(passportID);
            procedureService.removeProcedureFromStayPeriod(procedure_id, patientStayPeriod);
            return ResponseEntity.ok().body(Map.of("message", "Procedure deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
