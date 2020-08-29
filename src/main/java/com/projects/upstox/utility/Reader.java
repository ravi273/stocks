package com.projects.upstox.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.upstox.entity.Trade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


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
                            if(symbol != null && symbol.equals(trade.getSym()))
                            queue.put(trade);

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
