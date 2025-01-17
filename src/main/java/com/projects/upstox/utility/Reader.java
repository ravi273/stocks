package com.projects.upstox.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.upstox.entity.Trade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

/*
       First worker for the project
       Responsible for reading the data from the Trades json file and pushing the same to Queue

 */

public class Reader  implements Runnable {

    private final BlockingQueue<Trade> queue ;
    private final String fileName;
    private final String symbol;

    public Reader(BlockingQueue<Trade> queue,String fileName,String symbol)
    {
        this.queue =  queue;
        this.fileName = fileName;
        this.symbol = symbol;
    }


    @Override
    public void run() {
        try {

                try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

                    ObjectMapper objectMapper = new ObjectMapper();

                    stream.forEach(line->{
                        Trade trade = null;
                        try {
                            trade = objectMapper.readValue(line, Trade.class);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            //Filtering records based on Symbol set in config.properties
                            if(symbol != null && trade !=null && symbol.equals(trade.getSym())) {

                                //Converting TimeStamp2 from nanoseconds to seconds
                                trade.setTs2(trade.getTs2()/1000000000);
                                queue.put(trade);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
