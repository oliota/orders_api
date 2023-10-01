package com.rubem.oliota.api2023.pt.service;

import com.rubem.oliota.api2023.pt.model.CustomerOrder;
import com.rubem.oliota.api2023.pt.model.StockMovement;
import com.rubem.oliota.api2023.pt.model.UserSystem;
import com.rubem.oliota.api2023.pt.repository.CustomerOrderRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service class for managing customer orders.
 */
@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender javaMailSender;
    private final LoggingService loggingService;

    @Autowired
    public CustomerOrderService(
            CustomerOrderRepository customerOrderRepository,
            ModelMapper modelMapper,
            JavaMailSender javaMailSender,
            LoggingService loggingService ) {
        this.customerOrderRepository = customerOrderRepository;
        this.modelMapper = modelMapper;
        this.javaMailSender = javaMailSender;
        this.loggingService = loggingService;
    }

    /**
     * Get a list of all customer orders.
     *
     * @return A list of CustomerOrder entities.
     */
    public List<CustomerOrder> getAllCustomerOrders() {
        return customerOrderRepository.findAll();
    }

    /**
     * Get a customer order by its ID.
     *
     * @param id The ID of the customer order to retrieve.
     * @return The CustomerOrder entity if found, otherwise null.
     */
    public CustomerOrder getCustomerOrderById(Long id) {
        return customerOrderRepository.findById(id).orElse(null);
    }

    /**
     * Create a new customer order.
     *
     * @param newCustomerOrder The CustomerOrder entity representing the new order.
     * @return The created CustomerOrder entity.
     */
    public CustomerOrder createCustomerOrder(CustomerOrder newCustomerOrder) {
        CustomerOrder createdCustomerOrder = customerOrderRepository.save(newCustomerOrder);
        return createdCustomerOrder;
    }

    /**
     * Calculate reserved stock for a specific item.
     *
     * @param itemId The ID of the item to calculate reserved stock for.
     * @return The total reserved stock quantity.
     */
    public int calculateReservedStockForItem(Long itemId) {
        List<CustomerOrder> reservedOrders = customerOrderRepository.findReservedOrdersForItemId(itemId);
        int reservedStock = 0;

        for (CustomerOrder order : reservedOrders) {
            List<StockMovement> stockMovements = order.getStockMovements();
            for (StockMovement movement : stockMovements) {
                Integer quantity = movement.getQuantity();
                if (quantity != null) {
                    reservedStock += quantity;
                }
            }
        }

        return reservedStock;
    }

    /**
     * Update an existing customer order with the provided data.
     *
     * @param id               The ID of the customer order to update.
     * @param updatedCustomerOrder The CustomerOrder entity containing updated order details.
     * @return The updated CustomerOrder entity if successful, otherwise null.
     */
    public CustomerOrder updateCustomerOrder(Long id, CustomerOrder updatedCustomerOrder) {
        if (customerOrderRepository.existsById(id)) {
            updatedCustomerOrder.setId(id);
            return customerOrderRepository.save(updatedCustomerOrder);
        }
        return null;
    }

    /**
     * Delete a customer order by its ID.
     *
     * @param id The ID of the customer order to delete.
     * @return True if the order is deleted successfully, false otherwise.
     */
    public boolean deleteCustomerOrder(Long id) {
        if (customerOrderRepository.existsById(id)) {
            customerOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Complete a customer order with the specified ID.
     *
     * @param orderId The ID of the customer order to complete.
     * @return The completed CustomerOrder entity if successful, otherwise null.
     */
    public CustomerOrder completeCustomerOrder(Long orderId) {
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        order.setCompleted(true);

        CustomerOrder updatedOrder = customerOrderRepository.save(order);

        sendEmailNotification(order.getUserSystem());

        loggingService.logOrderCompletion(order);
        return updatedOrder;
    }

    /**
     * Send an email notification to the user.
     *
     * @param user The UserSystem entity to send the email to.
     */
    public void sendEmailNotification(UserSystem user) {
        try {
            String subject = "Email Subject";
            String content = "Your order has been successfully completed.";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(content, false);

            javaMailSender.send(message);

            loggingService.logEmailSent(user, "Your order has been completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();

            loggingService.logError("Intentional error, configure your GMAIL with SMTP");
            loggingService.logError(e.getMessage());
        }
    }
}
