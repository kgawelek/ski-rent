package com.io.skirent.unavailibility;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
abstract class Unavailability {

    @Column(
            name = "from",
            nullable = false,
            columnDefinition = "DATE"
    )
    LocalDate from; // package-private

    @Column(
            name = "to",
            nullable = false,
            columnDefinition = "DATE"
    )
    LocalDate to; // package-private

    public LocalDate getFrom() {
        return this.from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
