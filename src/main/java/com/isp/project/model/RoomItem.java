package com.isp.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="RoomItem")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class RoomItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemID")
    private int itemID;

    @Column(name="ItemName")
    private String itemName;

    @Column(name = "ItemsActive")
    private Boolean itemsActive;

    @OneToMany(mappedBy = "roomItem")
    @JsonManagedReference
    private List<RoomItemMapping> roomItemMapping;
}
