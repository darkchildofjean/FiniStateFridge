
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
import javax.swing.JButton;

/**
 * The abstract GUI JButton object. Helps to get rid of conditionals
 *
 */
public abstract class GUIButton extends JButton {
	/**
	 * Create the button with the proper text
	 * 
	 * @param string
	 *            the text
	 */
	public GUIButton(String string) {
		super(string);
	}

	/**
	 * Tell the listener that the button has been clicked.
	 * 
	 * @param context
	 *            the Microwave context
	 * @param display
	 *            the GUI
	 */
	public abstract void inform(FridgeDisplay display);
}
