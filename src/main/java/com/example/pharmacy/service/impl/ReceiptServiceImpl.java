package com.example.pharmacy.service.impl;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.*;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.mapper.ReceiptMapper;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.ReceiptRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.service.ReceiptService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;
    private final ReceiptMapper receiptMapper;
    @Autowired
    private ReceiptServiceImpl(ReceiptRepository receiptRepository, UserRepository userRepository, MedicineRepository medicineRepository, ReceiptMapper receiptMapper) {
        this.receiptRepository = receiptRepository;
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
        this.receiptMapper = receiptMapper;
    }

    @Override
    public List<ReceiptDto> findAllOpenedReceipts() {
        log.info("Fetching all opened receipts.");
        List<Receipt> openedReceipts = receiptRepository.findAllOpenedReceipts();
        return openedReceipts.stream()
                .map(receiptMapper::toDto)
                .toList();
    }
    @Override
    public void confirmReceipt(int receiptId, int doctorId) throws ServiceException {
        log.info("Confirming receipt with ID {} by doctor with ID {}.", receiptId, doctorId);
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new ServiceException(String.format("Doctor with id %s not found.", doctorId)));
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ServiceException(String.format("Receipt with id %s not found.", receiptId)));
        receipt.setDoctor(doctor);
        receiptRepository.save(receipt);
    }

    @Override
    public boolean isReceiptProvided(int patientId, int medicineId, int quantity){
        log.info("Checking if receipt is provided for patient with ID {}, medicine with ID {}, and quantity {}.", patientId, medicineId, quantity);
        List<Receipt> receipts = receiptRepository.findAllByPatientIdAndMedicineId(patientId, medicineId);
        int sum = receipts.stream()
                .mapToInt(Receipt::getQuantity)
                .sum();
        return sum >= quantity;
    }

    @Override
    public void requestReceipt(int userId, int medicineId, int quantity){
        log.info("Requesting receipt for user with ID {}, medicine with ID {}, and quantity {}.", userId, medicineId, quantity);
        Optional<User> userOptional= userRepository.findById(userId);
        Optional<Medicine> medicineOptional = medicineRepository.findById(medicineId);
        if(userOptional.isPresent() && medicineOptional.isPresent()){
            User user = userOptional.get();
            Medicine medicine = medicineOptional.get();
        receiptRepository.save(new Receipt(medicine, null, user, quantity));}
        else{
            log.warn(String.format("Error by requesting a receipt for user id %s , medicine id %s, quantity %s", userId, medicineId, quantity));
        }
    }

    @Override
    public Optional<Receipt> findReceiptById(int id){
        log.info("Fetching receipt by ID: {}", id);
        return receiptRepository.findById(id);
    }
    @Override
    public List<Receipt> findByPatientIdAndMedicineId(int userId, int medicineId){
        log.info("Fetching receipts for user with ID {} and medicine with ID {}.", userId, medicineId);
        return receiptRepository.findAllByPatientIdAndMedicineId(userId, medicineId);
    }

    @Override
    public void deleteReceiptById(int id){
        log.info("Deleting receipt with ID: {}", id);
        receiptRepository.deleteById(id);
    }

    @Override
    public void updateReceiptQuantity(Receipt receipt, int receiptQuantity){
        log.info("Updating receipt with ID {} quantity to {}.", receipt.getId(), receiptQuantity);
        receipt.setQuantity(receiptQuantity);
        receiptRepository.save(receipt);
    }

}
