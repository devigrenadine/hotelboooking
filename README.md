	HOTEL BOOKING
	GOAL 					
	The goal of this project is to implement some of the basic functionalities of a hotel booking system.
	
	The current implementation is comprised by
	* CRUD Controllers for hotel and booking manipulation (get/put/post/delete)
	* Hotel and Booking entities associated with each other
	* Hotel and Booking Repositories
	* Endpoints for more specific searches 
	
	

	 
	Steps
	1. Download and install a database( mysql 8.0)(
	
	2. From command line cd to HotelBooking 
		and
		 mvn clean install
		 mvn spring-boot:run
	
	
	 MYSQL SCRIPTS
	 * A Database should be created as a basic schema, JPA will take care of the table creation
	  In Mysql Workbench/Shell run the following script:
	  			CREATE schema hotel_booking;
	  
    
    Testing
    - Via postman (Please take a look on postmanTestData.md where CRUD scenarios are provided)
    - Via basic controller  Unit tests 
    
    
    
      APPLICATION.PROPERTIES
      Provided properties for database connectivity
        spring.jpa.hibernate.ddl-auto = create (Value can be switched to update after first run)
       
       
       TECHNOLOGIES USED
        Maven 4.0
        Java 8
        Spring Boot 2.1
        Spring framework
        REST
        Spring Data JPA 
        Lombok
        
       
    
