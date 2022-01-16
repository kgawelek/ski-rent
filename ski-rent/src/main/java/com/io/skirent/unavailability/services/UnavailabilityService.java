package com.io.skirent.unavailability.services;

import com.io.skirent.equipment.Equipment;
import com.io.skirent.unavailability.AvailabilityResult;
import com.io.skirent.unavailability.Rental;
import com.io.skirent.unavailability.UnavailabilityCheckParams;
import com.io.skirent.unavailability.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnavailabilityService {
    RentalRepository rentalRepository;

    @Autowired
    public UnavailabilityService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // TODO find alternatives if equipment is not available and test properly
    public AvailabilityResult checkAvailability(UnavailabilityCheckParams params){
        List<Rental> rentals = rentalRepository.getRentalsByDate(params.getFromDate(), params.getToDate());
        boolean dateColides = false;

        //find rents with given equipment
        for(var rental: rentals){
            for(Equipment e: rental.getEquipmentSet()){
                if(e.getId() == params.getEquipmentId()) {
                    dateColides = true;
                }
            }
        }

        AvailabilityResult result = new AvailabilityResult(!dateColides, params.getEquipmentId());
        return result;
    }

    public void addRental(Rental rental){
        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals(){
        return rentalRepository.findAll();
    }

    // for query tests
//    public List<Rental> getByDate(UnavailabilityCheckParams params){
//        return rentalRepository.getRentalsByDate(params.getFromDate(), params.getToDate());
//    }
}
