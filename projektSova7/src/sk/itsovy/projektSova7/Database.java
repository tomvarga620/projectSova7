package sk.itsovy.projektSova7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements carmethods {

    private final String username="root";
    private final String password="Dadada5";
    private final String host = "localhost";
    private final String port = "3306";
    private final String url = "jdbc:mysql://localhost:3306/sova7";

    private Connection getConnection(){
        Connection connection;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DriverLoaded");

            connection = DriverManager.getConnection(url, username, password);
            return connection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void closeConnection(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public void addCar(Car car) {
        try {
            Connection conn = getConnection();
            String sql;
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO cars(brand,color,fuel,spz,price) values(?,?,?,?,?)");
            stmt.setString(1,car.getBrand());
            stmt.setString(2,car.getColor());
            stmt.setString(3,car.getFuel());
            stmt.setString(4,car.getSpz());
            stmt.setString(5,car.getPrice());

            int result = stmt.executeUpdate();

            closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getCarsByPrice(int maxPrice) {

        Connection conn = getConnection();

        List <Car> cars = new ArrayList<>();
        try {

            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select * from cars where price=? ");
            pst.setString(1, Integer.toString(maxPrice));
            rs = pst.executeQuery();
            while (rs.next()) {
                Car car1 = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel")
                        ,rs.getString("spz"),rs.getString("price"));
                cars.add(car1);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByBrand(String brand) {

        Connection conn = getConnection();

        List <Car> cars = new ArrayList<>();
        try {

            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select * from cars where brand=? ");
            pst.setString(1, brand);
            rs = pst.executeQuery();
            while (rs.next()) {
                Car car1 = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel")
                        ,rs.getString("spz"),rs.getString("price"));
                cars.add(car1);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByFuel(char fuel) {

        Connection conn = getConnection();

        List <Car> cars = new ArrayList<>();
        try {

            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select * from cars where fuel=? ");
            pst.setString(1, Character.toString(fuel));
            System.out.println(pst);
            rs = pst.executeQuery();
            while (rs.next()) {
                Car car1 = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel")
                        ,rs.getString("spz"),rs.getString("price"));
                cars.add(car1);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByRegion(String spz) {

        Connection conn = getConnection();

        String region = spz.substring(0,2);
        char symbol = '%';

        List <Car> cars = new ArrayList<>();
        try {

            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select * from cars where spz like ?");
            pst.setString(1,region+'%');
            System.out.println(pst);
            rs = pst.executeQuery();
            while (rs.next()) {
                Car car1 = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel")
                        ,rs.getString("spz"),rs.getString("price"));
                cars.add(car1);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void changeSPZ(String oldSPZ, String newSPZ) {
        try {
            Connection conn = getConnection();
            String sql;
            PreparedStatement stmt = conn.prepareStatement("UPDATE cars SET spz=? WHERE spz like ?");
            stmt.setString(1,newSPZ);
            stmt.setString(2,oldSPZ);

            int result = stmt.executeUpdate();

            closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void generateXML() {

        Connection conn = getConnection();
        //String query = "SELECT * FROM persons WHERE dnar <= Current_date() - 18 ";
        String query = "SELECT * FROM cars";
        List <Car> cars = new ArrayList<>();

        try {

            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {

                Car car = new Car(rs.getString("brand"),rs.getString("color"),
                rs.getString("fuel"),rs.getString("spz"),rs.getString("price"));
                cars.add(car);
            }

            System.out.println(cars.size());

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootCars = doc.createElement("Cars");
            doc.appendChild(rootCars);

            for (int i = 0; i < cars.size(); i++) {

                Element car = doc.createElement("Car");
                rootCars.appendChild(car);

                Element brand = doc.createElement("Brand");
                brand.appendChild(doc.createTextNode(cars.get(i).getBrand()));// brand
                car.appendChild(brand);

                Element color = doc.createElement("Color");
                color.appendChild(doc.createTextNode(cars.get(i).getColor()));// color
                car.appendChild(color);

                Element fuel = doc.createElement("Fuel");
                fuel.appendChild(doc.createTextNode(cars.get(i).getFuel()));// fuel
                car.appendChild(fuel);

                Element spz = doc.createElement("Spz");
                spz.appendChild(doc.createTextNode(cars.get(i).getSpz()));// spz
                car.appendChild(spz);

                Element price = doc.createElement("Price");
                price.appendChild(doc.createTextNode(cars.get(i).getPrice()));// price
                car.appendChild(price);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("cars.xml"));

            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("File saved!");

    }
}
