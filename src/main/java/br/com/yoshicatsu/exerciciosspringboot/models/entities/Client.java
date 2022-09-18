package br.com.yoshicatsu.exerciciosspringboot.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private Integer id;
    private String name;
    private String cpf;
}
