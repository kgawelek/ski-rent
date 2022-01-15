package com.io.skirent.unavailability.services;

import com.io.skirent.unavailability.AvailabilityResult;
import com.io.skirent.unavailability.Rental;
import com.io.skirent.unavailability.UnavailabilityCheckParams;
import com.io.skirent.unavailability.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnavailabilityService {
    RentalRepository rentalRepository;

    @Autowired
    public UnavailabilityService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

/*    public AvailabilityResult checkAvailability(UnavailabilityCheckParams params){

    }*/

    public void addRental(Rental rental){
        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals(){
        return rentalRepository.findAll();
    }
}
