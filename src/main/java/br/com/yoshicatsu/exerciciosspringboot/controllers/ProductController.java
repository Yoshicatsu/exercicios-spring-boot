package br.com.yoshicatsu.exerciciosspringboot.controllers;

import br.com.yoshicatsu.exerciciosspringboot.models.entities.Product;
import br.com.yoshicatsu.exerciciosspringboot.models.enums.ResponseStatusEnum;
import br.com.yoshicatsu.exerciciosspringboot.models.exceptions.ProductBusinessException;
import br.com.yoshicatsu.exerciciosspringboot.models.exceptions.UnexpectedDatabaseException;
import br.com.yoshicatsu.exerciciosspringboot.models.requests.ProductRegisterRequest;
import br.com.yoshicatsu.exerciciosspringboot.models.requests.ProductUpdateRequest;
import br.com.yoshicatsu.exerciciosspringboot.models.responses.Response;
import br.com.yoshicatsu.exerciciosspringboot.models.utils.DateUtils;
import br.com.yoshicatsu.exerciciosspringboot.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/register")
    public @ResponseBody String register(@Valid @RequestBody ProductRegisterRequest request) {
        try {
            productService.register(request.getName(), request.getPrice(), request.getDiscount(), request.getAvailableQuantity());
        } catch (ProductBusinessException e) {
            return "Falha no registro! " + e.getMessage();
        } catch (UnexpectedDatabaseException e) {
            return e.getMessage();
        }
        return "Produto registrado!";
    }

    @GetMapping("/get-all")
    public @ResponseBody List<Product> getAll() {
        try {
            return productService.getAll();
        } catch (UnexpectedDatabaseException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Response<Page<Product>>> search(@RequestParam String name,
                                                          @RequestParam Integer page) {
        try {
            if(page < 0){
                throw new ProductBusinessException("A página não deve ser menor que 0");
            }
            Pageable pageable = PageRequest.of(page,5);
            Page<Product> products = productService.searchByName(name, pageable);
            return ResponseEntity.ok(new Response<>(ResponseStatusEnum.SUCCESS, products, "Produto encontrado com sucesso", "OK"));
        } catch (ProductBusinessException e) {
            Response<Page<Product>> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, "Falha ao procurar produto: " + e.getMessage(), "P000");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (UnexpectedDatabaseException e) {
            Response<Page<Product>> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, e.getMessage(), "P001");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/search-by-dates")
    public ResponseEntity<Response<Page<Product>>> search(
            @RequestParam(value = "date_initial") String dateInitial, @RequestParam(value = "date_final") String dateFinal, @RequestParam(value = "page") Integer page) {
        try {
            if(page < 0){
                throw new ProductBusinessException("A página não deve ser menor que 0");
            }
            Pageable pageable = PageRequest.of(page,5);
            Page<Product> products = productService.searchByDates(DateUtils.getDateMidnight(DateUtils.stringToDate(dateInitial)), DateUtils.getDateLastTime(DateUtils.stringToDate(dateFinal)), pageable);
            return ResponseEntity.ok(new Response<>(ResponseStatusEnum.SUCCESS, products, "Produto encontrado com sucesso", "OK"));
        } catch (ProductBusinessException e) {
            Response<Page<Product>> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, "Falha ao procurar produto: " + e.getMessage(), "P000");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (UnexpectedDatabaseException e) {
            Response<Page<Product>> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, e.getMessage(), "P001");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> getById(@PathVariable BigInteger id) {
        try {
            Product product = productService.getById(id);
            return ResponseEntity.ok(new Response<>(ResponseStatusEnum.SUCCESS, product, "Produto encontrado com sucesso", "OK"));
        } catch (ProductBusinessException e) {
            Response<Product> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, "Falha ao procurar produto: " + e.getMessage(), "P002");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (UnexpectedDatabaseException e) {
            Response<Product> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, e.getMessage(), "P003");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> alterById(@Valid @RequestBody ProductUpdateRequest request) {
        try {
            Product product = productService.alterById(request);
            return ResponseEntity.ok(new Response<>(ResponseStatusEnum.SUCCESS, product, "Produto alterado com sucesso", "OK"));
        } catch (ProductBusinessException e) {
            Response<Product> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, "Falha ao procurar produto: " + e.getMessage(), "P003");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (UnexpectedDatabaseException e) {
            Response<Product> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, e.getMessage(), "P004");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Product>> deleteById(@PathVariable BigInteger id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.ok(new Response<>(ResponseStatusEnum.SUCCESS, "Produto deletado com sucesso", "OK"));
        } catch (ProductBusinessException e) {
            Response<Product> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, "Falha ao procurar produto: " + e.getMessage(), "P005");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (UnexpectedDatabaseException e) {
            Response<Product> errorResponse = new Response<>(ResponseStatusEnum.ERROR, null, e.getMessage(), "P006");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }


}
