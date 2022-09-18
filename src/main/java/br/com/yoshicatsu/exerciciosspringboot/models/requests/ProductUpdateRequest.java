package br.com.yoshicatsu.exerciciosspringboot.models.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductUpdateRequest {
    @NotNull(message = "Favor informar o identificador do produto")
    private BigInteger id;
    @NotBlank(message = "Favor informar o nome do produto")
    private String name;
    @NotNull(message = "Favor informar o preço do produto")
    @Min(value = 0, message = "O preço está abaixo do mínimo")
    @Max(value = 99999999, message = "O preço está acima do máximo")
    private Double price;
    @NotNull(message = "Favor informar o desconto do produto")
    @Min(value = 0, message = "O desconto está abaixo do mínimo")
    @Max(value = 1, message = "O desconto está acima do máximo")
    private Double discount;
    @NotNull(message = "Favor informar a quantidade disponível do produto")
    @Min(value = 0, message = "A quantidade disponível está abaixo do valor mínimo")
    private Integer availableQuantity;
}
