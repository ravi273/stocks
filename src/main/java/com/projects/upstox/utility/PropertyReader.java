package com.projects.upstox.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/*
   Responsible for reading the properties from the properties file config.properties prsent in the resourcse folder

 */
public class PropertyReader {


    public Properties getPropValues() throws IOException {

        InputStream inputStream = null;
        Properties prop = new Properties();
        try {

            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }



        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if( inputStream!=null)
            inputStream.close();
        }

        return prop;
    }
}
