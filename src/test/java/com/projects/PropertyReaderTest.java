package com.projects;

import com.projects.upstox.utility.PropertyReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class PropertyReaderTest {


    @Test
    public void TestPropertyReader() throws IOException {
        PropertyReader propertyReader = new PropertyReader();


       Properties properties = propertyReader.getPropValues();

        Assert.assertEquals(properties.getProperty("filepath"),"testPath");
        Assert.assertEquals(properties.getProperty("interval"),"5");
        Assert.assertEquals(properties.getProperty("symbol"),"TestSymbol");
    }
}
