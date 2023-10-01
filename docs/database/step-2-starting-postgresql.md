# Step 2: Starting PostgreSQL

## Starting PostgreSQL

1. **Open Command Prompt as Administrator**: Open the Command Prompt as an administrator.

2. **Navigate to PostgreSQL Directory**: Navigate to the PostgreSQL installation directory (usually located at `C:\Program Files\PostgreSQL\<version>`).

3. **Add bin Directory to PATH**: Inside the directory, locate the `bin` folder and add its full path to your PATH environment variable. Replace `<version>` with your actual PostgreSQL version.

   ```shell
   setx PATH "%PATH%;C:\Program Files\PostgreSQL\<version>\bin"

[<< Back](../database-configuration.md)