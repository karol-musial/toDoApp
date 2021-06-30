package io.github.karolmusial.controller;

import io.github.karolmusial.logic.ProjectService;
import io.github.karolmusial.model.Project;
import io.github.karolmusial.model.ProjectStep;
import io.github.karolmusial.model.projection.ProjectWriteModel;
import io.micrometer.core.annotation.Timed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
//@PreAuthorize("hasRole('!ROLE_ADMIN') or hasRole('ROLE_USER')")
@Controller
@RequestMapping("/projects")
class ProjectController {
    private final ProjectService service;

    ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    String showProjects(Model model, Authentication auth) {
//        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("project", new ProjectWriteModel());
            return "projects";
//        }
//        return "index";
    }

    @PostMapping
    String addProject(
            @ModelAttribute("project") @Valid ProjectWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "projects";
        }
        service.save(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("projects", getProject());
        model.addAttribute("message", "Dodano projekt!");
        return "projects";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current) {
        current.getSteps().add(new ProjectStep());
        return "projects";
    }

    @Timed(value = "project.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("project") ProjectWriteModel current,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ) {
        try {
            service.createGroup(deadline, id);
            model.addAttribute("message", "Dodano grupę");
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("message", "Błąd podczas tworzenia grupy");
        }
        return "projects";
    }

    @PostMapping(params = "removeStep")
    String removeStep(@ModelAttribute("project") ProjectWriteModel current) {
        int id = current.getSteps().size()-1;
        current.getSteps().remove(id);
        return "projects";
    }

    @ModelAttribute("projects")
    List<Project> getProject() {
        return service.readAll();
    }
}