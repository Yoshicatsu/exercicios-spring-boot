package br.com.yoshicatsu.exerciciosspringboot.models.exceptions;

public class UnexpectedDatabaseException extends RuntimeException{

    private static final long serialVersionUID = 4449945631555954783L;

    public UnexpectedDatabaseException(String message) {
        super(message);
    }

    public UnexpectedDatabaseException(String message, Throwable cause){
        super(message, cause);
    }
}
