package com.ms3.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileIO {

        //Create Logger
        public static Logger logger;

        static {
            try {
                boolean append = true; //append variable
                FileHandler fh = new FileHandler("parseCSV.log", append); //Create file
                fh.setFormatter(new SimpleFormatter());// set formatting
                logger = Logger.getLogger("parseCSV"); // create logging obj
                logger.addHandler(fh); // log
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void quickCSV(String dbName, ArrayList<String> badData) {
            //Formatting for date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //get timestamp for file
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String today = sdf.format(timestamp);

            try{
                //Open File
                FileWriter fileWriter = new FileWriter("bad-data-" + today + ".csv");

                //Buf wrapper
                BufferedWriter buf = new BufferedWriter(fileWriter);

                //Write to csv
                buf.write("Error row #,Bad Data\n");
                for(String str : badData){
                    buf.write(str + "\n");
                }

                //Close file
                buf.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
