package com.example.cosmetology.error;

import ch.qos.logback.core.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerError implements ErrorController {
    @GetMapping("/error")
    public String error(HttpServletRequest request, HttpServletResponse response, Model model){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            statusCode = response.getStatus();
        }
        model.addAttribute("error", statusCode);
        return "error/error";
    }
}
