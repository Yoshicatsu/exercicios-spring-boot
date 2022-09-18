package br.com.yoshicatsu.exerciciosspringboot.controllers;

import br.com.yoshicatsu.exerciciosspringboot.models.entities.Client;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @GetMapping("/any")
    public Client getClient(){
        return new Client(124, "Pedro", "012.345.678-90");
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable int id){
        return new Client(id, "Ana", "021.234.293-00");
    }

    @GetMapping("/")
    public Client getClient2(@RequestParam(name = "id") int id){
        return new Client(id, "Bia", "374.525.643-89");
    }
}
