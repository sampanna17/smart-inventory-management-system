package com.smartinventorysystem.modules.sale.repository;

import com.smartinventorysystem.enums.SaleStatus;
import com.smartinventorysystem.modules.sale.entity.Sale;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    Optional<Sale> findByInvoiceNumber(String invoiceNumber);

    @EntityGraph(attributePaths = {"customer"})
    @Query("SELECT s FROM Sale s WHERE s.saleID = :id")
    Optional<Sale> findByIdWithCustomer(@Param("id") Integer id);

    @EntityGraph(attributePaths = {"customer"})
    @Query("SELECT s FROM Sale s")
    List<Sale> findAllWithCustomer();

    @EntityGraph(attributePaths = {"customer"})
    @Query("SELECT s FROM Sale s WHERE s.customer.customerID = :customerId")
    List<Sale> findByCustomerWithCustomer(@Param("customerId") Integer customerId);

    @EntityGraph(attributePaths = {"customer"})
    @Query("SELECT s FROM Sale s WHERE s.status = :status")
    List<Sale> findByStatusWithCustomer(@Param("status") SaleStatus status);
}