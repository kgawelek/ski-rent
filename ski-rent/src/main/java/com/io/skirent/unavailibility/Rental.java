package com.io.skirent.unavailibility;


import com.io.skirent.equipment.Equipment;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "rentals"
)
public class Rental extends Unavailability {

    @Id
    @SequenceGenerator(
            name = "rental_sequence",
            sequenceName = "rental_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rental_sequence"
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
            name = "accepted",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean accepted;

    @Column(
            name = "returned",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean returned;

    @Column(
            name = "deposit",
            nullable = false,
            columnDefinition = "REAL"
    )
    private float deposit;

    public Rental() {
    }

    public Rental(Long id, Equipment equipment, boolean accepted, boolean returned, float deposit) {
        this.id = id;
        this.equipment = equipment;
        this.accepted = accepted;
        this.returned = returned;
        this.deposit = deposit;
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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
