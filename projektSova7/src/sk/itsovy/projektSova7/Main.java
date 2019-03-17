package sk.itsovy.projektSova7;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*Car chiron = new Car("Bugatti","red","P","KE302LM","3260000");
        Car veyron = new Car("Bugatti","blue","P","KS201LM","1000000");*/

        Database db1 = new Database();
       /*db1.addCar(chiron);
        db1.addCar(veyron);*/

        /*List<Car> cars = db1.getCarsByPrice(3260000);
        System.out.println(cars);
        for(Car found: cars){
            System.out.println(found.getBrand()+" "+found.getPrice());
        }*/

        /*List<Car> cars = db1.getCarsByRegion("KS302LM");
        for(Car found: cars){
            System.out.println(found.getBrand()+" "+found.getPrice());
        }*/

        //db1.changeSPZ("KE302LM","NEW");

        db1.generateXML();
    }

}
