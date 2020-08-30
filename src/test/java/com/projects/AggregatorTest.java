package com.projects;

import com.projects.upstox.entity.Trade;
import com.projects.upstox.utility.Aggregator;
import org.junit.Test;

public class AggregatorTest {


    @Test
    public void TestForTotal()
    {

        Aggregator aggregator = new Aggregator(null,5);
        Trade trade1 = new Trade("XETHZUSD","Trade",0.01947,0.1,0,"s",1538409725339216503L);
        Trade trade2 = new Trade("XETHZUSD","Trade",0.01947,0.1,0,"s",1538409725339216503L);
        Trade trade3 = new Trade("XETHZUSD","Trade",0.01947,0.1,0,"s",1538409725339216503L);

        aggregator.AddToList(trade1);


    }
}
