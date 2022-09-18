package br.com.wildyoshi.exerciciosspringboot.repositories;

import br.com.wildyoshi.exerciciosspringboot.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Date;


public interface ProductRepository extends JpaRepository<Product, BigInteger> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> searchByNameLike(String name, Pageable pageable);
    //Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable); //Funciona igual
    Page<Product> findByRegistrationDateGreaterThanEqualAndRegistrationDateLessThanEqual(Date dateInitial, Date dateFinal, Pageable pageable);
}
