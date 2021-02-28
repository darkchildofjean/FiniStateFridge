
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author enricorastelli
 */
public class IceBoxDoorClosedState extends FridgeState implements
        IceBoxDoorOpenListener, FridgeDoorOpenListener, FridgeDoorCloseListener,
        TimerTickedListener, TimerRanOutListener {

    private static IceBoxDoorClosedState instance;
    private IceBoxThermostat iceBoxThermostat;

    /**
     * Private to make it a singleton
     */
    private IceBoxDoorClosedState() {
        iceBoxThermostat = IceBoxThermostat.instance();
    }

    /**
     * When the Microwave leaves from this state, this method is called to
     * remove the state as a listener for the appropriate events.
     */
    @Override
    public void leave() {
        IceBoxDoorOpenManager.instance().removeDoorOpenListener(instance);
        FridgeDoorOpenManager.instance().removeDoorOpenListener(instance);
        FridgeDoorCloseManager.instance().removeDoorCloseListener(instance);
        TimerTickedManager.instance().removeTimerTickedListener(instance);
        TimerRanOutManager.instance().removeTimerRanOutListener(instance);
    }

    /**
     * returns the instance
     *
     * @return this object
     */
    public static IceBoxDoorClosedState instance() {
        if (instance == null) {
            instance = new IceBoxDoorClosedState();
        }
        return instance;
    }

    /**
     * handle door open event
     *
     */
    @Override
    public void doorOpened(IceBoxDoorOpenEvent event) {
        context.changeCurrentFridgeState(IceBoxDoorOpenState.instance());
    }

    /**
     * initialize the state
     *
     */
    @Override
    public void run() {
        IceBoxDoorOpenManager.instance().addDoorOpenListener(this);
        FridgeDoorOpenManager.instance().addDoorOpenListener(this);
		FridgeDoorCloseManager.instance().addDoorCloseListener(this);
        TimerTickedManager.instance().addTimerTickedListener(this);
        TimerRanOutManager.instance().addTimerRanOutListener(this);
        iceBoxThermostat.setFridgeState(instance);
        iceBoxThermostat.setHeatTime(0);
        display.turnFreezerLightOff();
        display.doorClosedFreezer();
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

	@Override
	public void doorClosed(FridgeDoorCloseEvent event) {
		context.changeCurrentFridgeState(FridgeDoorClosedState.instance());
	}

	@Override
	public void doorOpened(FridgeDoorOpenEvent event) {
		context.changeCurrentFridgeState(FridgeDoorOpenState.instance());
	}
}
