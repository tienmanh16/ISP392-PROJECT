package com.isp.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomTypeDetailDTO {
    private int roomTypeId;
    private String roomTypeName;
    private String roomTypeDescription;
    private int priceHour;
    private int priceDay;
    private int imageId;
    private String image1;
    private String image2;

}
