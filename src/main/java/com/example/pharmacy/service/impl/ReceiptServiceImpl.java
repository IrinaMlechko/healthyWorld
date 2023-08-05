package com.example.pharmacy.service.impl;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.mapper.ReceiptMapper;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.ReceiptRepository;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.ReceiptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    static Logger logger = LogManager.getLogger();
    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;
    @Autowired
    private ReceiptServiceImpl(ReceiptRepository receiptRepository, ReceiptMapper receiptMapper) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
    }

    @Override
    public List<ReceiptDto> findAllOpenedReceipts() {
        logger.info("Get all opened receipts");
        List<Receipt> openedReceipts = receiptRepository.findAllOpenedReceipts();
        return openedReceipts.stream()
                .map(receiptMapper::toDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public void addMedicine(Medicine medicine) {
//        logger.info("Add medicine: " + medicine.getMedicineName());
//        medicineRepository.save(medicine);
//    }
//
//    @Override
//    public void deleteMedicine(int id) {
//        logger.info("Delete medicine with id: " + id);
//        medicineRepository.deleteById(id);
//    }

}
