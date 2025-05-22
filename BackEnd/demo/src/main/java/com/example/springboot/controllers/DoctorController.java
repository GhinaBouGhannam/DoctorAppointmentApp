package com.example.springboot.controllers;

import com.example.springboot.model.Doctor;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.springboot.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/healthub/doctor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorController {

    private String docname, password;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable long id) {
        Doctor doctors = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctors);
    }
    @GetMapping("/top")
    public ResponseEntity<List<Doctor>> getTopDoctorsWithAppointmentCount() {
        List<Doctor> dc = doctorService.findTopDoctorsWithAppointmentCount();
        return ResponseEntity.ok(dc);
    }


    @PostMapping("/add")
    public ResponseEntity<?> createDoctor(@Valid @RequestBody Doctor doctor) {
        Doctor savedDoctor;
        try{
            savedDoctor = doctorService.createDoctor(doctor);}
        catch(IllegalArgumentException e ){
            return new ResponseEntity<>("Email already exists",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor doctorDetails) {
        Doctor updatedDoctor;
        try{
            updatedDoctor = doctorService.updateDoctor(id, doctorDetails);}
        catch(IllegalArgumentException e ){
            return new ResponseEntity<>("Email already exists",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

   @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getDoctorsByCategory(@PathVariable String specialty) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialty(specialty);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/specialty/{specialty}/doc/{name}")
    public ResponseEntity<List<Doctor>> getDoctorsByCategoryAndName(@PathVariable String specialty,@PathVariable String name) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialtyAndName(specialty,name);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/doctorname/{docname}")
    public ResponseEntity<List<Doctor>> getDoctorsByName(@PathVariable String docname) {
        List<Doctor> doctors = doctorService.getDoctorsByName(docname);
        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Doctor> authenticate( @RequestBody Doctor dr) {
        this.docname  = dr.getDoc_email();
        this.password = dr.getDoc_password();
        Doctor doctor = doctorService.authenticate(docname, password);
        return ResponseEntity.ok(doctor);
    }


}
