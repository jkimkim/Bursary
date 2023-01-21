package com.example.bursary;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    List<String> downloadUrls;
    String userId;
    String uploadId;
    String Gender;
    String status;
    String appDate;
    private String firstName, Email, phoneNumber, dateOfBirth, admissionNumber, institutionName, courseName, institutionPhoneNumber, yearOfStudy,
            bankName, accountNumber, branchName, District, Division, Location, Ward, Constituency, subLocation, Village;

    public Student(List<String> downloadUrls, String userId, String uploadId, String firstName, String email, String phoneNumber, String dateOfBirth, String admissionNumber, String institutionName, String courseName, String institutionPhoneNumber, String yearOfStudy, String bankName, String accountNumber, String branchName, String district, String division, String location, String ward, String constituency, String subLocation, String village) {
        this.downloadUrls = downloadUrls;
        this.userId = userId;
        this.uploadId = uploadId;
        this.firstName = firstName;
        this.Email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.admissionNumber = admissionNumber;
        this.institutionName = institutionName;
        this.courseName = courseName;
        this.institutionPhoneNumber = institutionPhoneNumber;
        this.yearOfStudy = yearOfStudy;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.branchName = branchName;
        this.District = district;
        this.Division = division;
        this.Location = location;
        this.Ward = ward;
        this.Constituency = constituency;
        this.subLocation = subLocation;
        this.Village = village;
    }

    public Student() {
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
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender=Gender;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(List<String> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = Email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstitutionPhoneNumber() {
        return institutionPhoneNumber;
    }

    public void setInstitutionPhoneNumber(String institutionPhoneNumber) {
        this.institutionPhoneNumber = institutionPhoneNumber;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        this.District = District;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        this.Division = Division;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = Location;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        this.Ward = Ward;
    }

    public String getConstituency() {
        return Constituency;
    }

    public void setConstituency(String constituency) {
        this.Constituency = Constituency;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        this.Village = Village;
    }
}
