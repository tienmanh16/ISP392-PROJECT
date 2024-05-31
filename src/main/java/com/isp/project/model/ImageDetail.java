package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class ImageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ImageID", nullable = false)
    private int id;


    @Column(name = "Image1", nullable = false)
    private String image1;

    @Column(name = "Image2", nullable = false)
    private String image2;

    @ManyToOne
    @JoinColumn(name = "RoomTypeID")
    private RoomType roomType;

}
