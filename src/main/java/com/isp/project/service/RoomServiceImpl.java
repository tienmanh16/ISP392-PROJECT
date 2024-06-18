package com.isp.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomCustomerDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Room;
import com.isp.project.repositories.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // @Override
    // public List<RoomDetailDTO> getAllRoomsWithDetails() {
    //     return roomRepository.findAllRoomsWithDetails();
    // }

    @Override
    public Page<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id, Integer pageNo) {
        //List<RoomDetailDTO> list = this.roomRepository.findAllRoomsWithDetailsByRoomTypeId(id);
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        // Integer start = (int) pageable.getOffset();
        // Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
        // list = list.subList(start, end);
        // return new PageImpl<RoomDetailDTO>(list, pageable, list.size());
        return this.roomRepository.findAllRoomsWithDetailsByRoomTypeId(id, pageable);
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsByStatus(String status) {
        return roomRepository.findAllRoomsByStatus(status);
    }

    public Customer test(int id){
        return roomRepository.getReferenceById(id).getBookingMapping().get(0).getBookingID().getCustomerID();
    }
    public Room testR(int id){
        return roomRepository.getReferenceById(id);
    }


    public String test1(int id){
        return roomRepository.getReferenceById(id).getRoomNum();
    }

    @Override
    public RoomCustomerDTO getAllRoomCusWithDetailsByRoomId(Integer id) {
        return roomRepository.findAllRoomCusWithDetailsByRoomId(id);
    }

    @Override
    public Page<RoomDetailDTO> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 6);
        return this.roomRepository.findAllRoomsWithDetails(pageable);
    }

    @Override
    public List<RoomDetailDTO> getRoomFromFilter(String status, Integer id) {
        List<RoomDetailDTO> rooms = roomRepository.findAllRoomsWithDetails1();

        if (status != null) {
            rooms.removeIf(room -> !room.getStatus().equals(status));
        }

        if (id != null) {
            rooms.removeIf(room -> room.getRoomTypeId() != id);
        }


        return rooms;
    }

    @Override
   @Transactional
    public void updateRoomStatusByRoomId(Integer roomId) {
        roomRepository.updateRoomStatusByRoomId(roomId);
    }

   


    
}
