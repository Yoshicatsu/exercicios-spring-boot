package br.com.yoshicatsu.exerciciosspringboot.services;


import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public Integer sum(Integer a, Integer b){
        return a+b;
    }

    public Integer subtract(Integer a, Integer b){
        return a-b;
    }

    public Integer multiply(Integer a, Integer b){
        return a*b;
    }

    public Double divide(Integer a, Integer b){
        return (Double.valueOf(a)/Double.valueOf(b));
    }
}
