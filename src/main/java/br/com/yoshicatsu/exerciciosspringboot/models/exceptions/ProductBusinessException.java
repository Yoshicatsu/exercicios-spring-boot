package br.com.yoshicatsu.exerciciosspringboot.models.exceptions;

public class ProductBusinessException extends Exception{

    private static final long serialVersionUID = -7699571666838976926L;

    public ProductBusinessException(String message) {
        super(message);
    }

    public ProductBusinessException(String message, Throwable cause){
        super(message, cause);
    }
}
