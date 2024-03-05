import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;;

public class JACC {

    // instance variables
    boolean running = false;
    double ANS = 0;
    double phi = (1+MTH.Sqrt(5))/2;
    HashMap<String, Double[]> vars = new HashMap<>();
    public long timeTokenizeing = 0;
    public long timeFuncParsing = 0;
    public long timeElementaryParsing = 0;

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
                ANS = 0;
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

        if (str.length() == 0) {
            throw new IllegalCalculatorInput("You cannot input an empty string");
        }

        str = str.toLowerCase();

        // replaces ANS keywords with the previous answer
        str = str.replaceAll("ans", "" + this.ANS);

        // replace all constants
        str = str.replace("const.e", "" + Math.E);
        str = str.replace("const.pi", "" + Math.PI);
        str = str.replace("const.tau", "" + Math.TAU);
        str = str.replace("const.phi", "" + this.phi);

        int varLen;
        for (String i: this.vars.keySet()) {
            varLen = this.vars.get(i).length;
            if (varLen > 1) {
                for (int j = 0; j < varLen; j++) {
                    str = str.replace("var." + i + "[" + j + "]", "" + this.vars.get(i)[j]);
                }
            } else {
                str = str.replace("var." + i, "" + this.vars.get(i)[0]);
            }
            
        }
        str = str.toLowerCase();
        if (str.contains("=")) {
            variableParse(str);
        } else if (str.charAt(0) == '?') { // checks to see if it was a help command
            output = this.helpOut(str); // runs the help method
        } else {
            // checks if there is the same number of opening parenthesis and closing parenthesis.
            if (this.instacesOf(str, '(') != this.instacesOf(str, ')')) {
                throw new IllegalCalculatorInput("Incorrect number of parathesis"); 
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
                this.running = false;
                break;

            case "?quit":
                System.out.println(this.exitMessage);
                this.running = false;
                break;

            case "?stop":
                System.out.println(this.exitMessage);
                this.running = false;
                break;

            case "?end":
                System.out.println(this.exitMessage);
                this.running = false;
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
        System.out.println(exitMessage);
    }


    /**
     * parses a variable input in the calculator
     * @param str the string that is going to be parsed
     * @throws IllegalCalculatorInput if there is a illegal calculator input this is thrown
     */
    private void variableParse (String str) throws IllegalCalculatorInput {
        String[] varInf = str.split("=");
        String name = varInf[0];
        ArrayList<String> dataList = new ArrayList<>();
        String dataStr;
        try {
            dataStr = varInf[1];
        } catch (Exception e) {
            throw new IllegalCalculatorInput("No value to set variable to");
        }
        
        
        // if (dataStr.contains("{")) {
        //     for (int i = 0; i < dataStr.length(); i++) {

        //     }
        // } else {
        //     data = new Double[1];
        //     try {
        //         data[0] = Double.parseDouble(dataStr);
        //     } catch (Exception e) {
        //         data[0] = this.elementaryParse(dataStr);
        //     }
        // }
        
        // String[] dataStr = varInf[1].split(",");
        
        // for (int i = 0; i < dataStr.length; i++) {
        //     try {
        //         data[i] = Double.parseDouble(dataStr[i]);
        //     } catch (Exception e) {
        //         data[i] = this.elementaryParse(dataStr[i]);
        //     }
        // }
        
        long startTime = System.currentTimeMillis();

        int level = 0;
        int startIndex = 0;
        for (int i = 0; i < dataStr.length(); i++) {
            if (dataStr.charAt(i) == '(') { // if there is a '(' go up a level
                level++;
            } else if (dataStr.charAt(i) == ')') { // if there is a ')' go down a level
                level--;
            } else if (Character.toString(dataStr.charAt(i)).matches("[,]")) { // if there is an operator, and it is at level 0 end the current token and add it to the list of tokens
                if (level == 0) {
                    dataList.add(dataStr.substring(startIndex, i));
                    startIndex = i + 1;
                }
            }
        }

        this.timeTokenizeing += System.currentTimeMillis() - startTime;

        // add the final token
        dataList.add(dataStr.substring(startIndex, dataStr.length())); 
        
        for (int i = dataList.size() - 1; i >= 0; i--) {
            if (dataList.get(i).equals("")) {
                dataList.remove(i);
            }
            
        }

        Double[] data = new Double[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            try {
                data[i] = Double.parseDouble(dataList.get(i));
            } catch (Exception e) {
                data[i] = this.elementaryParse(dataList.get(i));
            }
            if (Double.isNaN(data[i]) || Double.isInfinite(data[i])) {
                data[i] = 0d;
            }
        }

        this.vars.put(name, data);
        System.out.print("var."+ name +" has been set to ");
        this.printArr(data);
    }

    private void printArr(Double[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
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

        long startTime = System.currentTimeMillis();
        // the start index of the currrent token
        int startIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            boolean prevCharCheck = false;
            boolean charCheck = false;
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/' || str.charAt(i) == '^' || str.charAt(i) == '%' ||str.charAt(i) == '!') {
                charCheck = true;
            }
            try {
                // prevCharCheck = Character.toString(str.charAt(i-1)).matches("[\\+\\-\\*\\/\\^\\%e]");
                if (str.charAt(i-1) == '+' || str.charAt(i-1) == '-' || str.charAt(i-1) == '*' || str.charAt(i-1) == '/' || str.charAt(i-1) == '^' || str.charAt(i-1) == '%' ||str.charAt(i-1) == 'e') {
                    prevCharCheck = true;
                }
            } catch (Exception e) {
                prevCharCheck = true;
            }
            if (str.charAt(i) == '(') { // if there is a '(' go up a level
                level++;
            } else if (str.charAt(i) == ')') { // if there is a ')' go down a level
                level--;
            } else if (charCheck && !prevCharCheck) { // if there is an operator, and it is at level 0 end the current token and add it to the list of tokens
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

        this.timeTokenizeing += System.currentTimeMillis() - startTime;

        // calculates the factorials

        startTime = System.currentTimeMillis();

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
            if (operators.lastIndexOf('^') != -1) {
                // get the two tokens at the index, and after to do the operation on
                str1 = tokens.get(operators.lastIndexOf('^'));
                str2 = tokens.remove(operators.lastIndexOf('^') + 1);

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
                tokens.set(operators.lastIndexOf('^'), "" + Math.pow(value1, value2));
                // removes the extra token
                operators.remove(operators.lastIndexOf('^'));
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
                
                double divVal = (value1 / value2);
                if  (value2 == 0) {
                    // divVal = 0;
                }
                tokens.set(firstDiv, "" + divVal);
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
        
        this.timeElementaryParsing += System.currentTimeMillis() - startTime;

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

        ArrayList<String> splitdataList = new ArrayList<String>();

        // tokenizer:

        // the level of the function, starts at 0, increases at '(' decreases at ')'
        int level = 0;

        long startTime = System.currentTimeMillis();

        // the start index of the currrent token
        int startIndex = 0;

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '(') { // if there is a '(' go up a level
                level++;
            } else if (data.charAt(i) == ')') { // if there is a ')' go down a level
                level--;
            } else if (data.charAt(i) == ',') { // if there is a , at level 0 start a new subterm
                if (level == 0) {
                    splitdataList.add(data.substring(startIndex, i));
                    startIndex = i + 1;
                }
            }
        }

        // add the last piece of data
        splitdataList.add(data.substring(startIndex, data.length()));

        for (int i = splitdataList.size() - 1; i >= 0; i--) {
            if (splitdataList.get(i).equals("")) {
                splitdataList.remove(i);
            }
            
        }
        
        String[] splitData = new String[splitdataList.size()];
        for(int i = 0; i < splitData.length; i++) {
            splitData[i] = splitdataList.get(i);
        }

        timeTokenizeing += System.currentTimeMillis() - startTime;

        // replace all the instaces of subterms in the data with "ST"
        // splitdataList.toArray(splitData);
        // for (int i = 0; i < splitData.length; i++) {
        //     try {
        //         Double.parseDouble(splitData[i]);
        //     } catch (Exception e) {
        //         subterm.add(splitData[i]);
        //         splitData[i] = "ST";
        //     }
        // }

        // variable name that are used in multiple cases
        double successPercent;
        int trialCount;
        String trialCountStr;
        String successPercentStr;
        String upperBoundStr;
        String lowerBoundStr;
        int lowerBound;
        int upperBound;
        String varName;

        startTime = System.currentTimeMillis();

        // switch with all of the operators
        switch (operator) {
            case "binomialpdf": // if the operator was "binomialpdf"
                // checks to see if the number of terms is correct
                if (splitData.length != 3) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for binomialPDF, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for binomialPDF, got none" + " in term " + term); 
                }

                // gets the string for the first term
                successPercentStr = splitData[0];
                try {
                    // if it not convert it to a double
                    successPercent = Double.parseDouble(successPercentStr);
                    
                } catch (Exception e) {
                    // if it is a subterm parse it again
                    successPercent = this.elementaryParse(successPercentStr);
                }
                
                // get the string for the second term
                trialCountStr = splitData[1];
                try {
                    // if not convert it to an int
                    trialCount = (int)Double.parseDouble(trialCountStr);
                    
                } catch (Exception e) {
                    // if it is a subterm parse it again
                    trialCount = (int)this.elementaryParse(trialCountStr);
                }

                // get the string for the third term
                String successCountStr = splitData[2];
                int successCount = 0;
                try {
                    // if not convert it to an int
                    successCount = (int)Double.parseDouble(successCountStr);
                    
                } catch (Exception e) {
                    // if it is a subterm parse it again
                    successCount = (int)this.elementaryParse(successCountStr);
                }
                
                // set output to the value calculated with the values
                output = MTH.binomialPDF(successPercent, trialCount, successCount);
                break;
            case "binomialcdf":
                if (splitData.length != 4) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 4 for binomialCDF, got " + splitData.length + " in term " + term);
                }else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 4 for binomialCDF, got none" + " in term " + term); 
                }

