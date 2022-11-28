package com.example.bursary;

import java.io.Serializable;
import java.util.List;

public class Upload implements Serializable {
    List<String> downloadUrls;
    String userId;
    String uploadId;
    String gender;
    String status;
    String appDate;
    private String name, phone, email, date, admNo, course, institutionPhoneNo,
            institution, bankName, bankAccountNo, bankBranch, district, division, location, ward, constituency, subLocation, village;

    public Upload(List<String> downloadUrls, String userId, String uploadId, String gender,String status,String appDate,String name, String phone, String email, String date, String admNo, String course, String institutionPhoneNo, String institution, String bankName, String bankAccountNo, String bankBranch, String district, String division, String location, String ward, String constituency, String subLocation, String village) {
        this.downloadUrls = downloadUrls;
        this.userId = userId;
        this.uploadId=uploadId;
        this.gender=gender;
        this.status=status;
        this.appDate=appDate;
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
    }

    public Upload() {
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public List<String> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(List<String> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
