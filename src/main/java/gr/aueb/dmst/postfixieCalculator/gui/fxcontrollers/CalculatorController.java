package gr.aueb.dmst.postfixieCalculator.gui.fxcontrollers;

import java.util.ResourceBundle;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import gr.aueb.dmst.postfixieCalculator.Calculator;
import gr.aueb.dmst.postfixieCalculator.Element;
import gr.aueb.dmst.postfixieCalculator.Main;


/**
 * The CalculatorController class is responsible for handling user interactions with the GUI.
 * It contains the methods so each button in the GUI can perform its specific action.
 */
public class CalculatorController implements Initializable {

    // The OpenAI API key that is stored as an environment variable
    // and user has to set it up in order to use the chatGPT method
    private static final String openai_ApiKey = System.getenv("OPENAI_API_KEY");

    // Flag to indicate whether the result of a calculation is being displayed
    public boolean resultState = false;

    // FXML annotations are used to link the JavaFX components defined in the FXML file to the controller class
    @FXML
    private TextArea previewArea;
    @FXML
    private Button zeroButton;
    @FXML
    private Button oneButton;
    @FXML
    private Button twoButton;
    @FXML
    private Button threeButton;
    @FXML
    private Button fourButton;
    @FXML
    private Button fiveButton;
    @FXML
    private Button sixButton;
    @FXML
    private Button sevenButton;
    @FXML
    private Button eightButton;
    @FXML
    private Button nineButton;
    @FXML
    private Button plusButton;
    @FXML
    private Button minusButton;
    @FXML
    private Button multiplyButton;
    @FXML
    private Button divideButton;
    @FXML
    private Circle validCircle;
    @FXML
    private TextArea factTextArea;
    @FXML
    private TextArea expressionArea;

    /**
     * This method sets up the initial state of the GUI and the event handlers for the buttons.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // In the start, both text areas are empty
        previewArea.setText("");
        expressionArea.setText("");
        // When user press a button, this number displays in the previewArea
        oneButton.setOnAction(this::addNumberToPreview);
        twoButton.setOnAction(this::addNumberToPreview);
        threeButton.setOnAction(this::addNumberToPreview);
        fourButton.setOnAction(this::addNumberToPreview);
        fiveButton.setOnAction(this::addNumberToPreview);
        sixButton.setOnAction(this::addNumberToPreview);
        sevenButton.setOnAction(this::addNumberToPreview);
        eightButton.setOnAction(this::addNumberToPreview);
        nineButton.setOnAction(this::addNumberToPreview);
        zeroButton.setOnAction(this::addNumberToPreview);
        // When user press an operator button, the operator displays in the expressionArea
        plusButton.setOnAction(this::addOperator);
        minusButton.setOnAction(this::addOperator);
        multiplyButton.setOnAction(this::addOperator);
        divideButton.setOnAction(this::addOperator);
    }

    /**
     * This method is called when a number button is clicked.
     * It adds the number to the previewArea and updates the display.
     * This happens so user can select a multi digit number and see it before he adds
     * it in the expression.
     *
     * @param event the event that triggered this method
     */
    public void addNumberToPreview(ActionEvent event) {
        // If the result of a calculation is being displayed, clear the stack and the display
        if (resultState) {
            clearStack();
            factTextArea.clear();
            resultState = false;
        }
        // Get the number from the button that triggered the event
        Button button = (Button) event.getSource();
        String buttonText = button.getText();
        // Update the display in the previewArea
        previewArea.setText(previewArea.getText() + buttonText);
    }

    /**
     * This method is used to remove the last character from the text in the previewArea.
     * If the previewArea is not empty, it updates the text in the previewArea by removing the last character.
     */
    public void removeFromPreview() {
        // Check if the previewArea is not empty
        if (!previewArea.getText().isEmpty()) {
            // Remove the last character from the text in the previewArea
            previewArea.setText(previewArea.getText().substring(0, previewArea.getText().length() - 1));
        }
    }

    /**
     * This method is called when an operator button is clicked.
     * It adds the operator to the postfix expression and updates the display.
     *
     * @param event the event that triggered this method
     */
    public void addOperator(ActionEvent event) {
        // If the result of a calculation is being displayed, clear the stack and the display
        if (resultState) {
            clearStack();
            factTextArea.clear();
            resultState = false;
        }
        // Get the type of operator from the button that triggered the event
        Button button = (Button) event.getSource();
        String buttonText = button.getText();
        // Update the display
        expressionArea.setText(expressionArea.getText() + " " + buttonText + " ");
        // Push the operator to the stack
        Main.stack.push(new Element(buttonText.charAt(0)));
    }

