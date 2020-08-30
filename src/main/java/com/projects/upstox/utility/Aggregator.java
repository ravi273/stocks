package com.projects.upstox.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.upstox.entity.Response;
import com.projects.upstox.entity.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Aggregator implements Runnable{

    private final BlockingQueue<Trade> queue;
    private List<Trade>  trades = new ArrayList<>();
    private int sequenceNumber =0;
    private long stratingTimeStamp = 0;
    private Double max;
    private Double min;
    private Double open;
    private Double volume;
    private long interval = 5L;

    public Aggregator(BlockingQueue<Trade> queue, int interval)
    {
        this.queue =  queue;
        this.interval = interval;
    }



    @Override
    public void run() {

    while(true)
    {
        try {
            Trade trade = queue.take();
            Response response =  AddToList(trade);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    }

    private Response ProduceOutput(List<Trade> trades,Trade trade,boolean isClosing) {

        Double closingPrice;
        Response response = null;

        ObjectMapper objectMapper = new ObjectMapper();

        if(trades.size()>0)
        {


            if(isClosing) {
                closingPrice = trade.getPrice();
                response = new Response(open,max,min,closingPrice,volume,"ohlc_notify",trade.getSym(),sequenceNumber);
                try {
                    System.out.println(objectMapper.writeValueAsString(response));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        if(trade.getTs2()-stratingTimeStamp > 2*interval) {
            int numberofMissed_intervals = (int)((trade.getTs2() - stratingTimeStamp) / interval);

            for (int i = 0; i < numberofMissed_intervals; i++) {
                sequenceNumber++;
                response = new Response("ohlc_notify", trade.getSym(), sequenceNumber);
                volume = 0.0;
                max = 0.0;
                min = 0.0;


                try {
                    System.out.println(objectMapper.writeValueAsString(response));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

    public Response AddToList(Trade trade) {
        Response response=null;
        boolean produceBlankOutput = false;

        if(trades.size()==0) {
            sequenceNumber++;
            stratingTimeStamp=trade.getTs2();
            open = trade.getPrice();
            max= trade.getPrice();
            min= trade.getPrice();
            volume = trade.getQ();
            trades.add(trade);
        }
        else
        {
            if(trade.getTs2()- stratingTimeStamp < interval)
            {
                trades.add(trade);
                response = ProduceOutput(trades,trade,false);
                if(trade.getPrice()>max)
                    max= trade.getPrice();
                if(trade.getPrice()<min)
                    min = trade.getPrice();
                volume += trade.getQ();
            }
            else
            {

                response = ProduceOutput(trades,trade,true);
                sequenceNumber++;
                stratingTimeStamp=trades.get(trades.size()-1).getTs2();
                max= trade.getPrice();
                min= trade.getPrice();
                volume = trade.getQ();
                trades.clear();
                trades.add(trade);

            }

        }
        return response;
    }
}
