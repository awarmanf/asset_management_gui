# Asset Management of Mining Company

In this project we choose the HT Radio as an asset to manage. In mining companies, 
HT radios are very much needed for communication between employees because the work 
environment generally does not receive signals from cellular operators.

There are four kind of radio communication used by the employees.

Icom F3003 | Icom V80
---------|-----------
![Screenshot](icom_f3003.jpg) | ![Screenshot](icom_v80.jpeg)

Motorola GP328 | Motorola GP338
---------|---------------------
![Screenshot](motorola_gp328.jpg) | ![Screenshot](motorola_gp338.jpg)

## Create Database AssetMining

Login to mysql as user root

```sql
CREATE DATABASE AssetMining;
GRANT ALL PRIVILEGES ON `AssetMining` . * TO 'user1'@'localhost';
EXIT;
```

Then execute sql statements to import file `AssetMining.sql`.

```sh
mysql -u root -p AssetMining < AssetMining.sql
```

You must login as user root to import database AssetMining.

## Export CLASSPATH

```sh
export CLASSPATH=lib/json-java.jar:lib/mysql-connector-java-5.1.23-bin.jar:lib/swingx-core-1.6.2.jar:lib/poi-3.11-20141221.jar:lib/poi-ooxml-3.11-20141221.jar:lib/poi-ooxml-schemas-3.11-20141221.jar:lib/xmlbeans-2.6.0.jar:.
```

## Compile

```sh
javac com/mining/program/MainForm.java
```

## Run Application

Run aplikasi
```sh
java com.mining.program.MainForm
```

## Screenshot

![Screenshot](00.png)

Only showing the current posession of the assets.

![Screenshot](01.png)

Select a specific asset then click `Show History` button

![Screenshot](02.png)

Select a specific asset then click `Add history` button

![Screenshot](03.png)

Show which assets once belong to the user in search

![Screenshot](04.png)

Save the belonging of current assets into a xlsx file

