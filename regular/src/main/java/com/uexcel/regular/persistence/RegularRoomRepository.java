package com.uexcel.regular.persistence;

import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.model.ReservationDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RegularRoomRepository extends JpaRepository<RegularRoom, Long> {
    @Query(value = "SELECT  d FROM  ReservationDates d WHERE d.regularRoom.roomNumber=:roomNumber ")
    List<ReservationDates> findByRoomNumberJpql(@Param("roomNumber") String roomNumber);
    boolean existsByRoomNumber ( String roomNumber);
    RegularRoom findByRoomNumber (String roomNumber);
}
