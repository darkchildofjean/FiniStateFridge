/*
 *
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 *
 *            Redistribution and use with or without modification, are permitted
 *            provided that the following conditions are met:
 *
 *            - the use is for academic purpose only - Redistributions of source
 *            code must retain the above copyright notice, this list of
 *            conditions and the following disclaimer. - Neither the name of
 *            Brahma Dathan or Sarnath Ramnath may be used to endorse or promote
 *            products derived from this software without specific prior written
 *            permission.
 *
 *            The authors do not make any claims regarding the correctness of
 *            the code in this module and are not responsible for any loss or
 *            damage resulting from its use.
 */
public class CompressorButton extends GUIButton {
    /**
     * The button for cooking
     *
     * @param string
     */
    public CompressorButton(String string) {
        super(string);
    }

    /**
     * Creates a CookRequestEvent, so the interested states can get it. Call the
     * context, so it can forward it through the event
     */
    @Override
    public void inform(FridgeDisplay source) {
        FridgeCompressorRequestManager.instance()
                .processEvent(new FridgeCompressorRequestEvent(source));

    }
}