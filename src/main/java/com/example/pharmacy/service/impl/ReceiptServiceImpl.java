package com.example.pharmacy.service.impl;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.mapper.ReceiptMapper;
import com.example.pharmacy.repository.ReceiptRepository;
import com.example.pharmacy.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ReceiptMapper receiptMapper;
    @Autowired
    private ReceiptServiceImpl(ReceiptRepository receiptRepository, UserRepository userRepository, ReceiptMapper receiptMapper) {
        this.receiptRepository = receiptRepository;
        this.userRepository = userRepository;
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

    @Override
    public void confirmReceipt(int receiptId, int doctorId) throws ServiceException {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new ServiceException(String.format("Doctor with id %s not found.", doctorId)));
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ServiceException(String.format("Receipt with id %s not found.", receiptId)));
        receipt.setDoctor(doctor);
        receiptRepository.save(receipt);
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