                successPercentStr = splitData[0];
                try {
                    successPercent = Double.parseDouble(successPercentStr);
                } catch (Exception e) {
                    successPercent = this.elementaryParse(successPercentStr);
                }

                trialCountStr = splitData[1];
                try {
                    trialCount = (int)Double.parseDouble(trialCountStr);
                    
                } catch (Exception e) {
                    trialCount = (int)this.elementaryParse(trialCountStr);

                }

                lowerBoundStr = splitData[2];
                lowerBound = 0;
                try {
                    lowerBound = (int)Double.parseDouble(lowerBoundStr);
                } catch (Exception e) {
                    lowerBound = (int)this.elementaryParse(lowerBoundStr);
                }

                upperBoundStr = splitData[3];
                upperBound = 0;
                try {
                    upperBound = (int)Double.parseDouble(upperBoundStr);
                } catch (Exception e) {
                    upperBound = (int)this.elementaryParse(upperBoundStr);
                }
                output = MTH.binomialCDF(successPercent, trialCount, lowerBound, upperBound);
                break;
            case "geometricpdf":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for geometricPDF, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for geometricPDF, got none" + " in term " + term); 
                }
                successPercentStr = splitData[0];
                try {
                    successPercent = Double.parseDouble(successPercentStr);
                } catch (Exception e) {
                    successPercent = this.elementaryParse(successPercentStr);
                }
                
                trialCountStr = splitData[1];
                try {
                    trialCount = (int)Double.parseDouble(trialCountStr);
                } catch (Exception e) {
                    trialCount = (int)this.elementaryParse(trialCountStr);    
                }
                
                output = MTH.geometricPDF(successPercent, trialCount);
                break;
            case "geometriccdf":
                if (splitData.length != 3) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for geometricCDF, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 3 for geometricCDF, got none" + " in term " + term); 
                }

                successPercentStr = splitData[0];
                try {
                    successPercent = Double.parseDouble(successPercentStr);
                } catch (Exception e) {
                    successPercent = this.elementaryParse(successPercentStr);
                }

                lowerBoundStr = splitData[1];
                lowerBound = 0;
                try {
                    lowerBound = (int)Double.parseDouble(lowerBoundStr);
                } catch (Exception e) {
                    lowerBound = (int)this.elementaryParse(lowerBoundStr);
                }

                upperBoundStr = splitData[2];
                upperBound = 0;
                try {
                    upperBound = (int)Double.parseDouble(upperBoundStr);
                } catch (Exception e) {
                    upperBound = (int)this.elementaryParse(upperBoundStr);
                }
                //int lowerBound = Integer.parseInt(splitData[2]);
                //int upperBound = Integer.parseInt(splitData[3]);
                output = MTH.geometricCDF(successPercent, lowerBound, upperBound);
                break;
            case "ncr":
                if (splitData.length != 2) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for nCr, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 2 for nCr, got none" + " in term " + term); 
                }

                String nStr = splitData[0];
                int n = 0;
                try {
                    n = (int)Double.parseDouble(nStr);
                } catch (Exception e) {
                    n = (int)this.elementaryParse(nStr);
                }

                String rStr = splitData[1];
                int r = 0;
                try {
                    r = (int)Double.parseDouble(rStr);
                } catch (Exception e) {
                    r = (int)this.elementaryParse(rStr);
                }
                output = MTH.nCr(n, r);
                break;
                
            case "sqrt":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for sqrt, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for sqrt, got none" + " in term " + term); 
                }

                String sqrtStr = splitData[0];
                double sqrt = 0;
                try {
                    sqrt = Double.parseDouble(sqrtStr);
                } catch (Exception e) {
                    sqrt = this.elementaryParse(sqrtStr);                    
                }
                output = Math.sqrt(sqrt);
                break;
            case "sin":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for sin, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for sin, got none" + " in term " + term); 
                }

                String sinStr = splitData[0];
                double sin = 0;
                try {
                    sin = Double.parseDouble(sinStr);
                } catch (Exception e) {
                    sin = this.elementaryParse(sinStr);
                }
                output = Math.sin(sin);
                break;
            case "cos":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for cos, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for cos, got none" + " in term " + term); 
                }

                String cosStr = splitData[0];
                double cos = 0;
                try {
                    cos = Double.parseDouble(cosStr);
                } catch (Exception e) {
                    cos = this.elementaryParse(cosStr);
                }
                output = Math.cos(cos);
                break;
            case "tan":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for tan, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for tan, got none" + " in term " + term); 
                }
                String tanStr = splitData[0];
                double tan = 0;
                try {
                    tan = Double.parseDouble(tanStr);
                } catch (Exception e) {
                    tan = this.elementaryParse(tanStr);
                }
                output = Math.tan(tan);
                break;
            case "log":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for log, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for log, got none" + " in term " + term); 
                }
                String logStr = splitData[0];
                double log = 0;
                try {
                    log = Double.parseDouble(logStr);
                } catch (Exception e) {
                    log = this.elementaryParse(logStr);
                }
                output = Math.log(log);
                break;   
            case "":
                output = this.elementaryParse(term);
                break;
            case "sum":
                if (splitData.length == 1) {
                    Double[] sumArr;
                    varName = splitData[0].split("[.]")[1];
                    try {
                        
                        sumArr = this.vars.get(varName); 
                    } catch (Exception e) {
                        throw new IllegalCalculatorInput("The input " + splitData[0] + " is not a valid list variable");
                    }
                    
                    if (sumArr == null) {
                        throw new IllegalCalculatorInput("The variable " + varName + " has not been initialized yet");
                    }
                    output = this.arrSum(sumArr);
                } else if (splitData.length == 4) {
                    String var = splitData[0];
                    
                    // if (!var.equals("[a-z]")) {
                    //     throw new IllegalCalculatorInput("You cannot pass in " + var + " into the variable term for the sum function");
                    // }
    
                    lowerBoundStr = splitData[1];
                    lowerBound = 0;
                    try {
                        lowerBound = (int)Double.parseDouble(lowerBoundStr);
                    } catch (Exception e) {
                        lowerBound = (int)this.elementaryParse(lowerBoundStr);
                    }
    
                    upperBoundStr = splitData[2];
                    upperBound = 0;
                    try {
                        upperBound = (int)Double.parseDouble(upperBoundStr);
                    } catch (Exception e) {
                        upperBound = (int)this.elementaryParse(upperBoundStr);
                    }
    
                    String sumFunc = splitData[3];
                    for (int i = lowerBound; i <= upperBound; i++) {
                        output += this.parseString(sumFunc.replaceAll(var, ""+i));
                    }
                } else if (splitData.length != 4){
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 4 for sum, got " + splitData.length + " in term " + term);
                }
                break;
            case "mean":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mean, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mean, got none" + " in term " + term); 
                }
                Double[] avgArr;
                varName = splitData[0].split("[.]")[1];
                try {
                    
                   avgArr = this.vars.get(varName); 
                } catch (Exception e) {
                    throw new IllegalCalculatorInput("The input " + splitData[0] + " is not a valid list variable");
                }

                if (avgArr == null) {
                    throw new IllegalCalculatorInput("The variable " + varName + " has not been initialized yet");
                }
                output = this.arrMean(avgArr);
                break;
            case "median":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for median, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for median, got none" + " in term " + term); 
                }
                Double[] meadianArr;
                varName = splitData[0].split("[.]")[1];
                try {
                    
                   meadianArr = this.vars.get(varName); 
                } catch (Exception e) {
                    throw new IllegalCalculatorInput("The input " + splitData[0] + " is not a valid list variable");
                }
                if (meadianArr == null) {
                    throw new IllegalCalculatorInput("The variable " + varName + " has not been initialized yet");
                }
                Arrays.sort(meadianArr);
                if (meadianArr.length % 2 == 0) {
                    output = (meadianArr[(meadianArr.length / 2) - 1] + meadianArr[((meadianArr.length / 2) + 1) - 1])/2;
                } else {
                    output = meadianArr[(int)((meadianArr.length / 2) + .5)];
                }
                
                break;
            case "mode":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got none" + " in term " + term); 
                }
                Double[] modeArr;
                varName = splitData[0].split("[.]")[1];
                try {
                    
                   modeArr = this.vars.get(varName); 
                } catch (Exception e) {
                    throw new IllegalCalculatorInput("The input " + splitData[0] + " is not a valid list variable");
                }
                
                if (modeArr == null) {
                    throw new IllegalCalculatorInput("The variable " + varName + " has not been initialized yet");
                }
                double maxValue = 0;
                int maxCount = 0;
                int count = 0;
                for (int i = 0; i < modeArr.length; i++) {
                    count = 0;
                    for (int j = 0; j < modeArr.length; j++) {
                        if (modeArr[i] == modeArr[j]) {
                            count++;
                        }
                    }

                    if (count > maxCount) {
                        maxCount = count;
                        maxValue = modeArr[i];
                    }
                }
                output = maxValue;     
                break;
            case "length":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got none" + " in term " + term); 
                }
                Double[] lenArr;
                varName = splitData[0].split("[.]")[1];
                try {
                    
                    lenArr = this.vars.get(varName); 
                } catch (Exception e) {
                    throw new IllegalCalculatorInput("The input " + splitData[0] + " is not a valid list variable");
                }
                
                if (lenArr == null) {
                    throw new IllegalCalculatorInput("The variable " + varName + " has not been initialized yet");
                }
                output = lenArr.length;     
                break;
            case "sd":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got none" + " in term " + term); 
                }
                Double[] sdArr;
                varName = splitData[0].split("[.]")[1];
                try {
                    
                    sdArr = this.vars.get(varName); 
                } catch (Exception e) {
                    throw new IllegalCalculatorInput("The input " + splitData[0] + " is not a valid list variable");
                }
                
                if (sdArr == null) {
                    throw new IllegalCalculatorInput("The variable " + varName + " has not been initialized yet");
                }
                
                output = this.arrSD(sdArr);  
                break;  
            case "varience":
                if (splitData.length != 1) {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got " + splitData.length + " in term " + term);
                } else if (data == "") {
                    throw new IllegalCalculatorInput("Incorrect number of inputs, expected 1 for mode, got none" + " in term " + term); 
                }
                Double[] varArr;
                varName = splitData[0].split("[.]")[1];
                try {
                    
                    varArr = this.vars.get(varName); 
                } catch (Exception e) {
                    throw new IllegalCalculatorInput("The input " + splitData[0] + " is not a valid list variable");
                }
                
                if (varArr == null) {
                    throw new IllegalCalculatorInput("The variable " + varName + " has not been initialized yet");
                }
                output = this.arrVarience(varArr);     
                break;
            
            default:
                throw new IllegalCalculatorInput("Unkown input: " + term);
                
        }
        
        this.timeFuncParsing += System.currentTimeMillis() - startTime;

        return(output);
    }

    private double arrSum(Double[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        } 
        return(sum);
    }

    private double arrMean(Double[] arr) {
        return(this.arrSum(arr)/arr.length);
    }

    private double arrVarience(Double[] arr) {
        double mean = this.arrMean(arr);
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += Math.pow(arr[i] - mean,2);
        }
        return(sum/arr.length);
    }

    public double arrSD(Double[] arr) {
        return(Math.sqrt(this.arrVarience(arr)));
    }

    public String parseDerivative(String str) {
        String output = "";
        
        // tokenize into operators and terms

        return(output);
    }

    public class derivativeTerm {
        String term = "";
        double constant = 0;
        derivativeTerm var;
        derivativeTerm exponent;

        public derivativeTerm(String str) {
            
        }
        
    }
}
