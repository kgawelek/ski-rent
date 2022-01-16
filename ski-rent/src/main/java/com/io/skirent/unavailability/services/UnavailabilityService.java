package com.io.skirent.unavailability.services;

import com.io.skirent.equipment.Equipment;
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

    @Autowired
    public UnavailabilityService(RentalRepository rentalRepository, RepairRepository repairRepository, CheckUpRepository checkUpRepository) {
        this.rentalRepository = rentalRepository;
        this.repairRepository = repairRepository;
        this.checkUpRepository = checkUpRepository;
    }

    // TODO find alternatives if equipment is not available and test properly
    public AvailabilityResult checkAvailability(UnavailabilityCheckParams params) {
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


        AvailabilityResult result = new AvailabilityResult(!dateCollides, params.getEquipmentId());
        return result;
    }

    public void addRental(Rental rental) {
        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // for query tests
//    public List<Rental> getByDate(UnavailabilityCheckParams params){
//        return rentalRepository.getRentalsByDate(params.getFromDate(), params.getToDate());
//    }
}
