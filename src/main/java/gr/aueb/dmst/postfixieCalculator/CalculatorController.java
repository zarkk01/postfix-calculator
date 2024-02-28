package gr.aueb.dmst.postfixieCalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

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
    private Button removeButton;
    @FXML
    private Button resultButton;
    @FXML
    private Button clearButton;
    @FXML
    private Circle validCircle;

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
        }
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        numbersArea.setText(numbersArea.getText() + " " + buttonText + " ");

        Main.stack.push(new Element(buttonText.charAt(0)));
    }

    public void removeFromStack() {
        if (!Main.stack.isEmpty()) {
            numbersArea.setText(numbersArea.getText().substring(0, numbersArea.getText().length() - 3));
            Main.stack.pop();
        }
    }

    public void calculateResult() {
        if (Calculator.isValidPostfix(Main.stack)) {
            try {
                Double result = Calculator.calculateExpression(Main.stack);
                numbersArea.setText(result.toString());
                validCircle.setFill(javafx.scene.paint.Color.GREEN);
                resultState = true;
            } catch (ArithmeticException e) {
                validCircle.setFill(javafx.scene.paint.Color.RED);
            }
        } else {
            validCircle.setFill(javafx.scene.paint.Color.RED);
        }
    }

    public void clearStack() {
        Main.stack.clear();
        numbersArea.setText("");
    }
}


