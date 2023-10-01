# Project Configuration

This document provides instructions for configuring the project, including setting up SMTP email for Gmail and configuring the PostgreSQL database.

## Configuring SMTP Email for Gmail

To enable email notifications and sending emails through Gmail, follow these steps:

1. Open the project's configuration file (e.g., `application.properties` ).

2. Find the email configuration section in the file.

3. Update the following properties with your Gmail email address and password:

   ```yaml
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your.email@gmail.com
   spring.mail.password=your-email-password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true


[<< Back to Main Page](../README.md)