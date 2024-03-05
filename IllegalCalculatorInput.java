public class IllegalCalculatorInput extends Exception {
    public IllegalCalculatorInput(String errorMessage) {
        super(errorMessage);
    }

    public IllegalCalculatorInput(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}