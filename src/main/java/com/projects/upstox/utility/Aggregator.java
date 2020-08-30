package com.projects.upstox.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.upstox.entity.Response;
import com.projects.upstox.entity.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/*
    Aggregator Class is the second worker for the project.
    Runs in its own thread.
    Responsible for generating the OHLC data.
    Produces output on the console.
 */


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
    private  Double closingPrice;

    public Aggregator(BlockingQueue<Trade> queue, int interval)
    {
        this.queue =  queue;
        this.interval = interval;
    }




    /*
       Iterates over all the elements present in the Queue
       Runs in a Infinite loop
       Once element is received sends to other methods for further processing
    */
    @Override
    public void run() {

    while(true)
    {
        try {
            Trade trade = queue.take();
            List<Response> responses =  AddToList(trade);
            ObjectMapper objectMapper = new ObjectMapper();

            if(responses!=null && responses.size()>0) {
                for (Response response : responses) {
                    try {
                        System.out.println(objectMapper.writeValueAsString(response));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    }


    /*
        ProduceOutput
        Generates a suitable response
        Response is Produced when the bar closes
        Generates an empty response if no trades were executed in the time frame of the bar
     */
    private List<Response> ProduceOutput(List<Trade> trades,Trade trade,boolean isClosing) {


        Response response;
        List<Response> responses = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        if(trades.size()>0)
        {
            if(isClosing) {
                response = new Response(open,max,min,closingPrice,volume,"ohlc_notify",trade.getSym(),sequenceNumber);
                responses.add(response);
            }
        }

        if(trade.getTs2()-stratingTimeStamp >= 2*interval) {
            int numberofMissed_intervals = (int)((trade.getTs2() - stratingTimeStamp) / interval);

            for (int i = 0; i < numberofMissed_intervals; i++) {
                sequenceNumber++;
                response = new Response("ohlc_notify", trade.getSym(), sequenceNumber);
                volume = 0.0;
                max = 0.0;
                min = 0.0;
                responses.add(response);
            }
        }

        return responses;
    }


    /*
        Maintains a list with set of elemnts in the current bar inside  a list
        Also manages the logic for open high close volume and sequens number
     */

    public List<Response> AddToList(Trade trade) {
        List<Response> responses=null;
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
                responses = ProduceOutput(trades,trade,false);
                if(trade.getPrice()>max)
                    max= trade.getPrice();
                if(trade.getPrice()<min)
                    min = trade.getPrice();
                volume += trade.getQ();
                closingPrice = trade.getPrice();
            }
            else
            {

                responses = ProduceOutput(trades,trade,true);
                sequenceNumber++;
                stratingTimeStamp=trades.get(trades.size()-1).getTs2();
                max= trade.getPrice();
                min= trade.getPrice();
                volume = trade.getQ();
                closingPrice = trade.getQ();
                trades.clear();
                trades.add(trade);

            }

        }
        return responses;
    }
}
