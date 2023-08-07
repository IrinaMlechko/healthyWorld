package com.example.pharmacy.service.impl;


import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.service.ReceiptService;
import com.example.pharmacy.util.ReceiptStatus;
import com.example.pharmacy.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMedicineRepository orderMedicineRepository;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    private Medicine testMedicine;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        testMedicine = new Medicine();
        testMedicine.setId(1);
        testMedicine.setMedicineName("Test Medicine");
        testMedicine.setManufacturer("Test Manufacturer");
        testMedicine.setPrice(BigDecimal.valueOf(10.0));
        testMedicine.setPrescriptionRequired(true);

        testOrder = new Order();
        testOrder.setId(1);
        testOrder.setStatus(Status.NEW);
    }

    @Test
    void testFindAll() {
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(testMedicine);
        when(medicineRepository.findAll()).thenReturn(medicines);

        List<Medicine> result = medicineService.findAll();

        assertEquals(1, result.size());
        assertEquals(testMedicine, result.get(0));
    }

    @Test
    void testFindById() {
        when(medicineRepository.findById(1)).thenReturn(Optional.of(testMedicine));

        Optional<Medicine> result = medicineService.findById(1);

        assertEquals(Optional.of(testMedicine), result);
    }

    @Test
    void testObtainReceiptStatus_NoPrescriptionRequired() {
        int patientId = 1;
        int medicineId = 1;
        int quantity = 5;
        testMedicine.setPrescriptionRequired(false);
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(testMedicine));

        ReceiptStatus result = medicineService.obtainReceiptStatus(patientId, medicineId, quantity);

        assertEquals(ReceiptStatus.NO_RECEIPT_NEEDED, result);
    }
}
