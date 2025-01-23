package com.uexcel.regular.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
public class ReservationDates {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDate date;
    @ManyToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Reservation reservation;
    @ManyToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "roomNumber",referencedColumnName = "roomNumber",
            foreignKey = @ForeignKey(name = "FK_RD_REGULAR"))
    private RegularRoom regularRoom;
}
