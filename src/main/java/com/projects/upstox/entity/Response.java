package com.projects.upstox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Response {

    @JsonProperty("o")
    private double open;

    @JsonProperty("h")
    private double high;

    @JsonProperty("l")
    private double low;

    @JsonProperty("c")
    private double close;

    @JsonProperty("volume")
    private double volume;

    @JsonProperty("event")
    private String event;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("bar_num")
    private int sequenceNumber;



    public Response(double open, double high, double low, double close, double volume, String event, String symbol, int sequenceNumber) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.event = event;
        this.symbol = symbol;
        this.sequenceNumber = sequenceNumber;
    }

    public Response(String event, String sym, int sequenceNumber) {
        this.event = event;
        this.symbol = sym;
        this.sequenceNumber = sequenceNumber;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }



    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void getEvent(String event) {
        this.event = event;
    }


    public void setEvent(String event) {
        this.event = event;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }





}
