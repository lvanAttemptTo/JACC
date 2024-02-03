import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;;

public class JACC {

    boolean running = false;
    double ANS = 0;

    // help messages that can printed if the user asks for them

    // the welcome message when the calculator is started
    String welcome = "Welcome to JACC (Java Advanced Console Calculator). You can input elementary mathematical functions (+-*/%^!) normally, and can use functions" + 
                     "to do more complicated math. To get this message again use '?help', and to get the list of functions use '?help.functions'. If you want to exit you can type '?exit";
    
    // the message that give the user a list of the functions
    String functionHelp = "Here is the list of functions currently in JACC, if you want more information on a specific function use ?help.functions.functionName.\n" +
                          "binomialPDF(successChance, trialCount, successCount)\n" +
                          "binomialCDF(successChance, trialCount, lowerBound, upperBound)\n" +
                          "geometricPDF(successChance, trialNumber)\n" +
                          "geometricCDF(successChance, lowerBound, upperBound)\n" +
                          "nCr(n, r)\n" +
                          "sqrt(value)\n" +
                          "sum(variable, lowerBound, upperBound, function)";

    // the message to help the user use the binomialPDF function
    String binomialPDFHelp = "bionomialPDF(successChance, trialCount, successCount): this function calculates the odds of having successCount trials succeed out of trialCount trials if there is a successChance chance of succeeding. " +
                             "It returns a value between 0-1. successChance takes a value from 0-1, trialCount takes any positive integer, and successCount takes any positive integer less than trialCount.";

    // the message to help the user use the binomialCDF function
    String binomialCDFHelp = "binomialCDF(successChance, trialCount, lowerBound, upperBound): this functions calculates the odds of having a success count from lowerBound to upperBound if there is trialCount trials, and there is a successChance chance of succeeding. " + 
                             "It returns a value between 0-1. successChance takes a value from 0-1, trial count takes any positive integer, lowerBound takes any positive integer less than upperBound, and upperBound takes any positve integer less than trialCount.";

    // the message to help the user use the geometricPDF function
    String geometricPDFHelp = "geometricPDF(successChance, trialNumber): this function calculates the odds of the first success being on trial number trialNumber, if there is a successChance chance of a success. It returns a value between 1-0. Success chance takes a value between 1-0, and trialNumber takes any positive integer.";

    // the message to help the user use the geometricCDF function
    String geometricCDFHelp = "geometricCDF(successChance, lowerBound, upperBound): this function calculates the odds of the odds of the first success occuring from lowerBound to upperBound, if there is a successChance chance of success. It returns a value from 0-1. " +
                              "successChance takes a value from 0-1, lowerBound takes a positive integer that is less than upperBound, and upperBound takes any positive integer.";

    // the message to help the user use the nCr function
    String nCrHelp = "nCr(n, r): this function calculates n Choose r, which is the amount of unique combintaions r things if there is a total number of n things. It returns a positive integer. n can be any positive integer, and r can be any positive integer that is less than n.";

    // the message to help the user use the sqrt function
    String sqrtHelp = "sqrt(value): this function returns the square root of value. It returns any number greater or equal to 0. value can be any number greater or equal to 0.";

    // the message to help the user use the sum function
    String sumHelp = "sum(variable, lowerBound, upperBound, function): this function calculates the sum of all values of the function if the variable replaced with every number from lowerBound to upperBound. It returns any number. variable can be any string besides the name of any function, lowerBound takes a positive integer that is less than upperBound, and upperBound takes any positive integer. function can be any function using the other functions and elemetary math.";

    // exit message that is displayed when the calculator is stopped
    String exitMessage = "Thank you for using JACC.";

