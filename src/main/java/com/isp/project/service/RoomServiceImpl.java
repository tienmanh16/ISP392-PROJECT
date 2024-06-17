package com.isp.project.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.dto.BookingRoomDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;
import com.isp.project.repositories.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsWithDetails() {
        return roomRepository.findAllRoomsWithDetails();
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id) {
        return roomRepository.findAllRoomsWithDetailsByRoomTypeId(id);
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsByStatus(String status) {
        return roomRepository.findAllRoomsByStatus(status);
    }

    // private int id;
    // private String roomNum;
    // private int roomTypeId;
    // private String roomTypeName;
    // private String roomTypeDescription;
    // private String priceHour;
    // private String priceDay;
    // private String status;
    @Override

    //public List<Object[]> findAvailableRooms(Date checkinDate, Date checkoutDate) 
    public List<RoomDetailDTO> getAvailableRooms(Date checkinDate, Date checkoutDate) {
        List<Object[]> rawResults = roomRepository.findAvailableRooms(checkinDate, checkoutDate);
        List<RoomDetailDTO> findRoom = new ArrayList<>();
        for (Object[] rawResult : rawResults) {

            Integer id = (rawResult[0] instanceof Integer) ? (Integer) rawResult[0] : null;
            String roomNum = (rawResult[1] instanceof String) ? (String) rawResult[1] : null;
            Integer roomTypeId = (rawResult[2] instanceof Number) ? ((Number) rawResult[2]).intValue() : null;
            String roomTypeName = (rawResult[3] instanceof String) ? (String) rawResult[3] : null;
            String roomTypeDescription = (rawResult[4] instanceof String) ? (String) rawResult[4] : null;
            String priceHour = (rawResult[5] instanceof String) ? (String) rawResult[5]
                    : (rawResult[5] != null ? rawResult[5].toString() : "");
            String priceDay = (rawResult[6] instanceof String) ? (String) rawResult[6]
                    : (rawResult[6] != null ? rawResult[6].toString() : "");
            String status = (rawResult[7] instanceof String) ? (String) rawResult[7] : null;

            // Create a new BookingRoomDTO with the extracted data
            RoomDetailDTO newRoom = new RoomDetailDTO(id, roomNum, roomTypeId, roomTypeName, roomTypeDescription,
                    priceHour,
                    priceDay, status);
            findRoom.add(newRoom);
        }

        return findRoom;
    }

}
