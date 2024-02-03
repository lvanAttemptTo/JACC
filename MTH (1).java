import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;



/**MATH
 * @author Luke VanDeGrift
 * @version 1
 * A basic class for doing simple math operations
 */
public class MTH {

    static Random r = new Random();


    private static void printArr(String[] arr) {
        for (String i : arr) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }


    

    public static Matrix matrixMult(Matrix matrix1, Matrix matrix2) {
        if (matrix1.columns == matrix2.rows) {
            Matrix output = new MTH.Matrix(matrix1.rows, matrix2.columns);
            for (int v = 0; v < output.rows; v++) {
                for (int h = 0; h < output.columns; h++) {

                    double[] hVec = matrix2.getVector(h, true);
                    output.set(v,h,MTH.dotProduct(matrix1.getVector(v, false), hVec));

                    System.out.println();
                }
            }
            
            return(output);
        } else {
            return(null);
        }
    }

    public static Matrix constMatrixMult(Matrix matrix, double constant) {
        Matrix output = new Matrix(matrix.matrix);
        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.columns; c++) {
                output.set(r, c, (constant * matrix.get(r, c)));
            }
        }
        return(output);
        
    }

    public static Matrix matrixAdd(Matrix matrix1, Matrix matrix2, boolean subtract) {
        if (matrix1.rows != matrix2.rows | matrix1.columns != matrix2.columns) {
            return(null);
        }
        Matrix output = new Matrix(matrix1.rows, matrix1.columns);
        double val;
        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.columns; c++) {
                if (subtract) {
                    val = matrix1.get(r, c) - matrix2.get(r, c);
                } else {
                    val = matrix1.get(r, c) + matrix2.get(r, c);
                }
                output.set(r, c, val);
            }
        }

        return(output);
    }

    public static void printVector(double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    public static double dotProduct(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length| vector1.length == 0) {
            return(0);
        }
        double output = 0;

        for (int i = 0; i < vector1.length; i++) {
            output += vector1[i] * vector2[i];
         }

        return(output);
    }

    public static double geometricCDF(double successPercent, int lowerBound, int upperBound) {
        double output = 0;

        if (successPercent < 0 | successPercent > 1 | lowerBound < 1 | upperBound < 1 | lowerBound >= upperBound) {
            return(output);
        }

        for (int i = lowerBound; i <= upperBound; i++) {
            output += MTH.geometricPDF(successPercent, i);
        }

        return(output);
    }

    public static double geometricPDF(double successPercent, int trialNumber) {
        double output = 0;
        if (successPercent < 0 | successPercent > 1 | trialNumber < 1) {
            return(output);
        }

        output = (Math.pow((1 - successPercent), (trialNumber - 1))) * (successPercent);
        
        return(output);
    }

    public static double binomialPDF(double successPercent, int trialCount, int successCount) {
        double output = 0;

        if (successCount > trialCount | trialCount <= 0 | successCount < 0 | successPercent < 0 | successPercent > 1) {
            return(output);
        }
        output = MTH.nCr(trialCount, successCount) * Math.pow(successPercent, successCount) * Math.pow((1-successPercent), (trialCount - successCount));
    
        return(output);
    }

    public static double binomialCDF(double successPercent, int trialCount, int lowerBound, int upperBound) {
        double output = 0;

        if (successPercent < 0 | successPercent > 1 | lowerBound < 0 | upperBound < 0 | lowerBound >= upperBound | upperBound > trialCount) {
            return(output);
        }

        for (int i = lowerBound; i <= upperBound; i++) {
            output += MTH.binomialPDF(successPercent, trialCount, i);
        }

        return(output);
    }
    /**
     * Returns the number of combinations for n choose r.
     * @param n the number of total items
     * @param r the number to be choosen
     * @return the total number of cobinations
     * 
     */
    public static double nCr(int n, int r) {
        double output = 0;

        output = MTH.factorial(n)/(MTH.factorial(r) * MTH.factorial(n-r));

        return(output);
    }

    public static double factorial(int value) {
        double output = 1;
        if (value >= 0) {
            for (int i = 0; i < value; i++) {
                output = output * (value - i);
            }
        } else {
            return(0);
        }
        
        return(output);
    }

    public static double Sqrt(double value) {
        if (value < 0)
        {
            return(0);
        }
        double output = 0;
        double guess = 0;
        double min = 0;
        double max = Double.MAX_VALUE;
        boolean correct = false;
        int i = 0;
        while (!correct && !(i > 1000)) {
            guess = r.nextDouble(min, max);
            if ((guess * guess) == value) {
                correct = true;
                output = guess;
            } else if ((guess * guess) < value) {
                min = guess;
            } else {
                max = guess;
            }
            i++;
        }
        
        if (!correct) {
            output = (min + max)/2;
        }
        return(output);
    }

    public static class Complex {
        double real = 0;
        double imaginary = 0;
        public Complex(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }
    }

    /**
     * @author Luke VanDeGrift
     * @version 1
     * A subclass of MTH that immplemnts matricies into java using a double[][].
     */
    public static class Matrix implements Cloneable {
        public double[][] matrix;
        int columns = 0;
        int rows = 0;
        double determinant = 0;
        double eigenValue = 0;
        public double[][] identityMatrix;

    protected Object clone() throws CloneNotSupportedException {
        
        return super.clone();
    }

        /**
         * Constuctor for a new Matrix object using a row-major 2D array of doubles
         * @param matrix a row-major 2D array to turn into an Matrix
         */
        public Matrix(double[][] matrix) {
            if (matrix.length > 0) {
                if (matrix[0].length > 0) {
                    boolean consSize = true;
                    int columns = matrix[0].length;
                    for (int i = 0; i < matrix.length; i++) {
                        if (matrix[i].length != columns) {
                            consSize = false;
                        }
                    }
                    if (consSize) {
                        this.matrix = matrix;
                        this.rows = matrix.length;
                        this.columns = columns;
                        if (this.rows == this. columns) {
                            double[][] temp = new double[this.rows][this.columns];
                            for (int r = 0; r < this.rows; r++) {
                                for (int c = 0; c < this.columns; c++) {
                                    if (r == c) {
                                        temp[r][c] = 1;
                                    } else {
                                        temp[r][c] = 0;
                                    }
                                }
                            }
                            this.identityMatrix = temp;
                        }
                        
                    }
                }
            }
            


        }

        /**
         * Constructor for a new Matrix using the number of rows and columns, creates an empty matrix.
         * @param rows the number of rows for the matrix, rows need to be greater than 0.
         * @param columns the number of columns for the matrix, columns need to be greater than 0.
         */
        public Matrix(int rows, int columns) {
            if (rows > 0 && columns > 0) {
                this.rows = rows;
                this.columns = columns;
                this.matrix = new double[rows][columns];if (this.rows == this. columns) {
                    double[][] temp = new double[this.rows][this.columns];
                    for (int r = 0; r < this.rows; r++) {
                        for (int c = 0; c < this.columns; c++) {
                            if (r == c) {
                                temp[r][c] = 1;
                            } else {
                                temp[r][c] = 0;
                            }
                        }
                    }
                }
            }
        }

        /**
         * Method to get the value at a specific row-column position in the array
         * @param row the row to get the data from, starting at 0.
         * @param column the column to get the data from, starting at 0.
         * @return a double from the position.
         */
        public double get(int row, int column) {
            double output = 0;
            if (row >= 0 && column >= 0 && row < this.rows && column < this.columns) {
                output = this.matrix[row][column];
            }
            return(output);
        }

        public void set(int row, int column, double value) {
            this.matrix[row][column] = value;
        }

        public double[] getVector(int index, boolean column) {
        double[] output;
            if (!column) {
                output = this.matrix[index];
                
            } else {
                output = new double[this.rows];
                for (int i  = 0; i < this.rows; i++) {
                    output[i] = this.matrix[i][index];
                }
            }
            return(output);
        }

        private void calculateDeterminent() {
            if (this.rows > 2 && this.columns > 2) {

                int row = 0;
                
                // subtracts the values
                double temp = 1;
                for (int r = 0; r < this.rows; r++) {
                    row = r;
                    temp = 1;
                    for (int c = 0; c < this.columns; c++) {
                        temp *= this.matrix[row][c];
                        row -= 1;
                        if (row < 0) {
                            row = this.rows - 1;
                        }
                    }
                    this.determinant -= temp;
                }

                // adds the values
                for (int r = 0; r < this.rows; r++) {
                    row = r;
                    temp = 1;
                    for (int c = 0; c < this.columns; c++) {
                        temp *= this.matrix[row][c];
                        row += 1;
                        if (!(row < this.rows)) {
                            row = 0;
                        }
                    }
                    this.determinant += temp;
                }
            } else {
                this.determinant = (this.matrix[0][0] * this.matrix[1][1]) - (this.matrix[1][0] * this.matrix[0][1]);
            }
        }

        public double getDeterminent() {
            this.calculateDeterminent();
            return(this.determinant);
        }
        // broken

        public void transpose() {
            if (this.rows == this.columns) {
                double[][] temp = new double[this.rows][this.columns];
                for (int r = 0; r < this.rows; r++) {
                    for (int c = 0; c < this.rows; c++) {
                        temp[c][r] = this.matrix[r][c];
                    }
                }
                this.matrix = temp;
            }
        }


        public void printMatrix() {
            this.calculateDeterminent();
            System.out.print("Determinant: ");
            System.out.println(this.determinant);
            System.out.print("Rows: ");
            System.out.println(this.rows);
            System.out.print("Columns: ");
            System.out.println(this.columns);
            
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
                
            }
        }
    }
}