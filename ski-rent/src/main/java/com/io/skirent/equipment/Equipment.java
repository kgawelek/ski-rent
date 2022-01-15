package com.io.skirent.equipment;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "equipments"
)
public class Equipment {

    @Id
    @SequenceGenerator(
            name = "equipment_sequence",
            sequenceName = "equipment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equipment_sequence"
    )
    @Column(
            name = "equipment_id",
            nullable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "manufacturer",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String manufacturer;

    @Column(
            name = "size",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String size;

    @Column(
            name = "next_check_up",
            columnDefinition = "DATE"

    )
    private LocalDate nextCheckUp;

    public Long getId() {
        return id;
    }

    @Column(
            name = "category",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Enumerated(EnumType.STRING)
    private Category category;

    public Equipment() {
    }

    public Equipment(String name, String manufacturer, String size, LocalDate nextCheckUp, Category category) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.size = size;
        this.nextCheckUp = nextCheckUp;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public LocalDate getNextCheckUp() {
        return nextCheckUp;
    }

    public void setNextCheckUp(LocalDate nextCheckUp) {
        this.nextCheckUp = nextCheckUp;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() { // obejście na pokazywanie id
        return """
                                
                \t{
                        "id": "%d",
                        "name": "%s",
                        "manufacturer": "%s",
                        "size": "%s",
                        "category": "%s"
                    }""".formatted(id, name, manufacturer, size, category.toString());
    }
}
