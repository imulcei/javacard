package fr.afpa.models;

import fr.afpa.controllers.ContactController;

public class Contact {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String birthday;
    private String pseudo;
    private String address;
    private String persoPhoneNum;
    private String proPhoneNum;
    private String email;
    private String githubPage;
    private ContactController contactController;

    public Contact(String firstName, String lastName, Gender gender, String birthday, String pseudo,
            String address, String persoPhoneNum, String proPhoneNum, String email, String githubPage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.pseudo = pseudo;
        this.address = address;
        this.persoPhoneNum = persoPhoneNum;
        this.proPhoneNum = proPhoneNum;
        this.email = email;
        this.githubPage = githubPage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersoPhoneNum() {
        return persoPhoneNum;
    }

    public void setPersoPhoneNum(String persoPhoneNum) {
        this.persoPhoneNum = persoPhoneNum;
    }

    public String getProPhoneNum() {
        return proPhoneNum;
    }

    public void setProPhoneNum(String proPhoneNum) {
        this.proPhoneNum = proPhoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithubPage() {
        return githubPage;
    }

    public void setGithubPage(String githubPage) {
        this.githubPage = githubPage;
    }

    public ContactController getContactController() {
        return contactController;
    }

    public void setContactController(ContactController contactController) {
        this.contactController = contactController;
    }

}
