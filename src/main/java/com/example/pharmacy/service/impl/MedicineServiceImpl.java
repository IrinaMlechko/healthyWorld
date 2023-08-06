package com.example.pharmacy.service.impl;

import com.example.pharmacy.entity.*;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.OrderMedicineRepository;
import com.example.pharmacy.repository.OrderRepository;
import com.example.pharmacy.service.MedicineService;
import com.example.pharmacy.util.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        order.setUser(user);
        order.setStatus(Status.NEW);
        order = orderRepository.save(order);
        return order.getId();
    }

    public void addMedicineToOrder(Integer orderId, int medicineId, int quantity) {
        Optional<Medicine> medicineOptional = medicineRepository.findById(medicineId);
        if (medicineOptional.isPresent()) {
            Medicine medicine = medicineOptional.get();
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                Optional<OrderMedicine> orderMedicineOptional = orderMedicineRepository.findById(new OrderMedicineId(orderId, medicineId));
                OrderMedicine orderMedicine;
                if(orderMedicineOptional.isEmpty()){
                    orderMedicine = new OrderMedicine(order, medicine, quantity, null);
                    order.addOrderMedicine(orderMedicine);
                    orderRepository.save(order);
                } else{
                    orderMedicine = orderMedicineOptional.get();
                    orderMedicine.setQuantity(orderMedicine.getQuantity()+quantity);
                }
                orderMedicineRepository.save(orderMedicine);
            }
        }
    }
}
