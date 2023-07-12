package com.kenzie.appserver.repositories.model;
//for merge
public class Enums {

    public enum Cuisine {
        ITALIAN("Italian"),
        MEXICAN("Mexican"),
        INDIAN("Indian"),
        FRENCH("French"),
        JAPANESE("Japanese"),
        AMERICAN("American"),
        ASIAN("Asian"),
        THAI("Thai"),
        CHINESE("Chinese");

        private final String displayName;

        Cuisine(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }

        // Add more cuisine types as needed
    }
    public enum DietaryRestrictions {
        VEGAN("Vegan"),
        VEGETARIAN("Vegetarian"),
        GLUTEN_FREE("Gluten Free"),
        DAIRY_FREE("Dairy Free"),
        NUT_FREE("Nut Free");

        private final String displayName;
        DietaryRestrictions(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
        // Add more dietary restrictions as needed
    }

    public enum QuantityType {
        GRAMS("Gram(s)"),
        KILOGRAMS("Kilogram(s)"),
        MILLILITERS("Milliliter(s)"),
        LITERS("Liter(s)"),
        TEASPOONS("Teaspoon(s)"),
        TABLESPOONS("Tablespoon(s)"),
        CUPS("Cup(s)"),
        PIECES("Piece(s)"),
        PACKS("Pack(s)"),
        POUNDS("Pound(s)"),
        OZ("Oz"),
        CLOVES("Clove(s)");

        private final String displayName;
        QuantityType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }

}
