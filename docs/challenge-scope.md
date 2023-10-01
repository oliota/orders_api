# Challenge Scope

This is a simple exercise, a basic order manager. You should develop an API where users can create and manage orders. Items can be ordered, and orders are automatically fulfilled as soon as the item stock allows it.

## Specification

The system should be able to provide the following features:

- Create, read, update, and delete and list all entities.
- When an order is created, it should try to satisfy it with the current stock.
- When a stock movement is created, the system should try to attribute it to an order that isn't complete.
- When an order is complete, send a notification by email to the user that created it.
- Trace the list of stock movements that were used to complete the order, and vice-versa.
- Show the current completion status of each order.
- Write a log file with information about orders completed, stock movements, emails sent, and errors.

## Entities

- **Item**
  - Name

- **StockMovement**
  - Creation Date
  - Item
  - Quantity

- **Order**
  - Creation Date
  - Item
  - Quantity
  - User (who created the order)

- **User**
  - Name
  - Email

## Requirements

- The API should be developed in Java 8 with Spring Boot + Spring JPA or Java EE + Hibernate, using PostgreSQL, GIT, and log4j (or another logging library).
- Instructions should be provided on how to run the project and how to call the routes.

[<< Back to Main Page](../README.md)
