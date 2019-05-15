# myRetailService

Tech Stack 
-----------------------------------------------
Maven, Spring Boot, Cassandra (NO SQL), JUNIT Mockito



System Requirements 
-----------------------------------------------
 Java version - 1.8.0_211
 maven 3.5.3
 apache-cassandra-3.11.4 (Executable version to setup in localhost)
 Python 2.7.16 (For executing cassandra in command line)
 Any REST clients - eg : Advance Rest Client, POSTMAN



To Run the Application
-----------------------------------------------

-------------------Cassandra Setup----------------------------------------------
Open command prompt and execute ---> cassandra -f
Cassandra should startup in localhost/127.0.0.1
Create keyspace and table  using the following script : 

CREATE KEYSPACE myretail WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3' } AND DURABLE_WRITES = true;

CREATE TABLE myretail.product (
    id int,
    currency_code text,
    last_change_user text,
    last_update_ts timestamp,
    price double,
    PRIMARY KEY (id)
) WITH read_repair_chance = 0.0
    AND dclocal_read_repair_chance = 0.1
    AND gc_grace_seconds = 864000
    AND bloom_filter_fp_chance = 0.01
    AND caching = { 'keys' : 'ALL', 'rows_per_partition' : 'NONE' }
    AND comment = ''
    AND compaction = { 'class' : 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold' : 32, 'min_threshold' : 4 }
    AND compression = { 'chunk_length_in_kb' : 64, 'class' : 'org.apache.cassandra.io.compress.LZ4Compressor' }
    AND default_time_to_live = 0
    AND speculative_retry = '99PERCENTILE'
    AND min_index_interval = 128
    AND max_index_interval = 2048
    AND crc_check_chance = 1.0
    AND cdc = false;

----------------------------------------------------------------------------------------------------------

-------------Application setup------------

Download the myRetailService-0.0.1-SNAPSHOT.jar and place it in local directory
Open new command prompt and browse through the directory
Run the command ---> java -jar myRetailService-0.0.1-SNAPSHOT.jar
Application should start with log :::: Started MyRetail Service ::::


To Test the Application
---------------------------------

Use POSTMAN or Advance rest Client to test the following services - 

****************** Create price for a product **********************

PUT Request

URL
http://localhost:8080/myretail/price

JSON Body 
{
  "id": 88888,
   "priceValue":{
  "price": 34.55,
  "currencyCode" : "USD"},
  "lastChangeUser" : "USERNAME"
}

Business Rules :

id should be between 1 and 999999999
price should not be null and should be >=0
currency code should not be null and should only have 3 characters
last change user id should not be null or empty and should have <=10 characters


****************** Retrieve price for a product **********************

GET Request

URL
http://localhost:8080/myretail/price/id

Replace id in the url with product id

Business Rules :

id should be between 1 and 999999999
Title will be retrieved from RedSky webservices. It is an external service which responds 200 only for the item number 13860428. For any other items, the external service will throw 404 in turn the my retail service throws 400, a bad request.

Please use 13860428 for getting valid response.
Service will also return 400 if the product is not available in Cassandra.


Sample response : 

{"id":13860428,"priceValue":{"price":12.55,"currencyCode":"USD"},"lastChangeUser":"ARUN","name":"The Big Lebowski (Blu-ray)"}

-------------------------------------------------------------------------------------------------------------------

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 