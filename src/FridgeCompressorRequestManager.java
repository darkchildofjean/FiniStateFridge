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

import javax.swing.event.EventListenerList;
import java.util.EventListener;

/**
 * Orchestrates clicks on the Cook button. It maintains a list of listeners for
 * the CookRequestEvent and invokes their cookRequested method when the button
 * is clicked.
 * 
 * @author Brahma Dathan
 *
 */
public class FridgeCompressorRequestManager {
	private EventListenerList listenerList = new EventListenerList();
	private static FridgeCompressorRequestManager instance;

	/**
	 * Private to make it a singleton
	 */
	private FridgeCompressorRequestManager() {
	}

	/**
	 * Returns the only instance of the class
	 * 
	 * @return the only instance of the class
	 */
	public static FridgeCompressorRequestManager instance() {
		if (instance == null) {
			instance = new FridgeCompressorRequestManager();
		}
		return instance;
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 *            an object that wants to listen to the event
	 */
	public void addCookRequestListener(FridgeCompressorRequestListener listener) {
		listenerList.add(FridgeCompressorRequestListener.class, listener);
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the object to be removed
	 */
	public void removeCookRequestListener(FridgeCompressorRequestListener listener) {
		listenerList.remove(FridgeCompressorRequestListener.class, listener);
	}

	/**
	 * Handles the request to cook.
	 * 
	 * @param event
	 *            the CookRequestEvent object
	 */
	public void processEvent(FridgeCompressorRequestEvent event) {
		EventListener[] listeners = listenerList
				.getListeners(FridgeCompressorRequestListener.class);
		for (int index = 0; index < listeners.length; index++) {
			((FridgeCompressorRequestListener) listeners[index]).coolRequested(event);
		}
	}
}
