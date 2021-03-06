package com.io.skirent.equipment.services;

import com.io.skirent.equipment.Category;
import com.io.skirent.equipment.Equipment;
import com.io.skirent.equipment.EquipmentFilters;
import com.io.skirent.equipment.repositories.EquipmentRepository;
import com.io.skirent.equipment.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository, PriceRepository priceRepository){
        this.equipmentRepository = equipmentRepository;
        this.priceRepository = priceRepository;
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
                return equipmentRepository.findEquipmentByCategory(cat);
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

    public long getRentalPrice(Long[] ids, LocalDate from, LocalDate to) {
        var lambdaContext = new Object() {
            long totalPrice = 0;
            long tmpPeriod;
        };
        long period = ChronoUnit.DAYS.between(from, to) + 1; // + 1 for double inclusive

        for (long id : ids) {
            lambdaContext.tmpPeriod = period;
            TreeMap<Integer, Integer> prices = new TreeMap<>(Comparator.reverseOrder());
            Category category = equipmentRepository.getById(id).getCategory();
            priceRepository.findPricesByCategory(category)
                    .forEach(price -> prices.put(price.getNumberOfDays(), price.getPricePerPeriod()));
            prices.forEach((days, price) -> {
                lambdaContext.totalPrice += (lambdaContext.tmpPeriod / days) * price; // price += number of periods * price of period
                lambdaContext.tmpPeriod = lambdaContext.tmpPeriod % days; // period -= number of periods * days of period
            });
            if(lambdaContext.tmpPeriod != 0)
                throw new IllegalStateException("Cannot calculate price for id: " + id + " and number of days: " + period);
        }

        return lambdaContext.totalPrice;
    }
}
