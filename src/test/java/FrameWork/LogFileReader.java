package FrameWork;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LogFileReader {



    public Iterator<Object[]> fileReader() {
        Set<Object[]> basePaths = new HashSet<Object[]>();
        try {

            File file = new File("src\\test\\resources\\webdq_access.log");
           System.out.println( );
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
           /* read log line by line */
            while ((strLine = br.readLine()) != null) {
             /* parse strLine to obtain what you want */
                //System.out.println(strLine);

                if(strLine.contains("GET"))
                {

                   String line = strLine.split("GET ")[1].split(" ")[0];
                    System.out.println(line);
                    basePaths.add(new Object[]{line});
                }

            }
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
      return basePaths.iterator();
    }
}
