package gr.aueb.dmst.postfixieCalculator;


/**
 * The Element class represents an element in a postfix expression.
 * An element can be either a number or an operator. It is used because the
 * stack in the Main class can hold both numbers and operators, and we need something to
 * represent both of them.
 */
public class Element {
    private Integer number;
    private Character operator;

    /**
     * Constructor for creating a number element.
     * This constructor is used when the element is a number.
     *
     * @param number the number value of the element
     */
    public Element(Integer number) {
        this.number = number;
        this.operator = null; // This is a number element, so operator is set to null
    }

    /**
     * Constructor for creating an operator element.
     * This constructor is used when the element is an operator.
     *
     * @param operator the operator value of the element
     */
    public Element(Character operator) {
        this.number = null; // This is an operator element, so number is set to null
        this.operator = operator;
    }

    /**
     * Checks if the element is a number.
     *
     * @return true if the element is a number, false otherwise
     */
    public boolean isNumber() {
        return number != null;
    }

    /**
     * Returns the number value of the element.
     *
     * @return the number value of the element, or null if the element is an operator
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Returns the operator value of the element.
     *
     * @return the operator value of the element, or null if the element is a number
     */
    public Character getOperator() {
        return operator;
    }
}