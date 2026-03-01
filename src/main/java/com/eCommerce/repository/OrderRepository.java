package com.eCommerce.repository;

import com.eCommerce.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
    Double getTotalRevenue();

    // Find all orders by email
    List<Order> findByEmail(String email);

    // Find orders by email with pagination
    Page<Order> findByEmail(String email, Pageable pageable);
}
