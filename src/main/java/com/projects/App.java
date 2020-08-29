package com.projects;

import com.projects.upstox.entity.Trade;
import com.projects.upstox.utility.Aggregator;
import com.projects.upstox.utility.PropertyReader;
import com.projects.upstox.utility.Reader;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Trade Streamin started" );

        final BlockingQueue<Trade> queue = new LinkedBlockingDeque<>();

        Properties properties = new Properties();

        try {
             properties = new PropertyReader().getPropValues();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread fileReader = new Thread(new Reader(queue,properties.getProperty("filepath"),properties.getProperty("symbol")));

        Thread tradesAggregator = new Thread(new Aggregator(queue,Integer.parseInt(properties.getProperty("interval"))));



        fileReader.start();
        tradesAggregator.start();
    }


}
