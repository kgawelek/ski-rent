package com.io.skirent.unavailability.services;

import com.io.skirent.equipment.Equipment;
import com.io.skirent.equipment.repositories.EquipmentRepository;
import com.io.skirent.unavailability.*;
import com.io.skirent.unavailability.repositories.CheckUpRepository;
import com.io.skirent.unavailability.repositories.RentalRepository;
import com.io.skirent.unavailability.repositories.RepairRepository;
import com.io.skirent.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UnavailabilityService {
    RentalRepository rentalRepository;
    RepairRepository repairRepository;
    CheckUpRepository checkUpRepository;
    EquipmentRepository equipmentRepository;
    UserRepository userRepository;

    @Autowired
    public UnavailabilityService(RentalRepository rentalRepository, RepairRepository repairRepository, CheckUpRepository checkUpRepository,
                                 EquipmentRepository equipmentRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.repairRepository = repairRepository;
        this.checkUpRepository = checkUpRepository;
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    public AvailabilityResult checkAvailability(UnavailabilityCheckParams params) {
        boolean dateCollides = checkIfDatesCollide(params);

        Long id = params.getEquipmentId();
        if(dateCollides)
            id = findAlternative(params);

        return new AvailabilityResult(!dateCollides, id);
    }

    public void addRental(Rental rental) {
        rental.setEquipmentSet(
            rental.getEquipmentSet().stream()
                    .map(equipment -> equipmentRepository.getById(equipment.getId()))
                    .collect(Collectors.toSet())
        ); // set equipments by id only
        rental.setClient(
                userRepository.findClientById(rental.getClient().getId()).orElseThrow()
        ); // set client by id only

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
            boolean datesCollides = checkIfDatesCollide(tempParams);
            if(!datesCollides && e.getSize().equals(equipment.getSize()))
                return e.getId();

        }
        return -1L;
    }

    private boolean checkIfDatesCollide(UnavailabilityCheckParams params){
        List<Rental> collidingRentals = rentalRepository.getCollidingRentalsByDate(params.getFromDate(), params.getToDate());

        //find rents with given equipment that collides with rentals
        for (var rental : collidingRentals) {
            for (Equipment e : rental.getEquipmentSet()) {
                if (Objects.equals(e.getId(), params.getEquipmentId())) {
                    return true; // date collides
                }
            }
        }

        List<Repair> collidingRepairs = repairRepository.getRepairsByDate(params.getFromDate(), params.getToDate());
        //find rents with given equipment that collides with repairs
        for (var repair : collidingRepairs) {
            if (Objects.equals(repair.getEquipment().getId(), params.getEquipmentId())) {
                return true; // date collides
            }
        }

        List<CheckUp> collidingCheckUps = checkUpRepository.getCheckUpsByDate(params.getFromDate(), params.getToDate());
        //find rents with given equipment that collides with check-ups
        for (var checkup : collidingCheckUps) {
            if (Objects.equals(checkup.getEquipment().getId(), params.getEquipmentId())) {
                return true; // date collides
            }
        }

        return false; // date does not collide
    }
}
