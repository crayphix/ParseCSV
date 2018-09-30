package com.ms3.java;

public class Main {
    public static void main(String[] args) throws Exception{
        CSVHandler csv = new CSVHandler("test.csv");
        SQLiteDB finalTest = new SQLiteDB("finalTest");
        finalTest.setTable("Test", csv.getHeader());
        finalTest.setDBData(csv.getRowData());
        FileIO.logger.info(csv.getStats());
        FileIO.quickCSV("finalTest", csv.getErrorMessages());
    }
}
