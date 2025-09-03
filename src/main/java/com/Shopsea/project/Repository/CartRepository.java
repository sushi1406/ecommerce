package com.Shopsea.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Shopsea.project.Entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	List<Cart> findByUserId(Long userId);
}
