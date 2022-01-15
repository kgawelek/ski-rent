package com.io.skirent.unavailability.controllers;

import com.io.skirent.unavailability.AvailabilityResult;
import com.io.skirent.unavailability.Rental;
import com.io.skirent.unavailability.UnavailabilityCheckParams;
import com.io.skirent.unavailability.services.UnavailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/available")
public class UnavailabilityController {

    private final UnavailabilityService unavailabilityService;

    @Autowired
    public UnavailabilityController(UnavailabilityService unavailabilityService) {
        this.unavailabilityService = unavailabilityService;
    }

/*    @PostMapping(path = "/check")
    public AvailabilityResult checkEquipmentAvailibilty(@RequestParam UnavailabilityCheckParams params){
        return unavailabilityService.checkAvailability(params);
    }*/

    @PostMapping(path = "/newrent")
    public void addNewRent(@RequestParam Rental rental){
        unavailabilityService.addRental(rental);
    }

    @GetMapping(path = "/rents")
    public List<Rental> getAllRents(){
        return unavailabilityService.getAllRentals();
    }
}
