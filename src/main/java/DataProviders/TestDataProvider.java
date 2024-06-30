package DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestDataProvider {

    //read from csv file

    private static final Logger logger = LogManager.getLogger(TestDataProvider.class);

    final static String CSV_FILE = "src/test/resources/TestData/weatherUITests.csv";
    final static String DELIMITER = ",";

    @DataProvider(name = "weatherUITests")
    public Iterator<Object []> readfromCSV() {
        String[] data = null;
        List<Object []> testCases = new ArrayList<>();
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(CSV_FILE));
            while ((line = br.readLine()) != null) {
                data = line.split(DELIMITER);
                testCases.add(data);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
         finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());                }
            }
        }
        return testCases.iterator();
    }



}
