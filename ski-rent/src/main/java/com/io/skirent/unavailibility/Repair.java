package com.io.skirent.unavailibility;


import com.io.skirent.equipment.Equipment;

import javax.persistence.*;

@Entity
@Table(
        name = "repairs"
)
public class Repair extends Unavailibility{

    @Id
    @SequenceGenerator(
            name = "repair_sequence",
            sequenceName = "repair_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "repair_sequence"
    )
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(
            name = "done",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean done;

    @Column(
            name = "reason",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String reason;


    public Repair() {
    }

    public Repair(Long id, Equipment equipment, boolean done, String reason) {
        this.id = id;
        this.equipment = equipment;
        this.done = done;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
