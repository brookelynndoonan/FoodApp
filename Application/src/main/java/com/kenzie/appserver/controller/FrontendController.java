package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.Enums;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")
//63342
public class FrontendController {

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

    @GetMapping("/dietaryRestrictionOptions")
    public String getDietaryRestrictionOptions() {
        Enums.DietaryRestrictions[] dietaryRestrictions = Enums.DietaryRestrictions.values();

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < dietaryRestrictions.length; i++) {
            jsonBuilder.append("\"").append(dietaryRestrictions[i].toString()).append("\"");
            if (i < dietaryRestrictions.length - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }

    @GetMapping("/quantityTypeOptions")
    public String getQuantityTypeOptions() {
        Enums.QuantityType[] quantityTypes = Enums.QuantityType.values();

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < quantityTypes.length; i++) {
            jsonBuilder.append("\"").append(quantityTypes[i].toString()).append("\"");
            if (i < quantityTypes.length - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }
}
