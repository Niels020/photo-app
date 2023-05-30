package myproject;

import java.util.Random;

public class Customer {
    private String name;
    private String address;
    private String zipcode;
    private String city;
    private String email;
    private String phoneNumber;
    private String customerId;

    // Constructor
    public Customer(String name, String address, String zipcode, String city, String email, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerId = generateCustomerId();
    }

        // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    // Random customer ID generator
    private String generateCustomerId() {
        int idLength = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder idBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < idLength; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            idBuilder.append(randomChar);
        }

        return idBuilder.toString();
    }
}
