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

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

   

}