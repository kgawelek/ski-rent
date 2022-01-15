package com.io.skirent.equipment.services;

import com.io.skirent.equipment.Category;
import com.io.skirent.equipment.Equipment;
import com.io.skirent.equipment.EquipmentFilters;
import com.io.skirent.equipment.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Equipment> getGearEquipment() {
        return equipmentRepository.findGearEquipment();
    }

    public List<Equipment> getClothesEquipment() {
        return equipmentRepository.findClothesEquipment();
    }

    public List<Equipment> getEquipmentByCategory(String category) {
        for(Category cat: Category.values()){
            if(cat.toString().equals(category))
                return equipmentRepository.findEquipmentByCategory(category);
        }
        throw new IllegalStateException("Category:  " + category + " doesnt exists");
    }

    public List<Equipment> getEquipmentWithFiltering(EquipmentFilters equipmentFilters) {
        List<Equipment> equipmentList = equipmentRepository.findAll();

        return filterEquipment(equipmentList, equipmentFilters);
    }

    public List<Equipment> getGearWithFiltering(EquipmentFilters equipmentFilters) {
        List<Equipment> equipmentList = equipmentRepository.findGearEquipment();

        return filterEquipment(equipmentList, equipmentFilters);
    }

    public List<Equipment> getClothesWithFiltering(EquipmentFilters equipmentFilters) {
        List<Equipment> equipmentList = equipmentRepository.findClothesEquipment();

        return filterEquipment(equipmentList, equipmentFilters);
    }

    public void addNewEquipment(Equipment equipment) {
        // TODO decide what validation is needed
        LocalDate today = LocalDate.now();
        if(equipment.getNextCheckUp().isBefore(today))
            throw new IllegalArgumentException("Next checkup date cannot be in the past");

        // TODO WTF???
        equipment.setName(equipment.getName());
        equipment.setManufacturer(equipment.getManufacturer());
        equipment.setSize(equipment.getSize());

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


    private List<Equipment> filterEquipment(List<Equipment> equipmentList, EquipmentFilters equipmentFilters) {
        if(equipmentFilters.getNames() != null && !equipmentFilters.getNames().isEmpty()) {
            equipmentList = equipmentList.stream()
                    .filter(equipment -> equipmentFilters.getNames().contains(equipment.getName().toUpperCase()))
                    .toList();
        }

        if(equipmentFilters.getManufacturers() != null && !equipmentFilters.getManufacturers().isEmpty()) {
            equipmentList = equipmentList.stream()
                    .filter(equipment -> equipmentFilters.getManufacturers().contains(equipment.getManufacturer().toUpperCase()))
                    .toList();
        }

        if(equipmentFilters.getSizes() != null && !equipmentFilters.getSizes().isEmpty()) {
            equipmentList = equipmentList.stream()
                    .filter(equipment -> equipmentFilters.getSizes().contains(equipment.getSize().toUpperCase()))
                    .toList();
        }

        if(equipmentFilters.getCategories() != null && !equipmentFilters.getCategories().isEmpty()) {
            equipmentList = equipmentList.stream()
                    .filter(equipment -> equipmentFilters.getCategories().contains(equipment.getCategory()))
                    .toList();
        }

        return equipmentList;
    }
}
