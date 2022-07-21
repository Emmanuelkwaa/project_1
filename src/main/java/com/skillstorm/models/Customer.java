package com.skillstorm.models;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String addresLine1;
    private String addresLine2;
    private String city;
    private char stateCode;
    private String zipcode;
    private String country;
    private String phone;
    private String email;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Customer(int id, String firstName, String lastName, String phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String addresLine1, String addresLine2, String city, char stateCode, String zipcode, String country, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresLine1 = addresLine1;
        this.addresLine2 = addresLine2;
        this.city = city;
        this.stateCode = stateCode;
        this.zipcode = zipcode;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }

    public Customer(int id, String firstName, String lastName, String addresLine1, String addresLine2, String city, char stateCode, String zipcode, String country, String phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresLine1 = addresLine1;
        this.addresLine2 = addresLine2;
        this.city = city;
        this.stateCode = stateCode;
        this.zipcode = zipcode;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddresLine1() {
        return addresLine1;
    }

    public void setAddresLine1(String addresLine1) {
        this.addresLine1 = addresLine1;
    }

    public String getAddresLine2() {
        return addresLine2;
    }

    public void setAddresLine2(String addresLine2) {
        this.addresLine2 = addresLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public char getStateCode() {
        return stateCode;
    }

    public void setStateCode(char stateCode) {
        this.stateCode = stateCode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addresLine1='" + addresLine1 + '\'' +
                ", addresLine2='" + addresLine2 + '\'' +
                ", city='" + city + '\'' +
                ", stateCode=" + stateCode +
                ", zipcode='" + zipcode + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
