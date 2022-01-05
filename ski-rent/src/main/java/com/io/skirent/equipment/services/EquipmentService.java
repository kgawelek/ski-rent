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

    public Optional<Equipment> getEquipmentByCategory(String category) {
        for(Category cat: Category.values()){
            if(cat.toString().equals(category))
                return equipmentRepository.findEquipmentByCategory(category);
        }
        throw new IllegalStateException("Category:  " + category + " doesnt exists");
    }

    public List<Equipment> getEquipmentWithFiltering(EquipmentFilters equipmentFilters) {
        // PR: po napisaniu tego zdałem sobie sprawę z tego, że jest to nieefektywne;
        // próbowałem napisać swoje query w natywnym SQL-u, ale nie umiałem go poprawnie wywołać

        List<Equipment> equipmentList = equipmentRepository.findAll();

        List<String> names = equipmentList.stream().map(Equipment::getName).toList(),
                manufacturers = equipmentList.stream().map(Equipment::getManufacturer).toList(),
                sizes = equipmentList.stream().map(Equipment::getSize).toList();
        List<Category> categories = equipmentList.stream().map(Equipment::getCategory).toList();
        boolean validFilters = false;

        if(equipmentFilters.getNames() != null && !equipmentFilters.getNames().isEmpty()) {
            names = equipmentFilters.getNames().stream()
                    .map(n -> n.toUpperCase().trim())
                    .toList();
            validFilters = true;
        }

        if(equipmentFilters.getManufacturers() != null && !equipmentFilters.getManufacturers().isEmpty()) {
            manufacturers = equipmentFilters.getManufacturers().stream()
                    .map(n -> n.toUpperCase().trim())
                    .toList();
            validFilters = true;
        }

        if(equipmentFilters.getSizes() != null && !equipmentFilters.getSizes().isEmpty()) {
            sizes = equipmentFilters.getSizes().stream()
                    .map(n -> n.toUpperCase().trim())
                    .toList();
            validFilters = true;
        }

        if(equipmentFilters.getCategories() != null && !equipmentFilters.getCategories().isEmpty()) {
            try {
                categories = equipmentFilters.getCategories().stream()
                        .map(n -> Category.valueOf(n.toUpperCase().trim()))
                        .toList();
                validFilters = true;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        if(validFilters)
            return equipmentRepository.findEquipmentWithFiltering(names, manufacturers, sizes, categories);
        else
            return equipmentList;

    }

    public void addNewEquipment(Equipment equipment) {
        // TODO decide what validation is needed
        LocalDate today = LocalDate.now();
        if(equipment.getNextCheckUp().isBefore(today))
            throw new IllegalArgumentException("Next checkup date cannot be in the past");

        // wszystko CAPS-em dla łatwiejszego wyszukiwania
        equipment.setName(equipment.getName().toUpperCase());
        equipment.setManufacturer(equipment.getManufacturer().toUpperCase());
        equipment.setSize(equipment.getSize().toUpperCase());

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
