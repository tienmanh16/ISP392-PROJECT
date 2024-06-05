package com.isp.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="RoomItemMapping")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class RoomItemMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RoomItemID")
    private int RoomItemID;

     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoomID")
    @JsonBackReference
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemID")
    @JsonBackReference
    private RoomItem roomItem;

    @Column(name="Quantity")
    private int Quantity;
}
