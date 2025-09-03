package com.Shopsea.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Shopsea.project.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
