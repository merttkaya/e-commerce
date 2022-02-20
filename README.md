# Hepsiburada e-Commerce Assignment

This project developed as command-line application.

## Technical Detail

- You need **Java 8** and **maven** to compile this project.
- You should run **mvn clean package** command on project folder to compile and get executable jar file.
- After compilation, you should run **"java -jar eCommerce-0.01.jar "inputFile" "outputFile"** command. After application finishes, you can check command outputs in outputFile.


### Notes

- Because of using in memory database(h2) unit test couldn't written.
- According to assignment document, there wasn't any double properties. Because of this reason, i used almost every number as Long.
- In assignment document, there wasn't any information about exceptional cases. I implemented some of them. 