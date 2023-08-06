package com.example.pharmacy.service;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;

import java.util.List;

public interface MedicineService {
    List<Medicine> findAll() throws ServiceException;

    void addMedicine(Medicine medicine);

    void deleteMedicine(int id);

    Integer createOrder(User user);

    void addMedicineToOrder(Integer orderId, int medicineId, int quantity);
}
