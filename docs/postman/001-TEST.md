# Test Sequence 001 - TEST

This test sequence, labeled "001 - TEST," demonstrates the core functionality of the application through a series of requests. Each request serves a distinct purpose in evaluating the system's behavior:

## Request 001: Create User
This request involves creating a user by sending a POST request to the URL `http://localhost:8080/users`. It includes user details in JSON format, such as name and email. The expected response is not specified.

## Request 002: Create Item
This request creates an item using a POST request to `http://localhost:8080/items`. It includes an item name in JSON format and expects an unspecified response.

## Request 003: Try Create Order Without Stock
A POST request to `http://localhost:8080/customer-orders/create` attempts to create an order without sufficient stock. It provides user and item information in JSON format and expects a response that is not specified.

## Request 004: Create Stock for Item ID 1
This request creates stock for an item with ID 1 by sending a POST request to `http://localhost:8080/stock-movements`. It includes item details in JSON format and expects an unspecified response.

## Request 005: Create Customer-Order with Success
A POST request to `http://localhost:8080/customer-orders/create` is used to create a customer order successfully. It provides user and item information in JSON format and expects a response that is not specified.

## Request 006: Complete Customer-Order with Sending Email
This request completes a customer order by sending a PUT request to `http://localhost:8080/customer-orders/complete/1` (with a specific order ID). It does not include a request body, and the expected response is not specified.

Executing these requests in the given order provides a comprehensive test of the application's functionality.


[<< Back](../postman-calls.md)