package com.isp.project.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.isp.project.model.GuestInformation;
import com.isp.project.model.Invoice;

public interface GuestInformationService {
    List<GuestInformation> getAll();
    Boolean create(GuestInformation guestInformation);
    Boolean contactStatus(int id, boolean currentStatus);
    Page<GuestInformation> pageInvoice(Integer pageNo);
}
