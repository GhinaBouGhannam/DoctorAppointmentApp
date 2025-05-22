package com.example.springboot.service;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.appointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
    @Service
    public class appointmentService {

        @Autowired
        private appointmentRepository appointmentRepository;
        public Appointment createAppointment(Appointment appointment) {
            LocalDate currentDate = LocalDate.now();
            LocalDate tomorrow = currentDate.plusDays(1);

            if(LocalDate.parse(appointment.getDoa()).isEqual(currentDate) || LocalDate.parse(appointment.getDoa()).isBefore(currentDate) || LocalDate.parse(appointment.getDoa()).isEqual(tomorrow)){
                throw new IllegalArgumentException();
            }
          return  appointmentRepository.save(appointment);

        }

        public List<Appointment> getAppointmentsNum(long id){
           return appointmentRepository.getCount(id);
        }

        public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
            Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
            if (appointmentOptional.isPresent()) {
                Appointment appointment = appointmentOptional.get();
                appointment.setDoa(appointmentDetails.getDoa());
                appointment.setStart_time(appointmentDetails.getStart_time());
                appointment.setAppointment_description(appointmentDetails.getAppointment_description());
                appointment.setUser(appointmentDetails.getUser());
                appointment.setDoc(appointmentDetails.getDoc());
              appointment.setAppointment_acception(appointmentDetails.getAppointment_acception());
                return appointmentRepository.save(appointment);
            } else {
                return null;
            }
        }

        public Map<String, Boolean> deleteAppointment(Long id) {
            Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
            if (appointmentOptional.isPresent()) {
                Appointment appointment = appointmentOptional.get();
                appointmentRepository.delete(appointment);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return response;
            } else {
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.FALSE);
                return response;
            }
        }

        public List<Appointment> getAllAppointments() {
            return appointmentRepository.findAll();
        }

        public List<Appointment> getAppointmentsByUser(Long userId) {
            return appointmentRepository.findByUserId(userId);
        }

        public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
            return appointmentRepository.findByDocId(doctorId);
        }

        public List<Appointment> getAppointmentsForUserByDate(Long userId, String date) {
            return appointmentRepository.findByUserIdAndDoa(userId, date);
        }
        public List<Appointment> getAppointmentsForUserByDateHist(Long userId, LocalDate date) {
            return appointmentRepository.findByUserIdAndDoaHist(userId, date);
        }
        public List<Appointment> getAppointmentsForUserByDateFut(Long userId, LocalDate date) {
            return appointmentRepository.findByUserIdAndDoaFut(userId, date);
        }

        public List<Appointment> getAppointmentsForDoctorByDate(Long doctorId, String date, String acception) {
            return appointmentRepository.findByDocIdAndDoa(doctorId, date,acception);
        }
        public List<Appointment> getAppointmentsForDoctorByName(Long doctorId, String name, String acception) {
            return appointmentRepository.findByDocIdAndName(doctorId, name,acception);
        }
        public List<Appointment> findAppointmentsByTime(Long doctorId, String date, String time) {
            return appointmentRepository.findAppointmentByTime(doctorId,date,time);
        }
        public List<Appointment> getAppointmentsForDoctorByAccept(Long doctorId, String acception) {
            return appointmentRepository.findByDocIdAndAccept(doctorId,acception);
        }

        public Appointment acceptAppointment(Long id) {
            Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
            if (appointmentOptional.isPresent()) {
                Appointment appointment = appointmentOptional.get();
                appointment.setAppointment_acception("1");
                return appointmentRepository.save(appointment);
            } else {
                return null;
            }
        }

        public Appointment rejectAppointment(Long id) {
            Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
            if (appointmentOptional.isPresent()) {
                Appointment appointment = appointmentOptional.get();
                appointment.setAppointment_acception("0");
                return appointmentRepository.save(appointment);
            } else {
                return null;
            }
        }

    }
