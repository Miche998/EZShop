Authors: Sébastien Cadusseau, Michele Formisano, Andrea Gaminara, Andrea Lafratta.

Date: 30/04/2021

Version: 1.0


# Contents

- [High level design](#high-level-design)
- [Low level design](#low-level-design)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

# High level design 


Patterns used:
* <b>Facade

Architectural styles used:
* <b>3-tier architecture

```plantuml
package "GUI" {
}

package "Application Logic and Model" {
  package "it.polito.ezshop.data"{
  }
  package "it.polito.ezshop.exceptions"{
  }
  package "it.polito.ezshop.model"{
  }
  package "it.polito.ezshop.util"{
  }
}

"GUI" --> "it.polito.ezshop.data"
"it.polito.ezshop.data" -->  "it.polito.ezshop.model"
"it.polito.ezshop.data" -->  "it.polito.ezshop.exceptions"
"it.polito.ezshop.data" -->  "it.polito.ezshop.util"

```




# Low level design


```plantuml
left to right direction


package "it.polito.ezshop.data"{
class EZShop_interface{
+reset() 
+createUser() 
+deleteUser() 
+getAllUsers() 
+getUser() 
+updateUserRights() 
+login() 
+logout() 
+createProductType() 
+updateProduct() 
+deleteProductType() 
+getAllProductTypes() 
+getProductTypeByBarCode() 
+getProductTypesByDescription()
+updateQuantity()
+updatePosition()
+issueOrder() 
+payOrderFor()
+payOrder()
+recordOrderArrival() 
+getAllOrders()
+defineCustomer()
+modifyCustomer() 
+deleteCustomer() 
+getCustomer()
+getAllCustomers()
+createCard() 
+attachCardToCustomer()
+modifyPointsOnCard() 
+startSaleTransaction()
+addProductToSale()
+deleteProductFromSale() 
+applyDiscountRateToProduct()
+applyDiscountRateToSale()
+computePointsForSale() 
+endSaleTransaction()
+deleteSaleTransaction()
+getSaleTransaction()
+startReturnTransaction()
+returnProduct() 
+endReturnTransaction()
+deleteReturnTransaction()
+receiveCashPayment()
+receiveCreditCardPayment()
+returnCashPayment() 
+returnCreditCardPayment() 
+recordBalanceUpdate() 
+getCreditsAndDebits() 
+computeBalance() 
+recordOrderArrivalRFID()
+addProductToSaleRFID()
+deleteProductFromSaleRFID()
+returnProductRFID() 
}
class EZShop{ 
-loggedUser : UserImpl
-DB : DBManager
-sales : HashMap<Integer, SaleTransactionImpl> 
-returns : HashMap<Integer,ReturnTransactionImpl>
+productCodeAlgorithm()
+checkLuhn()
} 
 class  EZShop  implements  EZShop_interface
}

package "it.polito.ezshop.model"{

class UserImpl{ 
-username : String 
-password : String 
-role : String 
-id : Integer 
} 


EZShop -->"*"BalanceOperationImpl

class BalanceOperationImpl {  
-type : String  
-amount : double  
-date : LocalDate 
-Integer : BalanceId
}

EZShop -->"*" CustomerImpl
UserImpl "1..*" <-- EZShop 

class ProductTypeImpl{     
-id : Integer     
-productCode : String     
-description :String 
-pricePerUnit : double
-quantity : Integer
-note : String
-productPosition : Position
-Location : String
+modifyQuantity()
} 

class ProductImpl{
-RFID : Long
-barCode : String
}
ProductTypeImpl -->"*" ProductImpl : describes

EZShop --> "*" ProductTypeImpl 
EZShop --> "*" SaleTransactionImpl 

class SaleTransactionImpl {     
-id : Integer    
-price :double
-status : String
-discount rate : double
-RFIDs : List<ProductImpl> 
-ticketEntries : List<TicketEntry>
+modifyEntryByBarcode()
+computePrice()
} 


class LoyaltyCardImpl {
-cardCode : String 
-points : Integer 
} 

class CustomerImpl {
-id : Integer
-customerName :String
-loyaltycard : LoyaltyCardImpl 
}  

LoyaltyCardImpl "0..1" <-- CustomerImpl  


class OrderImpl {
-orderId : Integer  
-balanceId : Integer
-productCode : String
-pricePerUnit :double
-quantity : int 
-status : String 
}  

OrderImpl  -down-> ProductTypeImpl

class ReturnTransactionImpl {
-returnId : Integer
-transactionId : Integer
-price : double
-status : String
-ticketEntries : List <TicketEntryImpl>
-RFIDs : List<ProductImpl> 
+computePrice()
}  

class TicketEntryImpl{
- productCode : String
- productDescription : String
- amount : int
- pricePerUnit : double
- discountRate : double
}

ReturnTransactionImpl "*"<-up- SaleTransactionImpl

SaleTransactionImpl ->"*"TicketEntryImpl
ReturnTransactionImpl ->"*" TicketEntryImpl

OrderImpl -up->BalanceOperationImpl
TicketEntryImpl ->ProductTypeImpl
}
SaleTransactionImpl ->"*" ProductImpl
ReturnTransactionImpl ->"*" ProductImpl

package "it.polito.ezshop.util"{
class DBManager{
+getConnection()
+initialise()
+reset()
+addUser()
+deleteUser()
+getAllUsers()
+getUser()
+updateUserRights()
+login()
+addProduct()
+updateProduct()
+deleteProduct()
+getAllProducts()
+getProductByBarCode()
+getProductByDescription()
+updateQuantity()
+updatePosition()
+issueOrder()
+payOrder()
+getOrder()
+getProductFromOrder()
+recordOrderArrival()
+getAllOrders()
+addCustomer()
+modifyCustomer()
+deleteCustomer()
+getCustomer()
+getAllCustomers()
+addCard()
+attachCardToCustomer()
+modifyPointsOnCard()
+getNewTransactionId()
+getSaleTransaction()
+addClosedSaleTransaction()
+deleteSaleTransaction()
+getNewReturnTransactionId()
+updateSaleTransaction()
+deleteReturnTransaction()
+addClosedReturnTransaction()
+getReturnTransaction()
+addBalanceOperation()
+getCreditsAndDebits()
+addSaleTransactionPayment()
+addReturnTransactionPayment()
+setBalanceIdToOrder()
+checkRFID()
+recordOrderArrivalRFID()
+getProductByRFID()
+removeItem()
+addItem()

}
}

EZShop -down-> DBManager

package "it.polito.ezshop.exceptions"{
class InvalidUsernameException
class InvalidPasswordException
class InvalidRoleException
class InvalidUserIdException
class UnauthorizedException
class InvalidProductDescriptionException
class InvalidProductCodeException
class InvalidPricePerUnitException
class InvalidProductIdException
class InvalidLocationException
class InvalidQuantityException
class InvalidOrderIdException
class InvalidCustomerNameException
class InvalidCustomerCardException
class InvalidCustomerIdException
class InvalidTransactionIdException
class InvalidDiscountRateException
class InvalidPaymentException
class InvalidCreditCardException
class InvalidRFIDException
}
EZShop_interface -> "it.polito.ezshop.exceptions"
```



# Verification traceability matrix


|             | FR1|FR3|FR4|FR5|FR6|FR7|FR8|             
| :----------- | :---------: | :---------: | :---------: | :---------: | :---------: | :---------: | :---------: |
|EZShop |x |x|x |x |x |x |x |
|UserImpl |x |x |x |x |x |x |x |
|ProductTypeImpl | |x |x | |x | | |
|OrderImpl | | |x | | | | |
|SaleTransactionImpl | | | | |x |x | |
|ReturnTransactionImpl | | | | |x |x | |
|CustomerImpl | | | |x |x | | |
|LoyaltyCardImpl | | | |x |x | | |
|BalanceOperationImpl | | |x | |x |x |x |
|TicketEntryImpl | | | | |x | | |






# Verification sequence diagrams 

<br><b>UC3 : Scenario 3-2<br>
```plantuml
Administrator -> EzShop : Log in()
Administrator -> EzShop : payOrder()
' You can also declare:
EzShop->OrderImpl: setStatus()
OrderImpl -> EzShop : status updated to PAYED
EzShop -> BalanceOperationImpl : recordBalanceUpdate()
BalanceOperationImpl -> EzShop : Balance Updated
```
<br><b>UC6 : Scenario 6-4<br>
```plantuml
User -> EzShop : Log in()
User -> EzShop : StartSaleTransaction()
EzShop -> SaleTransactionImpl : new SaleTransaction()
SaleTransactionImpl -> EzShop : Sale transaction added
 EzShop -> User : returns SaleTransactionid
User -> EzShop : reads barcode of product X
User -> EzShop : addProductToSale()
EzShop -> ProductTypeImpl : updateQuantity()
ProductTypeImpl -> EzShop : quantity updated
User -> EzShop : endSaleTransaction()
EzShop -> User : the system asks for payment type
User -> EzShop : reads loyalty Card serial number
User -> EzShop : reads Credit Card number
User ->EzShop : receiveCreditCardPayment()
EzShop -> User : payment successfull
EzShop -> EzShop : computePointsForSale()
EzShop -> LoyaltyCardImpl : modifyPointsOnCard()
LoyaltyCardImpl -> EzShop : operation successfull
EzShop -> BalanceOperationImpl : recordBalanceUpdate()
BalanceOperationImpl -> EzShop : Balance Updated
```
<br><b>UC6 : Scenario 6-5<br>
```plantuml
User -> EzShop : Log in()
User -> EzShop : StartSaleTransaction()
EzShop -> SaleTransactionImpl : new SaleTransaction()
SaleTransactionImpl -> EzShop : Sale transaction added
 EzShop -> User : returns SaleTransactionid
User -> EzShop : reads barcode of product X
User -> EzShop : addProductToSale()
EzShop -> ProductTypeImpl : updateQuantity()
ProductTypeImpl -> EzShop : quantity updated
User -> EzShop : endSaleTransaction()
EzShop -> User : the system asks for payment type
User -> EzShop : deleteSaleTransaction()
EzShop -> ProductTypeImpl : updateQuantity()
ProductTypeImpl -> EzShop : quantity restored
EzShop -> User : sale transaction aborted
```
<br><b>UC8 : Scenario 8-2<br>
```plantuml
User -> EzShop : Log in()
User -> EzShop : inserts transactionID
User -> EzShop : startReturnTransaction()
EzShop -> ReturnTransactionImpl : new ReturnTransaction()
ReturnTransactionImpl -> EzShop : Return transaction added
EzShop -> User : returns ReturnTransactionid
User -> EzShop : reads barcode of product X
User -> EzShop : returnProduct()
EzShop -> ProductTypeImpl : UpdateQuantity()
ProductTypeImpl -> EzShop : quantity updated
User -> EzShop : returnCashPayment()
User -> EzShop : endReturnTransaction()
EzShop -> User : Return Transaction successfull
EzShop -> SaleTransactionImpl : modifyEntryByBarcode()
EzShop -> SaleTransactionImpl : computePrice()
EzShop <- SaleTransactionImpl : Transaction Updated
EzShop -> BalanceOperationImpl : recordBalanceUpdate()
BalanceOperationImpl -> EzShop : Balance Updated
```
