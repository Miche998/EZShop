package it.polito.ezshop.util;

import it.polito.ezshop.data.*;
import it.polito.ezshop.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static Connection con;
    private static boolean hasData = false;

    public DBManager() {

    }

    public void getConnection() throws SQLException{
        // sqlite driver
        try {
            Class.forName("org.sqlite.JDBC");
            // database path, if it's new database, it will be created in the project folder
            con = DriverManager.getConnection("jdbc:sqlite:EZShop.db");
            initialise();
        } catch (ClassNotFoundException e) {
            System.out.println("Errore nella connessione con il db");
            throw new SQLException();
        }

    }


    private void initialise() throws SQLException {
        if( !hasData ) {
            hasData = true;
            // check for database table
            Statement state = con.createStatement();
            Statement state2 = con.createStatement();


            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Product'");
            if( !res.next()) {
                System.out.println("Building the Product table.");
                // need to build the table
                state2.executeUpdate("create table Product(Id integer,"
                        + "BarCode varchar(15)," + "Description varchar(20)," + "Note varchar(20),"
                        + "Location varchar(10)," + "Quantity integer,"
                        + "PricePerUnit double," + "primary key (Id));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Item'");
            if( !res.next()) {
                System.out.println("Building the Item table.");
                // need to build the table
                state2.executeUpdate("create table Item(BarCode varchar(15),"
                        + "RFID double," + "primary key (BarCode, RFID));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='ItemSold'");
            if( !res.next()) {
                System.out.println("Building the ItemSold table.");
                // need to build the table
                state2.executeUpdate("create table ItemSold(transactionId integer, " + "RFID double,"
                        + " BarCode varchar(15)," + "primary key (transactionId, RFID));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='ItemReturned'");
            if( !res.next()) {
                System.out.println("Building the ItemReturned table.");
                // need to build the table
                state2.executeUpdate("create table ItemReturned(returnId integer," + "RFID double,"
                        + " BarCode varchar(15)," + "primary key (returnId, RFID));");
            }


            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Customer'");
            if( !res.next()) {
                System.out.println("Building the Customer table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table Customer(Id integer,"
                        + "Name varchar(15)," + "Card varchar(20),"
                        + "primary key (Id));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='User'");
            if( !res.next()) {
                System.out.println("Building the User table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table User(Id integer,"
                        + "Username varchar(15)," + "Password varchar(20),"
                        + "Role varchar(15)," + "primary key (Id));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='LoyaltyCard'");
            if( !res.next()) {
                System.out.println("Building the LoyaltyCard table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table LoyaltyCard(CardCode varchar(20),"
                        + "Points integer," + "primary key (CardCode));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='BalanceOperation'");
            if( !res.next()) {
                System.out.println("Building the BalanceOperation table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table BalanceOperation(Id integer,"
                        + "Type varchar(15)," + "Amount double,"
                        + "Date date," + "primary key (Id));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='SaleTransaction'");
            if( !res.next()) {
                System.out.println("Building the SaleTransaction table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table SaleTransaction(Id integer,"
                        + "Price double," + "DiscountRate double,"
                        + "Status varchar(10)," + "primary key (Id));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='ReturnTransaction'");
            if( !res.next()) {
                System.out.println("Building the ReturnTransaction table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table ReturnTransaction(ReturnId integer,"
                        + "TransactionId integer," + "Price double,"
                        + "Status varchar(10)," + "primary key (ReturnId, TransactionId));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='SaleTransactionEntry'");
            if( !res.next()) {
                System.out.println("Building the SaleTransactionEntry table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table SaleTransactionEntry(TransactionId integer,"
                        + "ProductBarcode varchar(15)," + "ProductDescription varchar(20),"
                        + "Amount integer," + "DiscountRate double,"
                        + "PricePerUnit double," + "primary key (TransactionId, ProductBarcode));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='ReturnTransactionEntry'");
            if( !res.next()) {
                System.out.println("Building the ReturnTransactionEntry table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table ReturnTransactionEntry(ReturnId integer,"
                        + "ProductBarcode varchar(15)," + "ProductDescription varchar(20),"
                        + "Amount integer," + "DiscountRate double,"
                        + "PricePerUnit double," + "primary key (ReturnId, ProductBarcode));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='SaleTransactionPayment'");
            if( !res.next()) {
                System.out.println("Building the SaleTransactionPayment table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table SaleTransactionPayment(Id integer,"
                        + "Type varchar(15)," + "Price double," + "CreditCard varchar(20)," + "Cash double,"
                        + "Change double," + "primary key (Id));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='ReturnTransactionPayment'");
            if( !res.next()) {
                System.out.println("Building the ReturnTransactionPayment table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table ReturnTransactionPayment(Id integer,"
                        + "Type varchar(15)," + "Price double," + "CreditCard varchar(20),"
                        + "primary key (Id));");
            }

            res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Orders'");
            if( !res.next()) {
                System.out.println("Building the Orders table.");
                // need to build the table
                //Statement state2 = con.createStatement();
                state2.executeUpdate("create table Orders(Id integer,"
                        + "ProductCode varchar(15)," + "Status varchar(20),"
                        + "BalanceId integer," + "Quantity integer,"
                        + "PricePerUnit double," + "primary key (Id));");
            }

        }
    }

    public void reset(){
        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();

            String query = "DELETE FROM User;";
            stat.executeUpdate(query);

            query = "DELETE FROM BalanceOperation;";
            stat.executeUpdate(query);

            query = "DELETE FROM Customer;";
            stat.executeUpdate(query);

            query = "DELETE FROM LoyaltyCard;";
            stat.executeUpdate(query);

            query = "DELETE FROM Orders;";
            stat.executeUpdate(query);

            query = "DELETE FROM Product;";
            stat.executeUpdate(query);

            query = "DELETE FROM Item;";
            stat.executeUpdate(query);

            query = "DELETE FROM ItemSold;";
            stat.executeUpdate(query);

            query = "DELETE FROM ItemReturned;";
            stat.executeUpdate(query);

            query = "DELETE FROM ReturnTransaction;";
            stat.executeUpdate(query);

            query = "DELETE FROM ReturnTransactionEntry;";
            stat.executeUpdate(query);

            query = "DELETE FROM ReturnTransactionPayment;";
            stat.executeUpdate(query);

            query = "DELETE FROM SaleTransaction;";
            stat.executeUpdate(query);

            query = "DELETE FROM SaleTransactionEntry;";
            stat.executeUpdate(query);

            query = "DELETE FROM SaleTransactionPayment;";
            stat.executeUpdate(query);

            hasData = false;
        }
        catch (SQLException e){
            return;
        }

        return;
    }

    public Integer addUser(String username, String password, String role) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM User WHERE Username = '" + username + "'";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return -1;
            }

            PreparedStatement prep = con.prepareStatement("insert into User values(?,?,?,?);");
            prep.setString(2, username);
            prep.setString(3, password);
            prep.setString(4, role);
            prep.executeUpdate();

            res = prep.getGeneratedKeys();

            if(res.next()){
                return res.getInt(1);
            }
        }
        catch (SQLException e){
            return -1;
        }

        return -1;

    }

    public boolean deleteUser(Integer id){

        try{
            if(con == null) {
                getConnection();
            }

            /*Added query to check if the user to be deleted already exists
            not sure if the control is useful from the api requirements*/
            Statement stat = con.createStatement();
            String query = "SELECT * FROM User WHERE Id = " + id + ";";
            ResultSet res = stat.executeQuery(query);
            if(!res.next())
                return false;

            Statement stat1 = con.createStatement();
            String query2 = "DELETE FROM User WHERE Id = " + id + ";";
            stat1.executeUpdate(query2);

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public List<User> getAllUsers(){

        List<User> users = new ArrayList<>();

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM User ;";
            ResultSet res = stat.executeQuery(query);

            while ( res.next() ) {
                int Id = res.getInt("Id");
                String username = res.getString("Username");
                String password = res.getString("Password");
                String role = res.getString("Role");
                users.add(new UserImpl(Id, username, password, role));
            }
        }
        catch (SQLException e){
            users = null;
        }

        return users;
    }

    public UserImpl getUser(Integer Id){

        UserImpl u;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM User WHERE Id = " + Id + ";";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                Integer id = res.getInt("Id");
                String username = res.getString("Username");
                String password = res.getString("Password");
                String role = res.getString("Role");
                u = new UserImpl(id, username, password, role);
            }
            else{
                u = null;
            }
        }
        catch (SQLException e){
            u = null;
        }

        return u;
    }

    public boolean updateUserRights(Integer Id, String role){

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM User WHERE Id = " + Id + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "UPDATE User set Role = '" + role + "' WHERE Id = " + Id + ";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public UserImpl login(String username, String password){

        UserImpl u;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM User WHERE Username = '" + username + "' and Password = '" + password + "';";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                Integer id = res.getInt("Id");
                String role = res.getString("Role");
                u = new UserImpl(id, username, password, role);
            }
            else{
                u = null;
            }
        }
        catch (SQLException e){
            u = null;
        }

        return u;
    }

    public Integer addProduct(String description, String productCode, double pricePerUnit, String note) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE BarCode = '" + productCode + "'";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return -1;
            }

            PreparedStatement prep = con.prepareStatement("insert into Product values(?,?,?,?,?,?,?);");
            prep.setString(2, productCode);
            prep.setString(3, description);
            prep.setString(4, note);
            prep.setString(5, null);
            prep.setInt(6, 0);
            prep.setDouble(7, pricePerUnit);
            prep.executeUpdate();

            res = prep.getGeneratedKeys();

            if(res.next()){
                return res.getInt(1);
            }
        }
        catch (SQLException e){
            return -1;
        }

        return -1;
    }

    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE Id = " + id + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "SELECT * FROM Product WHERE BarCode = '" + newCode + "' AND Id <> " + id + ";";
            res = stat.executeQuery(query);

            if(res.next()){
                return false;
            }

            query = "UPDATE Product set Description = '" + newDescription + "', BarCode = '" + newCode + "'," +
                    "PricePerUnit = " + newPrice + ", Note = '" + newNote + "' WHERE Id = " + id + ";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean deleteProduct(Integer id) {

        try{
            if(con == null)
                getConnection();

             /*Added query to check if the product to be deleted already exists
            not sure if the control is useful from the api requirements*/
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE Id = " + id + ";";
            ResultSet res = stat.executeQuery(query);
            if(!res.next())
                return false;

            Statement stat2 = con.createStatement();
            String query2 = "DELETE FROM Product WHERE Id = " + id + ";";
            stat2.executeUpdate(query2);

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public List<ProductType> getAllProducts() {

        List<ProductType> products = new ArrayList<>();

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product ;";
            ResultSet res = stat.executeQuery(query);

            while ( res.next() ) {
                int Id = res.getInt("Id");
                String barCode = res.getString("BarCode");
                String description = res.getString("Description");
                String note = res.getString("Note");
                String location = res.getString("Location");
                Integer quantity = res.getInt("Quantity");
                Double pricePerUnit = res.getDouble("PricePerUnit");

                products.add(new ProductTypeImpl(Id, quantity, location, note, description, barCode, pricePerUnit));
            }
        }
        catch (SQLException e){
            products = null;
        }

        return products;
    }

    public ProductTypeImpl getProductByBarCode(String barCode) {

        ProductTypeImpl p;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE BarCode = '" + barCode + "';";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                Integer id = res.getInt("Id");
                String code = res.getString("BarCode");
                String description = res.getString("Description");
                String note = res.getString("Note");
                String location = res.getString("Location");
                Integer quantity = res.getInt("Quantity");
                Double pricePerUnit = res.getDouble("PricePerUnit");

                p = new ProductTypeImpl(id, quantity, location, note, description, code, pricePerUnit);

            }
            else{
                p = null;
            }
        }
        catch (SQLException e){
            p = null;
        }

        return p;
    }

    public List<ProductType> getProductByDescription(String description) {

        List<ProductType> products = new ArrayList<>();

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE Description LIKE '%" + description + "%';";
            ResultSet res = stat.executeQuery(query);

            while ( res.next() ) {
                int Id = res.getInt("Id");
                String barCode = res.getString("BarCode");
                String desc = res.getString("Description");
                String note = res.getString("Note");
                String location = res.getString("Location");
                Integer quantity = res.getInt("Quantity");
                Double pricePerUnit = res.getDouble("PricePerUnit");

                products.add(new ProductTypeImpl(Id, quantity, location, note, desc, barCode, pricePerUnit));
            }
        }
        catch (SQLException e){
            products = null;
        }

        return products;
    }

    public boolean updateQuantity(Integer productId, int toBeAdded) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE Id = " + productId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(res.getInt("Quantity")+toBeAdded <0){
                return false;
            }

            if(res.getString("Location")==null){
                return false;
            }

            if(toBeAdded<0)
                query = "UPDATE Product set Quantity = Quantity - " + -toBeAdded + " WHERE Id = " + productId + ";";
            else
                query = "UPDATE Product set Quantity = Quantity + " + toBeAdded + " WHERE Id = " + productId + ";";

            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean updatePosition(Integer productId, String newPos) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE Id = " + productId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "SELECT * FROM Product WHERE Location = '" + newPos + "';";
            res = stat.executeQuery(query);

            if(res.next()){
                return false;
            }

            query = "UPDATE Product set Location = '" + newPos + "' WHERE Id = " + productId + ";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }


    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Product WHERE BarCode = '" + productCode + "'";
            ResultSet res = stat.executeQuery(query);
            if(!res.next()){
                return -1;
            }

            PreparedStatement prep = con.prepareStatement("insert into Orders values(?,?,?,?,?,?);");
            prep.setString(2, productCode);
            prep.setString(3, "ISSUED");
            prep.setInt(4, -1);
            prep.setInt(5, quantity);
            prep.setDouble(6, pricePerUnit);
            prep.executeUpdate();

            res = prep.getGeneratedKeys();

            if(res.next()){
                return res.getInt(1);
            }
        }
        catch (SQLException e){
            return -1;
        }

        return -1;

    }

    public boolean payOrder(Integer orderId) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Orders WHERE Id = " + orderId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(!res.getString("Status").equals("ISSUED")){
                return false;
            }

            query = "UPDATE Orders set Status = 'PAYED' WHERE Id = "+ orderId +";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public OrderImpl getOrder(Integer orderId) {

        OrderImpl o;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Orders WHERE Id = " + orderId + ";";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                Integer id = res.getInt("Id");
                String productCode = res.getString("ProductCode");
                String status = res.getString("Status");
                Integer balanceId = res.getInt("BalanceId");
                Integer quantity = res.getInt("Quantity");
                Double pricePerUnit = res.getDouble("PricePerUnit");
                o = new OrderImpl(balanceId, productCode, pricePerUnit, quantity, status, id);
            }
            else{
                o = null;
            }
        }
        catch (SQLException e){
            o = null;
        }

        return o;
    }

    public ProductTypeImpl getProductFromOrder(Integer orderId) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Orders WHERE Id = " + orderId + ";";
            ResultSet res = stat.executeQuery(query);

            return getProductByBarCode(res.getString("ProductCode"));

        }
        catch (SQLException e){
            return null;
        }

    }

    public boolean recordOrderArrival(Integer orderId) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Orders WHERE Id = " + orderId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(!res.getString("Status").equals("PAYED")){
                return false;
            }

            Integer quantity = res.getInt("Quantity");
            ProductTypeImpl p = getProductFromOrder(orderId);

            query = "UPDATE Orders set Status = 'COMPLETED' WHERE Id = "+ orderId +";";
            stat.executeUpdate(query);

            query = "UPDATE Product set Quantity = Quantity + " + quantity + " WHERE Id = " + p.getId() + ";";

            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;

    }

    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Orders ;";
            ResultSet res = stat.executeQuery(query);

            while ( res.next() ) {
                Integer id = res.getInt("Id");
                String productCode = res.getString("ProductCode");
                String status = res.getString("Status");
                Integer balanceId = res.getInt("BalanceId");
                Integer quantity = res.getInt("Quantity");
                Double pricePerUnit = res.getDouble("PricePerUnit");

                orders.add(new OrderImpl(balanceId, productCode, pricePerUnit, quantity, status, id));
            }
        }
        catch (SQLException e){
            orders = null;
        }

        return orders;
    }

    public Integer addCustomer(String customerName) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Customer WHERE Name = '" + customerName + "'";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return -1;
            }

            PreparedStatement prep = con.prepareStatement("insert into Customer values(?,?,?);");
            prep.setString(2, customerName);
            prep.setString(3, null);
            prep.executeUpdate();

            res = prep.getGeneratedKeys();

            if(res.next()){
                return res.getInt(1);
            }
        }
        catch (SQLException e){
            return -1;
        }

        return -1;
    }

    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Customer WHERE Id = " + id + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(newCustomerCard==null){
                query = "UPDATE Customer set Name = '" + newCustomerName + "' WHERE Id = " + id + ";";
                stat.executeUpdate(query);
            }

            else if(newCustomerCard.equals("")){
                query = "UPDATE Customer set Name = '" + newCustomerName + "', Card = NULL WHERE Id = " + id + ";";
                stat.executeUpdate(query);
            }

            else{
                query = "SELECT * FROM Customer WHERE Card = '" + newCustomerCard + "';";
                res = stat.executeQuery(query);

                if(res.next()){
                    return false;
                }

                query = "SELECT * FROM LoyaltyCard WHERE CardCode = '" + newCustomerCard + "';";
                res = stat.executeQuery(query);

                if(!res.next()){
                    return false;
                }

                query = "UPDATE Customer set Name = '" + newCustomerName + "', Card = '" + newCustomerCard + "' WHERE Id = " + id + ";";
                stat.executeUpdate(query);
            }

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean deleteCustomer(Integer id) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();

            String query = "SELECT * FROM Customer WHERE Id = " + id + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "DELETE FROM Customer WHERE Id = " + id + ";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public CustomerImpl getCustomer(Integer id) {

        CustomerImpl c;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Customer WHERE Id = " + id + ";";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                String name = res.getString("Name");
                String card = res.getString("Card");
                if(card != null) {
                    query = "SELECT * FROM LoyaltyCard WHERE CardCode = '" + card + "';";
                    res = stat.executeQuery(query);
                    Integer points = res.getInt("Points");
                    LoyaltyCardImpl l = new LoyaltyCardImpl(card, points);
                    c = new CustomerImpl(name, id, l);
                }else
                    c= new CustomerImpl(name,id,new LoyaltyCardImpl(null,-1));
            }
            else{
                c = null;
            }
        }
        catch (SQLException e){
            c = null;
        }

        return c;
    }

    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Customer ;";
            ResultSet res = stat.executeQuery(query);

            while ( res.next() ) {
                Integer id = res.getInt("Id");
                String name = res.getString("Name");
                String card = res.getString("Card");
                if(card != null) {
                    Statement stat2 = con.createStatement();
                    String query1 = "SELECT * FROM LoyaltyCard WHERE CardCode = '" + card + "';";
                    ResultSet res1 = stat2.executeQuery(query1);
                    Integer points = res1.getInt("Points");
                    String card1 = res1.getString("CardCode");
                    LoyaltyCardImpl l = new LoyaltyCardImpl(card1, points);
                    customers.add(new CustomerImpl(name, id, l));
                }else
                    customers.add(new CustomerImpl(name, id, new LoyaltyCardImpl(null,-1)));
            }
        }
        catch (SQLException e){
            customers = null;
        }

        return customers;
    }

    public boolean addCard(String cardCode) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM LoyaltyCard WHERE CardCode = '" + cardCode + "'";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return false;
            }

            PreparedStatement prep = con.prepareStatement("insert into LoyaltyCard values(?,?);");
            prep.setString(1, cardCode);
            prep.setInt(2, 0);
            prep.executeUpdate();
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean attachCardToCustomer(String customerCard, Integer customerId) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Customer WHERE Id = " + customerId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "SELECT * FROM Customer WHERE Card = '" + customerCard + "';";
            res = stat.executeQuery(query);

            if(res.next()){
                return false;
            }

            query = "SELECT * FROM LoyaltyCard WHERE CardCode = '" + customerCard + "';";
            res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "UPDATE Customer set Card = '" + customerCard + "' WHERE Id = " + customerId + ";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }


    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM LoyaltyCard WHERE CardCode = '" + customerCard + "';";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(res.getInt("Points")+pointsToBeAdded<0){
                return false;
            }

            query = "UPDATE LoyaltyCard set Points = Points+" + pointsToBeAdded + " WHERE CardCode = '" + customerCard + "';";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public Integer getNewTransactionId() {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM SaleTransaction ;";
            ResultSet res = stat.executeQuery(query);

            Integer i = 0;
            while(res.next()){
                i++;
            }

            return i+1;
        }
        catch (SQLException e){
            return 0;
        }

    }

    public SaleTransactionImpl getSaleTransaction(Integer transactionId) {

        SaleTransactionImpl s;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM SaleTransaction WHERE Id = " + transactionId + ";";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                Integer id = res.getInt("Id");
                String status = res.getString("Status");
                Double discountRate = res.getDouble("DiscountRate");
                Double price = res.getDouble("Price");
                s = new SaleTransactionImpl(id, discountRate, price, status);
            }
            else{
                s = null;
            }

            query = "SELECT * FROM SaleTransactionEntry WHERE TransactionId = " + transactionId + ";";
            res = stat.executeQuery(query);

            while(res.next()){
                String code = res.getString("ProductBarcode");
                String desc = res.getString("ProductDescription");
                Integer amount = res.getInt("Amount");
                Double discount = res.getDouble("DiscountRate");
                Double pricePerUnit = res.getDouble("PricePerUnit");

                s.getEntries().add(new TicketEntryImpl(code, desc, amount, pricePerUnit, discount));
            }

            query = "SELECT * FROM ItemSold WHERE transactionId = " + transactionId + ";";
            res = stat.executeQuery(query);

            while(res.next()){
                String code = res.getString("Barcode");
                Long RFID = res.getLong("RFID");

                s.getRFIDs().add(new ProductImpl(RFID,code));
            }
        }
        catch (SQLException e){
            s = null;
        }

        return s;
    }

    public boolean addClosedSaleTransaction(SaleTransactionImpl actualSale) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM SaleTransaction WHERE Id = " + actualSale.getTicketNumber() + "";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return false;
            }

            PreparedStatement prep = con.prepareStatement("insert into SaleTransaction values(?,?,?,?);");
            prep.setInt(1, actualSale.getTicketNumber());
            prep.setDouble(2, actualSale.getPrice());
            prep.setDouble(3, actualSale.getDiscountRate());
            prep.setString(4, "CLOSED");
            prep.executeUpdate();

            for(TicketEntry t : actualSale.getEntries()){
                prep = con.prepareStatement("insert into SaleTransactionEntry values(?,?,?,?,?,?);");
                prep.setInt(1, actualSale.getTicketNumber());
                prep.setString(2, t.getBarCode());
                prep.setString(3, t.getProductDescription());
                prep.setInt(4, t.getAmount());
                prep.setDouble(5, t.getDiscountRate());
                prep.setDouble(6, t.getPricePerUnit());
                prep.executeUpdate();
            }

            for(ProductImpl p : actualSale.getRFIDs()){
                prep = con.prepareStatement("insert into ItemSold values(?,?,?);");
                prep.setInt(1, actualSale.getTicketNumber());
                prep.setLong(2, p.getRFID());
                prep.setString(3, p.getBarCode());
                prep.executeUpdate();
            }

            return true;
        }
        catch (SQLException e){
            return false;
        }

    }

    public boolean deleteSaleTransaction(Integer transactionId) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM SaleTransaction WHERE Id = " + transactionId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(res.getString("Status").equals("PAYED")){
                return false;
            }

            query = "DELETE FROM SaleTransaction WHERE Id = " + transactionId + ";";
            stat.executeUpdate(query);

            query = "DELETE FROM SaleTransactionEntry WHERE TransactionId = " + transactionId + ";";
            stat.executeUpdate(query);

            query = "DELETE FROM ItemSold WHERE transactionId = " + transactionId + ";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public Integer getNewReturnTransactionId() {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM ReturnTransaction ;";
            ResultSet res = stat.executeQuery(query);

            Integer i = 0;
            while(res.next()){
                i++;
            }

            return i+1;
        }
        catch (SQLException e){
            return 0;
        }
    }

    public boolean updateSaleTransaction(SaleTransactionImpl t) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM SaleTransaction WHERE Id = " + t.getTicketNumber() + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "UPDATE SaleTransaction set Status = '" + t.getStatus() + "', Price = " + t.getPrice() + "," +
                    "DiscountRate = " + t.getDiscountRate() + " WHERE Id = " + t.getTicketNumber() + ";";
            stat.executeUpdate(query);

            for(TicketEntry ticket : t.getEntries()){
                query = "UPDATE SaleTransactionEntry set Amount = " + ticket.getAmount() + " WHERE ProductBarcode = '" + ticket.getBarCode() + "';";
                stat.executeUpdate(query);
            }

            query = "DELETE FROM ItemSold WHERE transactionId = " + t.getTicketNumber() + ";";
            stat.executeUpdate(query);

            for(ProductImpl p : t.getRFIDs()){
                PreparedStatement prep = con.prepareStatement("insert into ItemSold values(?,?,?);");
                prep.setInt(1, t.getTicketNumber());
                prep.setLong(2, p.getRFID());
                prep.setString(3, p.getBarCode());
                prep.executeUpdate();
            }

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean deleteReturnTransaction(Integer returnId) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM ReturnTransaction WHERE ReturnId = " + returnId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(res.getString("Status").equals("PAYED")){
                return false;
            }

            ReturnTransactionImpl r = getReturnTransaction(returnId);
            for(ProductImpl p : r.getRFIDs()){
                removeItem(p);
            }

            query = "DELETE FROM ReturnTransaction WHERE ReturnId = " + returnId + ";";
            stat.executeUpdate(query);

            query = "DELETE FROM ReturnTransactionEntry WHERE ReturnId = " + returnId + ";";
            stat.executeUpdate(query);

            query = "DELETE FROM ItemReturned WHERE returnId = " + returnId + ";";
            stat.executeUpdate(query);

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean addClosedReturnTransaction(ReturnTransactionImpl r) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM ReturnTransaction WHERE ReturnId = " + r.getReturnId() + "";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return false;
            }

            PreparedStatement prep = con.prepareStatement("insert into ReturnTransaction values(?,?,?,?);");
            prep.setInt(1, r.getReturnId());
            prep.setInt(2, r.getTransactionId());
            prep.setDouble(3, r.getPrice());
            prep.setString(4, "CLOSED");
            prep.executeUpdate();

            for(TicketEntry t : r.getEntries()){
                prep = con.prepareStatement("insert into ReturnTransactionEntry values(?,?,?,?,?,?);");
                prep.setInt(1, r.getReturnId());
                prep.setString(2, t.getBarCode());
                prep.setString(3, t.getProductDescription());
                prep.setInt(4, t.getAmount());
                prep.setDouble(5, t.getDiscountRate());
                prep.setDouble(6, t.getPricePerUnit());
                prep.executeUpdate();
            }

            for(ProductImpl p : r.getRFIDs()){
                prep = con.prepareStatement("insert into ItemReturned values(?,?,?);");
                prep.setInt(1, r.getReturnId());
                prep.setLong(2, p.getRFID());
                prep.setString(3, p.getBarCode());
                prep.executeUpdate();
            }

            for(ProductImpl p : r.getRFIDs()){
                query = "DELETE FROM ItemSold WHERE RFID = " + p.getRFID() + ";";
                stat.executeUpdate(query);
            }

            for(ProductImpl p : r.getRFIDs()){
                addItem(p);
            }

            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    public ReturnTransactionImpl getReturnTransaction(Integer returnId) {

        ReturnTransactionImpl r;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM ReturnTransaction WHERE ReturnId = " + returnId + ";";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                Integer id = res.getInt("ReturnId");
                String status = res.getString("Status");
                Integer transactionId = res.getInt("TransactionId");
                Double price = res.getDouble("Price");
                r = new ReturnTransactionImpl(id, transactionId, price, status);
            }
            else{
                r = null;
            }

            query = "SELECT * FROM ReturnTransactionEntry WHERE ReturnId = " + returnId + ";";
            res = stat.executeQuery(query);

            while(res.next()){
                String code = res.getString("ProductBarcode");
                String desc = res.getString("ProductDescription");
                Integer amount = res.getInt("Amount");
                Double discount = res.getDouble("DiscountRate");
                Double pricePerUnit = res.getDouble("PricePerUnit");

                r.getEntries().add(new TicketEntryImpl(code, desc, amount, pricePerUnit, discount));
            }

            query = "SELECT * FROM ItemReturned WHERE returnId = " + returnId + ";";
            res = stat.executeQuery(query);

            while(res.next()){
                String code = res.getString("Barcode");
                Long RFID = res.getLong("RFID");

                r.getRFIDs().add(new ProductImpl(RFID,code));
            }
        }
        catch (SQLException e){
            r = null;
        }

        return r;
    }

    public Integer addBalanceOperation(double toBeAdded) {

        try{
            if(con == null)
                getConnection();

            String type;
            if(toBeAdded<0){
                type = "DEBIT";
            }
            else{
                type = "CREDIT";
            }

            PreparedStatement prep = con.prepareStatement("insert into BalanceOperation values(?,?,?,?);");
            prep.setString(2, type);
            prep.setDouble(3, toBeAdded);
            prep.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
            prep.executeUpdate();

            ResultSet res = prep.getGeneratedKeys();

            if(res.next()){
                return res.getInt(1);
            }
        }
        catch (SQLException e){
            return -1;
        }

        return -1;
    }

    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) {

        List<BalanceOperation> operations = new ArrayList<>();

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM BalanceOperation ;";
            ResultSet res = stat.executeQuery(query);

            if(from==null && to==null) {
                while (res.next()) {
                    LocalDate date = res.getDate("Date").toLocalDate();
                    Integer id = res.getInt("Id");
                    String type = res.getString("Type");
                    Double amount = res.getDouble("Amount");
                    operations.add(new BalanceOperationImpl(type, amount, date, id));
                }
            }

            if(from==null && to!=null) {
                while (res.next()) {
                    LocalDate date = res.getDate("Date").toLocalDate();
                    if(date.isBefore(to)||date.isEqual(to)) {
                        Integer id = res.getInt("Id");
                        String type = res.getString("Type");
                        Double amount = res.getDouble("Amount");
                        operations.add(new BalanceOperationImpl(type, amount, date, id));
                    }
                }
            }

            if(from!=null && to==null) {
                while (res.next()) {
                    LocalDate date = res.getDate("Date").toLocalDate();
                    if(date.isAfter(from) || date.isEqual(from)) {
                        Integer id = res.getInt("Id");
                        String type = res.getString("Type");
                        Double amount = res.getDouble("Amount");
                        operations.add(new BalanceOperationImpl(type, amount, date, id));
                    }
                }
            }

            if(from!=null && to!=null) {
                while (res.next()) {
                    LocalDate date = res.getDate("Date").toLocalDate();
                    if((date.isBefore(to)||date.isEqual(to)) && (date.isAfter(from)||date.isEqual(from))) {
                        Integer id = res.getInt("Id");
                        String type = res.getString("Type");
                        Double amount = res.getDouble("Amount");
                        operations.add(new BalanceOperationImpl(type, amount, date, id));
                    }
                }
            }
        }
        catch (SQLException e){
            operations = null;
        }

        return operations;
    }

    public boolean addSaleTransactionPayment(Integer ticketNumber, String type, double price, String creditCard, Double cash, Double change) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();

            PreparedStatement prep = con.prepareStatement("insert into SaleTransactionPayment values(?,?,?,?,?,?);");
            prep.setInt(1, ticketNumber);
            prep.setString(2, type);
            prep.setDouble(3, price);
            prep.setString(4, creditCard);
            if(cash != null)
                prep.setDouble(5, cash);
            if(change != null)
                prep.setDouble(6, change);
            prep.executeUpdate();

            String query = "UPDATE SaleTransaction set Status = 'PAYED' WHERE Id = " + ticketNumber + ";";
            stat.executeUpdate(query);

            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    public boolean addReturnTransactionPayment(Integer returnId, String type, double price, String creditCard) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();

            PreparedStatement prep = con.prepareStatement("insert into ReturnTransactionPayment values(?,?,?,?);");
            prep.setInt(1, returnId);
            prep.setString(2, type);
            prep.setDouble(3, price);
            prep.setString(4, creditCard);
            prep.executeUpdate();

            String query = "UPDATE ReturnTransaction set Status = 'PAYED' WHERE ReturnId = " + returnId + ";";
            stat.executeUpdate(query);

            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    public boolean setBalanceIdToOrder(Integer orderId, Integer balanceId) {
        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Orders WHERE Id = " + orderId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            query = "UPDATE Orders set BalanceId = " + balanceId + " WHERE Id = " + orderId + ";";
            stat.executeUpdate(query);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean checkRFID(Long RFID) {

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Item WHERE RFID = " + RFID +";";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return false;
            }

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean recordOrderArrivalRFID(Integer orderId, Long RFID) {

        try{
            if(con == null)
                getConnection();

            Statement stat = con.createStatement();
            String query = "SELECT * FROM Orders WHERE Id = " + orderId + ";";
            ResultSet res = stat.executeQuery(query);

            if(!res.next()){
                return false;
            }

            if(!res.getString("Status").equals("PAYED")){
                return false;
            }

            Integer quantity = res.getInt("Quantity");
            String code = res.getString("ProductCode");
            ProductTypeImpl p = getProductFromOrder(orderId);

            query = "UPDATE Orders set Status = 'COMPLETED' WHERE Id = "+ orderId +";";
            stat.executeUpdate(query);

            query = "UPDATE Product set Quantity = Quantity + " + quantity + " WHERE Id = " + p.getId() + ";";
            stat.executeUpdate(query);

            for(int i=0; i<quantity; i++){
                PreparedStatement prep = con.prepareStatement("insert into Item values(?,?);");
                prep.setString(1, code);
                prep.setLong(2, RFID+i);
                prep.executeUpdate();
            }
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public ProductImpl getProductByRFID(Long RFID) {

        ProductImpl p;

        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Item WHERE RFID = " + RFID + ";";
            ResultSet res = stat.executeQuery(query);

            if ( res.next() ) {
                String code = res.getString("BarCode");
                Long rfid= res.getLong("RFID");

                p = new ProductImpl(rfid, code);

            }
            else{
                p = null;
            }
        }
        catch (SQLException e){
            p = null;
        }

        return p;
    }

    public boolean removeItem(ProductImpl product) {

        try{
            if(con == null) {
                getConnection();
            }

            /*Added query to check if the user to be deleted already exists
            not sure if the control is useful from the api requirements*/
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Item WHERE RFID = " + product.getRFID() + " AND BarCode = '" + product.getBarCode() + "';";
            ResultSet res = stat.executeQuery(query);
            if(!res.next())
                return false;

            Statement stat1 = con.createStatement();
            String query2 = "DELETE FROM Item WHERE RFID = " + product.getRFID() + " AND BarCode = '" + product.getBarCode() + "';";
            stat1.executeUpdate(query2);

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    public boolean addItem(ProductImpl product) {
        try{
            if(con == null)
                getConnection();
            Statement stat = con.createStatement();
            String query = "SELECT * FROM Item WHERE RFID = " + product.getRFID() + ";";
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return false;
            }

            PreparedStatement prep = con.prepareStatement("insert into Item values(?,?);");
            prep.setString(1, product.getBarCode());
            prep.setLong(2, product.getRFID());

            prep.executeUpdate();

            res = prep.getGeneratedKeys();

        }
        catch (SQLException e){
            return false;
        }

        return true;
    }
}
