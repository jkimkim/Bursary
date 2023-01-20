package com.example.bursary;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class Student {
    private String firstName;
    private String Email;
    private String phoneNumber;
    private String dateOfBirth;
    private String admissionNumber;
    private String institutionName;
    private String courseName;
    private String institutionPhoneNumber;
    private String yearOfStudy;
    private String bankName;
    private String accountNumber;
    private String branchName;
    private String District;
    private String Division;
    private String Location;
    private String Ward;
    private String Constituency;
    private String subLocation;
    private String Village;
    private List<String> downloadUrls;
    private String id;

    public Student(String firstName, String email, String phoneNumber, String dateOfBirth, String admissionNumber, String institutionName, String courseName, String institutionPhoneNumber, String yearOfStudy, String bankName, String accountNumber, String branchName, String district, String division, String location, String ward, TextInputEditText constituency, String subLocation, String village) {
    }

    public Student(String firstName, String Email, String phoneNumber, String dateOfBirth, String admissionNumber, String institutionName, String courseName, String institutionPhoneNumber, String yearOfStudy, String bankName, String accountNumber, String branchName, String District, String Division, String Location, String Ward, String Constituency, String subLocation, String Village, List<String> downloadUrls) {
        this.firstName = firstName;
        this.Email = Email;
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
        this.District = District;
        this.Division = Division;
        this.Location = Location;
        this.Ward = Ward;
        this.Constituency = Constituency;
        this.subLocation = subLocation;
        this.Village = Village;
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

    public void setEmail(String Email) {
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

    public void setDistrict(String District) {
        this.District = District;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String Division) {
        this.Division = Division;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String Ward) {
        this.Ward = Ward;
    }

    public String getConstituency() {
        return Constituency;
    }

    public void setConstituency(String Constituency) {
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

    public void setVillage(String Village) {
        this.Village = Village;
    }

    public List<String> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(List<String> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

}
