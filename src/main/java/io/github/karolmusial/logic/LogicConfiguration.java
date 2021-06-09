package io.github.karolmusial.logic;

import io.github.karolmusial.TaskConfigurationProperties;
import io.github.karolmusial.model.ProjectRepository;
import io.github.karolmusial.model.TaskGroupRepository;
import io.github.karolmusial.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskGroupService taskGroupService,
            final TaskConfigurationProperties config
    ){
        return new ProjectService(projectRepository, taskGroupRepository, taskGroupService, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            TaskGroupRepository taskGroupRepository,
            TaskRepository taskRepository){
        return new TaskGroupService(
                taskGroupRepository,
                taskRepository) ;
    }
}
