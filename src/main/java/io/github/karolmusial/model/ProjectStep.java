package io.github.karolmusial.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectStep {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @NotBlank(message = "Project steps description must not be empty")
    private String description;
    private int daysToDeadline;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
