package com.example.bursary;

import java.io.Serializable;

public class FetchUserData implements Serializable {

    String name;
    String email;
    String phone;
    String date;
    String admNo;
    String course;
    String institution;
    String institutionPhoneNo;
    String bankName;
    String bankAccountNo;
    String bankBranch;
    String district;
    String division;
    String location;
    String ward;
    String constituency;
    String subLocation;
    String village;

    public FetchUserData(String name, String email, String date, String admNo, String course, String institution, String phone, String institutionPhoneNo, String bankName, String bankAccountNo, String bankBranch, String district, String division, String location, String ward, String constituency, String subLocation, String village) {
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

    public FetchUserData(){
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdmNo(String admNo) {
        this.admNo = admNo;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setInstitutionPhoneNo(String institutionPhoneNo) {
        this.institutionPhoneNo = institutionPhoneNo;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
