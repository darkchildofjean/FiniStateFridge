
/**
 * Orchestrates clicks on the Door Close button. It maintains a list of
 * listeners for the DoorCloseEvent and invokes their doorClosed method when the
 * button is clicked.
 * 
 * @author Brahma Dathan
 *
 */
import java.util.EventListener;
import javax.swing.event.EventListenerList;

public class IceBoxDoorCloseManager {
	private EventListenerList listenerList = new EventListenerList();
	private static IceBoxDoorCloseManager instance;

	/**
	 * Private to make it a singleton
	 */
	private IceBoxDoorCloseManager() {
	}

	/**
	 * Returns the only instance of the class
	 * 
	 * @return the only instance of the class
	 */
	public static IceBoxDoorCloseManager instance() {
		if (instance == null) {
			instance = new IceBoxDoorCloseManager();
		}
		return instance;
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 *            an object that wants to listen to the event
	 */
	public void addDoorCloseListener(IceBoxDoorCloseListener listener) {
		listenerList.add(IceBoxDoorCloseListener.class, listener);
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the object to be removed
	 */
	public void removeDoorCloseListener(IceBoxDoorCloseListener listener) {
		listenerList.remove(IceBoxDoorCloseListener.class, listener);
	}

	/**
	 * Handles the request to close the door.
	 * 
	 * @param event
	 *            the CookRequestEvent object
	 */
	public void processEvent(IceBoxDoorCloseEvent event) {
		EventListener[] listeners = listenerList
				.getListeners(IceBoxDoorCloseListener.class);
		for (int index = 0; index < listeners.length; index++) {
			((IceBoxDoorCloseListener) listeners[index]).doorClosed(event);
		}
	}
}