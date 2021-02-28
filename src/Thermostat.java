
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author enricorastelli
 */
public abstract class Thermostat implements Observer {
    
    protected double desiredTemp;
    protected int heatRateDoorOpen;
    protected int heatRateDoorClosed;
    protected int heatTime;
    protected int compressorStartDiff;
    protected int coolRate;
    protected int coolTime;
    private boolean compressorCanTurnOn;

    private double roomTemp;


    public abstract double getDesiredTemp();

    public abstract void setDesiredTemp(double desiredTemp);

    public abstract int getHeatRateDoorOpen();

    public abstract void setHeatRateDoorOpen(int heatRateDoorOpen);

    public abstract int getHeatRateDoorClosed();

    public abstract void setHeatRateDoorClosed(int heatRateDoorClosed);

    public int getCompressorStartDiff() {
        return compressorStartDiff;
    }

    public void setCompressorStartDiff(int compressorStartDiff) {
        this.compressorStartDiff = compressorStartDiff;
    }

    public int getCoolRate() {
        return coolRate;
    }

    public void setCoolRate(int coolRate) {
        this.coolRate = coolRate;
    }

    public void incrementCoolTime() {
        this.coolTime++;
    }
    public void setCoolTime(int coolTime) {
        this.coolTime = coolTime;
    }

    public int getCoolTime() {
        return coolTime;
    }

    public double getRoomTemp() {
        return roomTemp;
    }

    public void setRoomTemp(double roomTemp) {
        this.roomTemp = roomTemp;
    }

    public int getHeatTime() {
        return heatTime;
    }

    public void setHeatTime(int heatTime) {
        this.heatTime = heatTime;
    }

    public void incrementHeatTime() {
        this.heatTime++;
    }

    public abstract void setCurrentTemp(int value) ;

    public abstract void increaseCurrentTemp(int value) ;

    public boolean getCompressorCanTurnOn() {
        return compressorCanTurnOn;
    }

    public void setCompressorCanTurnOn(boolean compressorCanTurnOn) {
        this.compressorCanTurnOn = compressorCanTurnOn;
    }




    public abstract int getCurrentTemp();

    @Override
    public abstract void update(Observable arg0, Object arg1);
    
    
}
