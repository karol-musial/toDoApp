package io.github.karolmusial.model.event;

import io.github.karolmusial.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
    TaskDone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
