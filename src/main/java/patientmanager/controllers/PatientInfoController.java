package patientmanager.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import patientmanager.objects.Patient;
import patientmanager.objects.PatientStayPeriod;
import patientmanager.services.PatientService;
import patientmanager.services.PatientStayPeriodService;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientInfoController {
    private final PatientService patientService;
    private final PatientStayPeriodService patientStayPeriodService;

    @Autowired
    public PatientInfoController(PatientService patientService, PatientStayPeriodService patientStayPeriodService) {
        this.patientService = patientService;
        this.patientStayPeriodService = patientStayPeriodService;
    }

    @GetMapping
    public String getPatientId(Model model) {
        model.addAttribute("type", "patientInfo");
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
        List<PatientStayPeriod> periodList = patientStayPeriodService.showAllPeriods(passportID);
        if (lastPatientStayPeriod.getDischargeDate() == null) {
            model.addAttribute("lastPatientStayPeriod", lastPatientStayPeriod);
            periodList.removeLast();
        }
        model.addAttribute("patient", patient);
        model.addAttribute("periodList", periodList.reversed());
        return "fullPatientInfo";
    }
}
