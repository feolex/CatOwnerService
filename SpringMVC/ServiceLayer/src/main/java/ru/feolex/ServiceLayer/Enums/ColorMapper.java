package ru.feolex.ServiceLayer.Enums;

import ru.feolex.DaoLayer.Entities.CatColor;

import java.util.Objects;

public class ColorMapper {
    public static CatColor MapColor(Color color) {
        if (color == null) {
            return null;
        }
        switch (color) {
            case WHITE -> {
                return new CatColor(1L, "WHITE");
            }
            case GREY -> {
                return new CatColor(2L, "GREY");
            }
            case RED -> {
                return new CatColor(3L, "RED");
            }
            case ANOTHER -> {
                return new CatColor(4L, "ANOTHER");
            }
            default -> {
                return null;
            }
        }
    }

    public static Color MapColorId(Long id) {
        if (id == 1) {
            return Color.WHITE;
        } else if (id == 2) {
            return Color.GREY;
        } else if (id == 3) {
            return Color.RED;
        }
        return Color.ANOTHER;
    }

    public static Color MapColor(String colorName) {
        if (Objects.equals(colorName, "WHITE")) {
            return Color.WHITE;
        } else if (Objects.equals(colorName, "GREY")) {
            return Color.GREY;
        } else if (Objects.equals(colorName, "RED")) {
            return Color.RED;
        }
        return Color.ANOTHER;
    }

    public static String PrintColorName(Color color) {
        switch (color) {
            case WHITE -> {
                return "WHITE";
            }
            case GREY -> {
                return "GREY";
            }
            case RED -> {
                return "RED";
            }
            case ANOTHER -> {
                return "ANOTHER";
            }
        }
        return "NULL";
    }
}
