package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.Enums;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")
public class CuisineOptionsController {

    @GetMapping("/cuisineOptions")
    public String getCuisineOptions() {
        Enums.Cuisine[] cuisines = Enums.Cuisine.values();

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < cuisines.length; i++) {
            jsonBuilder.append("\"").append(cuisines[i].toString()).append("\"");
            if (i < cuisines.length - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }
}
