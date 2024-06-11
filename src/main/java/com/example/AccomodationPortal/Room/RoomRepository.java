package com.example.AccomodationPortal.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM room r WHERE r.available=1"
    )
    List<Room> getAllAvailableRooms();
}
