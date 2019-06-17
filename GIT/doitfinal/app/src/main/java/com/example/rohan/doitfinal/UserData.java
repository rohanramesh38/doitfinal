package com.example.rohan.doitfinal;

public class UserData {

    private String Address,DOB,Dept,Email,Name,Phone,Photo,Password;

    public UserData() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserData(String address, String DOB, String dept, String email, String name, String phone, String photo, String password) {
        Address = address;
        this.DOB = DOB;
        Dept = dept;
        Email = email;
        Name = name;
        Phone = phone;
        Photo = photo;
        Password = password;
    }
}
