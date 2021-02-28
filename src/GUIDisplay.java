
/*
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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Games (Cole Burnham) Refactored off original authors above. Changed
 * so much that I probably should have just written the class myself
 */
public class GUIDisplay extends FridgeDisplay implements ActionListener {
    private static SimpleDisplay frame;
    private Config cfg = new Config();
    private FridgeThermostat fridgeThermostat;
    private IceBoxThermostat iceBoxThermostat;

    /**
     * Creates the frame and displays it.
     */
    private GUIDisplay() {
        fridgeThermostat = FridgeThermostat.instance();
        iceBoxThermostat = IceBoxThermostat.instance();
        fridgeThermostat.setFridgeState(FridgeDoorClosedState.instance());
        iceBoxThermostat.setFridgeState(IceBoxDoorClosedState.instance());
        frame = new SimpleDisplay();

        initialize();
    }

    /**
     * Start the whole show
     *
     * @param args not used
     */
    public static void main(String[] args) {

        FridgeDisplay display = new GUIDisplay();

    }

    // adds panels method to create and populate frame

    /**
     * No conditionals. Let the clicked button do the hard work.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        ((GUIButton) event.getSource()).inform(this);
    }

    /**
     * Display a text indicating that the light is on
     */
    @Override
    public void turnFridgeLightOn() {
        frame.lightStatusFridge.setText("Light On");
    }

    /**
     * Display a text indicating that the light is off
     */
    @Override
    public void turnFridgeLightOff() {
        frame.lightStatusFridge.setText("Light Off");
    }

    /**
     * Display a text indicating that the door is open
     */
    @Override
    public void doorOpenedFridge() {
        frame.doorStatusFridge.setText("Door Opened");
    }

    /**
     * Display a text indicating that the door is closed
     */
    @Override
    public void doorClosedFridge() {
        frame.doorStatusFridge.setText("Door Closed");
    }

    @Override
    public void doorClosedFreezer() {
        frame.doorStatusFreezer.setText("Door Closed");
    }

    @Override
    public void doorOpenedFreezer() {
        frame.doorStatusFreezer.setText("Door Opened");
    }

    @Override
    public void fridgeCurrentTemp() {
        frame.fridgeTemp.setText("FRIDGE: " + fridgeThermostat.getCurrentTemp());
    }

    @Override
    public void freezerCurrentTemp() {
        frame.freezerTemp.setText("FREEZER: " + iceBoxThermostat.getCurrentTemp()); // fix to freezerThermostat upon creation of freezerThermostat field
    }

    @Override
    public void turnFreezerLightOn() {
        frame.lightStatusFreezer.setText("Light On");
    }

    @Override
    public void turnFreezerLightOff() {
        frame.lightStatusFreezer.setText("Light Off");
    }

    /**
     * Display the remaining cook time
     */
    @Override
    public void displayTimeRemaining(int value) {
        frame.timerValue.setText(" " + value);
    }

    /**
     * Display a text indicating that cooking has started
     */
    @Override
    public void startCoolingFridge() {
        frame.compressorStatusFridge.setText("Cooling");
    }

    /**
     * Display a text indicating that cooking has ended
     */
    @Override
    public void notCoolingFridge() {
        frame.compressorStatusFridge.setText("Not cooling");
    }

    /**
     * Display a text indicating that cooking has started
     */
    @Override
    public void startCoolingFreezer() {
        frame.compressorStatusFreezer.setText("Cooling");
    }

    /**
     * Display a text indicating that cooking has ended
     */
    @Override
    public void notCoolingFreezer() {
        frame.compressorStatusFreezer.setText("Not cooling");
    }

    /**
     * Inner class because the outer class extends MicrowaveDisplay.
     */
    private class SimpleDisplay extends JFrame {
        // new config item

