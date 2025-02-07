package patientmanager.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import patientmanager.entities.Medicine;
import patientmanager.entities.Patient;
import patientmanager.entities.PatientStayPeriod;
import patientmanager.services.MedicineService;
import patientmanager.services.PatientService;
import patientmanager.services.PatientStayPeriodService;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientInfoController {
    private final PatientService patientService;
    private final PatientStayPeriodService patientStayPeriodService;
    private final MedicineService medicineService;

    @Autowired
    public PatientInfoController(PatientService patientService, PatientStayPeriodService patientStayPeriodService, MedicineService medicineService) {
        this.patientService = patientService;
        this.patientStayPeriodService = patientStayPeriodService;
        this.medicineService = medicineService;
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
        List<PatientStayPeriod> periodList = patientStayPeriodService.showAllPeriods(passportID);
        if (lastPatientStayPeriod.getDischargeDate() == null) {
            model.addAttribute("lastPatientStayPeriod", lastPatientStayPeriod);
            periodList.removeLast();
        }
        model.addAttribute("patient", patient);
        model.addAttribute("periodList", periodList.reversed());
        model.addAttribute("medicines", medicines);
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
}
