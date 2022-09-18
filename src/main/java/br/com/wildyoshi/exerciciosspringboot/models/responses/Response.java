package br.com.wildyoshi.exerciciosspringboot.models.responses;

import br.com.wildyoshi.exerciciosspringboot.models.enums.ResponseStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response<T> {
    private T data;
    private String status;
    private String message;
    private String code;

    public Response(ResponseStatusEnum status, T data){
        this.status = status.getDescription();
        this.data = data;
    }

    public Response(ResponseStatusEnum status, T data, String message){
        this.status = status.getDescription();
        this.data = data;
        this.message = message;
    }

    public Response(ResponseStatusEnum status, String message){
        this.status = status.getDescription();
        this.message = message;
    }

    public Response(ResponseStatusEnum status, String message, String code){
        this.status = status.getDescription();
        this.message = message;
        this.code = code;
    }

    public Response(ResponseStatusEnum status, T data, String message, String code){
        this.status = status.getDescription();
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
