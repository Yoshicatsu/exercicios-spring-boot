package br.com.yoshicatsu.exerciciosspringboot.controllers;

import br.com.yoshicatsu.exerciciosspringboot.services.CalculatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calc")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/sum/{a}/{b}")
    public Integer sum(@PathVariable Integer a, @PathVariable Integer b) {
        return calculatorService.sum(a, b);
    }

    @GetMapping("/subtract")
    public Integer subtract(@RequestParam Integer a,
                            @RequestParam Integer b) {
        return calculatorService.subtract(a, b);
    }

    @GetMapping("/multiply")
    public Integer multiply(@RequestParam Integer a,
                            @RequestParam Integer b) {
        return calculatorService.multiply(a, b);
    }

    @GetMapping("/divide")
    public Double divide(@RequestParam Integer a,
                            @RequestParam Integer b) {
        return calculatorService.divide(a, b);
    }

}
