# Solution Steps

- Query database for subscriptions which are not Initialized yet for the month
- This can be heavy for 10 mio records, use paging like 1000 per page (will run ~10.000 times to get the full data)
- After each query 
  - Create a payment record with initial state (We could use MQ so that we could process each record multithreaded on multi servers...)
  - Sent HTTP Call to Bank to process (assumed that it gives direct response) 
  - Update payment record according to Response (PAID, ERROR). (just saving the state we could also save the detail of the error or block the process as the banking systems are down etc...)
  - Update Subscription last payment date
- loop until all active subscriptions are processed.

## Database

We need some information to process subscriptions, we will only take credit card into consideration, will also skip some columns like create date, update date etc...

User: Name, Surname, Email
payment_method: Type (Enum: PRIMARY, SECONDARY), Card Number, Valid Until
subscription: User, Type (Enum: Standard, Family), Start Date, Amount, Status (Enum: Active, Disabled), Last Payment Date
payment: Payment Method, Subscription, Status (Enum: INIT, SENT, PAID, ERROR), Process Date, Payment Date

