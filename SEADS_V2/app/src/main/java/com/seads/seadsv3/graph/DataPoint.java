package com.seads.seadsv3.graph;

/**
 * Created by root on 6/2/18.
 */

public class DataPoint {
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    private double energy;

    public DataPoint(int time, double energy){
        this.time =time;
        this.energy = energy;
    }
}
