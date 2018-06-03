# SpringBootDevlopment

/**
This example is to show the Integration of below

SpringBoot + JPA + Hibernate + RestApi 

Feel free to contact me.

Enjoy the learning till the next working example.
*/

Prerequisite : 

1) Must have the Java version 8. As the api is using the java 8 feature.
2) Download and install the Git if not already have. (You can download the Zip version as well).
3) Must have the oracle or other DB Install onto your machine.
   3.1) Change the below property of application.properties file accordingly.
   
        spring.datasource.url=jdbc:oracle:thin:@localhost:1521:orcl
        spring.datasource.username=YourDatabase UserName
        spring.datasource.password= YourDatabase Password
        spring.datasource.driver.class=oracle.jdbc.driver.OracleDriver

3) Import the project as maven project into Intellij or eclipse.
4) Resolve the maven dependency.
5) Run the main Application as a java application.
6) Enter the below URL,Similarly you can figure out other endPoint from the controller class.

 for get request : http://localhost:8080/designthink/api/users
 
 for Post Request : http://localhost:8080/designthink/api/user
 
 Below is the Jason Mesage you must send in the body of the Post request :  
 
 {
    "name": "MyName",
    "age": 32,
    "salary": 2532.23
  }
 
 7) if you want to chnage the the context rootof the application please chane the below property of 
    application.properties file to desired one.
    server.servlet.context-path=/designthink
    
 8) if you want to create the database structure manually please run the below script. 
    PleaseNote the below script is Oracle compatible.
 
     create sequence SEQ_USER_ID;
     
     create table sd_user_mst (
            user_id number(19,0) not null,
             age number(10,0),
             user_name varchar2(255 char),
             salary double precision,
             primary key (user_id)
         )
