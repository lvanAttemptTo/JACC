import java.util.random.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class JACCTest {

    JACC jacc = new JACC();
    boolean corr = true;
    HashMap<String, Double> vars = new HashMap<>();
    int divByZeros = 0;

    public JACCTest() {

    }

    public String[] functionTest(int[] allowedFunc, boolean root) throws IllegalCalculatorInput {
        String[] output = new String[3];
        Random r = new Random();
        int func = allowedFunc[r.nextInt(0, allowedFunc.length)];

        
        
        
        String testString = "";
        double corrAns = 0;

        double successChance = r.nextDouble(0,1);
        int trialCount;
        int successCount;
        int upperBound;
        int lowerBound;
        
        
        int[] allowedFuncForProb = {0,1,2,3};

        int[] allowedFuncForInt = {4};

        int[] allFunc = {0,1,2,3,4,5};
        
        String[] recursInf = new String[3];
        switch (func) {
            case 0:

                trialCount = r.nextInt(0, 10);
                successCount = r.nextInt(0, 10);
                testString += "binomialPDF(";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForProb, false);
                    testString += recursInf[2];
                    successChance = Double.parseDouble(recursInf[0]);

                } else {
                    testString += successChance;
                }
                
                
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    trialCount = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += trialCount;
                }
                
                testString += ",";
                if (r.nextInt(0,4) < 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    successCount = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += successCount;
                }
                testString += ")";
                corrAns = MTH.binomialPDF(successChance, trialCount, successCount);
                break;

            case 1:
            trialCount = r.nextInt(0, 100);
            upperBound = r.nextInt(0, 100);
            lowerBound =  upperBound - r.nextInt(1, 100);
            lowerBound = (lowerBound < 0) ? 0:lowerBound;
                testString += "binomialCDF(";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForProb, false);
                    testString += recursInf[2];
                    successChance = Double.parseDouble(recursInf[0]);

                } else {
                    testString += successChance;
                }
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    trialCount = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += trialCount;
                }
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    lowerBound = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += lowerBound;
                }
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    upperBound = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += upperBound;
                }
                testString += ")";
                corrAns = MTH.binomialCDF(successChance, trialCount, lowerBound, upperBound);
                break;

            case 2:
                trialCount = r.nextInt(0, 10);
                testString += "geometricPDF(";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForProb, false);
                    testString += recursInf[2];
                    successChance = Double.parseDouble(recursInf[0]);

                } else {
                    testString += successChance;
                }
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    trialCount = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += trialCount;
                }
                testString += ")";
                corrAns = MTH.geometricPDF(successChance, trialCount);
                break;

            case 3:
                upperBound = r.nextInt(0, 25);
                lowerBound =  upperBound - r.nextInt(1, 25);
                lowerBound = (lowerBound < 0) ? 0:lowerBound;
                testString += "geometricCDF(";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForProb, false);
                    testString += recursInf[2];
                    successChance = Double.parseDouble(recursInf[0]);

                } else {
                    testString += successChance;
                }
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    lowerBound = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += lowerBound;
                }
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    upperBound = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += upperBound;
                }
                testString += ")";
                corrAns = MTH.geometricCDF(successChance, lowerBound, upperBound);
                break;
            case 4:
                int n = 0;
                int R = 0;
                if (root) {
                    n = r.nextInt(0, 100);
                    R =  n - r.nextInt(1, 100);
                } else {
                    n = r.nextInt(0, 5);
                    R =  n - r.nextInt(1, 5);
                }
                
                R = (R < 1) ? 1:R;
                testString += "nCr(";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    n = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += n;
                }
                testString += ",";
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allowedFuncForInt, false);
                    testString += recursInf[2];
                    R = (int)Double.parseDouble(recursInf[0]);

                } else {
                    testString += R;
                }
                testString += ")";
                corrAns = MTH.nCr(n, R);
                break;
            case 5:
                testString += "sqrt(";
                double sqrtVal = r.nextDouble(0,100);
                if (r.nextInt(0,4) == 0) {
                    recursInf = this.functionTest(allFunc, false);
                    testString += recursInf[2];
                    sqrtVal = Double.parseDouble(recursInf[0]);

                } else {
                    testString += sqrtVal;
                }
                testString += ")";
                corrAns = Math.sqrt(sqrtVal);
                break;
        
            default:
                break;
        }
        double parseANS = 0;
        if (root) {
            try {
                parseANS = this.jacc.parseString(testString);
            } catch (IllegalCalculatorInput e) {
                throw new IllegalCalculatorInput(testString, e);
            }
            
        }

        output[0] = "" + corrAns;
        output[1] = "" + parseANS;
        output[2] = testString;
        if (corrAns == parseANS || (Double.isNaN(corrAns) && Double.isNaN(parseANS))) {
            this.corr = true;
        } else {
            this.corr = false;
        }


        return(output);
    }

    public String[] elementaryTest(boolean root) throws IllegalCalculatorInput {
        String[] output = new String[3];

        Random r = new Random();

        String testString = "(";

        String parseANS = "1";
        
        String corrAns = "";

        ArrayList<String> corrTermsList;
        // either make a elemetary string or a function string
        if (r.nextDouble(0, 1) <= .5) {

            // operation, term, and correct terms arrays
            String[] ops = new String[r.nextInt(1,10)];
            // String[] terms = new String[ops.length + 1];
            String[] correctTerms = new String[ops.length + 1];
            String[] possibleOps = {"+", "-", "*", "/", "%", "^"};
            for (int i = 0; i < ops.length; i++) {
                ops[i] = possibleOps[r.nextInt(0, possibleOps.length)];
            }

            double randNum;
            double num = 0;
            ArrayList<Integer> factorials = new ArrayList<>();
            for (int i = 0; i < ops.length + 1; i++) {

                // make it a number, elementary string, or a function string
                randNum = r.nextDouble(0,1);
                if (randNum <= .8) {
                    
                    if (randNum < .1) {
                        num = r.nextDouble(0,10);
                        factorials.add(i);
                        if (i < ops.length) {
                            testString += num + "!" + ops[i];
                        } else {
                            testString += num + "!";
                        }
                    } else {
                        num = r.nextDouble(-100,100);
                        if (i < ops.length) {
                            testString += num + ops[i];
                        } else {
                            testString += num;
                        }
                    }
                    
                    
                    correctTerms[i] = "" + num;

                    // if (randNum < .25) {
                    //     factorial = true;
                    // }
                } else if (randNum <= .9 && this.vars.size() > 0) {
                    Object[] keys = this.vars.keySet().toArray();
                    int randInt = r.nextInt(0, keys.length);
                    String var = "" + keys[randInt];
                    if (randNum < .1) {
                        
                        
                        factorials.add(i);
                        if (i < ops.length) {
                            testString += "var." + var + "!" + ops[i];
                        } else {
                            testString += "var." + var + "!";
                        }
                    } else {
                        
                        if (i < ops.length) {
                            testString += "var." + var + ops[i];
                        } else {
                            testString += "var." + var;
                        }
                    }
                    
                    
                    correctTerms[i] = "" + this.vars.get(var);

                    // if (randNum < .25) {
                    //     factorial = true;
                    // }
                } else {

                    String[] inf = this.elementaryTest(false);
                    if (r.nextDouble(0,1) < .1) {
                        factorials.add(i);
                        if (i < ops.length) {
                            testString += inf[2] + "!" + ops[i];
                        } else {
                            testString += inf[2] + "!";
                        }
                    } else {
                        if (i < ops.length) {
                            testString += inf[2] + ops[i];
                        } else {
                            testString += inf[2];
                        }
                    }
                    

                    correctTerms[i] = inf[0];
                }
            }

            testString += ")";



            corrTermsList = new ArrayList<String>(Arrays.asList(correctTerms));
            ArrayList<Character> operators = new ArrayList<>();
            for (String i:ops) {
                operators.add(i.charAt(0));
            }

            for (int i:factorials) {
                corrTermsList.set(i, "" + MTH.factorial((int)Double.parseDouble(corrTermsList.get(i))));
            }



            // calc corr anns 
            
            char op;
            double value1;
            double value2;
            String str1;
            String str2;
            // System.out.println(testString);
            // System.out.println(corrTermsList);
            // runs until there is only one token left
            while (corrTermsList.size() > 1) {

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
                    // get the two corrTermsList at the index, and after to do the operation on
                    str1 = corrTermsList.get(operators.lastIndexOf('^'));
                    str2 = corrTermsList.remove(operators.lastIndexOf('^') + 1);

                    // checks if it need to parse again for both and sets the values to the needed values

                    value1 = Double.parseDouble(str1);


                    value2 = Double.parseDouble(str2);

                    
                    // sets the token at the index to value1 ^ value2
                    corrTermsList.set(operators.lastIndexOf('^'), "" + Math.pow(value1, value2));
                    // removes the extra token
                    operators.remove(operators.lastIndexOf('^'));
                } else if (pemdasOp == '*') { // if multiplication is next
                    // repeats the same thing as power with multiplication

                    str1 = corrTermsList.get(firstMult);
                    str2 = corrTermsList.remove(firstMult + 1);
                    

                    value1 = Double.parseDouble(str1);
                    

                    value2 = Double.parseDouble(str2);
                    
                    
                    corrTermsList.set(firstMult, "" + (value1 * value2));
                    operators.remove(firstMult);
                } else if (pemdasOp == '/') { // if division is next
                    // repeats the same thing again

                    str1 = corrTermsList.get(firstDiv);
                    
                    str2 = corrTermsList.remove(firstDiv + 1);
                   
                    

                    value1 = Double.parseDouble(str1);


                    value2 = Double.parseDouble(str2);
                    
                    double divVal = (value1 / value2);
                    if  (Double.isNaN(divVal)) {
                        this.divByZeros++;
                    }
                    corrTermsList.set(firstDiv, "" + divVal);
                    operators.remove(firstDiv);
                } else if (pemdasOp == '%') { // if modulus is next
                    // repeats the same thing again

                    str1 = corrTermsList.get(firstMod);
                    str2 = corrTermsList.remove(firstMod + 1);
                    

                    value1 = Double.parseDouble(str1);


                    value2 = Double.parseDouble(str2);

                    
                    corrTermsList.set(firstMod, "" + (value1 % value2));
                    operators.remove(firstMod);
                } else { // if there is no need for pemdas go from right to left
                    // gets operator and values and removes the unneeded token
                    op = operators.remove(0);
                    str1 = corrTermsList.get(0);
                    str2 = corrTermsList.remove(1);
                    
                    // checks if they need to be parsed again

                    value1 = Double.parseDouble(str1);


                    value2 = Double.parseDouble(str2);

                    
                    // checks what the operation is
                    if (op == '+') {
                        corrTermsList.set(0, "" + (value1 + value2));
                    } else if (op == '-') {
                        corrTermsList.set(0, "" + (value1 - value2));
                    }
                }
            }
            corrAns = corrTermsList.get(0);
        } else {
            int[] allFunc = {0,1,2,3,4,5};
            String[] funcTestInf = this.functionTest(allFunc, false);
            testString = funcTestInf[2];
            corrAns = funcTestInf[0];
        }

        
        if (root) {
            try {
                parseANS = "" + this.jacc.parseString(testString);
            } catch (IllegalCalculatorInput e) {
                throw new IllegalCalculatorInput(testString, e);
            }
            if ((Double.parseDouble(corrAns) == Double.parseDouble(parseANS)) || (Double.isNaN(Double.parseDouble(corrAns)) && Double.isNaN(Double.parseDouble(parseANS)))) {
                this.corr = true;
            } else {
                this.corr = false;
            }
        }

        output[0] = "" + corrAns;
        output[1] = "" + parseANS;
        output[2] = testString;

        

        return(output);
    }

    public String[] variableTest(boolean root) throws IllegalCalculatorInput {
        String[] output = new String[4];
        Random r = new Random();
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
    
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)(r.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        String varName = buffer.toString();
        String corrAns = "";
        double parseAns = 0;
        String testString1 = "";

        testString1 += varName;
        String[] elemInf1 = this.elementaryTest(false);
        double varVal = Double.parseDouble(elemInf1[0]);
        if (Double.isNaN(varVal) || Double.isInfinite(varVal)) {
            varVal = 0;
        }
        this.vars.put(varName, varVal);
        testString1 += "=";
        testString1 += elemInf1[2];
        

        this.jacc.parseString(testString1);
        String[] elemInf2 = this.elementaryTest(false);
        String testString2 = elemInf2[2];
        

        parseAns = this.jacc.parseString(testString2);
        corrAns = elemInf2[0];
        if (Double.parseDouble(corrAns) == parseAns || (Double.isNaN(Double.parseDouble(corrAns)) && Double.isNaN(parseAns))) {
            this.corr = true;
        } else {
            this.corr = false;
        }

        output[3] = testString1;
        output[2] = testString2;
        output[1] = "" + parseAns;
        output[0] = corrAns;
        return(output);
    }
}