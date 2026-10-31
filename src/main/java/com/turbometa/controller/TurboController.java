package com.turbometa.controller;

import com.turbometa.model.Turbo;
import com.turbometa.service.TurboService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/backoffice/turbos")
@PreAuthorize("hasRole('ADMIN')")
public class TurboController {

    private final TurboService turboService;

    public TurboController(TurboService turboService) {
        this.turboService = turboService;
    }

    @GetMapping
    public String listTurbos(@RequestParam(required = false) String query, Model model) {
        List<Turbo> turbos;
        if (query != null && !query.isBlank()) {
            turbos = turboService.search(query);
        } else {
            turbos = turboService.findAll();
        }
        model.addAttribute("turbos", turbos);
        model.addAttribute("query", query);
        return "backoffice/turbos/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("turbo", new Turbo());
        return "backoffice/turbos/form";
    }

    @PostMapping
    public String createTurbo(@RequestParam String ref,
                              @RequestParam String brand,
                              @RequestParam String model,
                              @RequestParam(required = false) String geo,
                              RedirectAttributes redirectAttributes) {
        try {
            Turbo turbo = new Turbo(ref, brand, model, geo);
            turboService.create(turbo);
            redirectAttributes.addFlashAttribute("success", "Turbocharger created successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/backoffice/turbos/new";
        }
        return "redirect:/backoffice/turbos";
    }

    @GetMapping("/edit/{ref}")
    public String showEditForm(@PathVariable String ref, Model model) {
        Turbo turbo = turboService.findByRef(ref);
        model.addAttribute("turbo", turbo);
        return "backoffice/turbos/form";
    }

    @PostMapping("/edit/{ref}")
    public String updateTurbo(@PathVariable String ref,
                              @RequestParam String brand,
                              @RequestParam String model,
                              @RequestParam(required = false) String geo,
                              RedirectAttributes redirectAttributes) {
        try {
            Turbo turbo = turboService.findByRef(ref);
            turbo.setBrand(brand);
            turbo.setModel(model);
            turbo.setGeo(geo);
            turboService.update(turbo);
            redirectAttributes.addFlashAttribute("success", "Turbocharger updated successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/backoffice/turbos/edit/" + ref;
        }
        return "redirect:/backoffice/turbos";
    }

    @PostMapping("/delete/{ref}")
    public String deleteTurbo(@PathVariable String ref, RedirectAttributes redirectAttributes) {
        try {
            turboService.delete(ref);
            redirectAttributes.addFlashAttribute("success", "Turbocharger deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/backoffice/turbos";
    }
}
