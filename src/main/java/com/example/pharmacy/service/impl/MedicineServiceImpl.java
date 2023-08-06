package com.example.pharmacy.service.impl;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Order;
import com.example.pharmacy.entity.OrderMedicine;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.util.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MedicineServiceImpl implements MedicineService {
    static Logger logger = LogManager.getLogger();

    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;
    private final OrderMedicineRepository orderMedicineRepository;


    private MedicineServiceImpl(MedicineRepository medicineRepository, OrderRepository orderRepository, OrderMedicineRepository orderMedicineRepository) {
        this.medicineRepository = medicineRepository;
        this.orderRepository = orderRepository;
        this.orderMedicineRepository = orderMedicineRepository;
    }

    @Override
    public List<Medicine> findAll() {
        logger.info("Get all medicines");
        List<Medicine> medicines = medicineRepository.findAll();
        return medicines;
    }

    @Override
    public void addMedicine(Medicine medicine) {
        logger.info("Add medicine: " + medicine.getMedicineName());
        medicineRepository.save(medicine);
    }

    @Override
    public void deleteMedicine(int id) {
        logger.info("Delete medicine with id: " + id);
        medicineRepository.deleteById(id);
    }
    public Integer createOrder(User user) {
        Order order = new Order();
        // Set delivery address, status, and other order details if needed
        // ...
        order.setUser(user);
        order.setStatus(Status.NEW);
        order = orderRepository.save(order);
        return order.getId();
    }

    public void addMedicineToOrder(Integer orderId, int medicineId, int quantity) {
        Medicine medicine = medicineRepository.findById(medicineId).orElse(null);
        if (medicine != null) {
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order != null) {
                OrderMedicine orderMedicine = new OrderMedicine(order, medicine, quantity, "NEW");
                order.addOrderMedicine(orderMedicine);orderRepository.save(order);
                orderMedicineRepository.save(orderMedicine);
            }
        }
    }
}
