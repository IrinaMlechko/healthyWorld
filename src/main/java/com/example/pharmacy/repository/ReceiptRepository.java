package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    @Query("SELECT r FROM Receipt r WHERE r.doctor IS NULL")
    List<Receipt> findAllOpenedReceipts();
}
