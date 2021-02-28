
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
 * The context is an observer for the clock and stores the context info for
 * states
 *
 */
public class FridgeContext {

    private static FridgeDisplay fridgeDisplay;
    private FridgeState currentFridgeState;
    private FridgeState currentIceBoxState;
    private static FridgeContext instance;
    private Thermostat fridgeStat;
    private Thermostat iceBoxStat;

    /**
     * Make it a singleton
     */
    private FridgeContext() {
        instance = this;
        fridgeDisplay = FridgeDisplay.instance();
        fridgeStat = FridgeThermostat.instance();
        iceBoxStat = IceBoxThermostat.instance();
        currentFridgeState = FridgeDoorClosedState.instance();
        currentIceBoxState = IceBoxDoorClosedState.instance();

    }

    /**
     * Return the instance
     *
     * @return the object
     */
    public static FridgeContext instance() {
        if (instance == null) {
            instance = new FridgeContext();
        }
        return instance;
    }

    /**
     * lets door closed state be the starting state adds the object as an
     * observable for clock
     */
    public void initialize() {
        instance.changeCurrentFridgeState(FridgeDoorClosedState.instance());
        instance.changeCurrentIceBoxState(IceBoxDoorClosedState.instance());
    }
    
    public void changeCurrentFridgeState(FridgeState nextFridgeState){
        currentFridgeState.leave();
	currentFridgeState = nextFridgeState;
	nextFridgeState.run();
    }
    
     public void changeCurrentIceBoxState(FridgeState nextIceBoxState){
        currentIceBoxState.leave();
	currentIceBoxState = nextIceBoxState;
	nextIceBoxState.run();
    }
    

    /**
     * Called from the states to change the current state
     *
     * @param nextFridgeSt
     * @param nextIceBoxStateate the next state
     */
    public void changeCurrentState(FridgeState nextFridgeState, FridgeState nextIceBoxState) {
        currentFridgeState.leave();
        currentIceBoxState.leave();
        currentFridgeState = nextFridgeState;
        currentIceBoxState = nextIceBoxState;
        nextFridgeState.run();
    }

    /**
     * Gets the display
     *
     * @return the display
     */
    public FridgeDisplay getDisplay() {
        return fridgeDisplay;
    }
}
