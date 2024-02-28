package gr.aueb.dmst.postfixieCalculator;

public class Element {
    private Integer number;
    private Character operator;

    public Element(Integer number) {
        this.number = number;
        this.operator = null;
    }

    public Element(Character operator) {
        this.number = null;
        this.operator = operator;
    }

    public boolean isNumber() {
        return number != null;
    }

    public Integer getNumber() {
        return number;
    }

    public Character getOperator() {
        return operator;
    }
}