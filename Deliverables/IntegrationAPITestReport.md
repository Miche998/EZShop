# Integration and API Test Documentation

Authors: Sébastien Cadusseau, Michele Formisano, Andrea Gaminara, Andrea Lafratta.

Date: 26/05/2021

Version: 1.0


# Contents

- [Dependency graph](#dependency-graph)

- [Integration approach](#integration-approach)

- [Tests](#tests)

- [Coverage of scenarios and FR](#coverage-of-scenarios-and-fr)
- [Coverage of non-functional requirements](#coverage-of-non-functional-requirements)



# Dependency graph 

     
```plantuml
top to bottom direction
SaleTransactionImpl <-up- DBManager
SaleTransactionImpl <-up-EZShop
ReturnTransactionImpl <-up- DBManager
ReturnTransactionImpl <-up-EZShop
SaleTransactionImpl ->TicketEntryImpl
ReturnTransactionImpl -down-> TicketEntryImpl
TicketEntryImpl <-down-EZShop
DBManager -->BalanceOperationImpl

class EZShop{ 
} 
class UserImpl{ 
} 
class BalanceOperationImpl {  
}

DBManager -down-> CustomerImpl
EZShop -down-> CustomerImpl
UserImpl  <-up- DBManager 
UserImpl  <-up- EZShop
class ProductTypeImpl{     
} 

DBManager -down-> ProductTypeImpl 
EZShop -down-> ProductTypeImpl 

class SaleTransactionImpl {     
} 


class LoyaltyCardImpl {

} 

class CustomerImpl {
}  

LoyaltyCardImpl <-up- CustomerImpl  


class OrderImpl {
}  

class ReturnTransactionImpl {
}  
class TicketEntryImpl{
}
class ProductImpl{
}
ProductImpl<-up- SaleTransactionImpl
ProductImpl<-up- ReturnTransactionImpl
ProductImpl <-up- EZShop
OrderImpl <-up-DBManager
OrderImpl <-up-EZShop
class DBManager{
}
component "GUI EZShop" {
}

EZShop -down-> DBManager
"GUI EZShop" -down->EZShop
```

# Integration approach

    <Integration approach :  bottom up >
    <step1:  class A : TicketEntryImpl,OrderImpl,ProductTypeImpl,BalanceOperationImpl,UserImpl,LoyaltyCardImpl> 
    <step2: class A+B :  A + (CustomerImpl,SaleTransactionImpl,ReturnTransactionImpl)>
    <step3: class (A+B)+C : (A+B) + DBManager>
    <step4: class A+B+C+D: (A+B+C) + EzShop>



#  Tests


## Step 1
| Classes  | JUnit test cases |
|--|--|
| TicketEntryImpl | testTicketSetBarCode<br />testTicketSetProductDescription<br />testTicketSetAmount<br />testTicketSetDiscountRate<br />testTicketSetPricePerUnit |
| OrderImpl | testSetProductCode<br />testSetPricePerUnit<br />testSetQuantity<br />testSetStatus<br />testsetBalanceId<br />testsetOrderId |
| ProductTypeImpl | testSetQuantity<br />testSetLocation<br />testSetNote<br />testSetProductDescription<br />testSetBarcode<br />testSetPricePerUnit<br />testSetId<br />testModifyQuantity |
| BalanceOperationImpl | testSetBalanceId<br />testSetDate<br />testSetMoney<br />testSetType |
| UserImpl | testSetId<br />testSetPassword<br />testSetRole |
|LoyaltyCardImpl|testSetCardCode<br />testSetPoints|
|ProductImpl|testSetRFID<br/>testSetBarCode|


## Step 2
| Classes  | JUnit test cases |
|--|--|
|CustomerImpl|testSetCustomerName<br />testSetCustomerCard<br />testSetId<br />testSetPoints<br />testSetLoyaltyCard|
|SaleTransactionImpl|testSetTicketNumber<br />testSetEntries<br />testSetDiscountRate<br />testSetPrice<br />testSetStatus<br />testComputePrice<br />testModifyEntryByBarcode|
|ReturnTransactionImpl|testSetReturnId<br />testSetTransactionId<br />testSetPrice<br />testSetStatus<br />testSetEntries<br />testComputePrice|


## Step 3

| Classes  | JUnit test cases |
|--|--|
|DBManager|TestRecordOrderArrivalRFID<br />TestCheckRFID<br />TestGetProductRFID<br />TestRemoveItem<br />TestAddBalanceOperationCorrect<br />TestGetCreditsAndDebitsCorrect<br />TestGetCreditsAndDebitsInvertedDate<br />TestGetCreditsAndDebitsEmptyList<br />TestGetCreditsAndDebitsFrom_Null<br />TestGetCreditsAndDebitsTo_null<br />TestGetCreditsAndDebitAllNull<br/>TestAddReturnTransactionCorrect<br/>TestAddReturnTransactionEntriesCorrect<br />TestAddReturnTransactionWrong<br/>TestAddTransactionCorrect<br/>TestAddTransactionWrong<br/>TestAddTransactionCorrectList<br />TTestAddNewCustomer<br/>TestExistingCustomer<br>TestAddProductWorking<br/>TestAddProductSameBarcode<br/>TestAddReturnCreditCardPayment<br/>TestAddReturnCashPayment<br/>TestAddCreditCardPayment<br/>TestAddCashPayment<br/>TestAddNewUser<br/>TestExistingUser<br/>TestDeleteCustomerC<br/>TestDeleteCustomerW<br/>TestDeleteProductCorrect<br/>TestDeleteProductWrong<br/>TestDeleteReturnTransactionCorrect<br/>TestDeleteReturnTransactionNotExistent<br/>TestDeleteReturnTransactionPayed<br/>TestDeleteTransactionCorrect<br/>TestDeleteTransactionNotExistent<br/>TestDeleteTransactionPayed<br/>TestDeleteUserCorrect<br/>TestDeleteUserWrong<br/>TestGetAllOrdersCorrect<br/>TesGetAllOrdersWrong<br/>TestGetAllCustomers<br/>TestGetAllCustomersWithCard<br/>TestGetAllCustomersEmpty<br/>TestGetCustomer<br/>TestGetCustomerNull<br/>TestGetCustomerWithCard<br/>TestGetOrderCorrect<br/>TestGetOrderWrong<br/>TestGetAllProducts<br/>TestGetAllProductsEmpty<br/>TestGetProductByBarCode<br/>TestGetProductByBarCodeNull<br/>TestGetProductByDescription<br/>TestGetProductByDescriptionNull<br/>TestGetProductFromOrder<br/>TestGetReturnTransactionCorrect<br/>TestGetReturnTransactionEntriesCorrect<br />TestGetReturnTransactionNotExistent<br/>TestGetTransactionCorrect<br>TestGetTransactionEntriesCorrect<br />TestGetTransactionNotExistent<br/>TestGetAllUsers<br/>TestGetAllUsersEmpty<br/>TestGetUser<br/>TestGetUserNull<br/>TestIssueOrderWrong<br/>TestIssueOrderCorrect<br/>TestLoginCorrect<br/>TestLoginNoUserExisting<br/>TestLoginWrongCredentials<br/>TestModifyCustomerNameAndCardCorrect<br/>TestModifyCustomerNameAndCardWrong<br/>TestModifyCustomerNameAndCardEmptyString<br/>TestModifyCustomerNameAndCardNull<br/>TestNewReturnId<br/>TestNewReturnIdZero<br/>TestNewTransactionId<br/>TestNewTransactionIdZero<br/>TestPayOrderCorrect<br/>TestPayOrderNotExisting<br/>TestPayOrderWrongNotIssued<br/>TestRecordOrderArrivalNotExisting<br/>TestRecordOrderArrivalNotPayed<br/>TestRecordOrderArrivalCorrect<br/>testSetBalanceIdToOrderWrong<br/>testSetBalanceIdToOrderCorrect<br/>TestUpdatePositionCorrect<br/>TestUpdatePositionProductNotExists<br/>TestUpdatePositionOccupied<br/>TestUpdateProductCorrect<br/>TestUpdateProductWrongId<br/>TestUpdateProductWrongBarcode<br/>TestUpdateQuantityCorrect<br/>TestUpdateQuantityProductNotExists<br/>TestUpdateQuantityNegativeQuantity<br/>TestUpdateQuantityNoPosition<br/>TestUpdateTransactionCorrect<br/>TestUpdateTransactionEntriesCorrect<br />TestUpdateTransactionNotExistent<br/>TestUpdateUserRightsCorrect<br/>TestUpdateUserRightsWrong<br/>|

## Step 4

| Classes | JUnit test cases                                             |
| ------- | ------------------------------------------------------------ |
| EzShop  | TestAddProductToSaleRFID<br/>TestDeleteProductFromSaleRFID<br/>TestRecordOrderArrivalRFID<br/>TestReturnProductRFID<br/>TestAddProductToSaleTransactionInvalidTransaction<br/>TestAddProductToSaleTransactionUnauthorized<br/>TestAddProductToSaleTransactionNotExistent<br/>TestAddProductToSaleTransactionQuantityException<br/>TestAddProductToSaleCorrect<br/>TestAddProductToSaleTwiceCorrect<br/>TestAddProductToSaleTransactionInvalidCode<br/>TestAddProductToSaleTransactionProductNotExistent<br/>TestAddProductToSaleTransactionQuantityNotSufficient<br/>TestApplyDiscountRateToProductUnauthorized<br/>TestApplyDiscountRateToProductInvalidCode<br/>TestApplyDiscountRateToProductCorrect<br/>TestApplyDiscountRateToProductNotInTransaction<br/>TestApplyInvalidDiscountRateToProduct<br/>TestApplyDiscountRateToProductInvalidTransaction<br/>TestApplyDiscountRateToProductTransactionNotExistent<br/>TestApplyDiscountRateToProductNotExistent<br/>TestApplyDiscountRateToSaleInvalidTransaction<br/>TestApplyInvalidDiscountRateToSale<br/>TestApplyDiscountRateToSaleUnauthorized<br/>TestDeleteProductFromSaleTransactionNotExistent<br/>TestApplyDiscountRateToSaleCorrect<br/>TestAttachCardToCustomerNoUserLogged<br/>TestAttachCardToCustomerCorrect<br/>TestAttachCardToCustomerExistingCard<br/>TestAttachCardToCustomerNonExistingCustomer<br/>TestAttachCardToCustomerCidNullOrNegative<br/>TestAttachCardToCustomerCardWrongCardNumber<br/>TestComputTestAttachCardToCustomerNonExistingCard<br/>eBalanceUnauthorizedException<br/>TestComputeBalance<br/>TestComputePointsUnauthorized<br/>TestComputePointsForInvalidTransaction<br/>TestComputePointsCorrect<br/>TestDeleteProductFromSaleTransactionNotExistent<br/>TestCreateCardNoUserLogged<br/>TestCreateCardCorrect<br/>TestCreateProductTypeNullOrEmptyDescription<br/>TestCreateProductTypeNullNote<br/>TestCreateProductTypeNullOrEmptyProductCode<br/>TestCreateProductTypeWrong<br/>TestCreateProductTypeNegativePricePerUnit<br/>TestCreateProductTypeNoLoggedUser<br/>TestCreateProductTypeWrongLengthProductCode<br/>TestCreateProductTypeWrongRole<br/>TestCreateProductTypeCorrect<br/>TestCreateUserNullOrEmptyName<br/>TestCreateUserNullOrEmptyOrWrongRole<br/>TestCreateUserCorrect<br/>TestCreateUserNullOrEmptyPassword<br/>TestCreateUserExistingUser<br/>TestDefineCustomerNullOrEmptyName<br/>TestDefineCustomerExistingCustomer<br/>TestDefineCustomerCorrect<br/>TestDefineCustomerNoUserLogged<br/>TestDeleteCustomerNonExistingCustomer<br/>TestDeleteCustomerCorrect<br/>TestDeleteCustomerNoUserLogged<br/>TestDeleteCustomerIdNullOrNegative<br/>TestDeleteProductFromSaleTransactionUnauthorized<br/>TestDeleteProductFromSaleTransactionQuantityException<br/>TestDeleteProductFromSaleTransactionInvalidTransaction<br/>TestDeleteProductFromSaleTransactionNotExistent<br/>TestDeleteProductFromSaleTransactionProductNotExistent<br/>TestDeleteProductNotInSaleTransaction<br/>TestDeleteProductFromSaleTransactionQuantityNotSufficient<br/>TestDeleteProductFromSaleCorrect<br/>TestDeleteProductFromSaleTransactionInvalidCode<br/>TestDeleteProductTypeInvalidId<br/>TestDeleteProductTypeCorrect<br/>TestDeleteProductTypeNoLoggedUser<br/>TestDeleteProductTypeWrongRole<br/>TestDeleteInvalidReturnTransaction<br/>TestDeleteReturnTransactionCorrect<br/>TestDeleteReturnTransactionNotExistent<br/>TestDeleteReturnTransactionAlreadyPayed<br/>TestDeleteReturnTransactionUnauthorized<br/>TestDeleteSaleTransactionAlreadyPayed<br/>TestDeleteSaleTransactionCorrect<br/>TestDeleteSaleTransactionUnauthorized<br/>TestDeleteInvalidTransaction<br/>TestDeleteSaleTransactionNotExistent<br/>TestDeleteUserWrong<br/>TestDeleteUserWrongRole<br/>TestDeleteUserIdNull<br/>TestDeleteUserCorrect<br/>TestEndReturnTransactionUnauthorized<br/>TestEndReturnTransactionCorrectNoCommit<br/>TestEndInvalidReturnTransaction<br/>TestEndReturnTransactionNotExistent<br/>TestEndReturnTransactionCorrectCommit<br/>TestEndSaleTransactionCorrect<br/>TestEndSaleTransactionAlreadyClosed<br/>TestEndSaleTransactionNotExistent<br/>TestEndInvalidTransaction<br/>TestEndSaleTransactionUnauthorized<br/>TestGetAllOrdersCorrect<br/>TestGetAllOrdersNoLoggedUser<br/>TestGetAllProductTypesEmpty<br/>TestGetProductTypeByBarcodeWrong<br/>TestGetAllProductTypesNoLoggedUser<br/>TestGetProductTypesByDescriptionEmpty<br/>TestGetProductTypesByDescriptionWrongRole<br/>TestGetAllProductTypesCorrect<br/>TestGetProductTypeByBarcodeNoLoggedUser<br/>TestGetProductTypebyBarcodeInvalidProductCode<br/>TestUpdateProductTypeWrongRole<br/>TestGetProductTypesByDescriptionNoLoggedUser<br/>TestGetProductTypesByDescription<br/>TestGetProductTypeByBarcodeWrongRole<br/>TestGetProductTypeByBarcode<br/>TestGetCreditsAndDebitsUnauthorizedException<br/>TestGetCreditsAndDebits<br/>TestGetAllCustomersWithCard<br/>TestGetCustomerNonExistingCustomer<br/>TestGetAllCustomersNoCustomer<br/>TestGetCustomerInvalidId<br/>TestGetCustomerWithCard<br/>TestGetAllCustomersNoUserLogged<br/>TestGetCustomerNoUserLogged<br/>TestGetAllCustomersCorrect<br/>TestGetCustomerCorrect<br/>TestGetSaleTransactionCorrect<br/>TestGetSaleTransactionUnauthorized<br/>TestGetInvalidTransaction<br/>TestGetSaleTransactionNotExistent<br/>TestGetAllUserCorrect<br/>TestGetUserWrongRole<br/>TestGetUserCorrect<br/>TestGetUserNullorNegativeId<br/>TestGetAllUserWrongRole<br/>TestIUssueOrderProductCodeBadLength<br/>TestIssueOrderInvalidProductCode<br/>TestIssueOrderQuantityNegativeOrZero<br/>TestIssueOrderPricePerUnitNegativeOrZero<br/>TestIssueOrderProductCodeNullOrEmpty<br/>TestIssueOrderNoLoggedUser<br/>TestIssueOrderCorrect<br/>TestLogInWrong<br/>TestLogInCorrect<br/>TestLogInWrongUsername<br/>TestLogInWrongPassword<br/>TestLogoutCorrect<br/>TestLogoutWrong<br/>TestModifyCustomerNoUserLogged<br/>TestModifyCustomerModifyNameOnly<br/>TestModifyCustomerExistingCard<br/>TestModifyCustomerModifyNameAndRemoveCard<br/>TestModifyCustomerWrongCid<br/>TestModifyCustomerWrongCustomerName<br/>TestModifyCustomerNonExistingCard<br/>TestModifyCustomerCorrect<br/>TestModifyCustomerNonExistingCustomer<br/>TestModifyPointsOnCardNonExistingCard<br/>TestModifyPointsOnCardNoUserLogged<br/>TestModifyPointsOnCardCorrect<br/>TestModifyPointsOnCardWrongCardNumber<br/>TestModifyPointsOnCardNegativeTotalPoints<br/>TestModifyPointsOnCardZeroTotalPoints<br/>TestPayOrderNoLoggedUser<br/>TestPayOrderInvalidOrderId<br/>TestPayOrderCorrect<br/>TestPayOrderForNoLoggedUser<br/>TestPayOrderForProductCodeBadLength<br/>TestPayOrderForCorrect<br/>TestPayOrderForQuantityNegativeOrZero<br/>TestPayOrderForInvalidProductCode<br/>TestPayOrderForProductCodeNullOrEmpty<br/>TestPayOrderForPricePerUnitNegativeOrZero<br/>TestReceiveCashPaymentInvalidTransaction<br/>TestReceiveCashPaymentUnauthorized<br/>TestReceiveCashPayment<br/>TestReceiveCashPaymentInvalidPayment<br/>TestReceiveCreditCardPayment<br/>TestReceiveCreditCardPaymentInvalidCreditCard<br/>TestReceiveCreditCardPaymentInvalidTransaction<br/>TestReceiveCreditCardPaymentUnauthorized<br/>TestRecordBalanceUpdateUnauthorizedUser<br/>TestRecordBalanceUpdate<br/>TestRecordOrderArrivalNoLocation<br/>TestRecordOrderArrivalCorrect<br/>TestRecordOrderArrivalNoLoggedUser<br/>TestRecordOrderArrivalInvalidOrderId<br/>TestReset<br/>TestReturnCashPaymentUnauthorized<br/>TestReturnCashPayment<br/>TestReturnCashPaymentInvalidTransaction<br/>TestReceiveCreditCardPaymentInvalidCreditCard<br/>TestReturnCreditCardPaymentUnauthorized<br/>TestReturnCreditCardPaymentInvalidTransaction<br/>TestReturnCreditCardPayment<br/>TestReturnTooMuchProduct<br/>TestReturnProductUnauthorized<br/>TestReturnProductNotInTransaction<br/>TestReturnProductNotExistent<br/>TestReturnProductInvalidCode<br/>TestReturnProductQuantityException<br/>TestReturnProductTransactionNotExistent<br/>TestReturnProductInvalidReturnId<br/>TestReturnProductCorrect<br/>TestStartReturnTransactionSaleNotExistent<br/>TestStartReturnTransactionInvalidSale<br/>TestStartReturnTransactionUnauthorized<br/>TestStartReturnTransactionCorrect<br/>TestStartSaleTransactionCorrect<br/>TestStartSaleTransactionUnauthorized<br/>TestUpdatePositionNoLoggedUser<br/>TestUpdatePositionInvalidProductId<br/>TestUpdatePositionSameLocation<br/>TestUpdatePositionInvalidLocation<br/>TestUpdatePositionWrongRole<br/>TestUpdatePositionProductNotExists<br/>TestUpdatePositionCorrect<br/>TestUpdateProductTypeNullNote<br/>TestUpdateProductTypeNegativePricePerUnit<br/>TestUpdateProductTypeNoLoggedUser<br/>TestUpdateProductTypeNullOrEmptyDescription<br/>TestUpdateProductTypeWrongBarCode<br/>TestUpdateProductTypeNullOrEmptyProductCode<br/>TestUpdateProductTypeInvalidId<br/>TestUpdateProductTypeCorrect<br/>TestUpdateProductTypeWrongId<br/>TestUpdateProductTypeWrongRole<br/>TestUpdateProductTypeWrongLengthProductCode<br/>TestUpdateQuantityNoLoggedUser<br/>TestUpdateQuantityNegativeQuantity<br/>TestUpdateQuantityCorrect<br/>TestUpdateQuantityProductNotExists<br/>TestUpdateQuantityWrongRole<br/>TestUpdateQuantityInvalidProductId<br/>TestUpdateQuantityLocationNotAssigned<br/>TestUpdateUserRightsNullOrEmptyRole<br/>TestUpdateUserRightsNullOrNegativeId<br/>TestUpdateUserRightsWrongRole<br/>TestUpdateUserRightsWrong<br/>TestUpdateUserRightsCorrect |


# Coverage of Scenarios and FR

| Scenario ID | Functional Requirements covered | JUnit  Test(s) |
| ----------- | ------------------------------- | ----------- |
|  1.1 **Create product type X**  | FR 3.1 | TestCreateProductTypeCorrect<br />TestUpdatePositionCorrect  |
| 1.2 **Modify product type location** | FR 3.1,3.4 | TestGetProductTypeByBarcode<br />TestUpdatePositionCorrect   |
| 1.3 **Modify product type price per unit** | FR 3.1,3.4 | TestGetProductTypeByBarcode<br />TestUpdateProductTypeCorrect |
| 2-1 **Create user and define rights** | FR 1.1 | TestCreateUserCorrect |
| 2-2 **Delete user** | FR 1.2,1.4 | TestGetUserCorrect<br />TestDeleteUserCorrect |
| 2-3 **Modify user rights** | FR 1.1,1.4,1.5 | TestGetUserCorrect<br />TestUpdateUserRightsCorrect() |
| 3-1 **Order of product type X issued** | FR 4.3                                           | TestIssueOrderCorrect |
| 3-2 **Order of product type X payed** | FR 4.5                                           | TestGetOrderCorrect<br />TestPayOrderCorrect |
| 3-3 **Record order of product type X arrival** | FR 4.1,4.6                                       | TestRecordOrderArrival |
| 4-1 **Create customer record** | FR 5.1                                           | TestDefineCustomer |
| 4-2  **Attach Loyalty card to customer record** | FR 5.5,5.6                                       | TestCreateCard<br />TestAttachCardToCustomerCorrect |
| 4-3 **Detach Loyalty card from customer record** | FR 5.1,5.3                                       | TestGetCustomerCorrect<br />TestModifyCustomerModifyNameAndRemoveCard |
| 4-4 **Update customer record** | FR 5.1,5.3                                       | TestGetCustomerCorrect<br />TestModifyCustomerCorrect |
| 5-1 **Login** |                                                  | TestLogInCorrect |
| 5-2 **Logout** |                                                  | TestLogOutCorrect |
| 6-1 **Sale of product type X completed** | FR 4.1,6.1,6.2,6.7,6.8,6.10,6.11,7.1,7.2,8.2     | TestStartSaleTransactionCorrect<br />TestAddProductToSaleCorrect<br />TestEndSaleTransactionCorrect<br />TestReceiveCashPayment<br />TestReceiveCreditCardPayment<br />TestRecordBalanceUpdate |
| 6-2 **Sale of product type X with product discount** | FR 4.1,6.1,6.2,6.5,6.7,6.8,6.10,6.11,7.1,7.2,8.2 | TestStartSaleTransactionCorrect<br />TestAddProductToSaleCorrect<br />TestApplyDiscountRateToProductCorrect<br />TestEndSaleTransactionCorrect<br />TestReceiveCashPayment<br />TestReceiveCreditCardPayment<br /><br />TestRecordBalanceUpdate |
| 6-3 **Sale of product type X with sale discount** | FR 4.1,6.1,6.2,6.4,6.7,6.8,6.10,6.11,7.1,7.2,8.2 | TestStartSaleTransactionCorrect<br />TestAddProductToSaleCorrect<br />TestApplyDiscountRateToSale<br />TestEndSaleTransactionCorrect<br />TestReceiveCashPayment<br />TestReceiveCreditCardPayment<br /><br />TestRecordBalanceUpdate |
| 6-4 **Sale of product type X with Loyalty Card update**      | FR 4.1,5.7,6.1,6.2,6.6,6.7,6.8,6.10,6.11,7.2,8.2 | TestStartSaleTransactionCorrect<br />TestAddProductToSaleCorrect<br />TestEndSaleTransactionCorrect<br />TestReceiveCreditCardPayment<br /><br />TestComputePointsCorrect<br />TestModifyPointsOnCardCorrect<br />TestRecordBalanceUpdate |
| 6-5 **Sale of product type X cancelled**                     | FR 4.1,6.1,6.2,6.7,6.10,6.11                     | TestStartSaleTransactionCorrect<br />TestAddProductToSaleCorrect<br />TestEndSaleTransactionCorrect<br /><br />TestReceiveCashPayment<br />TestReceiveCreditCardPayment<br />TestDeleteTransactionCorrect |
| 6-6 **Sale of product type X completed (Cash)** | FR 4.1,6.1,6.2,6.7,6.8,6.10,6.11,7.1,8.2         | TestStartSaleTransactionCorrect<br />TestAddProductToSaleCorrect<br />TestEndSaleTransactionCorrect<br /><br />TestReceiveCashPayment<br />TestRecordBalanceUpdate |
| 7-1 **Manage payment by valid credit card** | FR 7.2                                           | TestReceiveCreditCardPayment<br />TestCheckLuhn |
| 7-2 **Manage payment by invalid credit card** | FR 7.2                                           | TestReceiveCreditCardPaymentInvalidCreditCard<br />(TestCheckLuhn2,TestCheckLuhn3,TestCheckLuhn4,TestCheckLuhn5) |
| 7-3 **Manage credit card payment with not enough credit** | FR 7.2                                           | TestReceiveCreditCardPayment<br />TestCheckLuhn |
| 7-4 **Manage cash payment** | FR 7.1                                           | TestReceiveCashPayment |
| 8-1 **Return transaction of product type X completed, credit card** | FR 6.7,6.9,6.12,6.13,6.14,7.4,8.1                | TestGetSaleTransactionCorrect<br />TestStartReturnTransactionCorrect<br />TestReturnProductCorrect<br />TestReturnCreditCardPayment<br />TestEndReturnTransactionCorrectCommit<br />TestRecordBalanceUpdate |
| 8-2 **Return transaction of product type X completed, cash** | FR 6.7,6.9,6.12,6.13,6.14,7.3,8.1                | TestGetSaleTransactionCorrect<br />TestStartReturnTransactionCorrect<br />TestReturnProductCorrect<br />TestReturnCashPayment<br />TestEndReturnTransactionCorrectCommit<br />TestRecordBalanceUpdate |
| 9-1 **List credits and debits** | FR 8.3                                           | TestGetCreditsAndDebits |
| 10-1  **Return payment by credit card** | FR 7.4                                           | TestReturnCreditCardPayment<br />TestCheckLuhn |
| 10-2 **return cash payment** | FR 7.3                                           | TestReturnCashPayment |

# Coverage of Non Functional Requirements




### 

| Non Functional Requirement | Test name                                                    |
| -------------------------- | ------------------------------------------------------------ |
| NFR4                       | TestProductCodeAlgorithm                                     |
| NFR5                       | TestCheckLuhn                                                |
| NFR6                       | TestAttachCardToCustomerCorrect<br />TestModifyPointsOnCardCorrect |



