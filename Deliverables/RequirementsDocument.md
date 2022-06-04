# Requirements Document 

Authors: Sébastien Cadusseau, Michele Formisano, Andrea Gaminara, Andrea Lafratta.

Date: 19/03/2021

Version: 1.0

# Contents

- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
    	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)
- [Deployment diagram](#deployment-diagram)

# Essential description

Small shops require a simple application to support the owner or manager. A small shop (ex a food shop) occupies 50-200 square meters, sells 500-2000 different item types, has one or a few cash registers 
EZShop is a software application to:
* manage sales
* manage inventory
* manage customers
* support accounting


# Stakeholders


| Stakeholder name  | Description | 
| ----------------- |:-----------:|
|   Employee    |Who sells shop’s items.| 
|   Manager (Owner)    |Manages the shop (order items, do the accountings)| 
|   Consumer     |Buyers| 
|   Supplier     | People or businesses who sell goods to the shop and rely on you for revenue from the sale of those goods| 
|   POS system    | System that enables the business transaction between the client and the company to be completed.<br> POS system is a computerized network that consists of the main computer linked with a checkout<br> terminal and supported by different hardware features starting from barcode scanners and<br> ending with card payment terminals| 
|  Cash Register    |Mechanical or electronic device for registering and calculating transactions at a point of sale| 
|   Software developer team    |Design management software. Also available to operate on software maintenance| 
|   Product    |Identified by a unique barcode. Bought by shop customers. Ordered by the shop Owner from Supplier| 
|   Fidelity Card |Associated to a unique customer with a code bar. Provides advantages to the customer when he<br> accumulates points on it, each time he buys products| 
|Barcode reader|Is an optical scanner that can read printed barcodes, decode the data contained in the barcode<br> and send the data to a computer|

# Context Diagram and interfaces

## Context Diagram

```plantuml
top to bottom direction
actor Employee as e 
actor Manager as m
actor "Cash Register" as cr
actor POS as p
actor "Barcode Reader" as br
actor Product as pr
actor "Fidelity Card" as fd
p-right-(EZshop)
cr-left-(EZshop)
fd-down-(EZshop)
pr -down-(EZshop)
e -up- (EZshop)
m -up-(EZshop)
br -up-(EZshop)
m-left-|>e
```


## Interfaces

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| :-----:|
|   Manager| GUI<br>(manager credentials)| Screen, keyboard and mouse on PC |
|   Employee    | GUI<br> (employee credentials) | Screen, keyboard and mouse on PC  |
|   Fidelity card | Barcode scanner  | Laser beam  |
|  Product | Barcode scanner  | Laser beam  |
| POS | POS API  |  Wired / Wireless |
|  Cash Register | Cash Register API | Wire  |
|  Barcode reader | Barcode reader API | Wire  |

We decided to consider Product and Fidelity card as actors since they have a barcode and can interact with the system through the barcode reader. It's an indirect interaction, but these two actors have also other direct interactions with the system (for example the barcode can be inserted directly in the system through the keyboard).

We decided to leave general information for what concerns POS, Cash Register and Barcode reader and not to insert information about 
specific model of these objects and related APIs. (Our system can work with different kinds of these objects, provided that the correct API is used).

# Stories and personas
<b>Tim</b> Manager
<b>Mark</b> Employee
<b>Alex</b> Employee


<b>Tim</b> is 50, he has been working in the retail business for 30 years. He has started as a simple salesman then he has filled different managerial posts in the companies over the years. Today he owns a small shop seeing as it’s a new business he decided to help himself through an application to execute all the roles essential to manage the store.
 
<b>Mark</b> is 23, he’s a sports lover and a student worker, moreover is very informed about sport’s apparel. He has been working for 4 years in a chain of sports stores. Now he’s looking for a job in a small store to try to grow up professionally and to start to get in touch with most shop issues. He would like to help customers in finding the products they like in an easy and efficient way.

<b>Alex</b> is a 29 years old man. He loves sports and practices tennis and running in his spare time. He works in the shop as an employee. He likes advising customers and sharing his expertise on their choices. He needs to be fast in managing customers when they are at the cash for the checkout. He likes when things work fast, and updating the inventory manually every day is a long and tedious task on which he would love to spend less time.



# Functional and non functional requirements

## Functional Requirements


| ID        | Description  |
| ------------- |:-------------:| 
|  <b>FR1</b>    | Manage users |
|  FR1.1    | Log in  |
| FR1.2  | Log out | 
|  FR1.3     | Create/Modify/Delete accounts  |
|  FR1.4    | Manage account’s rights  |
| <b>FR2</b>  | Manage Inventory | 
|  FR2.1    | Show products by barcode/name/categories/price/size/quantity  |
|  FR2.2     | Update the inventory after a sale   |
| FR2.3 | Add/Remove products | 
|  FR2.4    | Modify product(new, change price, quantity, description, date of order receipt,size)|
|  FR2.5   | Manage order to the supplier |
|  FR2.5.1   | Show a warning to the owner to Make an order when a product is missing or quantity is under a minimum threshold |
|  FR2.5.2  | Insert/Delete an order |
|  FR2.5.3   | Modify an order (set if it’s on pending state or it’s completed) |
|  FR2.5.4   | Update product availability after the arrival of new supply |
|  FR2.5.5   | Show order history |
|  FR2.5.6   | Search an order filtering by features (date,product,quantity..) |
|  <b>FR3</b>  | Manage Sales |
|  FR3.1 | Create a shopping cart  |
| FR3.1.1  | Read the barcode of products| 
| FR3.1.2  | Add/Remove products from shopping cart| 
| FR3.2  | Handle payment| 
| FR3.2.1  | Manage the transaction through the POS/by cash/smartphone/tablet | 
| FR3.2.2  | Apply a potential discount related to fidelity card points (if available)| 
| FR3.3  | Add sales to the DB | 
| FR3.4  | Cancel sale| 
| <b>FR4</b> | Manage customers| 
| FR4.1  | Manage fidelity cards | 
| FR4.1.1  | Add/Remove points | 
| FR4.1.2| Create/Destroy a fidelity card | 
| <b>FR5</b>  | Support accounting | 
| FR5.1  | Add/Remove incomes | 
| FR5.2  | Add/Remove expenses | 
| FR5.3  | Compute profit/loss | 
| FR5.3.1  | On daily/monthly/annual basis/all time | 

## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1 | Usability  | Application needs a simple training but it’s mostly intuitive | ALL FR |
|  NFR2 | Performances | All functions should complete in < 0.5 sec | ALL FR|
| NFR3  | Portability |The application should be accessed from All the Operating systems mounted <br>on pc or laptops (MacOS, Windows, Linux) | ALL FR |
| NFR4 | Privacy | The privacy of the customers is preserved  | FR4 | 
|  NFR5 | Maintainability | Very small effort since the software doesn’t need urgent or continuous updates | ALL FR |


# Use case diagram and use cases


## Use case diagram

```plantuml
top to bottom direction
actor Employee as e 
actor Manager as m
actor POS as p
actor "Cash register" as cr
actor Product as pr
actor "Fidelity Card" as fc
actor "Barcode Reader" as br

m-|>e

(EZshop).down.>(Manage Sales) #green;line.dashed;text:green :include
(EZshop).down.>(Manage Users) #green;line.dashed;text:green :include
(EZshop).down.>(Manage Inventory) #green;line.dashed;text:green :include
(EZshop).down.>(Support Accounting) #green;line.dashed;text:green :include
(EZshop).down.>(Manage Customers) #green;line.dashed;text:green :include

(Manage Sales) .> (Handle Payments) :include
(Manage Sales) .> (Shopping Cart) :include
(Manage Users) .> (Log in/out) :include
(Manage Customers) .up.> (Manage Fidelity Cards) :include

m -down-> (Manage Users)
e -down->(Log in/out)
e -up-> (Manage Sales)
p -down- (Handle Payments)
cr <-down- (Handle Payments)
pr <-up- (Shopping Cart)
fc <-up- (Handle Payments)
e-left->(Manage Inventory)
e-down->(Manage Customers)

fc<-down-(Manage Fidelity Cards)
m-up->(Support Accounting)
br -left->(Shopping Cart)
br -up->(Handle Payments)
```
<b>Manage Inventory
```plantuml
(Manage Inventory) as mi

mi .> (Manage order to the supplier) :include
mi .> (Show products) :include
mi .> (Update inventory after a sale) :include
mi .> (Add/Remove products) :include
mi .> (Modify products) :include
```
<b>Support Accounting
```plantuml
(Support Accounting) as sa

sa .> (Add/Remove incomes) :include
sa .> (Add/Remove expenses ) :include
sa .> (Compute profit/loss) :include
```



### Use case 1, UC1

<b>Use case ‘Log in’ UC1

| Actors Involved        | Manager, Employee  |
| ------------- |:-------------:| 
|  Precondition     |<ul> <li>Software is active</li> <li>manager account exists</li> |  
|  Post condition     | The user is logged in |
|  Nominal Scenario     | UC1.1 |
|  Variants     | UC1.2 |

##### Scenario 1.1 

| Scenario 1.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>Manager account exists</li></ul>|
|  Post condition     | The user is logged in and can use the application |
| Step#        | Description  |
|  1     | The user (Manager, Employee) inserts the username and password  |  
|  2     |  The user clicks “Log in” |
|  3     |  Software verifies if the credentials are correct |
|  4   |  The application shows the home for that user |

##### Scenario 1.2

| Scenario 1.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>Manager account exists</li></ul> |
|  Post condition     | The user isn't logged in and can't use the application |
| Step#        | Description  |
|  1     | The user (Manager, Employee) inserts the username and password  | 
|  2     |  The user clicks “Log in” | 
|  3     |  Software verifies if the credentials are correct |
|  4    |  Credentials are not correct and user has to try again |

### Use case 2, UC2

<b>Use case ‘Creating user account’  UC2

| Actors Involved        | Manager  |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>Manager account exists</li></ul> |  
|  Post condition     | Account is created |
|  Nominal Scenario     | UC2.1 |
|  Variants     | UC2.2 |

##### Scenario 2.1 

| Scenario 2.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>Manager account exists</li></ul> |
|  Post condition     | Account is created |
| Step#        | Description  |
|1|The manager clicks on “New account”|
|2|  The manager inserts the data and permissions | 
|3|  The manager  clicks on “Create account” |   
|4|   Software verifies if the username already exists and if the password format is correct |
|5|  The software shows the message “New account successfully created” |

##### Scenario 2.2

| Scenario 2.2 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>Manager account exists</li></ul> |
|  Post condition     | Account is not created |
| Step#        | Description  |
|1|The manager clicks on “New account”|
|2|  The manager inserts the data and permissions | 
|3|  The manager  clicks on “Create account” |   
|4|   Software verifies if the username already exists and if the password format is correct |
|5|   The software shows the message “Your password must contain a minimum of 8 characters” |

### Use case 3, UC3

<b>Use case ‘Add a new product’ UC3

| Actors Involved        | Manager, Employee  |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li></ul>  |  
|  Post condition     | Inventory is updated |
|  Nominal Scenario     | UC3.1 |
|  Variants     | UC3.2 |

##### Scenario 3.1 

| Scenario 3.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li></ul> |
|  Post condition     | Inventory is updated (The product is added to the inventory) |
| Step#        | Description  |
|1|   The user (manager or employee) clicks on the tab “Inventory”|  
|2|   The user (manager or employee)  clicks on “New product”|  
|3|   The manager insert product data (product barcode, name, description, size, quantity) |
|4|   The user(manager or employee) clicks on “Add product” |
|5|  The product is inserted in the inventory |

##### Scenario 3.2 

| Scenario 3.2 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li></ul> |
|  Post condition     | The product is not inserted |
| Step#        | Description  |
|1|   The user (manager or employee) clicks on the tab “Inventory”|  
|2|   The user (manager or employee)  clicks on “New product”|  
|3|   The manager insert product data (product barcode, name, description, size, quantity) |
|4|   The user(manager or employee) clicks on “Add product” |
|5| The software shows the message “There is already a product with this barcode: Insertion Failed” |

### Use case 4, UC4

<b>Use case ‘Add a new order’ UC4

| Actors Involved        | Manager, Employee  |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li><li>Manager has placed an order from the supplier</li></ul>  |  
|  Post condition     | History of order is updated |
|  Nominal Scenario     | UC4.1 |
|  Variants     |  |

##### Scenario 4.1 

| Scenario 4.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li><li>Manager has placed an order from the supplier</li></ul> |
|  Post condition     | History of order is updated |
| Step#        | Description  |
|1|   The  user (manager or employee) clicks on the tab “Orders”|  
|2|   The  user (manager or employee) clicks on “New order”|  
|3|  The  user (manager or employee) insert order data (product barcode, date, quantity) |
|4|   The user (manager or employee)  clicks on “Add order” |
|5| The order is inserted as “pending” |
|6| The order is added to the “pending orders” |

### Use case 5, UC5

<b>Use case ‘Modify an order’ UC5

| Actors Involved        | Employee, Manager  |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li><li>An order has arrived to the shop</li></ul> |  
|  Post condition     | <ul> <li>History of order is updated</li> <li>Inventory is updated</li></ul> |
|  Nominal Scenario     | UC5.1 |
|  Variants     | |

##### Scenario 5.1 

| Scenario 5.1 | |
| ------------- |:-------------:| 
|  Precondition     |<ul> <li>Software is active</li> <li>User is logged in</li><li>An order has arrived to the shop</li> </ul> |
|  Post condition     | <ul> <li>History of order is updated</li> <li>Inventory is updated</li></ul> |
| Step#        | Description  |
|1|  The user clicks on “pending Orders”|  
|2|   The user clicks on “pending Orders”|  
|3|  The user finds the right order |
|4|   The user presses “Edit” button |
|5| The order status is modified from “pending” to “completed” |
|6| The order is added to the “completed” orders section |
|7| Product availability in the inventory is updated |


### Use case 6, UC6

<b>Use case ‘Manage Sales ’  UC6

| Actors Involved        | Employee, Manager, POS, Cash Register, Fidelity Card, Product, Barcode scanner  |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li> <li>A customer is at the checkout</li></ul> |  
|  Post condition     | <ul> <li>The sale is completed</li> <li>The database of the sales is updated</li></ul> |
|  Nominal Scenario     | UC6.1 |
|  Variants     | UC6.2 UC6.3 |

##### Scenario 6.1 

| Scenario 6.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul> <li>Software is active</li> <li>User is logged in</li> <li>A customer is at the checkout</li></ul> |
|  Post condition     | <ul> <li>The sale is completed</li> <li>The database of the sales is updated</li></ul> |
| Step#        | Description  |
|1|  The user (manager or employee) clicks on the “sales” tab|  
|2|   The user (manager or employee) clicks on the “open shopping cart” button|  
|3| The user (manager or employee) uses the laser beam on the product barcode |
|4|  The product is added to the cart …  (until all the products are added)|
|5| The user clicks on “Proceed with payment” |
|6| The customer chooses to pay with the credit card |
|7| The user starts the transaction through the POS |
|8|  The transaction is completed |
|9| The cash register provides the ticket to the customer |
|10| The sale is added to the database |

##### Scenario 6.2

| Scenario 6.2 | |
| ------------- |:-------------:| 
|  Precondition     | <ul><li>Software is active</li> <li>User is logged in</li> <li>A customer is at the checkout</li> <li>The customer has his fidelity card</li> <li>Customer accumulated enough fidelity points to have a discount</li></ul>|
|  Post condition     | <ul><li>The sale is completed</li> <li>The points balance on the fidelity card is updated</li> <li>The database of the sales is updated</li></ul>|
| Step#        | Description  |
|1|  The user (manager or employee) clicks on the “sales” tab|  
|2|   The user (manager or employee) clicks on the “open shopping cart” button|  
|3| The user (manager or employee) uses the laser beam on the Fidelity Card and on the product barcode |
|4|The software displays customer’s fidelity card information (id, name, points, available discount)|
|5|  The product is added to the cart …  (until all the products are added) |
|6| The user (manager or employee)  clicks on “apply discount” button |
|7| Fidelity points are subtracted from the fidelity card |
|8| The user clicks on “Proceed with payment” |
|9| The customer chooses to pay with the credit card |
|10| The shop employee starts the transaction through the POS |
|11|  The transaction is completed |
|12| The cash register provides the ticket to the customer |
|13| The sale is added to the database |

##### Scenario 6.3

| Scenario 6.3 | |
| ------------- |:-------------:| 
|  Precondition     | <ul><li>Software is active</li> <li>User is logged in</li> <li>A customer is at the checkout</li>
|  Post condition     | The sale is not completed |
| Step#        | Description  |
|1|  The user (manager or employee) clicks on the “sales” tab|  
|2|   The user (manager or employee) clicks on the “open shopping cart” button|  
|3| The user (manager or employee) uses the laser beam on the product barcode |
|4|  The product is added to the cart …  (until all the products are added)|
|5| The user clicks on “Proceed with payment” |
|6| The customer chooses to pay with the credit card |
|7| The user starts the transaction through the POS |
|8|  The transaction isn’t completed since the card’s customer could not afford the payment|
|9| The user (manager or employee) clicks on “cancel sale” |
|10| The sale is canceled |

### Use case 7, UC7

<b>Use case ‘Create a fidelity card’  UC7

| Actors Involved        | Employee, Manager, Fidelity Card  |
| ------------- |:-------------:| 
|  Precondition     |<ul><li>Customer wants to make a fidelity card</li> <li>Software is active</li> <li>User logged in</li></ul>|  
|  Post condition     | Customer database is updated|
|  Nominal Scenario     | UC7.1 |
|  Variants     |  |

##### Scenario 7.1 

| Scenario 7.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul><li>Customer wants to make a fidelity card</li> <li>Software is active</li> <li>User logged in</li></ul> |
|  Post condition     | Customer database is updated |
| Step#        | Description  |
|1|  The user (Employee or Manager) goes to the tab “Customers”|  
|2|   The user (Employee or manager) clicks  “Add new Card”|  
|3| Customer provides personal data(name, surnamel) |
|4|  The user (Employee or manager) inserts customer data |
|5| The user clicks on “create card”, and customer data are saved into the customer database |
|6| The employee/manager provides the physical card to the customer |

### Use case 8, UC8

<b>Use case ‘Add new expense/income’   UC8

| Actors Involved        |  Manager  |
| ------------- |:-------------:| 
|  Precondition     |<ul><li>Software is active</li> <li>Manager logged in</li></ul>|  
|  Post condition     | The total expense/income  is updated|
|  Nominal Scenario     | UC8.1 |
|  Variants     |  |

##### Scenario 8.1 

| Scenario 8.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul><li>Software is active</li> <li>Manager logged in</li></ul> |
|  Post condition     | The total expense/income is updated |
| Step#        | Description  |
|1|  The manager clicks on tab “Accounting”|  
|2|   The manager clicks on “Add expense/income”|  
|3| The manager inserts expense/income data |
|4|  The manager click on “Complete procedure” |
|5| The new expense/income is inserted |
|6| The new expense/income is added to the total |

### Use case 9, UC9

<b>Use case ‘Check profit/loss’  UC9

| Actors Involved        |  Manager  |
| ------------- |:-------------:| 
|  Precondition     |<ul><li>Software is active</li> <li>Manager logged in</li></ul>|  
|  Post condition     | Manager checks updated net income in a chosen period|
|  Nominal Scenario     | UC9.1 |
|  Variants     |  |

##### Scenario 9.1 

| Scenario 9.1 | |
| ------------- |:-------------:| 
|  Precondition     | <ul><li>Software is active</li> <li>Manager logged in</li></ul> |
|  Post condition     | Manager checks updated net income in a chosen period |
| Step#        | Description  |
|1|  The manager clicks on tab “Accounting”|  
|2|  The manager clicks on “date filter” |  
|3| The manager specifies the period in which the net income has to be displayed |
|4|  The net income is displayed for the chosen period |




# Glossary

```plantuml
class Product{
+barcode
+quantity
}
class ProductDescriptor{
+barcode
+name
+category
+price
+size
}
class EZShop #yellow
class User{
+username
+password
+permissions
}

class Transaction{
+amount
+date
+card/cash or mobile device
}
class Customer
class Inventory
class UsualCustomer{
+name
+surname
+ID
}
class FidelityCard{
+ID
+points
}
class Manager{
+managerID
}
class Employee{
+employeeID
}
class Cash
class CreditCard
class MobileDevice{
+smartphone/tablet
}
class Order{
+ID
+barcode
+product
+quantity
+date
+status
+price
}
class Supplier{
+ID
}
class Expense{
+ID
+object
+date
+amount
}
class Income{
+ID
+object
+date
+amount
}


class Balance{
+total income
+total expenses
+net income
+time period
}
User <|-- Manager
User <|-- Employee
Customer <|-up- UsualCustomer
UsualCustomer -right- FidelityCard
UsualCustomer "*"-up- EZShop
EZShop -right-"*" User
EZShop -right- Inventory
Inventory --"*" Product
ProductDescriptor -left-"*" Product : +is described by
Transaction -up-"1..*" Product
Transaction -down-"0..1" Cash
Transaction -down-"0..1" CreditCard
Transaction -down-"0..1" MobileDevice
Customer --"0..*" Cash
Customer --"0..*" CreditCard
Customer --"0..*" MobileDevice
EZShop -up-"*" Order 
Order "*"-left-Supplier
Balance -up-"*" Expense
Balance --"*" Income
Order -right-|> Expense
Income <|-- Transaction
EZShop -up-Balance
```


# System Design
<b>Not really meaningful in this case. Only software components are needed.

# Deployment Diagram 

```plantuml
artifact Ezshop
node PC
database Database
Ezshop -- PC
PC -right- Database
```

