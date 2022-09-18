package br.com.yoshicatsu.exerciciosspringboot.services;

import br.com.yoshicatsu.exerciciosspringboot.models.entities.Product;
import br.com.yoshicatsu.exerciciosspringboot.models.exceptions.ProductBusinessException;
import br.com.yoshicatsu.exerciciosspringboot.models.exceptions.UnexpectedDatabaseException;
import br.com.yoshicatsu.exerciciosspringboot.models.requests.ProductUpdateRequest;
import br.com.yoshicatsu.exerciciosspringboot.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void register(String name, Double price, Double discount, Integer availableQuantity) throws ProductBusinessException {
        Product product = new Product();
        product.setRegistrationDate(new Date());
        product.setName(name);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setAvailableQuantity(availableQuantity);
        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Exceção: " + e.getMessage() + " Causada por " + e.getCause());
            throw new ProductBusinessException("Erro ao registrar novo produto: " + name, e);
        } catch (Exception e) {
            System.out.println("Exceção Inesperada! " + e);
            throw new UnexpectedDatabaseException("Falha na operação", e);
        }
    }

    public List<Product> getAll() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            System.out.println("Exceção Inesperada! " + e);
            throw new UnexpectedDatabaseException("Falha na operação", e);
        }
    }

    public Page<Product> searchByName(String name, Pageable pageable) throws ProductBusinessException {
        try {
            Page<Product> product = productRepository.searchByNameLike(name, pageable);

            if (Objects.isNull(product)) {
                throw new ProductBusinessException("Produto não está cadastrado");
            }
            return product;
        } catch (ProductBusinessException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Exceção Inesperada! " + e);
            throw new UnexpectedDatabaseException("Falha na operação", e);
        }
    }

    public Page<Product> searchByDates(Date dateInitial, Date dateFinal, Pageable pageable) throws ProductBusinessException {
        try {
            Page<Product> products = productRepository.findByRegistrationDateGreaterThanEqualAndRegistrationDateLessThanEqual(dateInitial, dateFinal, pageable);

            if (Objects.isNull(products)) {
                throw new ProductBusinessException("Produto não está cadastrado");
            }
            return products;
        } catch (ProductBusinessException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Exceção Inesperada! " + e);
            throw new UnexpectedDatabaseException("Falha na operação", e);
        }
    }

    public Product getById(BigInteger id) throws ProductBusinessException {
        try {
            Optional<Product> product = productRepository.findById(id);

            if (product.isEmpty()) {
                throw new ProductBusinessException("Produto não está cadastrado");
            }
            return product.get();
        } catch (ProductBusinessException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Exceção Inesperada! " + e);
            throw new UnexpectedDatabaseException("Falha na operação", e);
        }
    }

    public Product alterById(ProductUpdateRequest request) throws ProductBusinessException {
        try {
            Optional<Product> optProduct = productRepository.findById(request.getId());
            if (optProduct.isEmpty()) {
                throw new ProductBusinessException("Produto não está cadastrado");
            }
            Product product = optProduct.get();
            product.setName(request.getName());
            product.setDiscount(request.getDiscount());
            product.setPrice(request.getPrice());
            product.setAvailableQuantity(request.getAvailableQuantity());
            productRepository.save(product);
            return product;
        } catch (ProductBusinessException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Exceção Inesperada! " + e);
            throw new UnexpectedDatabaseException("Falha na operação", e);
        }
    }

    public void deleteById(BigInteger id) throws ProductBusinessException {
        try {
            Optional<Product> optProduct = productRepository.findById(id);
            if (optProduct.isEmpty()) {
                throw new ProductBusinessException("Produto não está cadastrado");
            }
            productRepository.deleteById(id);
        } catch (ProductBusinessException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Exceção Inesperada! " + e);
            throw new UnexpectedDatabaseException("Falha na operação", e);
        }
    }


}
