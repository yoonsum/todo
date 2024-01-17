package com.example.todoapp.domain;

public enum LabelColor {
    WHITE("#ffffff"),
    RED("#ff0000"),
    TANGERINE("#f98603"),
    MUSTARD("#fed657"),
    YELLOWGREEN("#abc853"),
    OFFICEGREEN("#05840a"),
    LIGHTSKYBLUE("#9dd1fb"),
    CHRYSIERBLUE("#0000e0"),
    PINKLAVENDER("#deb6e2"),
    LAVENDER("#b278ce"),
    SILVER("#b8b8b8"),
    BLACK("#000000");

    private final String colorValue;

    LabelColor(String color) {
        this.colorValue = color;
    }

    public String getHexCode() {
        return colorValue;
    }
}
