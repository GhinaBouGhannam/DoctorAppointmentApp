package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.service.appointmentService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
    @RequestMapping("/api/healthub/appointment")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public class AppointmentController {

        @Autowired
        private appointmentService appointmentService;


        @PostMapping("/add")
        public ResponseEntity<?> createAppointment(@Valid @RequestBody Appointment appointment) throws JsonProcessingException {
            Appointment savedAppointment;
            try {
                savedAppointment = appointmentService.createAppointment(appointment);
           }catch(IllegalArgumentException e ){
                return new ResponseEntity<>("No appointments accepted at this day",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Object> updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment appointmentDetails) {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
            return ResponseEntity.ok(updatedAppointment);
        }
    @GetMapping("/count/{id}")
    public ResponseEntity<?> countAppointment(@PathVariable Long id){
        return new ResponseEntity<>(appointmentService.getAppointmentsNum(id),HttpStatus.OK);
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/user/{userId}")
        public ResponseEntity<List<Appointment>> getAppointmentsByUser(@PathVariable Long userId) {
            List<Appointment> appointments = appointmentService.getAppointmentsByUser(userId);
            return ResponseEntity.ok(appointments);
        }

        @GetMapping("/doctor/{doctorId}")
        public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
            List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
            return ResponseEntity.ok(appointments);
        }


        @PostMapping("/accept/{id}")
        public ResponseEntity<Appointment> acceptAppointment(@PathVariable Long id) {
            Appointment acceptedAppointment = appointmentService.acceptAppointment(id);
            return ResponseEntity.ok(acceptedAppointment);
        }

        @PostMapping("/reject/{id}")
        public ResponseEntity<Appointment> rejectAppointment(@PathVariable Long id) {
            Appointment rejectedAppointment = appointmentService.rejectAppointment(id);
            return ResponseEntity.ok(rejectedAppointment);
        }
    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<List<?>>
    getAppointmentsForUserByDate(@PathVariable Long userId,
                                 @PathVariable String date) {
        List<Appointment> appointments = appointmentService.getAppointmentsForUserByDate(userId, date);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/hist/{userId}/date/{date}")
    public ResponseEntity<List<?>>
    getAppointmentsForUserByDateHist(@PathVariable Long userId,
                                 @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.getAppointmentsForUserByDateHist(userId, date);
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("/user/future/{userId}/date/{date}")
    public ResponseEntity<List<?>>
    getAppointmentsForUserByDateFut(@PathVariable Long userId,
                                     @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.getAppointmentsForUserByDateFut(userId, date);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}/date/{date}/accept/{acception}")
    public ResponseEntity<List<Appointment>>
    getAppointmentsForDoctorByDate(@PathVariable Long doctorId, @PathVariable String acception,
                                   @PathVariable String date) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctorByDate(doctorId, date,acception);
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("/doctor/{doctorId}/name/{name}/accept/{acception}")
    public ResponseEntity<List<Appointment>>
    getAppointmentsForDoctorByName(@PathVariable Long doctorId, @PathVariable String acception,
                                   @PathVariable String name) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctorByName(doctorId, name,acception);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}/accept/{acception}")
    public ResponseEntity<List<Appointment>>
    getAppointmentsForDoctorByAccept(@PathVariable Long doctorId, @PathVariable String acception) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctorByAccept(doctorId,acception);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}/date/{date}/time/{time}")
    public ResponseEntity<List<Appointment>>
    getAppointmentTime(@PathVariable Long doctorId, @PathVariable String date,@PathVariable String time) {
        List<Appointment> appointments = appointmentService.findAppointmentsByTime(doctorId,date,time);
        return ResponseEntity.ok(appointments);
    }

}

