
/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
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
import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * Orchestrates clicks on the Door Open button. It maintains a list of listeners
 * for the DoorOpenEvent and invokes their doorOpened method when the button is
 * clicked.
 * 
 * @author Brahma Dathan
 *
 */
public class FridgeDoorOpenManager {
	private EventListenerList listenerList = new EventListenerList();
	private static FridgeDoorOpenManager instance;

	/**
	 * Private to make it a singleton
	 */
	private FridgeDoorOpenManager() {
	}

	/**
	 * Returns the only instance of the class
	 * 
	 * @return the only instance of the class
	 */
	public static FridgeDoorOpenManager instance() {
		if (instance == null) {
			instance = new FridgeDoorOpenManager();
		}
		return instance;
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 *            an object that wants to listen to the event
	 */
	public void addDoorOpenListener(FridgeDoorOpenListener listener) {
		listenerList.add(FridgeDoorOpenListener.class, listener);
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the object to be removed
	 */
	public void removeDoorOpenListener(FridgeDoorOpenListener listener) {
		listenerList.remove(FridgeDoorOpenListener.class, listener);
	}

	/**
	 * Handles the request to open the door.
	 * 
	 * @param event
	 *            the CookRequestEvent object
	 */
	public void processEvent(FridgeDoorOpenEvent event) {
		EventListener[] listeners = listenerList
				.getListeners(FridgeDoorOpenListener.class);
		for (int index = 0; index < listeners.length; index++) {
			((FridgeDoorOpenListener) listeners[index]).doorOpened(event);
		}
	}
}
