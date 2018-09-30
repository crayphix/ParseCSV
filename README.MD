 ParseCSV
 V 1.0
 @BryanSpeelman

 Description: This program uses cmd line flags and arguments to parse a CSV file into a SQLite DB.
 A method for creating large CSV for testing can be located @ /src/test/java/ParseCSVTest

 Console flags for running program:

   -p (parse file into database)

   Format:"-p FileName.csv DatabaseName TableName"
   
   Example: "-p testCSV.csv testDB testTable2"



 
   -r (read database info to console) 
   
   Format: "-r DataBaseName TableName"
   
   Example: "-r testDB testTable"

Approach
--------------
-For this project I decided to utilized BeanReader from the Super CSV dependency to map the CSV 
into objects for easier handling when parsing into the database. I used it to 
weed out bad data and log it rather than doing this during the import phase. 

-To handle large files I set up the program to handle the data in transactions id: format, 
check and parse object data to arraylist then insert data into database from arraylist. 
This is would be more efficient than parsing and inserting each row one at a time.

-I also added a reading method so you can read the data in the table. It is a simple select method but in the future 
one could set up the program to do basic querying although the terminal app or gui for SQLite is more fully featured.

-I chose to create a database instead of sending it to in memory to have a more fully formed program and persistence.

-There is more functionality that could be passed to the user within the program 
however for the purposes of the assessment I kept the interface simple.

-There is a ParseCSVTest class method in the test folder that has a method to create a large CSV file. 
More testing methods could be added to fully form junit testing.
  
Assesment Goals
--------------
Customer X just informed us that we need to churn out a code enhancement ASAP for a new project.  Here is what they need:

1. We need a Java application that will consume a CSV file, parse the data and insert to a SQLite In-Memory database.
  a. Table X has 10 columns A, B, C, D, E, F, G, H, I, J which correspond with the CSV file column header names.
  b. Include all DDL in submitted repository
  c. Create your own SQLite DB

2. The data sets can be extremely large so be sure the processing is optimized with efficiency in mind.

3. Each record needs to be verified to contain the right number of data elements to match the columns.
  a. Records that do not match the column count must be written to the bad-data-<timestamp>.csv file
  b. Elements with commas will be double quoted

4. At the end of the process write statistics to a log file
  a. # of records received
  b. # of records successful
  c. # of records failed


Rules and guidelines:

1) Feel free to use any online resources
2) Utilizing existing tools like Maven and open source libraries is encouraged.
3) A finished solution is great but if you do not get it all completed, 
that is ok - we will evaluate based on approach

4) It is required that you provide a README detailing the challenge. 
Your readme should include instructions to run your code, 
and a brief paragraph describing the approach you took to solve the challenge
