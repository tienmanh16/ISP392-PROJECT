package com.isp.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceDetailDTO {
    private int seId;
    private String seName;
    private double sePrice;
    private int seActive;
    private int seTypeId;
    private String seTypeName;
    private int seTypeActive;

}
