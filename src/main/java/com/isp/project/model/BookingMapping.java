package com.isp.project.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BookingMapping")
public class BookingMapping {
    @Id
    @Column(name="BookingMappingID")
    private int BookingMappingID;

    @ManyToOne
    @JoinColumn(name = "RoomID")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;

    @Column(name="CheckInDate")
    private Date CheckInDate;

    @Column(name="CheckOutDate")
    private Date CheckOutDate;

    @Column(name="BookingTotalAmount")
    private double BookingTotalAmount;
    

    
    


}
