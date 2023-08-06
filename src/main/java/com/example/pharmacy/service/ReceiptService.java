package com.example.pharmacy.service;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ReceiptService {
    List<ReceiptDto> findAllOpenedReceipts() throws ServiceException;

    void confirmReceipt(int receiptId, int doctorId) throws ServiceException;

    boolean isReceiptProvided(int patientId, int medicineId, int quantity);

    void requestReceipt(int userId, int medicineId, int quantity);

    Optional<Receipt> findReceiptById(int id);
}
