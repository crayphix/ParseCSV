package com.ms3.java;

/**
 * ParseCSV
 * V 1.0
 * @BryanSpeelman
 *
 * Description: This program uses cmd line flags and arguments to parse a CSV file into a SQLite DB.
 * A method for creating large CSV for testing can be located @ /src/test/java/ParseCSVTest
 *
 * Console flags for running program:
 *   -p (parse file into database)
 *   Format:"-p FileName.csv DatabaseName TableName"
 *   Example: "-p testCSV.csv testDB testTable2"
 *
 *   -r (read database info to console)
 *   Format: "-r DataBaseName TableName"
 *   Example: "-r testDB testTable"
 *
 *
 */

public class Main {
    public static void main(String[] args) throws Exception{
        //Variables for clarity
        String csvFilename = null;
        String dbName = null;
        String tableName = null;

        //handle flags
            //catch flag for parsing data
            if(args[0].equals("-p") &&
                    args[1].endsWith(".csv") &&
                    !args[2].isEmpty() &&
                    !args[3].isEmpty()) {

                //initialize variables
                csvFilename = args[1];
                dbName = args[2];
                tableName = args[3];

                //read csv and parse to sqlite db
                try {
                    SQLiteDB dbObject = new SQLiteDB(dbName); //create db
                    CSVHandler csv = new CSVHandler(csvFilename); //read csv
                    dbObject.setTable(tableName, csv.getHeader()); // create table in db
                    dbObject.setDBData(csv.getRowData()); // insert data into db table
                    FileIO.logger.info(csv.getStats()); // log stats
                    FileIO.quickCSV(dbName, csv.getErrorMessages()); //create csv identifying bad data rows
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            //catch flag for reading database table
            else if(args[0].equals("-r") &&
                    !args[1].isEmpty() &&
                    !args[2].isEmpty()) {

                //initialize variables
                dbName = args[1];
                tableName = args[2];

                //Output database information
                try{
                    SQLiteDB dbObject = new SQLiteDB(dbName);
                    dbObject.getDBData(tableName);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            //give instructions for proper flag use for program
            else{
                System.out.println("\n\n***********\n" +
                        "Invalid flag or filename\n" +
                        "Console flags for running program:\n" +
                        " -p (parse file into database)\n" +
                        " ex:\"-p FileName.csv DBname TableName \"\n" +
                        " \n" +
                        " -r (read table to console)\n" +
                        " ex: \"-r DBname TableName");
            }
    }
}
