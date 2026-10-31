package com.turbometa.controller;

import com.turbometa.model.Turbo;
import com.turbometa.service.TurboService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/frontoffice")
public class FrontofficeController {

    private final TurboService turboService;

    public FrontofficeController(TurboService turboService) {
        this.turboService = turboService;
    }

    @GetMapping("/search")
    public String searchTurbos(@RequestParam(required = false) String query, Model model) {
        List<Turbo> turbos;
        if (query != null && !query.isBlank()) {
            turbos = turboService.search(query);
        } else {
            turbos = turboService.findAll();
        }
        model.addAttribute("turbos", turbos);
        model.addAttribute("query", query);
        return "frontoffice/search";
    }

    @GetMapping
    public String redirectSearch() {
        return "redirect:/frontoffice/search";
    }
}
