package com.example.pharmacy.service.impl;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.service.MedicineService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MedicineServiceImpl implements MedicineService {
    static Logger logger = LogManager.getLogger();
    private final MedicineRepository medicineRepository;

    private MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
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

}
