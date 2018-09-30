import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ParseCSVTest {
    /**Method that creates large CSV with errors for testing*/
    public static void testCSV() {
            try {
                //Open File
                FileWriter fileWriter = new FileWriter("testCSV.csv");

                //Buf wrapper
                BufferedWriter buf = new BufferedWriter(fileWriter);

                //Write to csv
                buf.write("Pkey,data2,data3,data4,data5,data6,data7,data8,data9,data10\n");
                int pkey = 1;
                for(int i=0;i<100;i++) { //loop write data
                    buf.write("233,2334,malformed data!!!!\n"); //write bad data every 100 rows
                    for (int j = 0; j < 100; j++) { //write 100 rows of good data
                        buf.write(pkey + ",data2: " + pkey +
                                ",data3: " + pkey +
                                ",data4: " + pkey +
                                ",data5: " + pkey +
                                ",data6: " + pkey +
                                ",data7: " + pkey +
                                ",data8: " + pkey +
                                ",data9: " + pkey +
                                ",data10: " + pkey + "\n");
                        pkey++;
                    }
                }
                //Close file
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void main(String[] args){
            testCSV();
        }
}
