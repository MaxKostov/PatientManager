package patientmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import patientmanager.entities.Procedure;
import patientmanager.services.ProcedureService;

import java.util.List;

@Controller
@RequestMapping("/procedures")
public class ProcedureController {

    private final ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping
    public String getProcedures(Model model) {
        List<Procedure> procedures = procedureService.getAllProcedure();
        model.addAttribute("procedures", procedures);
        return "procedures";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcedure(@PathVariable int id, Model model) {
        procedureService.deleteProcedure(id);
        List<Procedure> procedures = procedureService.getAllProcedure();
        model.addAttribute("procedures", procedures);
        return "redirect:/procedures";
    }

    @PostMapping("/edit/{id}")
    public String editProcedure(@PathVariable int id, @RequestParam String name, Model model) {
        Procedure optionalProcedure = procedureService.getProcedureById(id);
        optionalProcedure.setName(name);
        procedureService.updateProcedure(optionalProcedure);
        List<Procedure> procedures = procedureService.getAllProcedure();
        model.addAttribute("procedures", procedures);
        return "procedures";
    }

    @PostMapping("/add")
    public String createProcedure(@ModelAttribute Procedure procedure) {
        procedureService.createProcedure(procedure);
        return "redirect:/procedures";
    }
}
