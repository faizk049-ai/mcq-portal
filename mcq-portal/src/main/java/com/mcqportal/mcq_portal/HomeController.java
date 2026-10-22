package com.mcqportal.mcq_portal;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {


    public String home() {
        return "redirect:/index.html";
    }
}