        String tempString;
        double freezerUpperTemp;
        double freezerCurrentTemp = Double.parseDouble(cfg.getProperty(
                "freezerDesiredTemp"));
        double freezerLowTemp;
        double fridgeUpperTemp;
        double fridgeCurrentTemp = Double.parseDouble(cfg.getProperty(
                "fridgeDesiredTemp"));
        double fridgeLowTemp;
        int freezerHeatRateDoorOpen = Integer.parseInt(cfg.getProperty(
                "freezerHeatRateDoorOpen"));
        int freezerHeatRateDoorClosed = Integer.parseInt(cfg.getProperty(
                "freezerHeatRateDoorClosed"));
        int fridgeHeatRateDoorOpen = Integer.parseInt(cfg.getProperty(
                "fridgeHeatRateDoorOpen"));
        int fridgeHeatRateDoorClosed = Integer.parseInt(cfg.getProperty(
                "fridgeHeatRateDoorClosed"));
        int fridgeCompressorStartDiff = Integer.parseInt(cfg.getProperty(
                "fridgeCompressorStartDiff"));
        int freezerCompressorStartDiff = Integer.parseInt(cfg.getProperty(
                "freezerCompressorStartDiff"));
        int fridgeCoolRate = Integer.parseInt(cfg.getProperty(
                "fridgeCoolRate"));
        int freezerCoolRate = Integer.parseInt(cfg.getProperty(
                "freezerCoolRate"));
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        JPanel middlePanel = new JPanel(new GridLayout(2, 3));
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
        private GUIButton doorCloserFridge = new FridgeDoorCloseButton(
                "close fridge door");
        private GUIButton doorOpenerFridge = new FridgeDoorOpenButton(
                "open fridge door");
        private GUIButton doorCloserFreezer = new IceBoxDoorCloseButton(
                "close freezer door");
        private GUIButton doorOpenerFreezer = new IceBoxDoorOpenButton(
                "open freezer door");
        private JLabel doorStatusFridge = new JLabel("Door Closed");
        private JLabel doorStatusFreezer = new JLabel("Door Closed");
        private JLabel timerValue = new JLabel("            ");
        private JLabel lightStatusFridge = new JLabel("Light Off");
        private JLabel lightStatusFreezer = new JLabel("Light Off");
        private JLabel compressorStatusFridge = new JLabel("Not cooling");
        private JLabel compressorStatusFreezer = new JLabel("Not cooling");
        private JLabel roomTemp = new JLabel("Room Temp: " + cfg.getProperty(
                "roomTemp"));
        private JLabel fridgeDesTemp =
                new JLabel("Fridge Temp: " + cfg.getProperty(
                        "fridgeDesiredTemp"));
        private JLabel freezerDesTemp =
                new JLabel("Fridge Temp: " + cfg.getProperty(
                        "freezerDesiredTemp"));
        private TextField setRoomTemperatureField = new TextField("");
        private GUIButton setRoomTemperatureButton = new CompressorButton(
                "Set room temp");
        private JLabel fridgeTemp = new JLabel(
                "FRIDGE: " + fridgeCurrentTemp);
        private TextField setFridgeTemperatureField = new TextField("");
        private GUIButton setFridgeTemperatureButton = new CompressorButton(
                "set desired fridge temp");
        private JLabel freezerTemp = new JLabel(
                "FREEZER: " + freezerCurrentTemp);
        private TextField setFreezerTemperatureField = new TextField("");
        private GUIButton setFreezerTemperatureButton = new CompressorButton(
                "set desired freezer temp");
        private JPanel bigPanel = new JPanel(new BorderLayout());

        /**
         * Set up the JFrame
         */
        private SimpleDisplay() {
            super("Refrigerator");

            getConfigs();
            /*
             * This function actually changes the temp to 22 in the config file
             * run it then open the config file, it changes from 41 to 22. But
             * for some reason the buttons, which call the same method don't
             * change the temp in the file? maybe we need to
             * serialize/deserialize the file?
             */
            //setFridgeUpperTemp("22");// check this one out
            // functions for the button to set temps are way below (line 267?)
            // we can move that where ever if I can figure out why.

            getContentPane().setLayout(new FlowLayout());

            getContentPane().add(timerValue);
            // fridge upper temp

            doorCloserFridge.addActionListener(GUIDisplay.this);
            doorOpenerFridge.addActionListener(GUIDisplay.this);
            doorCloserFreezer.addActionListener(GUIDisplay.this);
            doorOpenerFreezer.addActionListener(GUIDisplay.this);
            setRoomTemperatureButton.addActionListener(new AddConfigListener());
            setFridgeTemperatureButton
                    .addActionListener(new AddConfigListener());
            setFreezerTemperatureButton
                    .addActionListener(new AddConfigListener());

            createTopPanel();

            createMiddlePanel();
            createBottomPanel();
            addPanelsToFrame();
            pack();
            setVisible(true);
        }

