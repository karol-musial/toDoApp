package io.github.karolmusial.controller;

import io.github.karolmusial.logic.TaskGroupService;
import io.github.karolmusial.model.Task;
import io.github.karolmusial.model.TaskGroup;
import io.github.karolmusial.model.TaskGroupRepository;
import io.github.karolmusial.model.TaskRepository;
import io.github.karolmusial.model.projection.GroupReadModel;
import io.github.karolmusial.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskRepository repository;
    private final TaskGroupService service;

    TaskGroupController(final TaskRepository repository, TaskGroupService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(service.readAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate){
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Task> toggleUpdateTaskGroup(@PathVariable int id){
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
