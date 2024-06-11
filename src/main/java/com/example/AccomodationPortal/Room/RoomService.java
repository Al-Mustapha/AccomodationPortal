package com.example.AccomodationPortal.Room;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    public void createRoom(Room room) {
        roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public String editRoomDetails(Long id, Room room) {
        Room updatedRoom =
                roomRepository.findById(id).get();
        updatedRoom.setCategory(room.getCategory());
        updatedRoom.setBlock(room.getBlock());
        updatedRoom.setRoomNumber(room.getRoomNumber());
        roomRepository.save(updatedRoom);
        return "Room details updated successfully";
    }

    public String deleteARoom(Long id) {
        roomRepository.deleteById(id);
        return "Room successfully deleted";
    }

    public List<Room> getAllAvailableRooms() {
        return roomRepository.getAllAvailableRooms();
    }

    public Room fetchRoomDetails(Long id) {
        return roomRepository.findById(id).get();
    }
}