        /**
         * get configs method to pull in the configs
         */
        void getConfigs() {
            //Room Temp
            tempString = cfg.getProperty("roomTemp");
            fridgeThermostat.setRoomTemp(Double.parseDouble(tempString));
            iceBoxThermostat.setRoomTemp(Double.parseDouble(tempString));
            System.out.println("room temp is: " + tempString);
            //Fridge Upper Temp
            tempString = cfg.getProperty("fridgeUpperTemp");
            fridgeUpperTemp = Double.parseDouble(tempString);
            System.out.println("fridge upper temp is: " + fridgeUpperTemp);
            //Fridge Desired Temp
            tempString = cfg.getProperty("fridgeDesiredTemp");
            fridgeCurrentTemp = Double.parseDouble(tempString);
            System.out.println("fridge desired temp is: " + fridgeCurrentTemp);
            fridgeThermostat.setCurrentTemp((int) fridgeCurrentTemp);
            fridgeThermostat.setDesiredTemp(fridgeCurrentTemp);
            // fridge lower temp
            tempString = cfg.getProperty("fridgeLowerTemp");
            fridgeLowTemp = Double.parseDouble(tempString);
            System.out.println("fridge lower temp is: " + fridgeLowTemp);
            //Freezer Upper Temp
            tempString = cfg.getProperty("freezerUpperTemp");
            fridgeUpperTemp = Double.parseDouble(tempString);
            System.out.println("freezer upper temp is: " + freezerUpperTemp);
            //Freezer Desired Temp
            tempString = cfg.getProperty("freezerDesiredTemp");
            freezerCurrentTemp = Double.parseDouble(tempString);
            System.out.println("freezer desired temp is: " + freezerCurrentTemp);
            iceBoxThermostat.setCurrentTemp((int) freezerCurrentTemp);
            iceBoxThermostat.setDesiredTemp(freezerCurrentTemp);
            // freezer lower temp
            tempString = cfg.getProperty("freezerLowerTemp");
            freezerLowTemp = Double.parseDouble(tempString);
            System.out.println("freezer lower temp is: " + freezerLowTemp);
            //set heatRate for thermostats
            fridgeThermostat.setHeatRateDoorOpen(fridgeHeatRateDoorOpen);
            fridgeThermostat.setHeatRateDoorClosed(fridgeHeatRateDoorClosed);
            iceBoxThermostat.setHeatRateDoorOpen(freezerHeatRateDoorOpen);
            iceBoxThermostat.setHeatRateDoorClosed(freezerHeatRateDoorClosed);
            fridgeThermostat.setCoolRate(fridgeCoolRate);
            fridgeThermostat.setCompressorStartDiff(fridgeCompressorStartDiff);
            iceBoxThermostat.setCoolRate(freezerCoolRate);
            iceBoxThermostat.setCompressorStartDiff(freezerCompressorStartDiff);
        }

        void setRoomTemp(String value) {
            System.out.println("Setting room temp to: " + value);
            cfg.setProperty("roomTemp", value);
            frame.roomTemp.setText("Room Temp: " + cfg.getProperty(
                    "roomTemp"));
            fridgeThermostat.setRoomTemp(Double.parseDouble(value));
            iceBoxThermostat.setRoomTemp(Double.parseDouble(value));
        }

        void setFridgeUpperTemp(String value) {//need a regular setFridgeTemp
            // and Fridge Temp in config file
            System.out.println("Setting fridge temp to: " + value);
            fridgeThermostat.setDesiredTemp(Double.parseDouble(value));
            cfg.setProperty("fridgeUpperTemp", value);
        }

        void setFridgeDesiredTemp(String value) {
            try {
                double temp = Double.parseDouble(value);
                if (temp <= Double.parseDouble(cfg.getProperty("fridgeUpperTemp"))
                        && temp >= Double.parseDouble(cfg.getProperty("fridgeLowerTemp"))) {
                    System.out.println("Setting fridge temp to: " + value);
                    fridgeThermostat.setDesiredTemp(temp);
                    cfg.setProperty("fridgeDesiredTemp", value);
                    frame.fridgeDesTemp.setText("Fridge Temp: " + cfg.getProperty(
                            "fridgeDesiredTemp"));
                } else System.out.println(value + " is outside of Fridge " +
                        "temperature range. Couldn't set desired temp.");
            } catch (NumberFormatException e) {
                System.out.println("MUST ENTER A NUMERIC VALUE!!!");
            }
        }

