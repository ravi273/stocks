package com.projects;

import com.projects.upstox.entity.Response;
import com.projects.upstox.entity.Trade;
import com.projects.upstox.utility.Aggregator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AggregatorTest {


    @Test
    public void TestForSums()
    {

        Aggregator aggregator = new Aggregator(null,5);
        Trade trade1 = new Trade("XETHZUSD","Trade",0.1,0.1,0,"s",1L);
        Trade trade2 = new Trade("XETHZUSD","Trade",0.2,0.1,0,"s",2L);
        Trade trade3 = new Trade("XETHZUSD","Trade",0.3,0.1,0,"s",3L);
        Trade trade4 = new Trade("XETHZUSD","Trade",0.8,0.1,0,"s",7L);

        aggregator.AddToList(trade1);
        aggregator.AddToList(trade2);
        aggregator.AddToList(trade3);

        List<Response> responseList =   aggregator.AddToList(trade4);

        assertNotNull(responseList);
        assertEquals(responseList.size() ,1);
        Response response = responseList.get(0);

        assertEquals(response.getHigh(),0.3,0.000001);
        assertEquals(response.getLow(),0.1,0.000001);
        assertEquals(response.getOpen(),0.1,0.000001);
        assertEquals(response.getClose(),0.3,0.000001);
        assertEquals(response.getSymbol(),"XETHZUSD");
        assertEquals(response.getVolume(),0.3,0.000001);


    }

    @Test
    public void TestForSequenceNumberIncrement()
    {

        Aggregator aggregator = new Aggregator(null,5);
        Trade trade1 = new Trade("XETHZUSD","Trade",0.1,0.1,0,"s",1L);
        Trade trade2 = new Trade("XETHZUSD","Trade",0.2,0.1,0,"s",2L);
        Trade trade3 = new Trade("XETHZUSD","Trade",0.3,0.1,0,"s",3L);
        Trade trade4 = new Trade("XETHZUSD","Trade",0.8,0.1,0,"s",7L);
        Trade trade5 = new Trade("XETHZUSD","Trade",0.8,0.1,0,"s",11L);

        aggregator.AddToList(trade1);
        aggregator.AddToList(trade2);
        aggregator.AddToList(trade3);
        aggregator.AddToList(trade4);

        List<Response> responseList =   aggregator.AddToList(trade5);

        assertNotNull(responseList);
        assertEquals(responseList.size() ,1);
        Response response = responseList.get(0);

        assertEquals(response.getSequenceNumber(),2);



    }

    @Test
    public void TestForBlankBar()
    {

        Aggregator aggregator = new Aggregator(null,5);
        Trade trade1 = new Trade("XETHZUSD","Trade",0.1,0.1,0,"s",1L);
        Trade trade2 = new Trade("XETHZUSD","Trade",0.2,0.1,0,"s",2L);
        Trade trade3 = new Trade("XETHZUSD","Trade",0.3,0.1,0,"s",3L);
        Trade trade4 = new Trade("XETHZUSD","Trade",0.8,0.1,0,"s",11L);

        aggregator.AddToList(trade1);
        aggregator.AddToList(trade2);
        aggregator.AddToList(trade3);


        List<Response> responseList =   aggregator.AddToList(trade4);

        assertNotNull(responseList);
        assertEquals(responseList.size() ,3);
        Response response = responseList.get(1);

        assertEquals(response.getSequenceNumber(),2);

        assertEquals(response.getVolume(),0.0,0.000001);
        assertEquals(response.getHigh(),0.0,0.000001);




    }
}
