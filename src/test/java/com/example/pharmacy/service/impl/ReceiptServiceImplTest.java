package com.example.pharmacy.service.impl;

import com.example.pharmacy.dto.ReceiptDto;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.mapper.ReceiptMapper;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.ReceiptRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.util.ReceiptStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceImplTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    private Receipt testReceipt;
    private User testUser;
    private Medicine testMedicine;

    @BeforeEach
    void setUp() {
        testMedicine = new Medicine();
        testMedicine.setId(1);
        testMedicine.setMedicineName("Test Medicine");

        testUser = new User();
        testUser.setId(1);
        testUser.setFirstName("Name");
        testUser.setLastName("Lname");

        testReceipt = new Receipt();
        testReceipt.setId(1);
        testReceipt.setMedicine(testMedicine);
        testReceipt.setPatient(testUser);
        testReceipt.setQuantity(10);
    }

    @Test
    void testConfirmReceipt() {
        int receiptId = 1;
        int doctorId = 2;
        User doctor = new User();
        doctor.setId(doctorId);
        when(userRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(testReceipt));
        when(receiptRepository.save(testReceipt)).thenReturn(testReceipt);

        assertDoesNotThrow(() -> receiptService.confirmReceipt(receiptId, doctorId));
        assertEquals(doctorId, testReceipt.getDoctor().getId());
    }

    @Test
    void testConfirmReceipt_InvalidReceiptId() {
        int receiptId = 1;
        int doctorId = 2;

        assertThrows(ServiceException.class, () -> receiptService.confirmReceipt(receiptId, doctorId));
    }

}
