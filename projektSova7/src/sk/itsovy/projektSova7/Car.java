package sk.itsovy.projektSova7;

import java.util.List;

public class Car {

    private String brand;
    private String color;
    private String fuel;
    private String spz;
    private String price;

    public Car(String brand, String color, String fuel, String spz, String price) {

        this.brand = brand;
        this.color = color;
        this.fuel = fuel;
        this.spz = spz;
        this.price = price;

    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getFuel() {
        return fuel;
    }

    public String getSpz() {
        return spz;
    }

    public String getPrice() {
        return price;
    }
}
