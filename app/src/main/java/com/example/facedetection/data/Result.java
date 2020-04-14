package com.example.facedetection.data;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result<T> {

    // hide the private constructor to limit subclass types (Success, Error)
    private Result() {
    }

    public boolean isSuccess() {
        if (this instanceof Result.Success) {
            return true;
        } else if (this instanceof Result.Error) {
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return success.getData().toString();
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error) this;
            return error.getError().getMessage();
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends Result {
        private T data;
        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error<T> extends Result {
        private Exception error;
        private T data;
        public Error(T data) {
            this.data = data;
        }

        public Error(Exception error) {
            this.error = error;
        }
        public T getData() {
            return this.data;
        }

        public Exception getError() {
            return this.error;
        }
    }
}
