package io.github.karolmusial.logic;

import io.github.karolmusial.model.TaskGroup;
import io.github.karolmusial.model.TaskGroupRepository;
import io.github.karolmusial.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when group has undone tasks")
    void shouldToggleGroup_withUndoneTasks_throwsIllegalStateException() {
        // given
        var mockTaskRepository = taskRepositoryReturning(true);
        // system under test
        var result = new TaskGroupService(null, mockTaskRepository);
        // when
        var exception = catchThrowable(() -> result.toggleGroup(0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has undone tasks");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when group doesn't exist")
    void shouldToggleGroup_withNoExistingTaskGroup_throwsIllegalArgumentException() {
        var mockTaskRepository = taskRepositoryReturning(false);
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        // system under test
        var result = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        // when
        var exception = catchThrowable(() -> result.toggleGroup(0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should toggle group")
    void shouldToggleGroup_workedAsExpected() {
        var mockTaskRepository = taskRepositoryReturning(false);
        // and
        var mockTaskGroup = new TaskGroup();
        var beforeToggle = mockTaskGroup.isDone();
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(mockTaskGroup));
        // system under test
        var result = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        // when
        result.toggleGroup(0);
        // then
        assertThat(mockTaskGroup.isDone()).isEqualTo(!beforeToggle);
    }
    private TaskRepository taskRepositoryReturning(boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }
}