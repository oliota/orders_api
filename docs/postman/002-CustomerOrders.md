# Folder 002 - Customer Orders

This folder contains requests related to customer orders.

## Request 1: Create a New Customer Order
- **Method**: POST
- **Endpoint**: /api/customer-orders/create
- **Description**: Creates a new customer order based on the provided information.
- **Example**: `POST /api/customer-orders/create`

## Request 2: Get a Customer Order by ID
- **Method**: GET
- **Endpoint**: /api/customer-orders/{id}
- **Description**: Retrieves a customer order based on the specified ID.
- **Example**: `GET /api/customer-orders/1`

## Request 3: Update a Customer Order
- **Method**: PUT
- **Endpoint**: /api/customer-orders/{id}
- **Description**: Updates a customer order based on the ID and provided information.
- **Example**: `PUT /api/customer-orders/1`

## Request 4: Delete a Customer Order
- **Method**: DELETE
- **Endpoint**: /api/customer-orders/{id}
- **Description**: Deletes a customer order based on the specified ID.
- **Example**: `DELETE /api/customer-orders/1`

## Request 5: Complete a Customer Order
- **Method**: PUT
- **Endpoint**: /api/customer-orders/complete/{id}
- **Description**: Completes a customer order with the specified ID.
- **Example**: `PUT /api/customer-orders/complete/1`

[<< Back](../postman-calls.md)