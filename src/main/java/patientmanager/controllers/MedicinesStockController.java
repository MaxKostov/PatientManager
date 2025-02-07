package patientmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import patientmanager.entities.Medicine;
import patientmanager.services.MedicineService;

import java.util.List;

@Controller
@RequestMapping("/medicines")
public class MedicinesStockController {
    private final MedicineService medicineService;

    @Autowired
    public MedicinesStockController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping
    public String showMedicines(Model model) {
        List<Medicine> medicines = medicineService.getAllMedicine();
        model.addAttribute("medicines", medicines);
        return "medicines";
    }

    @PostMapping("/add")
    public String addMedicine(@RequestParam String name, @RequestParam int quantity, @RequestParam double price, Model model) {
        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setQuantity(quantity);
        medicine.setPrice(price);
        medicineService.createMedicine(name, quantity, price);
        return "redirect:/medicines";
    }

    @PostMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable long id, Model model) {
        medicineService.deleteMedicine(id);
        List<Medicine> medicines = medicineService.getAllMedicine();
        model.addAttribute("medicines", medicines);
        return "redirect:/medicines";
    }

    @PostMapping("/edit/{id}")
    public String editMedicine(@PathVariable long id, @RequestParam String name, @RequestParam int quantity, @RequestParam double price) {
        Medicine medicine = medicineService.getMedicine(id);
        medicine.setName(name);
        medicine.setQuantity(quantity);
        medicine.setPrice(price);
        medicineService.updateMedicine(medicine);
        return "redirect:/medicines";
    }
}
