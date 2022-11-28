package com.example.bursary;

import java.io.Serializable;

public class User implements Serializable {
    private String name, phone, email, date, admNo, course, institutionPhoneNo,
            institution, bankName, bankAccountNo, bankBranch, district, division, location, ward, constituency, subLocation, village,id;

    public User(String name, String phone, String email, String date, String admNo, String course, String institutionPhoneNo, String institution, String bankName, String bankAccountNo, String bankBranch, String district, String division, String location, String ward, String constituency, String subLocation, String village, String id) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.date = date;
        this.admNo = admNo;
        this.course = course;
        this.institutionPhoneNo = institutionPhoneNo;
        this.institution = institution;
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
        this.id = id;

    }

    public User(){

    }

public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getAdmNo() {
        return admNo;
    }

    public String getCourse() {
        return course;
    }

    public String getInstitutionPhoneNo() {
        return institutionPhoneNo;
    }

    public String getInstitution() {
        return institution;
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

    public String getId() {
        return id;
    }
}

