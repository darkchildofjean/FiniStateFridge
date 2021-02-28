
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
 * Represents the state of the fridge when the door is open. When the
 * fridge has its door opened, the run method of this class is called. After
 * that, when an event occurs, the handle method is invoked.
 */
public class FridgeDoorOpenState extends FridgeState implements FridgeDoorCloseListener,
		IceBoxDoorOpenListener, IceBoxDoorCloseListener, TimerTickedListener, TimerRanOutListener {
	
	private static FridgeDoorOpenState instance;
	private FridgeThermostat fridgeThermostat;

	/**
	 * Private to make it a singleton
	 */
	private FridgeDoorOpenState() {
		fridgeThermostat = FridgeThermostat.instance();
	}

	/**
	 * When the Fridge leaves from this state, this method is called to
	 * remove the state as a listener for the appropriate events.
	 */
	@Override
	public void leave() {
		FridgeDoorCloseManager.instance().removeDoorCloseListener(this);
		IceBoxDoorOpenManager.instance().removeDoorOpenListener(this);
		IceBoxDoorCloseManager.instance().removeDoorCloseListener(this);
		TimerRanOutManager.instance().removeTimerRanOutListener(this);
		TimerTickedManager.instance().removeTimerTickedListener(this);
	}

	/**
	 * For the singleton pattern
	 *
	 * @return the object
	 */
	public static FridgeDoorOpenState instance() {
		if (instance == null) {
			instance = new FridgeDoorOpenState();
		}
		return instance;
	}

	/**
	 * Process door closed event
	 */
	@Override
	public void doorClosed(FridgeDoorCloseEvent event) {
		context.changeCurrentFridgeState(FridgeDoorClosedState.instance());
	}

	/**
	 * Initialize the state
	 */
	@Override
	public void run() {
		FridgeDoorCloseManager.instance().addDoorCloseListener(this);
		IceBoxDoorOpenManager.instance().addDoorOpenListener(this);
		IceBoxDoorCloseManager.instance().addDoorCloseListener(this);
		TimerTickedManager.instance().addTimerTickedListener(this);
		TimerRanOutManager.instance().addTimerRanOutListener(this);
		fridgeThermostat.setFridgeState(instance);
		fridgeThermostat.setHeatTime(0);
		display.turnFridgeLightOn();
		display.doorOpenedFridge();
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
		// TODO Auto-generated method stub
		context.changeCurrentFridgeState(IceBoxDoorClosedState.instance());
	}

	@Override
	public void doorOpened(IceBoxDoorOpenEvent event) {
		// TODO Auto-generated method stub
		context.changeCurrentFridgeState(IceBoxDoorOpenState.instance());
	}
}