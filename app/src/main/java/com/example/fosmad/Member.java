package com.example.fosmad;

public class Member {

    private String Name;
    private String Address;
    private String ConNo;
    private String id;

    public Member() {
    }

    public Member(String name, String address, String conNo, String id) {
        Name = name;
        Address = address;
        ConNo = conNo;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getConNo() {
        return ConNo;
    }

    public void setConNo(String conNo) {
        ConNo = conNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
