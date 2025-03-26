package ru.feolex.OperationClasses;

public class OperationResultReader {
    public static <T> T read(OperationResult.SuccessWithMeaning<T> result){
        return result.value;
    }
    public static String read(OperationResult.Failure result){
        return result.toString();
    }

    public static boolean read(OperationResult.Success result){
        return true;
    }
}
