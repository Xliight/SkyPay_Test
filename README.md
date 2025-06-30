# Skypay Technical Tests – Java Solutions

This repository contains my solutions for two technical tests provided by Skypay, implemented in Java. Each test focuses on a different problem domain and is designed to assess object-oriented design, logic implementation, and testing practices.


## Test 1 – Banking Service

A simple banking system that allows a user to:
- **Deposit** money
- **Withdraw** money
- **Print** the bank statement (showing all transactions in reverse chronological order)


## Test 2 – Hotel Reservation System

A mini hotel booking system to manage:
- **Users** with a balance
- **Rooms** of different types and prices
- **Bookings** based on availability and user balance


## Unit Tests

Both solutions include **unit tests** to verify correctness and edge cases. These tests cover:
- Valid and invalid transactions/bookings
- Sufficient/insufficient balance scenarios
- Room availability logic
- Output formatting checks


## Sample Output Screenshots

### `printAll()` and `printAllUsers()` from Hotel Reservation System:

![Sample Output](https://raw.githubusercontent.com/Xliight/SkyPay_Test/main/src/main/resources/img.png)


## Technologies Used
- Java 8+
- JUnit (for testing)
