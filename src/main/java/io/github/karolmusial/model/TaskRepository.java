package io.github.karolmusial.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(Integer id);
    Task save(Task entity);
    Page<Task> findAll(Pageable page);
    List<Task> findByDone(boolean done);
    boolean existsById(Integer id);
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
    List<Task> findAllByGroup_Id(Integer groupId);
}
