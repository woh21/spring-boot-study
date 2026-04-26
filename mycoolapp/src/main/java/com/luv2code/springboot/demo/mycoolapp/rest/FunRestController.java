package com.luv2code.springboot.demo.mycoolapp.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class FunRestController {
    // inject properties for: coach.name and team.name

    @Value("${coach.name}")
    private String coachName;

    @Value("${team.name}")
    private String teamName;

    //expose new endpoint for "teaminfo"
    @GetMapping("/teaminfo")
    public String getTeamInfo() {
        return "Coach: " + coachName + ", Team: " + teamName;
    }
    // expose "/" that return "Hello World"

    @GetMapping("/")
    public String sayHello() {
        return "Hello World!";
    }
}
