			post hotels

	http://localhost:8080/hotels/
	{"name" : "Hilton",
	"address" : "NeverLand",
	"rating" : 4
	}
	{
	"name" : "Radisson",
	"address" : "Park",
	"rating" : 3
	}
	
	get hotels
	http://localhost:8080/hotels/
	
	delete hotels
	http://localhost:8080/hotels/1 
	(this will lead to cascadingly delete bookings made in the specified hotel)



			post bookings

	http://localhost:8080/hotels/1/bookings
	{
	"customerName" : "cosmo",
	"customerSurname" : "politan",
	"numberOfPax" : 4,
	"amount" : 300,
	"currency" : "USD"
	}
	http://localhost:8080/hotels/2/bookings
	{
	"customerName" : "cosmo",
	"customerSurname" : "politan",
	"numberOfPax" : 4,
	"amount" : 300,
	"currency" : "USD"
	}
	http://localhost:8080/hotels/2/bookings
	{
	"customerName" : "cruela",
	"customerSurname" : "deVille",
	"numberOfPax" : 1,
	"amount" : 300,
	"currency" : "USD"
	}

	get bookings
	•  All the bookings that a particular hotel has
		http://localhost:8080/hotels/2/bookings/
	get hotels based on bookings
	•  All the hotels that a particular “surname” has made bookings for 
		http://localhost:8080/hotels/bookings/politan
	get price amount/booking/hotel
	• Get the sum of the price amounts of the bookings for a given hotel
	 	(http://localhost:8080/hotels/{hotel_id}/sum/
		 http://localhost:8080/hotels/2/sum/
	

