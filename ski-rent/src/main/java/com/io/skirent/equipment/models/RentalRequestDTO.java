package com.io.skirent.equipment.models;


import java.time.LocalDate;

public class RentalRequestDTO {

    public Long[] ids;
    public LocalDate dateFrom;
    public LocalDate dateTo;

    public RentalRequestDTO() {
    }

    public RentalRequestDTO(Long[] ids, LocalDate dateFrom, LocalDate dateTo) {
        this.ids = ids;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
