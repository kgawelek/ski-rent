package com.io.skirent.unavailability.services;

import com.io.skirent.equipment.Equipment;
import com.io.skirent.equipment.repositories.EquipmentRepository;
import com.io.skirent.unavailability.*;
import com.io.skirent.unavailability.repositories.CheckUpRepository;
import com.io.skirent.unavailability.repositories.RentalRepository;
import com.io.skirent.unavailability.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnavailabilityService {
    RentalRepository rentalRepository;
    RepairRepository repairRepository;
    CheckUpRepository checkUpRepository;
    EquipmentRepository equipmentRepository;

    @Autowired
    public UnavailabilityService(RentalRepository rentalRepository, RepairRepository repairRepository, CheckUpRepository checkUpRepository, EquipmentRepository equipmentRepository) {
        this.rentalRepository = rentalRepository;
        this.repairRepository = repairRepository;
        this.checkUpRepository = checkUpRepository;
        this.equipmentRepository = equipmentRepository;
    }

    // TODO przetestowac to dokladnie
    public AvailabilityResult checkAvailability(UnavailabilityCheckParams params) {
        boolean dateCollides = checkIfDatesCollides(params);

        Long id = params.getEquipmentId();
        if(dateCollides)
            id = findAlternative(params);

        AvailabilityResult result = new AvailabilityResult(!dateCollides, id);
        return result;
    }

    public void addRental(Rental rental) {
        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    private Long findAlternative(UnavailabilityCheckParams params){
        Equipment equipment = equipmentRepository.getById(params.getEquipmentId());
        List<Equipment> sameCategoryEquipment = equipmentRepository.findEquipmentByCategory(equipment.getCategory());

        for(var e: sameCategoryEquipment){
            UnavailabilityCheckParams tempParams = new UnavailabilityCheckParams(e.getId(), params.getFromDate(), params.getToDate());
            boolean datesCollides = checkIfDatesCollides(tempParams);
            if(!datesCollides && e.getSize().equals(equipment.getSize()))
                return e.getId();

        }
        return -1L;
    }

    private boolean checkIfDatesCollides(UnavailabilityCheckParams params){
        List<Rental> collidingRentals = rentalRepository.getCollidingRentalsByDate(params.getFromDate(), params.getToDate());
        boolean dateCollides = false;

        //find rents with given equipment that collides with rentals
        for (var rental : collidingRentals) {
            for (Equipment e : rental.getEquipmentSet()) {
                if (e.getId() == params.getEquipmentId()) {
                    dateCollides = true;
                    break;
                }
            }
        }

        if (!dateCollides) {
            List<Repair> collidingRepairs = repairRepository.getRepairsByDate(params.getFromDate(), params.getToDate());
            //find rents with given equipment that collides with repairs
            for (var repair : collidingRepairs) {
                if (repair.getEquipment().getId() == params.getEquipmentId()) {
                    dateCollides = true;
                    break;
                }
            }
        }

        if (!dateCollides) {
            List<CheckUp> collidingCheckUps = checkUpRepository.getCheckUpsByDate(params.getFromDate(), params.getToDate());
            //find rents with given equipment that collides with check-ups
            for (var checkup : collidingCheckUps) {
                if (checkup.getEquipment().getId() == params.getEquipmentId()) {
                    dateCollides = true;
                    break;
                }
            }
        }

        return dateCollides;
    }
}
