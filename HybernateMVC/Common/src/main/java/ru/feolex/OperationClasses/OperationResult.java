package ru.feolex.OperationClasses;


import lombok.EqualsAndHashCode;
import lombok.Getter;

public abstract class OperationResult {

    public abstract OperationEnum Type();

    public static class Success extends OperationResult{

        @Override
        public OperationEnum Type() {
            return OperationEnum.Success;
        }
    }
    @Getter
    public static class SuccessWithMeaning<T> extends OperationResult{
        T value;

        public SuccessWithMeaning(T value) {
            super();
            this.value = value;
        }

        @Override
        public OperationEnum Type() {
            return OperationEnum.SuccessWithMeaning;
        }
    }
    @EqualsAndHashCode(callSuper = false)
    public static class Failure extends OperationResult{
        private final String failureMessage;
        public Failure(String s) {
            super();
            failureMessage = s;
        }

        @Override
        public OperationEnum Type() {
            return OperationEnum.Failure;
        }

        @Override
        public String toString(){
            return failureMessage;
        }
    }
}
