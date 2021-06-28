package io.github.karolmusial.reports;

import io.github.karolmusial.model.event.TaskDone;
import io.github.karolmusial.model.event.TaskEvent;
import io.github.karolmusial.model.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
class ChangedTaskEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    private final PersistedTaskEventRepository repository;

    public ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void on(TaskDone event) {
        onChange(event);
    }

    @Async
    @EventListener
    public void on(TaskUndone event) {
        onChange(event);
    }

    private void onChange(final TaskEvent event) {
        logger.info("Got" + event);
        repository.save(new PersistedTaskEvent(event));
    }
}
