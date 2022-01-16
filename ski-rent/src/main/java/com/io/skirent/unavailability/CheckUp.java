package com.io.skirent.unavailability;


import com.io.skirent.equipment.Equipment;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "checkups"
)
public class CheckUp extends Unavailability {

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

    // TODO co to jest za zmienna?
    @Column(
            name = "date",
            columnDefinition = "DATE"

    )
    private LocalDate date;

    public CheckUp() {
    }

    public CheckUp(Long id, Equipment equipment, boolean done, LocalDate date) {
        this.id = id;
        this.equipment = equipment;
        this.done = done;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
