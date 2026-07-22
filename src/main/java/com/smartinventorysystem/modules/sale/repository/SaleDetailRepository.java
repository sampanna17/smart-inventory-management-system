package com.smartinventorysystem.modules.sale.repository;

import com.smartinventorysystem.modules.sale.entity.SaleDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {

    @EntityGraph(attributePaths = {"product"})
    @Query("SELECT sd FROM SaleDetail sd WHERE sd.sale.saleID = :saleId")
    List<SaleDetail> findBySaleIdWithProduct(@Param("saleId") Integer saleId);

    @Modifying
    @Query("DELETE FROM SaleDetail sd WHERE sd.sale.saleID = :saleId")
    void deleteBySaleId(@Param("saleId") Integer saleId);
}
