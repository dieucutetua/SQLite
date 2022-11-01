package com.example.sqllite;

public class Computer {
    private int id;
    private String tenMay;

    public Computer(int id, String tenMay) {
        this.id = id;
        this.tenMay = tenMay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMay() {
        return tenMay;
    }

    public void setTenMay(String tenMay) {
        this.tenMay = tenMay;
    }
}
