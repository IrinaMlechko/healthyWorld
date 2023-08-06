package com.example.pharmacy.service;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.exception.ServiceException;

import java.util.List;

public interface ReceiptService {
    List<ReceiptDto> findAllOpenedReceipts() throws ServiceException;

    void confirmReceipt(int receiptId, int doctorId) throws ServiceException;

    boolean isReceiptProvided(int patientId, int medicineId, int quantity);
}
