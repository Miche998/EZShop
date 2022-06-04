package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;
import it.polito.ezshop.util.*;


import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EZShop implements EZShopInterface {

    private DBManager db = new DBManager();
    private HashMap<Integer, SaleTransactionImpl> sales = new HashMap<>();
    private HashMap<Integer, ReturnTransactionImpl> returns = new HashMap<>();
    private UserImpl loggedUser;

    public EZShop() {
        this.loggedUser = null;
    }

    @Override
    public void reset() {
        loggedUser = null;
        sales.clear();
        returns.clear();
        db.reset();
    }

    @Override
    public Integer createUser(String username, String password, String role) throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {

        //Handle empty or null username
        if(username == null || username.isEmpty())
            throw new InvalidUsernameException();

        //Handle empty or null password
        if(password == null || password.isEmpty())
            throw new InvalidPasswordException();

        //Handle invalid role
        if(role == null || role.isEmpty())
            throw new InvalidRoleException();

        if(!role.equals("Administrator") && !role.equals("ShopManager") && !role.equals("Cashier")) {
            throw new InvalidRoleException();
        }

        return db.addUser(username, password, role);
    }

    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        if(id == null || id <= 0)
            throw new InvalidUserIdException();

        if(loggedUser == null || !(loggedUser.getRole().equals("Administrator")))
            throw new UnauthorizedException();

        return db.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {
        if(loggedUser == null || !(loggedUser.getRole().equals("Administrator")))
            throw new UnauthorizedException();

        return db.getAllUsers();
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        if(id == null || id <= 0)
            throw new InvalidUserIdException();

        if(loggedUser == null || !(loggedUser.getRole().equals("Administrator")))
            throw new UnauthorizedException();

        return db.getUser(id);
    }

    @Override
    public boolean updateUserRights(Integer id, String role) throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {

        if(id == null || id <= 0)
            throw new InvalidUserIdException();

        //Handling role exceptions
        if(role == null || role.isEmpty())
            throw new InvalidRoleException();

        if(!role.equals("Administrator") && !role.equals("ShopManager") && !role.equals("Cashier")) {
            throw new InvalidRoleException();
        }

        if(loggedUser == null || !(loggedUser.getRole().equals("Administrator")))
            throw new UnauthorizedException();

        return db.updateUserRights(id, role);
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {

        if(username == null || username.isEmpty())
            throw new InvalidUsernameException();

        if(password == null || password.isEmpty())
            throw new InvalidPasswordException();

        loggedUser = db.login(username, password);
        //Wrong credentials or db problems
        return loggedUser;
    }

    @Override
    public boolean logout() {
        if(loggedUser == null)
            return false;
        else{
            loggedUser = null;
            return true;
        }
    }


    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note) throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(description==null || description.isEmpty()){
            throw new InvalidProductDescriptionException();
        }

        if(pricePerUnit<=0){
            throw new InvalidPricePerUnitException();
        }

        if(productCode==null || productCode.isEmpty()){
            throw new InvalidProductCodeException();
        }

        if(productCode.length()<12 || productCode.length()>14){
            throw new InvalidProductCodeException();
        }

        if(!productCodeAlgorithm(productCode)){
            throw new InvalidProductCodeException();
        }

        if(note == null){
            note = "";
        }

        return db.addProduct(description, productCode, pricePerUnit, note);
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote) throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(newDescription==null || newDescription.isEmpty()){
            throw new InvalidProductDescriptionException();
        }

        if(newPrice<=0){
            throw new InvalidPricePerUnitException();
        }

        if(id==null || id<=0 ){
            throw new InvalidProductIdException();
        }

        if(newCode==null || newCode.isEmpty()){
            throw new InvalidProductCodeException();
        }

        if(newCode.length()<12 || newCode.length()>14){
            throw new InvalidProductCodeException();
        }

        if(!productCodeAlgorithm(newCode)){
            throw new InvalidProductCodeException();
        }

        if(newNote == null){
            newNote = "";
        }

        return db.updateProduct(id, newDescription, newCode, newPrice, newNote);
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(id==null || id<=0){
            throw new InvalidProductIdException();
        }

        return db.deleteProduct(id);
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.getAllProducts();
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode) throws InvalidProductCodeException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(barCode==null || barCode.equals("")){
            throw new InvalidProductCodeException();
        }

        if(barCode.length()<12 || barCode.length()>14){
            throw new InvalidProductCodeException();
        }


        if(!productCodeAlgorithm(barCode)){
            throw new InvalidProductCodeException();
        }

        return db.getProductByBarCode(barCode);
    }


    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(description==null){
            return getAllProductTypes();
        }

        return db.getProductByDescription(description);
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded) throws InvalidProductIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(productId==null || productId<=0 ){
            throw new InvalidProductIdException();
        }

        return db.updateQuantity(productId, toBeAdded);
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos) throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(productId==null || productId<=0 ){
            throw new InvalidProductIdException();
        }

        String[] pos = newPos.split("-");

        int aisle;
        int level;

        try {
            aisle = Integer.parseInt(pos[0]);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidLocationException();
        }

        try {
            level = Integer.parseInt(pos[2]);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidLocationException();
        }

        if(!pos[1].matches("[a-zA-Z]+")){
            throw new InvalidLocationException();
        }


        return db.updatePosition(productId, newPos);
    }

    @Override
    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(pricePerUnit<=0){
            throw new InvalidPricePerUnitException();
        }

        if(quantity<=0){
            throw new InvalidQuantityException();
        }

        if(productCode==null || productCode.isEmpty()){
            throw new InvalidProductCodeException();
        }

        if(productCode.length()<12 || productCode.length()>14){
            throw new InvalidProductCodeException();
        }


        if(!productCodeAlgorithm(productCode)){
            throw new InvalidProductCodeException();
        }

        return db.issueOrder(productCode, quantity, pricePerUnit);
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(pricePerUnit<=0){
            throw new InvalidPricePerUnitException();
        }

        if(quantity<=0){
            throw new InvalidQuantityException();
        }

        if(productCode==null || productCode.equals("")){
            throw new InvalidProductCodeException();
        }

        if(productCode.length()<12 || productCode.length()>14){
            throw new InvalidProductCodeException();
        }


        if(!productCodeAlgorithm(productCode)){
            throw new InvalidProductCodeException();
        }

        if(computeBalance() - (pricePerUnit*quantity) < 0){
            return -1;
        }

        Integer id = db.issueOrder(productCode, quantity, pricePerUnit);
        if(id!=-1 && db.payOrder(id)){
            OrderImpl o = db.getOrder(id);
            Integer balanceId = db.addBalanceOperation(-o.getPricePerUnit() * o.getQuantity());
            if(balanceId==-1){
                return -1;
            }
            if (db.setBalanceIdToOrder(id, balanceId))
                return id;
        }

        return -1;
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(orderId == null || orderId <= 0){
            throw new InvalidOrderIdException();
        }

        OrderImpl o = db.getOrder(orderId);
        if(o == null)
            return false;

        if(computeBalance() - (o.getPricePerUnit()*o.getQuantity()) < 0){
            return false;
        }

        if(db.payOrder(orderId)){
            Integer balanceId = db.addBalanceOperation(-o.getPricePerUnit() * o.getQuantity());
            if(balanceId==-1){
                return false;
            }
            else{
                return db.setBalanceIdToOrder(orderId, balanceId);
            }
        }

        return false;
    }

    @Override
    public boolean recordOrderArrival(Integer orderId) throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(orderId==null || orderId<=0){
            throw new InvalidOrderIdException();
        }

        OrderImpl ord = db.getOrder(orderId);

        if(ord == null)
            return false;

        ProductTypeImpl prod = db.getProductFromOrder(orderId);

        if(prod.getLocation() == null) {
            throw new InvalidLocationException();
        }

        return db.recordOrderArrival(orderId);
    }

    @Override
    public boolean recordOrderArrivalRFID(Integer orderId, String RFIDfrom) throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {

        if (loggedUser == null) {
            throw new UnauthorizedException("No logged user");
        }

        if (!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if (orderId == null || orderId <= 0) {
            throw new InvalidOrderIdException();
        }


        OrderImpl ord = db.getOrder(orderId);

        if (ord == null)
            return false;

        ProductTypeImpl prod = db.getProductFromOrder(orderId);

        if (prod.getLocation() == null) {
            throw new InvalidLocationException();
        }

        if (RFIDfrom == null || RFIDfrom.equals("") || (RFIDfrom.length() != 12 || !(RFIDfrom.matches("[0-9]+"))))
            throw new InvalidRFIDException();

        for (int i = 0; i < ord.getQuantity(); i++) {
            if (!db.checkRFID(Long.parseLong(RFIDfrom) + i)) {
                System.out.println(Long.parseLong(RFIDfrom));
                throw new InvalidRFIDException();
            }
        }


        return db.recordOrderArrivalRFID(orderId, Long.parseLong(RFIDfrom));
    }
    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.getAllOrders();
    }

    @Override
    public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {

        if(customerName == null || customerName.equals(""))
            throw new InvalidCustomerNameException();

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.addCustomer(customerName);

    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard) throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {

        if(newCustomerName == null || newCustomerName.isEmpty())
            throw new InvalidCustomerNameException();

        if(id == null || id <= 0)
            throw new InvalidCustomerIdException();

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(newCustomerCard != null && !newCustomerCard.equals("") ){
            if(newCustomerCard.length() != 10 || !(newCustomerCard.matches("[0-9]+")))
                throw new InvalidCustomerCardException();
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.modifyCustomer(id, newCustomerName, newCustomerCard);
    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {

        if(id == null || id <= 0)
            throw new InvalidCustomerIdException();

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.deleteCustomer(id);
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        if(id == null || id <= 0)
            throw new InvalidCustomerIdException();

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.getCustomer(id);
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.getAllCustomers();
    }

    @Override
    public String createCard() throws UnauthorizedException {
        String cardCode = "";
        long randNumber;

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        //Create unique random 10 digits cardCode
        randNumber = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        //Convert it to String
        cardCode = Long.toString(randNumber);


        if(db.addCard(cardCode)){
            return cardCode;
        }

        return "";
    }

    @Override
    public boolean attachCardToCustomer(String customerCard, Integer customerId) throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {

        if(customerId == null || customerId <= 0)
            throw new InvalidCustomerIdException();

        if(customerCard == null || customerCard.equals("") || (customerCard.length() != 10 || !(customerCard.matches("[0-9]+"))))
            throw new InvalidCustomerCardException();

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.attachCardToCustomer(customerCard, customerId);
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded) throws InvalidCustomerCardException, UnauthorizedException {

        if(customerCard == null || customerCard.equals("") || (customerCard.length() != 10 || !(customerCard.matches("[0-9]+"))))
            throw new InvalidCustomerCardException();

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        return db.modifyPointsOnCard(customerCard, pointsToBeAdded);
    }


    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }
        Integer transactionID = db.getNewTransactionId();

        sales.put(transactionID,new SaleTransactionImpl(transactionID,0.0,0.0,"OPENED"));

        return transactionID;
    }

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(amount <=0){
            throw new InvalidQuantityException("INVALID quantity");
        }

        if(transactionId == null || transactionId <=0)
            throw new InvalidTransactionIdException("INVALID TransactionID");

        //Product code check glossary
        if(productCode == null || productCode.isEmpty())
            throw new InvalidProductCodeException("INVALID ProductCod");

        if(!productCodeAlgorithm(productCode)){
            throw new InvalidProductCodeException("INVALID ProductCod");
        }

        /* Get the sale from the Map*/
        SaleTransactionImpl actualSale = sales.get(transactionId);
        if(actualSale == null)
            return false;

        /*Check if the status of the sale is OPENED*/
        if(!actualSale.getStatus().equals("OPENED"))
            return false;

        /*Get the entire list from the sale of ticket entries if any*/
        List<TicketEntry> ticketList = actualSale.getEntries();

        /*Get the product type for the current transaction through the productCode*/
        ProductType saleProduct = db.getProductByBarCode(productCode);

        if(saleProduct == null)
            return false;

        /*check if the amount needed is available*/
        if(amount > saleProduct.getQuantity())
            return false;
        else /*update quantity*/
            db.updateQuantity(saleProduct.getId(), -amount);

        /*Search in the list of ticket entry if the ticket is already present*/
        TicketEntry actualTicket = ticketList.stream().filter(p->p.getBarCode().equals(productCode)).findFirst().orElse(null);

        /*If not present add the ticket entry to the list*/
        if(actualTicket == null)
            ticketList.add(new TicketEntryImpl(saleProduct.getBarCode(),
                    saleProduct.getProductDescription(),amount,saleProduct.getPricePerUnit(),0));
        else{
            /*else update the amount*/
            int lastAmount = actualTicket.getAmount();
            actualTicket.setAmount(lastAmount+amount);
        }

        actualSale.computePrice();

        return true;
    }

    @Override
    public boolean addProductToSaleRFID(Integer transactionId, String RFID) throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException{
        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(transactionId == null || transactionId <=0)
            throw new InvalidTransactionIdException("INVALID TransactionID");

        if(RFID == null || RFID.equals("") || (RFID.length() != 12 || !(RFID.matches("[0-9]+"))))
            throw new InvalidRFIDException();


        /* Get the sale from the Map*/
        SaleTransactionImpl actualSale = sales.get(transactionId);
        if(actualSale == null)
            return false;

        /*Check if the status of the sale is OPENED*/
        if(!actualSale.getStatus().equals("OPENED"))
            return false;

        if(actualSale.getRFIDs().stream().anyMatch(p -> p.getRFID() == Long.parseLong(RFID))){
            return false;
        }

        ProductImpl product = db.getProductByRFID(Long.parseLong(RFID));

        if(product == null)
            return false;

        ProductTypeImpl type = db.getProductByBarCode(product.getBarCode());

        if(type == null) {
            return false;
        }

        /*check if the amount needed is available*/
        db.updateQuantity(type.getId(), -1);

        /*Get the entire list from the sale of ticket entries if any*/
        List<TicketEntry> ticketList = actualSale.getEntries();

        /*Search in the list of ticket entry if the ticket is already present*/
        TicketEntry actualTicket = ticketList.stream().filter(p->p.getBarCode().equals(type.getBarCode())).findFirst().orElse(null);

        /*If not present add the ticket entry to the list*/
        if(actualTicket == null)
            ticketList.add(new TicketEntryImpl(type.getBarCode(),
                    type.getProductDescription(),1,type.getPricePerUnit(),0));
        else{
            /*else update the amount*/
            int lastAmount = actualTicket.getAmount();
            actualTicket.setAmount(lastAmount+1);
        }

        actualSale.getRFIDs().add(product);
        actualSale.computePrice();

        return true;
    }
    
    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(transactionId == null || transactionId <= 0 )
            throw new InvalidTransactionIdException("INVALID TransactionID");

        if(productCode == null || productCode.isEmpty())
            throw new InvalidProductCodeException("INVALID ProductCode");

        if(!productCodeAlgorithm(productCode)){
            throw new InvalidProductCodeException("INVALID ProductCode");
        }

        if(amount <=0){
            throw new InvalidQuantityException("INVALID quantity");
        }

        /* Get the sale from the Map*/
        SaleTransactionImpl actualSale = sales.get(transactionId);

        if(actualSale == null)
            return false;

        /*Check if the status of the sale is OPENED*/
        if(!actualSale.getStatus().equals("OPENED"))
            return false;

        /*Get the product type for the current transaction through the productCode*/
        ProductType saleProduct = db.getProductByBarCode(productCode);
        if(saleProduct == null)
            return false;

        /*Search in the list of ticket entry if the ticket is already present*/
        List<TicketEntry> ticketList = actualSale.getEntries();
        /*Search in the list of ticket entry if the ticket is already present*/
        TicketEntry actualTicket = ticketList.stream().filter(t -> t.getBarCode().equals(productCode)).findFirst().orElse(null);

        /*check if the ticket exists and if the amount to delete is equal/less to the actual amount delete it from the list*/
        if(actualTicket!=null ) {
            if(amount > actualTicket.getAmount()) {
                return false;
            }
            else if(amount == actualTicket.getAmount()) {
                ticketList = ticketList.stream().filter(p -> !p.getBarCode().equals(productCode)).collect(Collectors.toList());
                actualSale.setEntries(ticketList);
            }
            else if (amount < actualTicket.getAmount()) {
                int lastAmount = actualTicket.getAmount();
                actualTicket.setAmount(lastAmount - amount);
            }
        }
        else{
            System.out.println("TICKET ENTRY NOT PRESENT!\n");
            return false;
        }

        db.updateQuantity(saleProduct.getId(), amount);
        actualSale.computePrice();
        return true;
    }

    @Override
    public boolean deleteProductFromSaleRFID(Integer transactionId, String RFID) throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException{

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(transactionId == null || transactionId <= 0 )
            throw new InvalidTransactionIdException("INVALID TransactionID");

        if(RFID == null || RFID.equals("") || (RFID.length() != 12 || !(RFID.matches("[0-9]+"))))
            throw new InvalidRFIDException();

        /* Get the sale from the Map*/
        SaleTransactionImpl actualSale = sales.get(transactionId);

        if(actualSale == null) {
            return false;
        }

        /*Check if the status of the sale is OPENED*/
        if(!actualSale.getStatus().equals("OPENED")) {
            return false;
        }

        /*Get the product type for the current transaction through the productCode*/
        ProductImpl product = db.getProductByRFID(Long.parseLong(RFID));
        if(product == null){
            return false;
        }

        ProductType type = db.getProductByBarCode(product.getBarCode());
        if(type == null) {
            return false;
        }

        if (actualSale.getRFIDs().stream().noneMatch(r -> r.getRFID() == product.getRFID())) {
            return false;
        }

        /*Search in the list of ticket entry if the ticket is already present*/
        List<TicketEntry> ticketList = actualSale.getEntries();
        /*Search in the list of ticket entry if the ticket is already present*/
        TicketEntry actualTicket = ticketList.stream().filter(t -> t.getBarCode().equals(product.getBarCode())).findFirst().orElse(null);

        /*check if the ticket exists and if the amount to delete is equal/less to the actual amount delete it from the list*/
        if(actualTicket!=null ) {
            if(actualTicket.getAmount() == 1) {
                ticketList = ticketList.stream().filter(p -> !p.getBarCode().equals(product.getBarCode())).collect(Collectors.toList());
                actualSale.setEntries(ticketList);
            }
            else if (actualTicket.getAmount() > 1) {
                int lastAmount = actualTicket.getAmount();
                actualTicket.setAmount(lastAmount - 1);
            }
        }
        else{
            System.out.println("TICKET ENTRY NOT PRESENT!\n");
            return false;
        }

        db.updateQuantity(type.getId(), 1);
        actualSale.computePrice();
        actualSale.setRFIDs(actualSale.getRFIDs().stream().filter(p->p.getRFID() != product.getRFID()).collect(Collectors.toList()));

        return true;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(discountRate >= 1 || discountRate<0 )
            throw new InvalidDiscountRateException("INVALID discount rate");
        if(transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("INVALID TransactionID");
        if(productCode == null || productCode.isEmpty())
            throw new InvalidProductCodeException("INVALID ProductCode");
        if(!productCodeAlgorithm(productCode)){
            throw new InvalidProductCodeException("INVALID ProductCode");
        }

        SaleTransactionImpl actualSale = sales.get(transactionId);

        if(actualSale == null)
            return false;

        if(!actualSale.getStatus().equals("OPENED"))
            return false;

        ProductType saleProduct = db.getProductByBarCode(productCode);
        if(saleProduct == null)
            return false;

        /*Search in the list of ticket entry if the ticket is already present*/
        List<TicketEntry> ticketList = actualSale.getEntries();
        /*Search in the list of ticket entry if the ticket is already present*/
        TicketEntry actualTicket = ticketList.stream().filter(p->p.getBarCode().equals(productCode)).findFirst().orElse(null);

        if(actualTicket != null) {
            actualTicket.setDiscountRate(discountRate);
            actualSale.computePrice();
        }
        else
            return false;

        return true;
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate) throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(discountRate >= 1 || discountRate<0 )
            throw new InvalidDiscountRateException("INVALID discount rate");

        if(transactionId == null || transactionId <= 0 )
            throw new InvalidTransactionIdException("INVALID TransactionID");

        SaleTransactionImpl actualSale = sales.get(transactionId);

        if(actualSale == null)
            return false;
        if(!actualSale.getStatus().equals("OPENED"))
            return false;

        actualSale.setDiscountRate(discountRate);
        actualSale.computePrice();

        return true;
    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(transactionId == null || transactionId <= 0)
            throw new InvalidTransactionIdException("INVALID TransactionID");

        SaleTransaction actualSale = sales.get(transactionId);

        if(actualSale == null){
            actualSale = db.getSaleTransaction(transactionId);
            if(actualSale==null){
                return -1;
            }
        }

        return (int)Math.floor(actualSale.getPrice()/10.00);
    }

    @Override
    public boolean endSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(transactionId == null){
            throw new InvalidTransactionIdException("INVALID TransactionID");
        }

        if(transactionId <= 0)
            throw new InvalidTransactionIdException("INVALID TransactionID");

        SaleTransactionImpl actualSale = sales.get(transactionId);
        if (actualSale==null)
            return false;


        if(db.addClosedSaleTransaction(actualSale)){
            for(ProductImpl p : db.getSaleTransaction(transactionId).getRFIDs()){
                db.removeItem(p);
            }
            sales.remove(transactionId);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteSaleTransaction(Integer saleNumber) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(saleNumber==null){
            throw new InvalidTransactionIdException("INVALID TransactionID");
        }

        if(saleNumber <= 0)
            throw new InvalidTransactionIdException("INVALID TransactionID");

        if(db.getSaleTransaction(saleNumber) == null)
            return false;
        List <TicketEntry> entries = db.getSaleTransaction(saleNumber).getEntries();
        for( TicketEntry entry : entries){
            db.updateQuantity(db.getProductByBarCode(entry.getBarCode()).getId(),entry.getAmount());
        }

        for(ProductImpl p : db.getSaleTransaction(saleNumber).getRFIDs()){
            db.addItem(p);
        }

        return db.deleteSaleTransaction(saleNumber);
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(transactionId == null){
            throw new InvalidTransactionIdException("INVALID TransactionID");
        }

        if(transactionId <= 0)
            throw new InvalidTransactionIdException("INVALID TransactionID");

        return db.getSaleTransaction(transactionId);
    }



    @Override
    public Integer startReturnTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(transactionId == null || transactionId<=0){
            throw new InvalidTransactionIdException();
        }

        SaleTransactionImpl actualSale = db.getSaleTransaction(transactionId);
        if(actualSale==null){
            return -1;
        }

        Integer returnId = db.getNewReturnTransactionId();

        returns.put(returnId,new ReturnTransactionImpl(returnId, transactionId, 0.0,"OPENED"));

        return returnId;
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(returnId == null || returnId<=0){
            throw new InvalidTransactionIdException();
        }

        if(productCode==null || productCode.isEmpty()){
            throw new InvalidProductCodeException();
        }

        if(productCode.length()<12 || productCode.length()>14){
            throw new InvalidProductCodeException();
        }


        if(!productCodeAlgorithm(productCode)){
            throw new InvalidProductCodeException();
        }

        if(amount<=0){
            throw new InvalidQuantityException();
        }

        ProductTypeImpl p = db.getProductByBarCode(productCode);
        if(p == null){
            return false;
        }

        if(!returns.containsKey(returnId)){
            return false;
        }

        SaleTransactionImpl s = db.getSaleTransaction(returns.get(returnId).getTransactionId());
        if(s.getEntries().stream().noneMatch(t -> t.getBarCode().equals(productCode))){
            return false;
        }

        List<TicketEntry> entries = s.getEntries();
        for(TicketEntry t : entries) {
            if(t.getBarCode().equals(productCode)){
                if(t.getAmount()<amount) {
                    return false;
                }
            }
        }

        TicketEntry from = entries.stream().filter(e -> e.getBarCode().equals(productCode)).findFirst().orElse(null);

        returns.get(returnId).getEntries().add(new TicketEntryImpl(productCode, from.getProductDescription(), amount, from.getPricePerUnit(), from.getDiscountRate()));

        return true;
    }

    @Override
    public boolean returnProductRFID(Integer returnId, String RFID) throws InvalidTransactionIdException, InvalidRFIDException, UnauthorizedException 
    {
        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(returnId == null || returnId<=0){
            throw new InvalidTransactionIdException();
        }

        if(RFID == null || RFID.equals("") || (RFID.length() != 12 || !(RFID.matches("[0-9]+"))))
            throw new InvalidRFIDException();

        if(!returns.containsKey(returnId)){
            return false;
        }

        if(returns.get(returnId).getRFIDs().stream().anyMatch(p -> p.getRFID() == Long.parseLong(RFID))){
            return false;
        }

        SaleTransactionImpl s = db.getSaleTransaction(returns.get(returnId).getTransactionId());

        if(s==null){
            return false;
        }

        ProductImpl product = null;

        for(ProductImpl p : s.getRFIDs()){
            if(p.getRFID() == Long.parseLong(RFID)){
                product = p;
            }
        }

        if(product == null){
            return false;
        }

        ProductImpl finalProduct = product;

        List<TicketEntry> entries = s.getEntries();

        TicketEntry from = entries.stream().filter(e -> e.getBarCode().equals(finalProduct.getBarCode())).findFirst().orElse(null);

        ProductTypeImpl type = db.getProductByBarCode(product.getBarCode());
        if(type == null){
            return false;
        }

        TicketEntry ticket = returns.get(returnId).getEntries().stream().filter(e -> e.getBarCode().equals(type.getBarCode())).findFirst().orElse(null);

        if(ticket == null){
            returns.get(returnId).getEntries().add(new TicketEntryImpl(product.getBarCode(), from.getProductDescription(), 1, from.getPricePerUnit(), from.getDiscountRate()));

        }
        else{
            ticket.setAmount(ticket.getAmount()+1);
        }

        returns.get(returnId).getRFIDs().add(product);

        return true;
    }


    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(returnId == null || returnId<=0){
            throw new InvalidTransactionIdException();
        }

        if(!returns.containsKey(returnId)){
            return false;
        }

        if(commit){
            ReturnTransactionImpl r = returns.get(returnId);
            SaleTransactionImpl t = db.getSaleTransaction(r.getTransactionId());

            for(TicketEntry ticket : r.getEntries()){
                Integer amount = ticket.getAmount();
                t.modifyEntryByBarcode(ticket.getBarCode(), -amount);
                ProductTypeImpl p = db.getProductByBarCode(ticket.getBarCode());
                db.updateQuantity(p.getId(), amount);
            }

            //remove all the RFIDs in the return transaction from the sale transaction
            for(ProductImpl p : r.getRFIDs()){
                t.getRFIDs().remove(p);
            }

            t.computePrice();
            r.computePrice();
            if(db.updateSaleTransaction(t) && db.addClosedReturnTransaction(r)) {
                returns.remove(returnId);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            returns.remove(returnId);
            return true;
        }
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(returnId == null || returnId<=0){
            throw new InvalidTransactionIdException();
        }

        ReturnTransactionImpl r = db.getReturnTransaction(returnId);

        if(db.deleteReturnTransaction(returnId)){

            SaleTransactionImpl t = db.getSaleTransaction(r.getTransactionId());

            for(TicketEntry ticket : r.getEntries()){
                Integer amount = ticket.getAmount();
                t.modifyEntryByBarcode(ticket.getBarCode(), amount);
                ProductTypeImpl p = db.getProductByBarCode(ticket.getBarCode());
                db.updateQuantity(p.getId(), -amount);
            }

            for(ProductImpl p : r.getRFIDs()){
                t.getRFIDs().add(p);
            }

            t.computePrice();
            if(db.updateSaleTransaction(t)) {
                return true;
            }
            else{
                return false;
            }
        }

        return false;
    }

    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash) throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(cash <= 0){
            throw new InvalidPaymentException();
        }

        if(ticketNumber==null || ticketNumber<=0 ){
            throw new InvalidTransactionIdException();
        }

        SaleTransactionImpl s = db.getSaleTransaction(ticketNumber);
        if(s == null){
            return -1;
        }
        if(cash<s.getPrice()){
            return -1;
        }

        if(db.addBalanceOperation(s.getPrice()) != -1 && db.addSaleTransactionPayment(ticketNumber, "Cash", s.getPrice(), null, cash, cash-s.getPrice())){
            return cash-s.getPrice();
        }

        return -1;
    }

    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(ticketNumber==null || ticketNumber<=0 ){
            throw new InvalidTransactionIdException();
        }

        if(creditCard==null || creditCard.isEmpty() ){
            throw new InvalidCreditCardException();
        }

        if(!checkLuhn(creditCard)){
            throw new InvalidCreditCardException();
        }


        SaleTransactionImpl s = db.getSaleTransaction(ticketNumber);
        if(s == null){
            return false;
        }

        String line = "";
        String splitBy = ";";
        BufferedWriter out = null;
        String fileContent = "";

        try
        {
            boolean flag = false;
            BufferedReader br = new BufferedReader(new FileReader("CreditCards.txt"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] card = line.split(splitBy);    // use comma as separator
                if(card[0].equals(creditCard)){
                    flag = true;
                    if(!(Double.parseDouble(card[1])>s.getPrice())){
                        return false;
                    }
                    if(db.addBalanceOperation(s.getPrice()) !=-1 && db.addSaleTransactionPayment(ticketNumber, "Credit Card", s.getPrice(), creditCard, null, null)){
                        Double newBalance = Double.parseDouble(card[1])-s.getPrice();
                        fileContent = fileContent + card[0]+";"+newBalance+"\n";
                    }
                    else{
                        return false;
                    }
                }
                else{
                    fileContent = fileContent + line+"\n";
                }
            }
            br.close();
            out = new BufferedWriter(new FileWriter("CreditCards.txt"));
            out.write(fileContent);

            out.flush();
            out.close();
            if(flag!=true)
                return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(returnId==null || returnId<=0){
            throw new InvalidTransactionIdException();
        }

        ReturnTransactionImpl r = db.getReturnTransaction(returnId);
        if(r == null){
            return -1;
        }
        else{
            if(db.addReturnTransactionPayment(returnId, "Cash", r.getPrice(), null)
                    && db.addBalanceOperation(-r.getPrice())!=-1){
                return r.getPrice();
            }
        }

        return -1;
    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }

        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier")) {
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if( returnId==null || returnId<=0){
            throw new InvalidTransactionIdException();
        }

        if( creditCard==null || creditCard.isEmpty() ){
            throw new InvalidCreditCardException();
        }

        if(!checkLuhn(creditCard)){
            throw new InvalidCreditCardException();
        }

        ReturnTransactionImpl r = db.getReturnTransaction(returnId);
        if(r == null){
            return -1;
        }

        String line = "";
        String splitBy = ";";
        BufferedWriter out = null;
        String fileContent = "";

        try
        {
            boolean flag = false;
            BufferedReader br = new BufferedReader(new FileReader("CreditCards.txt"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] card = line.split(splitBy);    // use comma as separator
                if(card[0].equals(creditCard)){
                    flag = true;
                    if(db.addBalanceOperation(-r.getPrice())!=-1 && db.addReturnTransactionPayment(returnId, "Credit Card", r.getPrice(), creditCard)){
                        Double newBalance = Double.parseDouble(card[1])+r.getPrice();
                        fileContent = fileContent + card[0]+";"+newBalance+"\n";
                    }
                    else{
                        return -1;
                    }
                }
                else{
                    fileContent = fileContent + line+"\n";
                }
            }
            br.close();
            out = new BufferedWriter(new FileWriter("CreditCards.txt"));
            out.write(fileContent);

            out.flush();
            out.close();
            if(flag!=true)
                return -1;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return r.getPrice();
    }

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {

        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(computeBalance() + toBeAdded <0)
            return false;

        if(db.addBalanceOperation(toBeAdded) == -1)
            return false;

        return true;
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        if(from == null || to == null){
            return db.getCreditsAndDebits(from, to);
        }
        else if(from.isBefore(to)){
            return db.getCreditsAndDebits(from, to);
        }
        else {
            return db.getCreditsAndDebits(to, from);
        }
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        if(loggedUser == null){
            throw new UnauthorizedException("No logged user");
        }
        if(!loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager")){
            throw new UnauthorizedException("No rights to perform this operation");
        }

        List<BalanceOperation> l = getCreditsAndDebits(null, null);

        return l.stream().mapToDouble(b -> b.getMoney()).sum();
    }

    public boolean checkLuhn(String cardNo) {


        if(cardNo==null || cardNo.isEmpty()){
            return false;
        }

        long code;
        try {
            code = Long.parseLong(cardNo);
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = cardNo.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

     public boolean productCodeAlgorithm(String productCode) {

        char c;
        int somma = 0;

        if(productCode==null || productCode.isEmpty()){
            return false;
        }

         long code;
         try {
             code = Long.parseLong(productCode);
         }
         catch (NumberFormatException e)
         {
             return false;
         }

        for (int i = 0; i < productCode.length() - 1; i++) {
            c = productCode.charAt(i);
            if((productCode.length() %2 == 0)){
                if (i % 2 == 0) {
                    somma += Character.getNumericValue(c)*3 ;
                } else {
                    somma += Character.getNumericValue(c);
                }
            }
            else {
                if (i % 2 == 0) {
                    somma += Character.getNumericValue(c);
                } else {
                    somma += Character.getNumericValue(c) * 3;
                }
            }
        }

        int sup = (somma / 10 + 1) * 10;
        if (sup - somma > 9) {
            sup = sup - 10;
        }

        if(Character.getNumericValue(productCode.charAt(productCode.length()-1)) != sup - somma){
            return false;
        }else
            return true;

    }

}