        public void setFridgeLowerTemp(String value) {
            cfg.setProperty("fridgeLowerTemp", value);
        }

        void setFreezerUpperTemp(String value) {
            System.out.println("Setting freezer temp to: " + value);
            iceBoxThermostat.setDesiredTemp(Double.parseDouble(value));
            cfg.setProperty("freezerUpperTemp", value);
        }

        void setFreezerDesiredTemp(String value) {
            try {
                double temp = Double.parseDouble(value);
                if (temp <= Double.parseDouble(cfg.getProperty("freezerUpperTemp")) && temp >= Double.parseDouble(cfg.getProperty("freezerLowerTemp"))) {
                    System.out.println("Setting freezer temp to: " + value);
                    iceBoxThermostat.setDesiredTemp(Double.parseDouble(value));
                    cfg.setProperty("freezerDesiredTemp", value);
                    frame.freezerDesTemp.setText("Fridge Temp: " + cfg.getProperty(
                            "freezerDesiredTemp"));
                } else System.out.println(value + " is outside of Freezer " +
                        "temperature range. Couldn't set desired temp.");
            } catch (NumberFormatException e) {
                System.out.println("MUST ENTER A NUMERIC VALUE!!!");
            }
        }

        public void setFreezerLowerTemp(String value) {
            cfg.setProperty("freezerLowerTemp", value);
        }

        private void addPanelsToFrame() {
            bigPanel.add(topPanel, BorderLayout.NORTH);
            bigPanel.add(middlePanel, BorderLayout.CENTER);
            bigPanel.add(bottomPanel, BorderLayout.SOUTH);
            add(bigPanel);
            setVisible(true);
        }

        private void createTopPanel() {
            this.setSize(400, 400);
            topPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
            topPanel.setBackground(Color.WHITE);
            topPanel.add(roomTemp);
            topPanel.add(setRoomTemperatureField);
            topPanel.add(setRoomTemperatureButton);
            topPanel.add(fridgeDesTemp);
            topPanel.add(setFridgeTemperatureField);
            topPanel.add(setFridgeTemperatureButton);
            topPanel.add(freezerDesTemp);
            topPanel.add(setFreezerTemperatureField);
            topPanel.add(setFreezerTemperatureButton);
        }

        private void createMiddlePanel() {
            this.setSize(400, 400);
            middlePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            middlePanel.setBackground(Color.BLUE);
            middlePanel.add(doorOpenerFridge);
            middlePanel.add(doorCloserFridge);
            middlePanel.add(doorOpenerFreezer);
            middlePanel.add(doorCloserFreezer);
        }

        private void createBottomPanel() {
            this.setSize(300, 300);
            bottomPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
            bottomPanel.setBackground(Color.YELLOW);
            bottomPanel.add(fridgeTemp);
            bottomPanel.add(compressorStatusFridge);
            bottomPanel.add(doorStatusFridge);
            bottomPanel.add(lightStatusFridge);
            bottomPanel.add(freezerTemp);
            bottomPanel.add(compressorStatusFreezer);
            bottomPanel.add(doorStatusFreezer);
            bottomPanel.add(lightStatusFreezer);
        }

        private class AddConfigListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String callingButton = e.getActionCommand();
                String fieldValue;

                // check to see which button was clicked
                // circle button
                if (callingButton.equals("Set room temp")) {

                    fieldValue = setRoomTemperatureField.getText();
                    setRoomTemp(fieldValue);

                }
                if (callingButton.equals("set desired fridge temp")) {

                    fieldValue = setFridgeTemperatureField.getText();
                    setFridgeDesiredTemp(fieldValue);

                }
                if (callingButton.equals("set desired freezer temp")) {

                    fieldValue = setFreezerTemperatureField.getText();
                    setFreezerDesiredTemp(fieldValue);

                }
            }

        }
    }

}
