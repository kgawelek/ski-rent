package com.io.skirent.equipment.services;

import com.io.skirent.equipment.Category;
import com.io.skirent.equipment.Equipment;
import com.io.skirent.equipment.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository){
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getEquipmentById(Long equipmentId) {
        boolean exists =  equipmentRepository.existsById(equipmentId);

        if(!exists){
            throw new IllegalStateException("Equipment with id " + equipmentId + " doesnt exists");
        }
        return equipmentRepository.findById(equipmentId);
    }

    public Optional<Equipment> getEquipmentByCategory(String category) {
        for(Category cat: Category.values()){
            if(cat.toString().equals(category))
                return equipmentRepository.findEquipmentByCategory(category);
        }
        throw new IllegalStateException("Category:  " + category + " doesnt exists");
    }

    public void addNewEquipment(Equipment equipment) {
        // TODO decide what validation is needed
        equipmentRepository.save(equipment);
    }

    public void deleteEquipment(Long equipmentId) {
        // TODO decide if some validation is needed
        boolean exists =  equipmentRepository.existsById(equipmentId);

        if(!exists){
            throw new IllegalArgumentException("Equipment with id " + equipmentId + " doesnt exists");
        }
        equipmentRepository.deleteById(equipmentId);
    }



}
