package com.example.springboot.service;

import com.example.springboot.model.Doctor;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private com.example.springboot.repository.doctorRepository doctorRepository;

    public Doctor createDoctor(Doctor doctor) {
        if( findByDocEmail(doctor.getDoc_email())){
            throw new IllegalArgumentException();
        }
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isPresent()) {

            Doctor doctor = doctorOptional.get();

            if(!doctorDetails.getDoc_email().equals(doctor.getDoc_email())){
                if( countUserName(doctorDetails.getDoc_email(),0)){
                    throw new IllegalArgumentException();
                }}
            else{System.out.println("Equal"); if( countUserName(doctorDetails.getDoc_email(),1)){
                throw new IllegalArgumentException();}

            }
            doctor.setDoc_fullname(doctorDetails.getDoc_fullname());
            doctor.setDoc_email(doctorDetails.getDoc_email());
            doctor.setDoc_password(doctorDetails.getDoc_password());
            doctor.setDoc_specialty(doctorDetails.getDoc_specialty());
            doctor.setDoc_availability(doctorDetails.getDoc_availability());
            doctor.setDoc_fees(doctorDetails.getDoc_fees());
            doctor.setDoc_description(doctorDetails.getDoc_description());
            doctor.setDoc_phonenumber(doctorDetails.getDoc_phonenumber());
            doctor.setDoc_address(doctorDetails.getDoc_address());
            return doctorRepository.save(doctor);
        } else {
            return null;
        }
    }
    public Doctor deleteDoctor(Long id) {
       Doctor doc = doctorRepository.findDocById(id);

            doctorRepository.delete(doc);
        return doc;
    }

    public Doctor authenticate(String doc_email, String doc_password) {
        Optional<Doctor> doctorOptional = doctorRepository.findByDocEmailAndDocPassword(doc_email, doc_password);
        return doctorOptional.orElse(null); // Return null if doctor not found
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findByDocSpecialty(specialty);
    }
    public List<Doctor> findTopDoctorsWithAppointmentCount() {
        return doctorRepository.findTopDoctorsWithAppointmentCount();
    }

    public Doctor getDoctorById(long id) {
        return doctorRepository.findDocById(id);
    }

    public boolean findByDocEmail(String name){
        if(doctorRepository.findByDocEmail(name).isPresent())
            return true;
        return false;
    }
    public boolean countUserName(String name,int x){
        int i= doctorRepository.countByEmail(name);
        return i>x;
    }

    public List<Doctor> getDoctorsByName(String docname) {
        return doctorRepository.findByDocName(docname);
    }

    public List<Doctor> getDoctorsBySpecialtyAndName(String specialty, String name) {
        return doctorRepository.findByDocSpecialtyAndName(specialty,name);
    }
}
