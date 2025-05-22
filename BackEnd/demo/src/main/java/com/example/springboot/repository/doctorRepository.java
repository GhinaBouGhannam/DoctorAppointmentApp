package com.example.springboot.repository;

import com.example.springboot.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface doctorRepository extends JpaRepository<Doctor,Long> {
    @Query("SELECT d FROM Doctor d WHERE d.doc_email = :docEmail AND d.doc_password = :docPassword")
    Optional<Doctor> findByDocEmailAndDocPassword(@Param("docEmail") String docEmail, @Param("docPassword") String docPassword);
    @Query(value = "SELECT d.doc_address, d.doc_email, d.doc_fees, d.doc_password, d.doc_availabilty, d.doc_phonenumber,d.doc_description,d.doc_id, d.doc_fullname, d.doc_specialty, COUNT(a.doc_id) AS appointmentCount " +
            "FROM Doctor d " +
            "NATURAL JOIN Appointment a " +
            "GROUP BY d.doc_id, d.doc_fullname, d.doc_specialty " +
            "ORDER BY appointmentCount DESC  Limit 6", nativeQuery = true)
    List<Doctor> findTopDoctorsWithAppointmentCount();
    @Query(value = "SELECT * FROM Doctor WHERE doc_specialty LIKE %:specialty%", nativeQuery = true)
    List<Doctor> findByDocSpecialty(@Param("specialty") String specialty);
    @Query("SELECT d FROM Doctor d WHERE d.doc_id = :id")
    Doctor findDocById(@Param("id")long id);

    @Query("SELECT d FROM Doctor d WHERE d.doc_email = :docemail")
    Optional<Doctor> findByDocEmail(@Param("docemail")String docemail);
    @Query("SELECT COUNT(u) FROM Doctor u WHERE u.doc_email = :docemail")
    int countByEmail(@Param("docemail")String docemail);
    @Query(value = "SELECT * FROM Doctor d WHERE d.doc_fullname LIKE %:docname%", nativeQuery = true)
    List<Doctor> findByDocName(@Param("docname")String docname);

    @Query(value = "SELECT * FROM Doctor WHERE doc_specialty LIKE %:specialty% AND doc_fullname LIKE %:name%", nativeQuery = true)
    List<Doctor> findByDocSpecialtyAndName(@Param("specialty")String specialty,@Param("name") String name);
}
