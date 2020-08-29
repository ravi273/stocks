package com.projects;

import com.projects.upstox.entity.Trade;
import com.projects.upstox.utility.Aggregator;
import com.projects.upstox.utility.Reader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Trade Streamin started" );

        final BlockingQueue<Trade> queue = new LinkedBlockingDeque<>();

        Thread fileReader = new Thread(new Reader(queue,"/home/f5341086/Downloads/trades-data/reduced.json","XXBTZUSD"));

        Thread tradesAggregator = new Thread(new Aggregator(queue));



        fileReader.start();
        tradesAggregator.start();
    }
}
