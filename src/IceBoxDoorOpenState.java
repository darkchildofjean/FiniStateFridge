
/**
 *
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 *
 * Redistribution and use with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * - the use is for academic purpose only - Redistributions of source code must
 * retain the above copyright notice, this list of conditions and the following
 * disclaimer. - Neither the name of Brahma Dathan or Sarnath Ramnath may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in
 * this module and are not responsible for any loss or damage resulting from its
 * use.
 */
/**
 * Represents the state of the microwave when the door is open. When the
 * microwave has its door opened, the run method of this class is called. After
 * that, when an event occurs, the handle method is invoked.
 */
public class IceBoxDoorOpenState extends FridgeState implements IceBoxDoorCloseListener,
		FridgeDoorOpenListener, FridgeDoorCloseListener, TimerTickedListener,
		TimerRanOutListener{

    private static IceBoxDoorOpenState instance;
	private IceBoxThermostat iceBoxThermostat;

	/**
	 * Private to make it a singleton
	 */
	private IceBoxDoorOpenState() {
		iceBoxThermostat = IceBoxThermostat.instance();
	}

	/**
	 * When the Fridge leaves from this state, this method is called to
	 * remove the state as a listener for the appropriate events.
	 */
	@Override
	public void leave() {
		IceBoxDoorCloseManager.instance().removeDoorCloseListener(this);
		//IceBoxCompressorRequestManager.instance().removeCoolRequestListener
		// (this);
		FridgeDoorOpenManager.instance().removeDoorOpenListener(instance);
		FridgeDoorCloseManager.instance().removeDoorCloseListener(instance);
		TimerTickedManager.instance().removeTimerTickedListener(instance);
		TimerRanOutManager.instance().removeTimerRanOutListener(instance);
	}

	/**
	 * For the singleton pattern
	 *
	 * @return the object
	 */
	public static IceBoxDoorOpenState instance() {
		if (instance == null) {
			instance = new IceBoxDoorOpenState();
		}
		return instance;
	}

	/**
	 * Process door closed event
	 */
	@Override
	public void doorClosed(IceBoxDoorCloseEvent event) {
		context.changeCurrentIceBoxState(IceBoxDoorClosedState.instance());

	}

	/**
	 * Initialize the state
	 */
	@Override
	public void run() {
		IceBoxDoorCloseManager.instance().addDoorCloseListener(this);
		FridgeDoorOpenManager.instance().addDoorOpenListener(this);
		FridgeDoorCloseManager.instance().addDoorCloseListener(this);
		TimerTickedManager.instance().addTimerTickedListener(instance);
		TimerRanOutManager.instance().addTimerRanOutListener(instance);
		iceBoxThermostat.setFridgeState(instance);
		iceBoxThermostat.setHeatTime(0);
		display.turnFreezerLightOn();
		display.doorOpenedFreezer();
	}

	@Override
	public void doorClosed(FridgeDoorCloseEvent event) {
		context.changeCurrentFridgeState(FridgeDoorClosedState.instance());
		
	}

	@Override
	public void doorOpened(FridgeDoorOpenEvent event) {
		context.changeCurrentFridgeState(FridgeDoorOpenState.instance());
		
	}


	@Override
	public void timerRanOut(TimerRanOutEvent event) {
		display.displayTimeRemaining(Timer.instance().getTimeValue());
		display.freezerCurrentTemp();


	}

	@Override
	public void timerTicked(TimerTickedEvent event) {
		display.displayTimeRemaining(Timer.instance().getTimeValue());
		display.freezerCurrentTemp();
	}
}