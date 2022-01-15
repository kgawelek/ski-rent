package com.io.skirent.unavailability;


import com.io.skirent.equipment.Equipment;
import com.io.skirent.user.Client;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToMany
    @JoinColumn(name = "equipment_id")
    private Set<Equipment> equipmentSet;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Client client;


    public Rental() {
    }

    public Rental(Long id, Set<Equipment> equipmentSet, boolean accepted, boolean returned, float deposit, Client client) {
        this.id = id;
        this.equipmentSet = equipmentSet;
        this.accepted = accepted;
        this.returned = returned;
        this.deposit = deposit;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Equipment> getEquipmentSet() {
        return equipmentSet;
    }

    public void setEquipmentSet(Set<Equipment> equipmentSet) {
        this.equipmentSet = equipmentSet;
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

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
