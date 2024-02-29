package gr.aueb.dmst.postfixieCalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    private static final String openai_ApiKey = System.getenv("OPENAI_API_KEY");
    // "sk-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


    public boolean resultState = false;

    @FXML
    private TextArea numbersArea;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numbersArea.setText("");
        oneButton.setOnAction(this::addNumber);
        twoButton.setOnAction(this::addNumber);
        threeButton.setOnAction(this::addNumber);
        fourButton.setOnAction(this::addNumber);
        fiveButton.setOnAction(this::addNumber);
        sixButton.setOnAction(this::addNumber);
        sevenButton.setOnAction(this::addNumber);
        eightButton.setOnAction(this::addNumber);
        nineButton.setOnAction(this::addNumber);
        zeroButton.setOnAction(this::addNumber);
        plusButton.setOnAction(this::addOperator);
        minusButton.setOnAction(this::addOperator);
        multiplyButton.setOnAction(this::addOperator);
        divideButton.setOnAction(this::addOperator);
    }

    public void addNumber(ActionEvent event) {
        if (resultState) {
            clearStack();
            resultState = false;
            factTextArea.clear();
        }
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        numbersArea.setText(numbersArea.getText() + " " + buttonText + " ");

        Main.stack.push(new Element(Integer.parseInt(buttonText)));
    }

    public void addOperator(ActionEvent event) {
        if (resultState) {
            clearStack();
            resultState = false;
            factTextArea.clear();
        }
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        numbersArea.setText(numbersArea.getText() + " " + buttonText + " ");

        Main.stack.push(new Element(buttonText.charAt(0)));
    }

    public void removeFromStack() {
        if (resultState) {
            clearStack();
            resultState = false;
            return;
        }
        if (!Main.stack.isEmpty()) {
            numbersArea.setText(numbersArea.getText().substring(0, numbersArea.getText().length() - 3));
            Main.stack.pop();
        }
    }

    public void calculateResult() {
        if (Calculator.isValidPostfix(Main.stack)) {
            try {
                Double result = Calculator.calculateExpression(Main.stack);
                String resultText = result == Math.floor(result) ? Integer.toString(result.intValue()) : result.toString();
                numbersArea.setText(resultText);
                validCircle.setFill(Color.web("#36ea4e"));
                resultState = true;
                factTextArea.clear();
                factTextArea.setText(chatGPT("Tell me fun and interesting fact about number " + resultText + ", either historical, physiscs, biology, animals. It should be a bit interesting and if possible funny. Maximum 20 words answer.").replace("\\", ""));
            } catch (ArithmeticException e) {
                validCircle.setFill(Color.RED);
            }
        } else {
            validCircle.setFill(Color.RED);
        }
    }

    public void clearStack() {
        Main.stack.clear();
        numbersArea.setText("");
    }

    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = openai_ApiKey;
        String model = "gpt-3.5-turbo";
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }
}


