 Readme Instruction:

 -------------------------
MySql Server :

•You need to refer Mysql.sql file which is attached. First create database named bestdeal. Then run this sql files which has a syntax of creating tables, stored procedures and inserting information of products and its accessories. It will create tables,store procedures and enter products and its accessories information into tables.
--------------------------
•MongoDb Server : 

•To use MongoDb server you need to first run the mongod.exe file from your c drive. After running mongod.exe file you need to run mongo.exe file.
•You can see “>” arrow like this. Now to go to database file you need to write command as given below.

•Use CustomerReviews

Then create collection  using below command
•db.createCollection("myReviews")

To see collection in the database you need to write command given below.

•db.myReviews.find()

---------------------------
I am assuming you already set classpath as mentioned in Tutorial 2 and 3.

---------------------------
•Project Configuration Steps:

•To setup the project in your local machine, first of all you need to put the folder named AS3 under your webapps folder.
•After putting folder in the webapps you need to open cmd there and go to AS3/WEB-INF/classes using cmd command and after reaching there you can run below command to compile all java files.

•Javac *.java

•After Compiling it start tomcat server then you can run the project in your machine. 
You need to open browser and type URL like localhost:8080/AS2/home which redirects you to Home page of application.
 
