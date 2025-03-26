package ru.feolex.ServiceLayer.Enums;

import ru.feolex.DaoLayer.Entities.CatColor;

public class ColorMapper {
    public static CatColor MapColor(Color color){
       switch (color){
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
               return new CatColor(3L, "ANOTHER");
           }
       }
       return null;
    }
    public static Color MapColorId(Long id){
        if (id == 1) {
            return Color.WHITE;
        } else if (id == 2) {
            return Color.GREY;
        } else if (id == 3) {
            return Color.RED;
        }
        return Color.ANOTHER;
    }

    public static String PrintColorName(Color color) {
        switch (color){
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
