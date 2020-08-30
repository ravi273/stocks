package com.projects.upstox.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Trade {


    public Trade(String sym, String t, Double price, Double q, long ts, String side, long ts2) {
        this.sym = sym;
        this.t = t;
        this.price = price;
        this.q = q;
        this.ts = ts;
        this.side = side;
        this.ts2 = ts2;
    }

    @JsonProperty("sym")
    private String sym;

    @JsonProperty("T")
    private String t;

    @JsonProperty("P")
    private Double price;

    @JsonProperty("Q")
    private Double q;

    @JsonProperty("TS")
    private long ts;

    @JsonProperty("side")
    private String side;

    @JsonProperty("TS2")
    private long ts2;



    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQ() {
        return q;
    }

    public void setQ(Double q) {
        this.q = q;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public long getTs2() {
        return ts2;
    }

    public void setTs2(long ts2) {
        this.ts2 = ts2;
    }
}
