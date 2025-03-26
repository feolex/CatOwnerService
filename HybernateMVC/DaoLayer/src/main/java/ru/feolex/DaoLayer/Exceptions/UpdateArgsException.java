package ru.feolex.DaoLayer.Exceptions;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UpdateArgsException extends DaoException {
    private String message;
    private List<String> invalid_params;

    public String what(){
        return message + invalid_params.toString();
    }
}
