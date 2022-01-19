package com.io.skirent.equipment;

import javax.persistence.*;

@Entity
@Table(name = "prices")
public class Prices {
    @Id
    @SequenceGenerator(
            name = "prices_sequence",
            sequenceName = "prices_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "prices_sequence"
    )
    @Column(
            name = "prices_id",
            nullable = false
    )
    private Long id;

    @Column(
            name = "category",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(
            name = "days",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int numberOfDays;

    @Column(
            name = "price",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int pricePerPeriod;

    public Prices() {
    }

    public Prices(Long id, Category category, int numberOfDays, int pricePerPeriod) {
        this.id = id;
        this.category = category;
        this.numberOfDays = numberOfDays;
        this.pricePerPeriod = pricePerPeriod;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getPricePerPeriod() {
        return pricePerPeriod;
    }

    public void setPricePerPeriod(int pricePerPeriod) {
        this.pricePerPeriod = pricePerPeriod;
    }
}
