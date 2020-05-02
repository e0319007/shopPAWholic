Group 10 - shopPAWholic

Eileen Leong Yi Qing (Leader)
A0185716W
e0319007@u.nus.edu
90084077 

Hew Yee Qing 
A0185618U
e0318909@u.nus.edu
98517588

Joanna Ng Rui Wen 
A0187707R
e0323291@u.nus.edu
93722815

Shi Zhan 
A0187133E
e0322717@u.nus.edu
96747395

===== Web application ===== 
database name: shoppawholic
http://localhost:8080/shopPAWholic-war/
*jasperReport and primefaces-humanity libraries have to be added manually 
*web.xml's alternatedocroot has to be configured  
*data will be loaded via postConstruct() during startup in dataInitSessionBean

== Admin login ==
username: admin
password: password

*existing users:
== Customer login ==
email: timleong@email.com	
password: password 

== Seller login ==
email: thepetshop@email.com	
password: thepetshop

== Sell (sellerSellListing.xhtml) ==
Picture: <upload photo "listing1.jpg" in pictures subfolder>
SKU Code: LIST020
Category: Cat Food
Listing Title: Grain-free Cat Food
Price: 23
Description: Complete and balanced holistic cat food for all ages 
Quantity on Hands: 1
Tag(s): discount

== Create an event (sellerEvent.xhtml) ==
Event name: Pet Expo 2020 
Description: Singapore's largest pet fair
Location: Expo Hall 2
Picture: <upload poster "petEvent5.jpg" in pictures subfolder>
From: 09/05/2020 10:30:00
To: 10/05/2020 20:00 
Url: https://petexposg.com/

== Create Category == 
Name: Dog Clothes 
Description: Dog Clothes 

== Create Tag == 
Name: new 

===== Mobile application ===== 
ionic serve
http://localhost:8100/

== Customer login ==
email: timleong@email.com	
password: password 

== Seller login ==
email: thepetshop@email.com	
password: thepetshop

== Create listing ==
IMAGE: https://www.rover.com/blog/wp-content/uploads/2020/02/51bGNumh0XL.jpg
SKUCODE: LIST01T
NAME: Wellness Kittles Cat Treat
Description : Wellness Kittles Cat Treat 60g
Quantity on Hand: 10
Unit Price: 8
Category: cat food

== Create tag ==
Tag name: treats

== Create an advertisement ==
Start Date: 20/05/2020
End Date: 25/05/2020
Credit Card Number: 6666 5555 4444 3333
URL of Picture: https://coconuts.co/wp-content/uploads/2019/03/1.Hellodog_Main-VisualBanner.jpg
URL of Event: https://coconuts.co/hongkong/lifestyle/doggos-day-theres-no-better-way-bond-pet-two-days-fun-learning-hellodog-fest/
Description: Join us to get a 20% discount off all pet food!

== Create review ==
Rating: 5
Description: great product! My cat loves it
Picture: https://www.catster.com/wp-content/uploads/2017/11/A-fluffy-cat-eating-wet-food-off-of-a-dish.jpg

== Checkout details == 
Address: Blk 123 Punggol Ave 5 #18-48 
Contact Number: 90084077
Delivery Method: Regular(Singpost)
Credit Card Number: 6666 5555 4444 3333
