package com.turbometa.controller;

import com.turbometa.model.User;
import com.turbometa.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/backoffice/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(@RequestParam(required = false) String query, Model model) {
        List<User> users;
        if (query != null && !query.isBlank()) {
            users = userService.search(query);
        } else {
            users = userService.findAll();
        }
        model.addAttribute("users", users);
        model.addAttribute("query", query);
        return "backoffice/users/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "backoffice/users/form";
    }

    @PostMapping
    public String createUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam(required = false) boolean isAdmin,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.create(username, email, password, isAdmin);
            redirectAttributes.addFlashAttribute("success", "User created successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/backoffice/users/new";
        }
        return "redirect:/backoffice/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "backoffice/users/form";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam(required = false) String password,
                             @RequestParam(required = false) boolean isActive,
                             @RequestParam(required = false) boolean isAdmin,
                             RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findById(id);
            user.setUsername(username);
            user.setEmail(email);
            user.setActive(isActive);
            user.setAdmin(isAdmin);
            userService.update(user, password);
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/backoffice/users/edit/" + id;
        }
        return "redirect:/backoffice/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/backoffice/users";
    }

    @PostMapping("/toggle/{id}")
    public String toggleUserActive(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.toggleActive(id);
            redirectAttributes.addFlashAttribute("success", "User status updated");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/backoffice/users";
    }
}
