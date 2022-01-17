package com.io.skirent.unavailability;

/**
 * Class for checking availability, returns if given equipment is available, if not it returns similar equipment that is available
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
