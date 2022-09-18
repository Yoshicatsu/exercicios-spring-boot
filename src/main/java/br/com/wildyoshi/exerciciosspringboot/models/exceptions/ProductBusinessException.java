package br.com.wildyoshi.exerciciosspringboot.models.exceptions;

import java.io.Serial;

public class ProductBusinessException extends Exception{

    @Serial
    private static final long serialVersionUID = -7699571666838976926L;

    public ProductBusinessException(String message) {
        super(message);
    }

    public ProductBusinessException(String message, Throwable cause){
        super(message, cause);
    }
}
