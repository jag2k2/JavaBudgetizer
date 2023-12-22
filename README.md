# Filthy Lucre Budgetizer

## Database Setup Instructions:
- http://www.appservnetwork.com/
- AppServ 9.3.0
- During install config apache server to run on port 81
- Run MySQL Command Line Client
- `CREATE USER '<username>'@'localhost' IDENTIFIED WITH mysql_native_password BY '<password>';`
- `GRANT ALL ON *.* TO '<username>'@'localhost';`
- go to http://localhost:81/phpmyadmin
- Import database from backup location
- Install MySQL Connector / ODBC 8.20 (64 bit)
- Navigate to control panel > administrative tools > ODBC Data Sources (64-bit)
- Click "Add"
- Select MySQL ODBC X.0 Unicode Driver
- Data Source Name: `gringotts`
- TCP/IP Server: `localhost`
- Do not change port
- User: <username>
- Password: <password>
- Click Test to connect to MySQL and populate list of databases
- Select `gringotts` database