    /**
     * This method is used to add the number from the previewArea to the expressionArea.
     * It also pushes the number to the stack and clears the previewArea.
     */
    public void addNumberToExpression() {
        // Append the number from the previewArea to the expressionArea
        expressionArea.setText(expressionArea.getText() + " " + previewArea.getText() + " ");

        // Push the number to the stack
        Main.stack.push(new Element(Integer.parseInt(previewArea.getText())));

        // Clear the previewArea
        previewArea.setText("");
    }

    /**
     * This method is called to remove the last element from the postfix expression.
     * It updates the display and the postfix expression accordingly.
     */
    public void removeFromStack() {
        // If the result of a calculation is being displayed, clear the stack and the display
        if (resultState) {
            clearStack();
            resultState = false;
            return;
        }
        // If the stack is not empty, remove the last element and update the display
        if (!Main.stack.isEmpty()) {
            // If the last element is a number, remove 3 characters from the display (space, number, space)
            expressionArea.setText(expressionArea.getText().substring(0, expressionArea.getText().length() - 3));
            Main.stack.pop();
        }
    }

    /**
     * This method is called to calculate the result of the postfix expression.
     * If the expression is valid, it calculates the result and updates the display.
     * If the expression is not valid, it updates the display to indicate an error.
     */
    public void calculateResult() {
        // If the expression is valid, calculate the result and update the display
        if (Calculator.isValidPostfix(Main.stack)) {
            try {
                // Take the result of the calculation and if it is an integer, display it as an integer, else as a double
                Double result = Calculator.calculateExpression(Main.stack);
                String resultText = result == Math.floor(result) ? Integer.toString(result.intValue()) : result.toString();
                // Update the display
                expressionArea.setText(resultText);
                // Make the valid circle green
                validCircle.setFill(Color.web("#36ea4e"));

                // Ask the chatGPT for a fun fact about the number
                factTextArea.clear();
                // Display the fun fact ONLY if user has given the OpenAI API key
                if (openai_ApiKey != null && !openai_ApiKey.isEmpty()) {
                    // Send to the chatGPT the prompt and display the response
                    factTextArea.setText(chatGPT("Tell me a fun and interesting fact about number " +
                            resultText + ", either historical, physiscs," +
                            " biology, animals. It should be a bit interesting and if possible funny." +
                            " Maximum 20 words answer.").replace("\\", ""));
                } else {
                    // If user has not given the OpenAI API key, display a message to set it up
                    factTextArea.setText("Please set up the OpenAI API key to use this feature.");
                }

                resultState = true;
            } catch (ArithmeticException e) {
                // If a division by zero is attempted, make the valid circle next to "RESULT" red
                validCircle.setFill(Color.RED);
            }
        } else {
            // If the expression is not valid, make the valid circle next to "RESULT" red
            validCircle.setFill(Color.RED);
        }
    }

    /**
     * This method is called to clear the postfix expression and the display.
     * It is called when the "CLEAR" button is clicked.
     */
    public void clearStack() {
        factTextArea.setText("");
        expressionArea.setText("");
        Main.stack.clear();
    }

    /**
     * This method interacts with the OpenAI GPT-3.5 turbo model.
     * It sends a prompt to the model and returns the model's response.
     * It also uses the OpenAI API key to authenticate the request.
     *
     * @param prompt the prompt to send to the model
     * @return the model's response
     * @throws RuntimeException if the HTTP request fails
     */
    public static String chatGPT(String prompt) {
        // The URL of the OpenAI GPT-3.5 turbo model
        String url = "https://api.openai.com/v1/chat/completions";
        // The API key used to authenticate the request
        String apiKey = openai_ApiKey;
        // The model used for the request
        String model = "gpt-3.5-turbo";
        try {
            // Create a URL object with the API URL
            URL obj = new URL(url);
            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            // Set the request method to POST
            connection.setRequestMethod("POST");
            // Set the Authorization and Content-Type request headers
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // Create the body of the request
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            // Enable output for the connection
            connection.setDoOutput(true);
            // Write the body to the output stream of the connection
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Get the status code of the response
            int statusCode = connection.getResponseCode();
            // If the status code is not 200 (HTTP_OK), throw a RuntimeException
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            // Create a BufferedReader to read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            // Use a StringBuffer to build the response
            StringBuffer response = new StringBuffer();

            // Read the response line by line
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            // Close the BufferedReader
            br.close();

            // Extract the message from the JSON response and return it
            return extractMessageFromJSONResponse(response.toString());
        } catch (IOException e) {
            // If an IOException is caught, print the stack trace and throw a RuntimeException
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * This method extracts the message from the JSON response returned by the OpenAI GPT-3 model.
     *
     * @param response the JSON response from the model
     * @return the extracted message
     */
    public static String extractMessageFromJSONResponse(String response) {
        // Find the start and end indices of the message in the response
        int start = response.indexOf("content")+ 11;
        int end = response.indexOf("\"", start);
        // Extract and return the final ready answer from gpt-3.5-turbo
        return response.substring(start, end);
    }
}


