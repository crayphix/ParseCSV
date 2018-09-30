package com.ms3.java;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**Class that parses CSV file into DBean objects and supplies accessors and setters*/
public class CSVHandler {

    private String csvFilename;
    private DBean headerBean;
    private ArrayList<DBean> rawDBeans = new ArrayList<>();
    private ICsvBeanReader beanReader = null;
    private int rowNum = 0;
    private int goodRecords = 0;
    private int badRecords;
    private ArrayList<String> ErrorMessages = new ArrayList<>();

    /**Method that reads line with error and returns line as string*/
    private String getErrorLine(int lineNum) throws Exception {
        try (
                Stream<String> lines = Files.lines(Paths.get(this.csvFilename))) {
            String lineError = lines.skip(lineNum - 1).findFirst().get();
            return lineError;
        }
    }

    /**Create CellProcessor for DBean object*/
    private static CellProcessor[] getProcessors() {
        //Cell Processor template for DBean
        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // A
                new NotNull(), // B
                new NotNull(), // C
                new NotNull(), // D
                new NotNull(), // E
                new NotNull(), // F
                new NotNull(), // G
                new NotNull(), // H
                new NotNull(), // I
                new NotNull() // J
        };
        return processors;
    }

    /**Default Constructor*/
    public CSVHandler(){}

    /**Args Constructor*/
    public CSVHandler(String csvFilename) throws Exception {
        this.csvFilename = csvFilename;

        //Initialize rawDBeans
        try {
            beanReader = new CsvBeanReader(new FileReader(csvFilename), CsvPreference.STANDARD_PREFERENCE);
            String[] header = new String[]{"A","B","C","D","E","F","G","H","I","J"};
            CellProcessor[] processors = getProcessors();

            DBean ground = null;
                for(int i = 0; i < csvLineCount(csvFilename); i++) { // loop through data and send to arraylist rawDBeans
                    try {
                        this.rowNum++;
                        ground = beanReader.read(DBean.class, header, processors);
                        if (ground != null) { //prevent null rows in database
                            this.goodRecords++;
                            this.rawDBeans.add(ground);
                        }
                    }
                    catch (IllegalArgumentException e){
                       ErrorMessages.add(rowNum + "," + getErrorLine(rowNum) ); //record row of error
                       this.badRecords++;
                    }
                }
            //Initialize headerBean
            this.headerBean = rawDBeans.get(0);
        }
        finally{
            if(beanReader != null){
                beanReader.close();
            }
        }
    }

    /**Method that returns DBean Header object*/
    public DBean getHeader() {
        return headerBean;
    }
    /**Method that returns rowdata */
    public ArrayList<DBean> getRowData() {
        rawDBeans.remove(0);
        return rawDBeans;
    }

    public void setcsvFilename(String csvFilename) throws Exception {
        this.csvFilename = csvFilename;

        //Initialize rawDBeans
        try {
            beanReader = new CsvBeanReader(new FileReader(csvFilename), CsvPreference.STANDARD_PREFERENCE);
            String[] header = new String[]{"A","B","C","D","E","F","G","H","I","J"};
            CellProcessor[] processors = getProcessors();

            DBean ground = null;
            for(int i = 0; i < csvLineCount(csvFilename); i++) { // loop through data and send to arraylist rawDBeans
                try {
                    this.rowNum++;
                    ground = beanReader.read(DBean.class, header, processors);
                    if (ground != null) { //prevent null rows in database
                        this.goodRecords++;
                        this.rawDBeans.add(ground);
                    }
                }
                catch (IllegalArgumentException e){
                    ErrorMessages.add(rowNum + "," + getErrorLine(rowNum) ); //record row of error
                    this.badRecords++;
                }
            }
            //Initialize headerBean
            this.headerBean = rawDBeans.get(0);
        }
        finally{
            if(beanReader != null){
                beanReader.close();
            }
        }
    }

    @Override
    public String toString() {
        rawDBeans.remove(0);
        return "CSVHandler{" +
                "csvFilename='" + csvFilename + '\'' +
                ", headerInfo=" + headerBean +
                ", rowData=" + rawDBeans +
                '}';
    }

    /**Method that returns number of lines in document*/
    public int csvLineCount(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if(endsWithoutNewLine) {
                ++count;
            }
            return count;
        } finally {
            is.close();
        }
    }

    /**Method that returns csv parse stats*/
    public String getStats(){
        return "\n" + this.rowNum + " of records received\n" +
                this.goodRecords + " of records successful\n" +
                this.badRecords + " of records failed";
    }

    /**Method that returns error Messages*/
    public ArrayList<String> getErrorMessages(){
        return ErrorMessages;
    }
}


