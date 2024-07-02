package com.isp.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "Room")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomID", nullable = false)
    private int id;

    @Column(name = "RoomNumber", nullable = false)
    private String roomNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoomTypeID")
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    
    private RoomType roomType;

    @Column(name = "RoomStatus", nullable = false)
    private String status;

    @Column(name = "RoomActive", nullable = false)
    private int roomActive;
    
    @OneToMany(mappedBy = "roomID")
    @JsonManagedReference
    private List<BookingMapping> bookingMapping;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<RoomItemMapping> roomItemMapping;

    @Column(name = "Cleaning", nullable = false)
    private String cleaning;
    
    
}
