
/*

  @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010

 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.
 */
/**
 * Represents the state of the microwave when the door is closed. When the
 * microwave has its door closed, the run method of this class is called. After
 * that, when an event occurs, the handle method is invoked.
 */
public class FridgeDoorClosedState extends FridgeState implements
		FridgeDoorOpenListener, IceBoxDoorOpenListener, IceBoxDoorCloseListener,
		TimerTickedListener, TimerRanOutListener {

	private static FridgeDoorClosedState instance;
	private FridgeThermostat fridgeThermostat;

	/**
	 * Private to make it a singleton
	 */
	private FridgeDoorClosedState() {
		fridgeThermostat = FridgeThermostat.instance();
	}

	/**
	 * When the Microwave leaves from this state, this method is called to
	 * remove the state as a listener for the appropriate events.
	 */
	@Override
	public void leave() {
		FridgeDoorOpenManager.instance().removeDoorOpenListener(instance);
		IceBoxDoorOpenManager.instance().removeDoorOpenListener(this);
		IceBoxDoorCloseManager.instance().removeDoorCloseListener(this);
		TimerRanOutManager.instance().removeTimerRanOutListener(this);
		TimerTickedManager.instance().removeTimerTickedListener(this);
	}

	/**
	 * returns the instance
	 *
	 * @return this object
	 */
	public static FridgeDoorClosedState instance() {
		if (instance == null) {
			instance = new FridgeDoorClosedState();
		}
		return instance;
	}

	/**
	 * handle door open event
	 *
	 */

	@Override
	public void doorOpened(FridgeDoorOpenEvent event) {
		context.changeCurrentFridgeState(FridgeDoorOpenState.instance());
	}

	/**
	 * initialize the state
	 *
	 */
	@Override
	public void run() {
		FridgeDoorOpenManager.instance().addDoorOpenListener(this);
		IceBoxDoorOpenManager.instance().addDoorOpenListener(this);
		IceBoxDoorCloseManager.instance().addDoorCloseListener(this);
		TimerTickedManager.instance().addTimerTickedListener(this);
		TimerRanOutManager.instance().addTimerRanOutListener(this);
		fridgeThermostat.setFridgeState(instance);
		fridgeThermostat.setHeatTime(0);
		display.turnFridgeLightOff();
		display.doorClosedFridge();
	}

	@Override
	public void timerRanOut(TimerRanOutEvent event) {
		display.displayTimeRemaining(Timer.instance().getTimeValue());
		display.fridgeCurrentTemp();
	}

	@Override
	public void timerTicked(TimerTickedEvent event) {
		display.displayTimeRemaining(Timer.instance().getTimeValue());
		display.fridgeCurrentTemp();
	}

	@Override
	public void doorClosed(IceBoxDoorCloseEvent event) {
		context.changeCurrentIceBoxState(IceBoxDoorClosedState.instance());
		
	}

	@Override
	public void doorOpened(IceBoxDoorOpenEvent event) {
		context.changeCurrentIceBoxState(IceBoxDoorOpenState.instance());
	}
}
