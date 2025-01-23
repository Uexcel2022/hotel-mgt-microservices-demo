package com.uexcel.apartment.persistence;

import com.uexcel.apartment.model.Apartment;
import com.uexcel.apartment.model.Reservation;
import com.uexcel.apartment.model.ReservationDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Query(value = "SELECT  d FROM  ReservationDates d WHERE d.apartment.apartmentCode=:apartmentCode ")
    List<ReservationDates> findByApartmentCodeJpql(@Param("apartmentCode") String apartmentCode);
    boolean existsByApartmentCode(String apartmentCode);
    Apartment findByApartmentCode(String apartmentCode);

    @Query (nativeQuery = true,value = "SELECT * from apartment where  apartment_code like 'A1%'")
    List<Apartment> findByA1Apartment();

    @Query (nativeQuery = true,value = "SELECT * from apartment where  apartment_code like 'A2%'")
    List<Apartment> findByA2Apartment();

}
