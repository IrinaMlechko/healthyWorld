package com.example.pharmacy.service;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    List<Medicine> findAll() throws ServiceException;

    Optional<Medicine> findById(int id);

    void addMedicine(Medicine medicine);

    void deleteMedicine(int id);

    Integer createOrder(User user);

    void addMedicineToOrder(Integer orderId, int medicineId, int quantity, int userId);

    void refreshReceiptsStatus(int receiptId);
}
