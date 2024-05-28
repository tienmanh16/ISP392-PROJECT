package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ImageDetail")
public class ImageDetail {
    @Id
    @Column(name = "ImageID")
    private int ImageID;

    @Column(name="Image1")
    private String Image1;

    @Column(name="Image2")
    private String Image2;

    public ImageDetail() {
    }

    public ImageDetail(int imageID, String image1, String image2) {
        ImageID = imageID;
        Image1 = image1;
        Image2 = image2;
    }

}