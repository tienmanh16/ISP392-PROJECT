package com.isp.project.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomCustomerDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Room;
import com.isp.project.repositories.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }



    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public List<RoomDetailDTO> getAllRoomsWithDetails() {
        return roomRepository.findAllRoomsWithDetails();
    }

    @Override
    public Page<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id, Integer pageNo) {
        // List<RoomDetailDTO> list =
        // this.roomRepository.findAllRoomsWithDetailsByRoomTypeId(id);
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        // Integer start = (int) pageable.getOffset();
        // Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) >
        // list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
        // list = list.subList(start, end);
        // return new PageImpl<RoomDetailDTO>(list, pageable, list.size());
        return this.roomRepository.findAllRoomsWithDetailsByRoomTypeId(id, pageable);
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsByStatus(String status) {
        return roomRepository.findAllRoomsByStatus(status);
    }

    @Override
    public Boolean create(Room room) {
        try {
            this.roomRepository.save(room);
            return true;

        } catch (Exception e) {
            // Ghi log chi tiết hơn để dễ dàng chẩn đoán lỗi
            System.err.println("Failed to save Room: " + room);
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public void updateRoomStatus(int id, String status) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room != null) {
            room.setStatus(status);
            roomRepository.save(room);
        }
    }

    public Customer test(int id) {
        Customer customer = new Customer();
        for(int i = 0; i < roomRepository.getReferenceById(id).getBookingMapping().size(); i++) {
            if (roomRepository.getReferenceById(id).getBookingMapping().get(i).getBookingMappingActive() == 0) {
                customer = null;
            }
            customer = roomRepository.getReferenceById(id).getBookingMapping().get(i).getBookingID().getCustomerID();
        }
        // if (roomRepository.getReferenceById(id).getBookingMapping().get(0).getBookingMappingActive() == 0) {
        //     return null;
        // }
        //return roomRepository.getReferenceById(id).getBookingMapping().get(0).getBookingID().getCustomerID();
        return customer;
    }

    public Room testR(int id) {
        return roomRepository.getReferenceById(id);
    }

    public String test1(int id) {
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

    @Override

    // public List<Object[]> findAvailableRooms(Date checkinDate, Date checkoutDate)
    public List<RoomDetailDTO> getAvailableRooms(Date checkinDate, Date checkoutDate) {
        List<Object[]> rawResults = roomRepository.findAvailableRooms(checkinDate, checkoutDate);
        List<RoomDetailDTO> findRoom = new ArrayList<>();
        for (Object[] rawResult : rawResults) {

            Integer id = (rawResult[0] instanceof Integer) ? (Integer) rawResult[0] : null;
            String roomNum = (rawResult[1] instanceof String) ? (String) rawResult[1] : null;
            Integer roomTypeId = (rawResult[2] instanceof Number) ? ((Number) rawResult[2]).intValue() : null;
            String roomTypeName = (rawResult[3] instanceof String) ? (String) rawResult[3] : null;
            String roomTypeDescription = (rawResult[4] instanceof String) ? (String) rawResult[4] : null;
            Integer priceHour = (rawResult[5] instanceof Number) ? ((Number) rawResult[5]).intValue() : null;
            Integer priceDay = (rawResult[6] instanceof Number) ? ((Number) rawResult[6]).intValue() : null;
            String status = (rawResult[7] instanceof String) ? (String) rawResult[7] : null;

            // Create a new BookingRoomDTO with the extracted data
            RoomDetailDTO newRoom = new RoomDetailDTO(id, roomNum, roomTypeId, roomTypeName, roomTypeDescription,
                    priceHour,
                    priceDay, status);
            findRoom.add(newRoom);
        }

        return findRoom;
    }



    @Override
    @Transactional
    public void updateRoomStatusByRoomId2(Integer roomId) {
        roomRepository.updateRoomStatusByRoomId2(roomId);
    }

}
