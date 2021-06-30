package io.github.karolmusial.controller;

import io.github.karolmusial.TaskConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/info")
public class InfoController {

    private DataSourceProperties datasource;
    private TaskConfigurationProperties myProp;

    public InfoController(DataSourceProperties datasource, TaskConfigurationProperties myProp) {
        this.datasource = datasource;
        this.myProp = myProp;
    }

//    @Secured("ROLE_ADMIN")
    @GetMapping("/url")
    String url(){
        return datasource.getUrl();
    }

//    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/prop")
    boolean myProp(){
        return myProp.getTemplate().isAllowMultipleTasks();
    }
}
