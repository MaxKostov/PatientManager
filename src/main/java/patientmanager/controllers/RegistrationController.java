package patientmanager.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import patientmanager.objects.Patient;
import patientmanager.objects.PatientStayPeriod;
import patientmanager.services.PatientService;

import java.time.LocalDate;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final PatientService patientService;

    public RegistrationController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public String welcome() {
        return "registration";
    }

    @GetMapping("/navigate")
    public String navigateToForm(@RequestParam("button") String button, HttpSession session) {
        session.setAttribute("canAccessForm", true);
        String displayText;
        switch (button) {
            case "registration":
                displayText = "Please enter registration details:";
                session.setAttribute("type", "registration");
                break;
            case "discharge":
                displayText = "Enter discharge information:";
                session.setAttribute("type", "discharge");
                break;
            case "search":
                displayText = "Search for a patient by passport ID:";
                session.setAttribute("type", "search");
                break;
            default:
                displayText = "Unknown action.";
        }
        session.setAttribute("displayText", displayText);

        return "redirect:/register/passportIn";
    }


    @GetMapping("/passportIn")
    public String formPage(HttpSession session, Model model) {
        Boolean canAccess = (Boolean) session.getAttribute("canAccessForm");
        if (canAccess == null || !canAccess) {
            return "redirect:/register/error";
        }

        String displayText = (String) session.getAttribute("displayText");
        if (displayText == null) {
            return "redirect:/register/error";
        }

        model.addAttribute("displayText", displayText);
        session.removeAttribute("displayText");

        return "passportIn";
    }


    @GetMapping("/error")
    public String errorPage(HttpSession session, Model model) {
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        if (session.getAttribute("errorDetails") != null) {
            model.addAttribute("errorDetails", session.getAttribute("errorDetails"));
        }
        return "error";
    }

    @PostMapping("/submit")
    public String submitPassportID(@RequestParam("input") String passportID, HttpSession session) {
        System.out.println("passportID: " + passportID);
        session.setAttribute("passportID", passportID);
        String type = (String) session.getAttribute("type");
        switch (type) {
            case "registration" -> {
                return "redirect:/register/form";
            }
            case "discharge" -> {
                return "redirect:/register/discharge";
            }
            case "search" -> {
                return "redirect:/register/patient";
            }
        }
        return "redirect:/register/error";
    }

    @GetMapping("/form")
    public String showPatientForm(HttpSession session, Model model) {
        String passportID = (String) session.getAttribute("passportID");
        Boolean canAccess = (Boolean) session.getAttribute("canAccessForm");
        if (passportID == null || !canAccess) {
            return "redirect:/register/error";
        }

        Patient optionalPatient = patientService.getPatientByPassportId(passportID);
        if (optionalPatient != null) {
            session.setAttribute("patient", optionalPatient);
            if (optionalPatient.getLastPeriod().getDischargeDate() == null) {
                session.setAttribute("errorMessage", "This patient has not been discharged yet");
                return "redirect:/register/error";
            }
            return "redirect:/register/addStayPeriod";
        }

        session.removeAttribute("canAccessForm");
        session.removeAttribute("passportID");
        model.addAttribute("passportID", passportID);
        Patient patient = new Patient();
        PatientStayPeriod patientStayPeriod = new PatientStayPeriod();

        model.addAttribute("patient", patient);
        model.addAttribute("stayPeriod", patientStayPeriod);
        return "patientForm";
    }

    @PostMapping("/submitPatient")
    public String handlePatientForm(@Valid @ModelAttribute("patient") Patient patient,
                                    @Valid @ModelAttribute("stayPeriod") PatientStayPeriod stayPeriod,
                                    BindingResult bindingResult,
                                    Model model) {
        System.out.println("Received patient data: " + patient);
        System.out.println("Received travel voucher: " + stayPeriod.getTravelVoucher());
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("name", patient.getName());
            model.addAttribute("surname", patient.getSurname());
            model.addAttribute("address", patient.getAddress());
            model.addAttribute("passportID", patient.getPassportID());
            model.addAttribute("travelVoucher", stayPeriod.getTravelVoucher());
            return "patientForm";
        }
        patient.addPeriod(stayPeriod);
        patientService.savePatient(patient);
        return "redirect:/register";
    }

    @GetMapping("/patient")
    public String showPatient(HttpSession session, Model model) {
        String passportID = (String) session.getAttribute("passportID");
        Patient patient = patientService.getPatientByPassportId(passportID);
        session.removeAttribute("passportID");
        session.removeAttribute("canAccessForm");
        if (patient != null) {
            model.addAttribute("patient", patient);
            return "patientDetails";
        } else {
            session.setAttribute("errorMessage", "Patient with id: " + passportID + " not found");
            return "redirect:/register/error";
        }
    }

    @GetMapping("/discharge")
    public String showDischargeForm(HttpSession session) {
        String passportID = (String) session.getAttribute("passportID");
        Boolean canAccess = (Boolean) session.getAttribute("canAccessForm");
        if (passportID == null || !canAccess) {
            return "redirect:/register/error";
        }
        if (patientService.dischargePatient(passportID) == null) {
            session.setAttribute("errorMessage", "There are no stay periods available");
            return "redirect:/register/error";
        }
        return "redirect:/register/patient";
    }

    @GetMapping("/addStayPeriod")
    public String addStayPeriod(HttpSession session, Model model) {
        Boolean canAccess = (Boolean) session.getAttribute("canAccessForm");
        Patient patient = (Patient) session.getAttribute("patient");
        if (canAccess == null || !canAccess) {
            return "redirect:/register/error";
        }
        PatientStayPeriod patientStayPeriod = new PatientStayPeriod();
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("patient", patient);
        model.addAttribute("stayPeriod", patientStayPeriod);
        session.removeAttribute("canAccessForm");

        return "addStayPeriod";
    }

    @PostMapping("/submitNewStayPeriod")
    public String submitStayPeriod(@Valid @ModelAttribute("stayPeriod") PatientStayPeriod stayPeriod,
                                   HttpSession session,
                                   Model model) {
        Patient patient = (Patient) session.getAttribute("patient");
        System.out.println("Received patient data: " + patient);
        System.out.println("Received travel voucher: " + stayPeriod.getTravelVoucher());
        Patient servicePatient = patientService.getPatientByPassportId(patient.getPassportID());
        patientService.addStayPeriod(servicePatient, stayPeriod);

        return "redirect:/register";
    }
}
