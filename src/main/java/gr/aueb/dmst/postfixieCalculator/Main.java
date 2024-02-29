package gr.aueb.dmst.postfixieCalculator;

import java.util.Stack;

import gr.aueb.dmst.postfixieCalculator.gui.GuiApplication;


/**
 * The Main class of the application.
 * This class is responsible for launching the GUI application.
 */
public class Main {

    // Stack to hold the elements for the postfix expression before the calculation.
    // This stack will be passed for every operation and after the calculation it will be updated.
    public static Stack<Element> stack = new Stack<>();

    /**
     * The main method of the application.
     * This method launches the GUI application and starts the Postfixie Calculator.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Launch the GUI application
        GuiApplication.launch(GuiApplication.class, args);
    }
}