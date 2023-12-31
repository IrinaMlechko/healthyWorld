package com.example.pharmacy.service.impl;

import com.example.pharmacy.entity.*;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.service.ReceiptService;
import com.example.pharmacy.util.ReceiptStatus;
import com.example.pharmacy.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;
    private final OrderMedicineRepository orderMedicineRepository;
    private final ReceiptService receiptService;


    private MedicineServiceImpl(MedicineRepository medicineRepository, OrderRepository orderRepository, OrderMedicineRepository orderMedicineRepository, ReceiptService receiptService) {
        this.medicineRepository = medicineRepository;
        this.orderRepository = orderRepository;
        this.orderMedicineRepository = orderMedicineRepository;
        this.receiptService = receiptService;
    }

    @Override
    public List<Medicine> findAll() {
        log.info("Get all medicines");
        return medicineRepository.findAll();
    }

    @Override
    public Optional<Medicine> findById(int id) {
        log.info("Get medicine by id: "+ id);
        return medicineRepository.findById(id);
    }

    @Override
    public Integer createOrder(User user) {
        log.info("Creating order for user with ID: {}", user.getId());
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.NEW);
        order = orderRepository.save(order);
        return order.getId();
    }
    @Override
    public void addMedicineToOrder(Integer orderId, int medicineId, int quantity, int userId) {
        log.info("Adding medicine with ID {} to order with ID {} for user with ID {} and quantity {}.", medicineId, orderId, userId, quantity);
        medicineRepository.findById(medicineId).ifPresent(medicine -> {
            orderRepository.findById(orderId).ifPresent(order -> {
                OrderMedicineId orderMedicineId = new OrderMedicineId(orderId, medicineId);
                OrderMedicine orderMedicine = orderMedicineRepository.findById(orderMedicineId)
                        .orElseGet(() -> new OrderMedicine(order, medicine, 0, null));
                orderMedicine.setQuantity(orderMedicine.getQuantity() + quantity);
                order.addOrderMedicine(orderMedicine);
                ReceiptStatus receiptStatus = obtainReceiptStatus(userId, medicineId, orderMedicine.getQuantity());
                orderMedicine.setReceiptStatus(receiptStatus);
                if(receiptStatus.equals(ReceiptStatus.RECEIPT_NEEDED)){
                    receiptService.requestReceipt(userId, medicineId, quantity);
                }
                orderRepository.save(order);
                orderMedicineRepository.save(orderMedicine);
            });
        });
    }
    @Override
    public void refreshReceiptsStatus(int receiptId) {
        log.info("Refreshing receipts status for receipt with ID: {}", receiptId);
        Optional<Receipt> receiptOptional = receiptService.findReceiptById(receiptId);
        receiptOptional.ifPresentOrElse(
                receipt -> {
                    List<OrderMedicine> orderMedicines = orderMedicineRepository.findAllByOrderUserAndMedicineIdAndOrderStatus(
                            receipt.getPatient(),
                            receipt.getMedicine().getId(),
                            Status.NEW
                    );
                    for (OrderMedicine orderMedicine : orderMedicines) {
                        orderMedicine.setReceiptStatus(obtainReceiptStatus(receipt.getPatient().getId(), receipt.getMedicine().getId(), orderMedicine.getQuantity()));
                        orderMedicineRepository.save(orderMedicine);
                    }
                },
                () -> {
                    log.warn("Receipt with ID " + receiptId + " not found.");
                }
        );
    }
   @Override
    public ReceiptStatus obtainReceiptStatus(int patientId, int medicineId, int quantity) {
        log.info("Obtaining receipt status for patient with ID {}, medicine with ID {}, and quantity {}.", patientId, medicineId, quantity);
        Optional<Medicine> medicineOptional = findById(medicineId);
        if (medicineOptional.isPresent()) {
            Medicine medicine = medicineOptional.get();
            if (!medicine.isPrescriptionRequired()) {
                return ReceiptStatus.NO_RECEIPT_NEEDED;
            } else if (receiptService.isReceiptProvided(patientId, medicineId, quantity)) {
                return ReceiptStatus.RECEIPT_PROVIDED;
            } else {
                return ReceiptStatus.RECEIPT_NEEDED;
            }
        }
        return ReceiptStatus.MEDICINE_NOT_FOUND;
    }
}
