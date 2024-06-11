package com.example.AccomodationPortal.Room;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room/v1/")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("createRoom")
    public String createARoom(@RequestBody Room room){
        roomService.createRoom(room);
        return "Room created successfully";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping("getAllRooms")
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping("getAllAvailableRooms")
    public List<Room> getAllAvailableRooms(){
        return roomService.getAllAvailableRooms();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("editRoomDetails/{id}")
    public String editRoomDetails(@PathVariable("id") Long id, @RequestBody Room room){
        return roomService.editRoomDetails(id, room);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deleteRoom/{id}")
    public String deleteARoom(@PathVariable("id") Long id){
        return roomService.deleteARoom(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping("fetchRoomDetails/{id}")
    public Room fetchRoomDetails(@PathVariable("id") Long id){
        return roomService.fetchRoomDetails(id);
    }
}
