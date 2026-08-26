/*
 * Copyright (c) 2026 Sanjay Ghosh
 *
 * Chapter 5 – Cross-Site Scripting
 * Section 5.6 – Cross-Site Scripting in Java
 *
 * Author: Sanjay Ghosh
 *
 * Description:
 * This Spring Boot application demonstrates the difference between
 * vulnerable and secure rendering of user-supplied content using
 * Thymeleaf.
 *
 * The vulnerable implementation uses th:utext, which renders content
 * as unescaped HTML.
 *
 * The secure implementation uses th:text, which escapes the content
 * before rendering it.
 *
 * This application is intentionally designed for security education
 * and should only be run in a controlled local development environment.
 *
 * Security Warning:
 * Do not deploy the vulnerable implementation to a production
 * environment or expose it to untrusted users.
 */

package com.securitybook.xss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class XssApplication {

    public static void main(String[] args) {
        SpringApplication.run(XssApplication.class, args);
    }
}

@Controller
class XssController {

    @GetMapping("/")
    public String home() {
        return "vulnerable";
    }

    @GetMapping("/vulnerable")
    public String vulnerable(
            @RequestParam(
                    name = "input",
                    defaultValue = "Hello from the user")
            String input,
            Model model) {

        model.addAttribute("userInput", input);

        return "vulnerable";
    }

    @GetMapping("/secure")
    public String secure(
            @RequestParam(
                    name = "input",
                    defaultValue = "Hello from the user")
            String input,
            Model model) {

        model.addAttribute("userInput", input);

        return "secure";
    }
}
