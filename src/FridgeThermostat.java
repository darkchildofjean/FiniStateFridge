
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Observable;

/**
 *
 * @author Jemal Abdullahi
 */
public class FridgeThermostat extends Thermostat {

	private static FridgeThermostat instance;
	private int currentTemp;
	protected static FridgeDisplay display;

	FridgeState fridgeState;

    private FridgeThermostat(){
		instance = this;
		display = FridgeDisplay.instance();
		setCompressorCanTurnOn(true);
		Clock.instance().addObserver(instance);
    }

    /**
	 * Return the instance
	 *
	 * @return the object
	 */
	public static FridgeThermostat instance() {
		if (instance == null) {
			instance = new FridgeThermostat();
		}
		return instance;
	}


	public FridgeState getFridgeState() {
		return fridgeState;
	}

	public void setFridgeState(FridgeState fridgeState) {
		this.fridgeState = fridgeState;
	}

	@Override
	public double getDesiredTemp() {
		return desiredTemp;
	}

	@Override
	public void setDesiredTemp(double desiredTemp) {
		this.desiredTemp = desiredTemp;
	}

	@Override
	public int getHeatRateDoorOpen() {
		return heatRateDoorOpen;
	}

	@Override
	public void setHeatRateDoorOpen(int heatRateDoorOpen) {
		this.heatRateDoorOpen = heatRateDoorOpen;
	}

	@Override
	public int getHeatRateDoorClosed() {
		return heatRateDoorClosed;
	}

	@Override
	public void setHeatRateDoorClosed(int heatRateDoorClosed) {
		this.heatRateDoorClosed = heatRateDoorClosed;
	}

	@Override
	public void setCurrentTemp(int value) {
		currentTemp = value;
	}

	@Override
	public void increaseCurrentTemp(int value) {
		currentTemp +=value;
	}

	@Override
	public int getCurrentTemp() {
		return currentTemp;
	}

	@Override
	public void update(Observable clock, Object value) {
		if(getFridgeState().equals(FridgeDoorOpenState.instance())) setCompressorCanTurnOn(false);
		if((getCurrentTemp() > getDesiredTemp()-getCompressorStartDiff()
				&& getCompressorCanTurnOn())) {
			//Compressor ON
			display.startCoolingFridge();
			incrementCoolTime();
			System.out.println("Fridge Cool TIME: " + getCoolTime() + " -> " + getCoolRate());
			if(getCoolTime() == getCoolRate()) {
				if ((--currentTemp == getDesiredTemp() - getCompressorStartDiff()
						&& getCurrentTemp() <= getRoomTemp()) || currentTemp == getRoomTemp()) {
					setCompressorCanTurnOn(false);
					TimerRanOutManager.instance().processEvent(
							new TimerRanOutEvent(instance));
				} else {
					TimerTickedManager.instance().processEvent(
							new TimerTickedEvent(instance));
				}
				setCoolTime(0);
			}
			setHeatTime(0);
		}
		else if((getCurrentTemp() < getDesiredTemp()+getCompressorStartDiff())
				|| (getFridgeState().equals(FridgeDoorOpenState.instance())
				&& getCurrentTemp() < getRoomTemp())){
			// Compressor OFF
			display.notCoolingFridge();
			incrementHeatTime();
			if(getCurrentTemp() >= getRoomTemp()){
				setHeatTime(0);
			} else if(getFridgeState().equals(FridgeDoorOpenState.instance())){
				//rate of temp increase is greater
				if(getHeatRateDoorOpen() == getHeatTime()) {
					currentTemp++;
					setHeatTime(0);
				}
			}else if(getHeatRateDoorClosed() == getHeatTime()){
				//rate of temp increase is lower
				currentTemp++;
				setHeatTime(0);
			}
			TimerTickedManager.instance().processEvent(
					new TimerTickedEvent(instance));
		}else{
			setCompressorCanTurnOn(true);
		}
	}
    
}
