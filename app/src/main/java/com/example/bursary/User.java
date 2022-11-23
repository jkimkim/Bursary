package com.example.bursary;

import java.io.Serializable;

public class User implements Serializable {
    public String name, email, date, admNo, course,
            institution, phone, institutionPhoneNo, bankName, bankAccountNo, bankBranch, district, division, location, ward, constituency, subLocation, village;

    public User(String name, String email, String date, String phone, String admNo, String course, String institution, String institutionPhoneNo, String bankName, String bankAccNo, String bankBranch, String district, String division, String location, String ward, String constituency, String subLocation, String village){

    }
    public User(String name, String email, String date){
        this.date = date;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.admNo = admNo;
        this.course = course;
        this.institution = institution;
        this.institutionPhoneNo = institutionPhoneNo;
        this.bankName = bankName;
        this.bankAccountNo = bankAccountNo;
        this.bankBranch = bankBranch;
        this.district = district;
        this.division = division;
        this.location = location;
        this.ward = ward;
        this.constituency = constituency;
        this.subLocation = subLocation;
        this.village = village;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdmNo() {
        return admNo;
    }

    public String getCourse() {
        return course;
    }

    public String getInstitution() {
        return institution;
    }

    public String getInstitutionPhoneNo() {
        return institutionPhoneNo;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public String getDistrict() {
        return district;
    }

    public String getDivision() {
        return division;
    }

    public String getLocation() {
        return location;
    }

    public String getWard() {
        return ward;
    }

    public String getConstituency() {
        return constituency;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public String getVillage() {
        return village;
    }

}

