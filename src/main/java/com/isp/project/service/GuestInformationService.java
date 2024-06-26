package com.isp.project.service;

import java.util.List;


import com.isp.project.model.GuestInformation;

public interface GuestInformationService {
    List<GuestInformation> getAll();
    Boolean create(GuestInformation guestInformation);
    Boolean contactStatus(int id, boolean currentStatus);
}
