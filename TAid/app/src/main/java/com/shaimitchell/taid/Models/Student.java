package com.shaimitchell.taid.Models;

/**
 * Model class to create and manage student entries
 */
public class Student {

    private String url;
    private String universityId;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private String email;

    public Student(){}

    public Student(String url, String universityId, String studentNumber, String firstName,
                   String lastName, String email){

        setUrl(url);
        setUniversityId(universityId);
        setStudentNumber(studentNumber);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
