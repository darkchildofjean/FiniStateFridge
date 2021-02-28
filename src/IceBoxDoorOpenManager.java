
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
public class IceBoxDoorOpenManager {
	private EventListenerList listenerList = new EventListenerList();
	private static IceBoxDoorOpenManager instance;

	/**
	 * Private to make it a singleton
	 */
	private IceBoxDoorOpenManager() {
	}

	/**
	 * Returns the only instance of the class
	 * 
	 * @return the only instance of the class
	 */
	public static IceBoxDoorOpenManager instance() {
		if (instance == null) {
			instance = new IceBoxDoorOpenManager();
		}
		return instance;
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 *            an object that wants to listen to the event
	 */
	public void addDoorOpenListener(IceBoxDoorOpenListener listener) {
		listenerList.add(IceBoxDoorOpenListener.class, listener);
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the object to be removed
	 */
	public void removeDoorOpenListener(IceBoxDoorOpenListener listener) {
		listenerList.remove(IceBoxDoorOpenListener.class, listener);
	}

	/**
	 * Handles the request to open the door.
	 * 
	 * @param event
	 *            the CookRequestEvent object
	 */
	public void processEvent(IceBoxDoorOpenEvent event) {
		EventListener[] listeners = listenerList
				.getListeners(IceBoxDoorOpenListener.class);
		for (int index = 0; index < listeners.length; index++) {
			((IceBoxDoorOpenListener) listeners[index]).doorOpened(event);
		}
	}
}
