package com.isp.project.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomTypeDetailDTO;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Room;
import com.isp.project.model.RoomType;
import com.isp.project.repositories.RoomTypeRepository;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomTypeService roomTypeService;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public List<RoomTypeDetailDTO> getAllRoomTypesWithDetails() {
        return roomTypeRepository.findAllRoomTypesWithDetails();
    }

    @Override
    public RoomTypeDetailDTO getRoomTypeDetailById(Integer id) {
        return roomTypeRepository.findRoomTypeDetailByRoomTypeId(id);
    }

    @Override
    public Boolean create(RoomType roomType) {
        try {
            this.roomTypeRepository.save(roomType);
            return true;

        } catch (Exception e) {
            // Ghi log chi tiết hơn để dễ dàng chẩn đoán lỗi
            System.err.println("Failed to save RoomType: " + roomType);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<RoomType> getAll() {
        return this.roomTypeRepository.findAll();
    }

    @Override
    public RoomType findByID(Integer id) {
        return this.roomTypeRepository.findById(id).get();
    }

    @Override
    public Boolean update(RoomType roomType) {
        try {
            this.roomTypeRepository.save(roomType);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            this.roomTypeRepository.delete(findByID(id));
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    public List<RoomType> findAllActive() {
        return roomTypeRepository.findAll().stream()
            .filter(roomType -> roomType.getRoomTypeActive() == 1)
            .toList();
    }

    public List<RoomType> findAllInactive() {
        return roomTypeRepository.findAll().stream()
            .filter(roomType -> roomType.getRoomTypeActive() == 0)
            .toList();
    }

    public void updateRoomTypeActiveStatus(int id, int status) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("RoomType not found"));
        roomType.setRoomTypeActive(status);
        roomTypeRepository.save(roomType);
    }

    @Override
    public List<RoomType> searchRoomType(String name) {
     return this.roomTypeRepository.searchRoomType(name);   
    }

    @Override
    public List<String> rateUseRoomTypeByMonth(int month, int year) {
        List<RoomType> roomTypes = this.roomTypeRepository.findAll();
        List<String> result = new ArrayList<>();
        int totalDaysInMonth = YearMonth.of(year, month).lengthOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
        int totalBookedDays = 0; // tổng số ngày phòng đã được đặt trong tháng
    
        // Tính tổng số ngày phòng đã được đặt trong tháng
        for (RoomType roomType : roomTypes) {
            for (Room room : roomType.getRoom()) {
                for (BookingMapping bookingMapping : room.getBookingMapping()) {
                    LocalDate checkIn = LocalDate.parse(dateFormat.format(bookingMapping.getCheckInDate()), formatter);
                    LocalDate checkOut = LocalDate.parse(dateFormat.format(bookingMapping.getCheckOutDate()), formatter);
    
                    if ((checkIn.getYear() == year && checkIn.getMonthValue() == month) ||
                        (checkOut.getYear() == year && checkOut.getMonthValue() == month) ||
                        (checkIn.getYear() < year && checkOut.getYear() > year) ||
                        (checkIn.getYear() == year && checkOut.getYear() == year && 
                         checkIn.getMonthValue() < month && checkOut.getMonthValue() > month)) {
                        
                        LocalDate start = checkIn.getYear() == year && checkIn.getMonthValue() == month ? checkIn : LocalDate.of(year, month, 1);
                        LocalDate end = checkOut.getYear() == year && checkOut.getMonthValue() == month ? checkOut : LocalDate.of(year, month, totalDaysInMonth);
                        
                        totalBookedDays += (int) ChronoUnit.DAYS.between(start, end);
                    }
                }
            }
        }
    
        // Tính phần trăm sử dụng cho từng loại phòng
        for (RoomType roomType : roomTypes) {
            int bookedDays = 0;
    
            for (Room room : roomType.getRoom()) {
                for (BookingMapping bookingMapping : room.getBookingMapping()) {
                    LocalDate checkIn = LocalDate.parse(dateFormat.format(bookingMapping.getCheckInDate()), formatter);
                    LocalDate checkOut = LocalDate.parse(dateFormat.format(bookingMapping.getCheckOutDate()), formatter);
    
                    if ((checkIn.getYear() == year && checkIn.getMonthValue() == month) ||
                        (checkOut.getYear() == year && checkOut.getMonthValue() == month) ||
                        (checkIn.getYear() < year && checkOut.getYear() > year) ||
                        (checkIn.getYear() == year && checkOut.getYear() == year && 
                         checkIn.getMonthValue() < month && checkOut.getMonthValue() > month)) {
                        
                        LocalDate start = checkIn.getYear() == year && checkIn.getMonthValue() == month ? checkIn : LocalDate.of(year, month, 1);
                        LocalDate end = checkOut.getYear() == year && checkOut.getMonthValue() == month ? checkOut : LocalDate.of(year, month, totalDaysInMonth);
                        
                        bookedDays += (int) ChronoUnit.DAYS.between(start, end);
                    }
                }
            }
    
            double usageRate = ((double) bookedDays / totalBookedDays) * 100;
            result.add(roomType.getName() + " - " + String.format("%.2f", usageRate) + "%");
        }
    
        return result;
    }

    @Override
    public boolean existsByRoomTypeName(String name) {
        String trimmedName = name.replaceAll("\\s+", "").toLowerCase();
        return roomTypeRepository.existsByRoomTypeName(trimmedName);
    }


}
