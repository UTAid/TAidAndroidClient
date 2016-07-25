package com.shaimitchell.taid.Models;

/**
 * Created by shaimitchell on 16-07-24.
 */
public class Instructor {

    private String url;
    private String universityId;
    private String firstName;
    private String lastName;
    private String email;

    public Instructor(){}

    public Instructor(String url, String universityId, String firstName,
                   String lastName, String email){

        setUrl(url);
        setUniversityId(universityId);
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
