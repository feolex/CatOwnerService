package ru.feolex.ControllerLayer.Exceptions;

public class SyncException extends ControllerException{
    private final String message;
    public SyncException(String string) {
        super(string);

        message = string;
    }

    public String what(){
        return message;
    }
}
