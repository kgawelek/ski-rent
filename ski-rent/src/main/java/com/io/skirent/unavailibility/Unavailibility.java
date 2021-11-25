package com.io.skirent.unavailibility;

import javax.persistence.Column;
import java.time.LocalDate;

abstract class Unavailibility {

    @Column(
            name = "from",
            columnDefinition = "DATE"

    )
    private LocalDate from;

    @Column(
            name = "to",
            columnDefinition = "DATE"

    )
    private LocalDate to;
}
