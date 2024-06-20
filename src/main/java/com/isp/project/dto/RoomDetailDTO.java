package com.isp.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDetailDTO {
    private int id;
    private String roomNum;
    private int roomTypeId;
    private String roomTypeName;
    private String roomTypeDescription;
    private int priceHour;
    private int priceDay;
    private String status;

    public RoomDetailDTO() {
    }
    
    
}
