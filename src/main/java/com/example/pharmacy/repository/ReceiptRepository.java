package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    @Query("SELECT r FROM Receipt r WHERE r.doctor IS NULL")
    List<Receipt> findAllOpenedReceipts();

//    @Query("SELECT SUM(r.quantity) FROM Receipt r WHERE r.medicine.id = :medicineId AND r.patient.id = :patientId AND r.doctor IS NOT NULL")
//    Integer sumQuantityByMedicineIdAndPatientId(@Param("medicineId") int medicineId, @Param("patientId") int patientId);
@Query("SELECT r FROM Receipt r WHERE r.patient.id = :patientId AND r.medicine.id = :medicineId AND r.doctor.id IS NOT NULL")
List<Receipt> findAllByPatientIdAndMedicineId(@Param("patientId") int patientId, @Param("medicineId") int medicineId);

}
