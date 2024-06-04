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

@Data
@Entity
@Table(name = "ImageDetail")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class ImageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ImageID", nullable = false)
    private int id;


    @Column(name = "Image1", nullable = false)
    private String image1;

    @Column(name = "Image2", nullable = false)
    private String image2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoomTypeID")
    @JsonBackReference
    private RoomType roomType;



//ok
}
