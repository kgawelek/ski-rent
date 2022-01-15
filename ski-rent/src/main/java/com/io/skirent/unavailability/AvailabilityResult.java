package com.io.skirent.unavailability;

/**
 * Class for checking availibilty, returns if given equipment is availble, if not it returns similar equipment that is available
 */
public class AvailabilityResult {
    boolean isAvailable;
    Long equipmentId;

    public AvailabilityResult(boolean isAvailable, Long equipmentId) {
        this.isAvailable = isAvailable;
        this.equipmentId = equipmentId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }
}