    /**
     * Counts the instances of the char char in the string str
     * @param str string to be checked
     * @param chr the char to check for
     * @return the number of occurences
     */
    private int instacesOf(String str, char chr) {
        int output = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == chr) {
                output++;
            }
        }
        return(output);
    }

    /**
     * Start the calculator object
     * @throws IllegalCalculatorInput there was an incrorrect input into the calculator
     * @throws IOException 
     */
    public void startCalculator() throws IllegalCalculatorInput, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        this.running = true;

        // introduction text
        System.out.println(this.welcome);

        // starts the read parse write cycle
        while (this.running) {
            // Reading data using readLine
            String input = reader.readLine();

            // sets ANS to the result of the parse
            ANS = this.parseString(input);
            
            // if the input was a help command it returns NaN, which sets ANS back to 0
            if (Double.isNaN(ANS)) {
                ANS = 0;
            } else if (Double.isInfinite(ANS)) { // if an exit was called it returns positive infinity
                this.stopCalculator();
            } else { // otherwise it was a normal function call
                System.out.println(ANS); // print the answer
            }
            
          }
    }

    /**
     * parses the string and returns a double with the answer
     * @param str the string to be parsed
     * @return returns the answer, unless it was a help command or a exit command. If it was a help command then it returns NaN, if it was an exit then it returns positive infinity.
     * @throws IllegalCalculatorInput there was an incorrect input into the calculator
     */
    public double parseString(String str) throws IllegalCalculatorInput {
        double output = Double.NaN;
        
        // cleans up the string
        str = str.replace(" ", "");
        str = str.toLowerCase();

        // replaces ANS keywords with the previous answer
        str = str.replaceAll("ans", "" + this.ANS);
        
        // checks to see if it was a help command
        if (str.charAt(0) == '?') {
            output = this.helpOut(str); // runs the help method
        } else {
            // checks if there is the same number of opening parenthesis and closing parenthesis.
            if (this.instacesOf(str, '(') != this.instacesOf(str, ')')) {
                throw new IllegalCalculatorInput("Incorrect number of parathesis"); 
            }
            if (str.contains("st")) {
                throw new IllegalCalculatorInput("You cannot use 'st'"); 
            }

            // runs the elementary notation parser on str and sets output to the value returned
            output = elementaryParse(str);
        }

        return(output);
            
    }

    /**
     * prints the help messages for the different functions based on the inputted command
     * @param tokens the tokenized version of the command
     * @param str the original command string
     * @return a double with value NaN
     */
    private double specFuncHelp(ArrayList<String> tokens, String str) {
        double output = Double.NaN;

        //check the term at the second index in the command
        switch (tokens.get(2)) {
            case "binomialpdf":
                System.out.println(this.binomialPDFHelp);
                break;

            case "binomialcdf":
                System.out.println(this.binomialCDFHelp);
                break;

            case "geometricpdf":
                System.out.println(this.geometricPDFHelp);
                break;

            case "geometriccdf":
                System.out.println(this.geometricCDFHelp);
                break;

            case "ncr":
                System.out.println(this.nCrHelp);
                break;

            case "sqrt":
                System.out.println(this.geometricCDFHelp);
                break;

            case "sum":
                System.out.println(this.sumHelp);
                break;

            default:
                System.out.println("unreconised term " + tokens.get(2) + " in " + str);
                break;
        }
        return(output);
    }

    /**
     * Get the list of functions, or call specFuncHelp to get specific function help
     * @param tokens the tokenized version of the command
     * @param str the original command string
     * @return a double with value NaN
     */
    private double funcHelp(ArrayList<String> tokens, String str) {
        double output = Double.NaN;

        //check the term at the first index in the command
        switch (tokens.get(1)) {
            case "functions":
                // if it is the last term print out the list of functions, otherwise call specFuncHelp
                if (tokens.size() == 2) {
                    System.out.println(this.functionHelp);
                } else {
                    output = this.specFuncHelp(tokens, str);
                }
                break;

            default:
                System.out.println("unreconised term " + tokens.get(1) + " in " + str);
                break;
        
        }
        return(output);
    }

    /**
     * general method for printing out help, or exiting the calculator
     * @param str the help command
     * @return NaN if it was a help command, or positive infinity if it was an exit command.
     */
    private double helpOut(String str) {
        double output = Double.NaN;
        
        // tokenize the command
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList((str.split("[\\.]"))));

        // checks the first term
        switch (tokens.get(0)) {
            case "?help":
                if (tokens.size() == 1) {
                    System.out.println(this.welcome);
                } else {
                    output = this.funcHelp(tokens, str);
                }
                break;
        
            case "?exit":
                System.out.println(this.exitMessage);
                output = Double.POSITIVE_INFINITY;
                break;

            case "?quit":
                System.out.println(this.exitMessage);
                output = Double.POSITIVE_INFINITY;
                break;

            case "?stop":
                System.out.println(this.exitMessage);
                output = Double.POSITIVE_INFINITY;
                break;

            case "?end":
                System.out.println(this.exitMessage);
                output = Double.POSITIVE_INFINITY;
                break;
            default:
                System.out.println("Unreconised term " + tokens.get(0) + " in " + str);
                break;
        }

        return(output);
    }

    /**
     * Currently broken, but is meant to stop the calculator
     */
    public void stopCalculator() {
        this.running = false;
    }

    /**
     * parses a equation in elementary notation (+-*%/^!) and returns the answer, or will recursively call functionParse to deal with function notation.
     * @param str the string to be parsed
     * @return a double with the answer to the equation
     * @throws IllegalCalculatorInput there was an incrorrect input into the calculator
     */
    private double elementaryParse (String str) throws IllegalCalculatorInput {
        // an arrayList of the operators (+-*%/^!)
        ArrayList<Character> operators = new ArrayList<>();

        // checks to see if there is a '(' at the beginning and a ')' at the end and removes both
        if (str.charAt(0) == '(' && str.charAt(str.length()-1) == ')') {
            str = str.substring(1, str.length()-1);
        }

        double output = 0;

        // an Array list of tokens which are the values inputed
        ArrayList<String> tokens = new ArrayList<String>();
        
        // tokenizer:

        // the level of the function, starts at 0, increases at '(' decreases at ')'
        int level = 0;

        // the start index of the currrent token
        int startIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') { // if there is a '(' go up a level
                level++;
            } else if (str.charAt(i) == ')') { // if there is a ')' go down a level
                level--;
            } else if (Character.toString(str.charAt(i)).matches("[\\+\\-\\*\\/\\!\\^\\%]")) { // if there is an operator, and it is at level 0 end the current token and add it to the list of tokens
                if (level == 0) {
                    tokens.add(str.substring(startIndex, i));
                    operators.add(str.charAt(i));
                    startIndex = i + 1;
                }
            }
        }

        // add the final token
        tokens.add(str.substring(startIndex, str.length()));   

        // remove any empty tokens
        for (int i  = tokens.size() - 1; i >= 0; i--) {
            if (tokens.get(i).matches("")) {
                tokens.remove(i);
            }
        }

        // calculates the factorials

        String value = "";
        while (operators.contains('!')) {
            int index = operators.indexOf('!');

            // the try catch block is used to check if the token needs to be passed into functionParse or is a useable value already
            try {
                value = "" + MTH.factorial((int)Double.parseDouble(tokens.get(index)));
            } catch (Exception e) {
                value = "" + MTH.factorial((int)this.functionParse(tokens.get(index)));
            }

            // remove the ! operator and set the value to the new value
            operators.remove(index);
            tokens.set(index, value);
        }

        // calculates the math

        // variables that are used in the while loop
        char op;
        double value1;
        double value2;
        String str1;
        String str2;

        // runs until there is only one token left
        while (tokens.size() > 1) {

            // pemdas check for *, /, and %
            char pemdasOp = 'n';
            int firstMult = operators.indexOf('*');
            int firstDiv = operators.indexOf('/');
            int firstMod = operators.indexOf('%');
            for (char i : operators) {
                if ((i == '*' || i == '%' || i == '/') && pemdasOp == 'n') {
                    pemdasOp = i;
                }
            }
            
            // checks for powers
            if (operators.indexOf('^') != -1) {
                // get the two tokens at the index, and after to do the operation on
                str1 = tokens.get(operators.indexOf('^'));
                str2 = tokens.remove(operators.indexOf('^') + 1);

                // checks if it need to parse again for both and sets the values to the needed values
                try {
                    value1 = Double.parseDouble(str1);
                } catch (Exception e) {
                    value1 = this.functionParse(str1);
                }
                try {
                    value2 = Double.parseDouble(str2);
                } catch (Exception e) {
                    value2 = this.functionParse(str2);
                }
                
                // sets the token at the index to value1 ^ value2
                tokens.set(operators.indexOf('^'), "" + Math.pow(value1, value2));
                // removes the extra token
                operators.remove(operators.indexOf('^'));
            } else if (pemdasOp == '*') { // if multiplication is next
                // repeats the same thing as power with multiplication

                str1 = tokens.get(firstMult);
                str2 = tokens.remove(firstMult + 1);
                try {
                    value1 = Double.parseDouble(str1);
                } catch (Exception e) {
                    value1 = this.functionParse(str1);
                }
                try {
                    value2 = Double.parseDouble(str2);
                } catch (Exception e) {
                    value2 = this.functionParse(str2);
                }
                
                tokens.set(firstMult, "" + (value1 * value2));
                operators.remove(firstMult);
            } else if (pemdasOp == '/') { // if division is next
                // repeats the same thing again

                str1 = tokens.get(firstDiv);
                str2 = tokens.remove(firstDiv + 1);
                
                try {
                    value1 = Double.parseDouble(str1);
                } catch (Exception e) {
                    value1 = this.functionParse(str1);
                }
                try {
                    value2 = Double.parseDouble(str2);
                } catch (Exception e) {
                    value2 = this.functionParse(str2);
                }
                
                tokens.set(firstDiv, "" + (value1 / value2));
                operators.remove(firstDiv);
            } else if (pemdasOp == '%') { // if modulus is next
                // repeats the same thing again

                str1 = tokens.get(firstMod);
                str2 = tokens.remove(firstMod + 1);
                
                try {
                    value1 = Double.parseDouble(str1);
                } catch (Exception e) {
                    value1 = this.functionParse(str1);
                }
                try {
                    value2 = Double.parseDouble(str2);
                } catch (Exception e) {
                    value2 = this.functionParse(str2);
                }
                
                tokens.set(firstMod, "" + (value1 % value2));
                operators.remove(firstMod);
            } else { // if there is no need for pemdas go from right to left
                // gets operator and values and removes the unneeded token
                op = operators.remove(0);
                str1 = tokens.get(0);
                str2 = tokens.remove(1);
                
                // checks if they need to be parsed again
                try {
                    value1 = Double.parseDouble(str1);
                } catch (Exception e) {
                    value1 = this.functionParse(str1);
                }
                try {
                    value2 = Double.parseDouble(str2);
                } catch (Exception e) {
                    value2 = this.functionParse(str2);
                }
                
                // checks what the operation is
                if (op == '+') {
                    tokens.set(0, "" + (value1 + value2));
                } else if (op == '-') {
                    tokens.set(0, "" + (value1 - value2));
                }
            }
        }
        try {
            
            output = Double.parseDouble(tokens.get(0));
        } catch (Exception e) {
            output = this.functionParse(tokens.get(0));
        }
        
        return(output);
    }

    /**
     * parses equations in function notation
     * @param inputStr the string to be parsed
     * @return a double with the answer to the equation
     * @throws IllegalCalculatorInput there was an incrorrect input into the calculator
     */
    private double functionParse(String inputStr) throws IllegalCalculatorInput{
        double output = 0;
        String term = inputStr;
        
        // splits at the first '(' to get the operator
        String operator = term.split("[(]")[0];

        // a list to store the subterms
        ArrayList<String> subterm = new ArrayList<>();

        // index of which subterm to access
        int subtermIndex = 0;

        // sets data to everything to the left of the first '('
        String data = term.substring(term.indexOf('(') + 1, term.length() - 1);


        // tokenizer:

        // the level of the function, starts at 0, increases at '(' decreases at ')'
        int level = 0;

        // the start index of the currrent token
        int startIndex = 0;

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '(') { // if there is a '(' go up a level
                level++;
            } else if (data.charAt(i) == ')') { // if there is a ')' go down a level
                level--;
                // if the level goes back to 0 add the subterm
                if (level == 0) {
                    subterm.add(data.substring(startIndex, i + 1));
                }
            } else if (data.charAt(i) == ',') { // if there is a , at level 0 start a new subterm
                if (level == 0) {
                    startIndex = i + 1;
                }
            }
        }

        // replace all the instaces of subterms in the data with "ST"
        for (int i = 0; i < subterm.size(); i++) {
            data = data.replace(subterm.get(i), "ST");
        }

        // remove the final ')'
        data = data.replace(")", "");

        // variable name that are used in multiple cases
        double successPercent;
        int trialCount;
        String trialCountStr;
        String successPercentStr;
        String upperBoundStr;
        String lowerBoundStr;
        int lowerBound;
        int upperBound;
        String[] splitData = data.split("[,]");

        // switch with all of the operators
        switch (operator) {
            case "binomialpdf": // if the operator was "binomialpdf"
                // checks to see if the number of terms is correct
                if (splitData.length != 3) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for binomialPDF, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for binomialPDF, got none"); 
                }

                // gets the string for the first term
                successPercentStr = splitData[0];
                if (successPercentStr.equals("ST")) {
                    // if it is a subterm parse it again
                    successPercent = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    // if it not convert it to a double
                    successPercent = Double.parseDouble(successPercentStr);
                }
                
                // get the string for the second term
                trialCountStr = splitData[1];
                if (trialCountStr.equals("ST")) {
                    // if it is a subterm parse it again
                    trialCount = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    // if not convert it to an int
                    trialCount = (int)Double.parseDouble(trialCountStr);
                }

                // get the string for the third term
                String successCountStr = splitData[2].replace(")", "");
                int successCount = 0;
                if (successCountStr.equals("ST")) {
                    // if it is a subterm parse it again
                    successCount = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    // if not convert it to an int
                    successCount = (int)Double.parseDouble(successCountStr);
                }
                
                // set output to the value calculated with the values
                output = MTH.binomialPDF(successPercent, trialCount, successCount);
                break;
            case "binomialcdf":
                if (splitData.length != 4) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 4 for binomialCDF, got " + splitData.length);
                }else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 4 for binomialCDF, got none"); 
                }
                successPercentStr = splitData[0];
                if (successPercentStr.equals("ST")) {
                    successPercent = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    successPercent = Double.parseDouble(successPercentStr);
                }
                // successPercent = Double.parseDouble(splitData[0]);
                // trialCount = Integer.parseInt(splitData[1]);
                trialCountStr = splitData[1];
                if (trialCountStr.equals("ST")) {
                    trialCount = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    trialCount = (int)Double.parseDouble(trialCountStr);
                }

                lowerBoundStr = splitData[2];
                lowerBound = 0;
                if (lowerBoundStr.equals("ST")) {
                    lowerBound = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    lowerBound = (int)Double.parseDouble(lowerBoundStr);
                }

                upperBoundStr = splitData[3].replace(")", "");
                upperBound = 0;
                if (upperBoundStr.equals("ST")) {
                    upperBound = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    upperBound = (int)Double.parseDouble(upperBoundStr);
                }
                output = MTH.binomialCDF(successPercent, trialCount, lowerBound, upperBound);
                break;
            case "factorial":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for factorial, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for factorial, got none"); 
                }
                String facInStr = splitData[0].replace(")", "");
                int facIn = 0;
                if (facInStr.equals("ST")) {
                    facIn = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    facIn = (int)Double.parseDouble(facInStr);
                }
                output = MTH.factorial(facIn);
                break;
            case "geometricpdf":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for geometricPDF, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for geometricPDF, got none"); 
                }
                successPercentStr = splitData[0];
                if (successPercentStr.equals("ST")) {
                    successPercent = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    successPercent = Double.parseDouble(successPercentStr);
                }
                
                trialCountStr = splitData[1].replace(")", "");
                if (trialCountStr.equals("ST")) {
                    trialCount = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    trialCount = (int)Double.parseDouble(trialCountStr);
                }
                
                output = MTH.geometricPDF(successPercent, trialCount);
                break;
            case "geometriccdf":
                if (splitData.length != 3) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for geometricCDF, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for geometricCDF, got none"); 
                }
                successPercentStr = splitData[0];
                if (successPercentStr.equals("ST")) {
                    successPercent = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    successPercent = Double.parseDouble(successPercentStr);
                }

                lowerBoundStr = splitData[1];
                lowerBound = 0;
                if (lowerBoundStr.equals("ST")) {
                    lowerBound = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    lowerBound = (int)Double.parseDouble(lowerBoundStr);
                }

                upperBoundStr = splitData[2];
                upperBound = 0;
                if (upperBoundStr.equals("ST")) {
                    upperBound = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    upperBound = (int)Double.parseDouble(upperBoundStr);
                }
                //int lowerBound = Integer.parseInt(splitData[2]);
                //int upperBound = Integer.parseInt(splitData[3].replace(")", ""));
                output = MTH.geometricCDF(successPercent, lowerBound, upperBound);
                break;
            case "ncr":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for nCr, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for nCr, got none"); 
                }
                String nStr = splitData[0].replace(")", "");
                int n = 0;
                if (nStr.equals("ST")) {
                    n = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    n = (int)Double.parseDouble(nStr);
                }

                String rStr = splitData[1].replace(")", "");
                int r = 0;
                if (rStr.equals("ST")) {
                    r = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    r = (int)Double.parseDouble(rStr);
                }
                output = MTH.nCr(n, r);
                break;
                
            case "sqrt":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for sqrt, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for sqrt, got none"); 
                }
                String sqrtStr = splitData[0].replace(")", "");
                double sqrt = 0;
                if (sqrtStr.equals("ST")) {
                    sqrt = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    sqrt = Double.parseDouble(sqrtStr);
                }
                output = MTH.Sqrt(sqrt);
                break;
            case "add":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for add, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for add, got none"); 
                }
                String addStr1 = splitData[0].replace(")", "");
                double add1 = 0;
                if (addStr1.equals("ST")) {
                    add1 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    add1 = Double.parseDouble(addStr1);
                }

                String addStr2 = splitData[1].replace(")", "");
                double add2 = 0;
                if (addStr2.equals("ST")) {
                    add2 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    add2 = Double.parseDouble(addStr2);
                }
                output = add1 + add2;
                break;
            case "mult":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for mult, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for mult, got none"); 
                }
                String multStr1 = splitData[0].replace(")", "");
                double mult1 = 0;
                if (multStr1.equals("ST")) {
                    mult1 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    mult1 = Double.parseDouble(multStr1);
                }

                String multStr2 = splitData[1].replace(")", "");
                double mult2 = 0;
                if (multStr2.equals("ST")) {
                    mult2 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    mult2 = Double.parseDouble(multStr2);
                }
                output = mult1 * mult2;
                break;
            case "sub":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for sub, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for sub, got none"); 
                }
                String subStr1 = splitData[0].replace(")", "");
                double sub1 = 0;
                if (subStr1.equals("ST")) {
                    sub1 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    sub1 = Double.parseDouble(subStr1);
                }

                String subStr2 = splitData[1].replace(")", "");
                double sub2 = 0;
                if (subStr2.equals("ST")) {
                    sub2 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    sub2 = Double.parseDouble(subStr2);
                }
                output = sub1 - sub2;
                break;
            case "div":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for div, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for div, got none"); 
                }
                String divStr1 = splitData[0].replace(")", "");
                double div1 = 0;
                if (divStr1.equals("ST")) {
                    div1 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    div1 = Double.parseDouble(divStr1);
                }

                String divStr2 = splitData[1].replace(")", "");
                double div2 = 0;
                if (divStr2.equals("ST")) {
                    div2 = this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    div2 = Double.parseDouble(divStr2);
                }
                output = div1 / div2;
                break;
            case "":
                output = this.elementaryParse(term);
                break;
            case "sum":
                if (splitData.length != 4) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 4 for sum, got " + splitData.length);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 4 for sum, got none"); 
                }
                String var = splitData[0];
                if (var.equals("ST")) {
                    throw new IllegalCalculatorInput("You cannot pass in " + subterm.get(startIndex) + " into the variable term for the sum function");
                }

                lowerBoundStr = splitData[1];
                lowerBound = 0;
                if (lowerBoundStr.equals("ST")) {
                    lowerBound = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    lowerBound = (int)Double.parseDouble(lowerBoundStr);
                }

                upperBoundStr = splitData[2];
                upperBound = 0;
                if (upperBoundStr.equals("ST")) {
                    upperBound = (int)this.elementaryParse(subterm.get(subtermIndex));
                    subtermIndex++;
                } else {
                    upperBound = (int)Double.parseDouble(upperBoundStr);
                }

                String sumFunc = splitData[3];
                for (int i = lowerBound; i <= upperBound; i++) {
                    output += this.parseString(sumFunc.replaceAll(var, ""+i));
                }
                //int lowerBound = Integer.parseInt(splitData[2]);
                //int upperBound = Integer.parseInt(splitData[3].replace(")", ""));
                break;
            default:
                throw new IllegalCalculatorInput("Unkown input: " + term);
                
        }
        return(output);
    }
}
