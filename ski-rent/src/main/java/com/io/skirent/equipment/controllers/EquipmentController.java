package com.io.skirent.equipment.controllers;

import com.io.skirent.equipment.Equipment;
import com.io.skirent.equipment.EquipmentFilters;
import com.io.skirent.equipment.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService){
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public List<Equipment> getEquipment(){
        return equipmentService.getAllEquipment();
    }

    @GetMapping(path = "{equipmentId}")
    public Optional<Equipment> getEquipmentById(@PathVariable("equipmentId") Long equipmentId){
        return equipmentService.getEquipmentById(equipmentId);
    }

    @GetMapping(path = "/category/{category}")
    public Optional<Equipment> getEquipmentByCategory(@PathVariable("category") String category){
        return equipmentService.getEquipmentByCategory(category);
        // TODO to chyba powinno zwracac List<Equipment> wszystkich, ktore sa z tej kategorii
    }

    @PostMapping(path = "/filter")
    public List<Equipment> getEquipmentWithFiltering(@RequestBody EquipmentFilters equipmentFilters) {
        return equipmentService.getEquipmentWithFiltering(equipmentFilters);
    }

    @PostMapping
    public void registerNewEquipment(@RequestBody Equipment equipment){
        equipmentService.addNewEquipment(equipment);
    }

    @DeleteMapping(path = "{equipmentId}")
    public void deleteEquipment(@PathVariable("equipmentId") Long equipmentId){
        equipmentService.deleteEquipment(equipmentId);
    }

}
