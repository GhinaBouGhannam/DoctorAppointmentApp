package com.example.springboot.model;


import com.example.springboot.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Table(name = "User")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(name = "user_name", unique = true)
   @NotEmpty(message = "Username can't be empty")
   @Size(min = 2, message = "Username should have at least 2 characters")
    private String user_name;

    @Column(name = "phone_number", nullable = false)
    @NotEmpty(message = "phone number name can't be empty")
    @Size(min = 8,max=8, message = "phone number should have 8 characters")
    private String phone_number;

    // password should not be null or empty
    // password should have at least 8 characters
    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password can't be empty")
   @Size(min = 8, message = "password should have at least 8 characters")
    @ValidPassword(message = "Password should contain lower and upper case, number, and special character")
    private String password;

    public User() {

    }

    public User(String user_name, String phone_number, String password) {
        super();
        this.user_name = user_name;
        this.phone_number = phone_number;
        this.password = password;
    }
    public long getId() {
        return user_id;
    }
    public void setId(long id) {
        this.user_id = id;
    }
    public String getName() {
        return user_name;
    }
    public void setName(String name) {
        this.user_name = name;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}