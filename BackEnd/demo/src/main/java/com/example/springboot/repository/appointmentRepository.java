package com.example.springboot.repository;

import com.example.springboot.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface appointmentRepository extends JpaRepository<Appointment,Long>{
    @Query("SELECT a FROM Appointment a WHERE a.doc.id = :doctorId")
    List<Appointment> findByDocId(@Param("doctorId") Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId")
    List<Appointment> findByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT(a) FROM Appointment a WHERE a.user.id = :userId AND a.doa = :date")
    List<Appointment> findByUserIdAndDoa(@Param("userId") Long userId, @Param("date") String date);
    @Query("SELECT DISTINCT(a) FROM Appointment a WHERE a.user.id = :userId AND DATE(a.doa) < :date")
    List<Appointment> findByUserIdAndDoaHist(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT DISTINCT(a) FROM Appointment a WHERE a.user.id = :userId AND DATE(a.doa) > :date")
    List<Appointment> findByUserIdAndDoaFut(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE a.doc.id = :doctorId AND a.doa = :date AND a.appointment_acceptation= :accept")
    List<Appointment> findByDocIdAndDoa(@Param("doctorId") Long doctorId, @Param("date") String date,  @Param("accept")  String acception);

    @Query(value = "SELECT a.appointment_id, a.start_time, a.doa, a.doc_id, a.user_id, a.appointment_description, a.appointment_acceptation FROM Appointment a NATURAL JOIN doctor NATURAL JOIN user u WHERE a.doc_id = :doctorId AND" +
            " u.user_name LIKE %:name% AND a.appointment_acceptation= :accept", nativeQuery = true)
    List<Appointment> findByDocIdAndName(@Param("doctorId") Long doctorId, @Param("name") String name,  @Param("accept")  String acception);


    @Query(value = "SELECT a.appointment_id, a.start_time, a.doa, a.doc_id, a.user_id, a.appointment_description, a.appointment_acceptation" +
            " FROM Appointment a WHERE a.doc_id = :doctorId AND " +
            "a.doa = :date AND a.start_time= :time", nativeQuery = true)
    List<Appointment> findAppointmentByTime(@Param("doctorId") Long doctorId,@Param("date")  String date, @Param("time") String time);

    @Query("SELECT a FROM Appointment a WHERE a.doc.id = :doctorId AND a.appointment_acceptation= :accept")
    List<Appointment> findByDocIdAndAccept(@Param("doctorId") Long doctorId,  @Param("accept")  String acception);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId")
    List<Appointment> getCount(@Param("userId") Long userId);


}
