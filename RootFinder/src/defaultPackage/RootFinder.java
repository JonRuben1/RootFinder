package defaultPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This program finds the n-th root of a real number to a desired level of precision.
 * 
 * @author Jonathan Ruben
 * 
 */

public final class RootFinder {

	/**
	 * This function calculates the power of a number
	 * @param base 
	 * @param power  
	 * @return base ^ power
	 */
	private static double power(double base, int power){
		double result = 1;
		while(power > 0){
			result = result * base;
			power--;
		}
		return result;
	}
 
	/**
	 * This function determines whether or not the candidates for the root meet the desired level of precision
	 * @param highCandidate The upper value of all possible root candidates
	 * @param lowCandidate The floor of all possible root candidates 
	 * @param degreeOfPrecision The level of precision for the final root candidate
	 * @return boolean determining whether or not the candidates meet the desired level of precision
	 */
	private static boolean checkPrecision(double highCandidate, double lowCandidate, int degreeOfPrecision){
		int orderOfMagnitude = 0;
		double difference = highCandidate - lowCandidate;
		
		if(highCandidate > 1 ){
			 double tempCandidate = highCandidate;
			 while(tempCandidate >= 1){
				 tempCandidate = tempCandidate/10;
				 orderOfMagnitude++; 
			 }
		} else {
			double tempCandidate = lowCandidate;
			while(tempCandidate < 1){
				tempCandidate = tempCandidate * 10;
				orderOfMagnitude--;
			}
		}
		//final degree is order of magnitude of comparison 
		int finalDegree = orderOfMagnitude -degreeOfPrecision; 
		//test is the final comparison value
		double test = 1;
		//calculate actual comparison value
		if(finalDegree > 0){
			while(finalDegree > 0) {
				test = test*10;
				finalDegree--;
			}
		} else {
			while(finalDegree < 0){
				test = test/10;
				finalDegree++;
			}
		}
		if(difference < 0){
			difference = difference *-1;
		}
		if(difference < test){
			return true;
		}
		
		return false;
	}


    /**
     * This function calculates the n-th root of a radicand within a desired degree of precision.
     * @param index this function will calculate the n-th root of a value, n is the index
     * @param radicand this is the value that will be rooted by the function
     * @param degree this is the order of magnitude for degree of precision, the final candidate will be within 10^(degree * -1) of the actual value
     * @return the final root candidate for 
     */
    private static double root(int index, double radicand, int degree) {
    	if(index == 0){
    		return 1;
    	} 
    	if(radicand >= 1){
    		//set initial parameters
    		double highCandidate = radicand;
    		double lowCandidate = 0;
    		boolean differencePrecision = false;
    		double middleCandidate = (highCandidate + lowCandidate)/2;
    		double middleCandidatePower = power(middleCandidate, index);
    		//reset parameters and continue interval halving until candidate meets desired level of precision
    		while(!differencePrecision){
    			if(middleCandidatePower > radicand){
    				highCandidate = middleCandidate;
    			} else {
    				lowCandidate = middleCandidate;
    			}
    			middleCandidate = (highCandidate + lowCandidate)/2;
    			middleCandidatePower = power(middleCandidate, index);
    			differencePrecision = checkPrecision(highCandidate, lowCandidate, degree);
    		}
    		return middleCandidate;
    	} else if(radicand < 1 && radicand >0){
    		//set initial parameters
    		double lowCandidate = radicand;
    		double highCandidate = 1;
    		boolean differencePrecision = false;	
    		double middleCandidate = (highCandidate - lowCandidate)/2;
    		double middleCandidatePower = power(middleCandidate, index);
    		//reset parameters and continue interval halving until candidate meets desired level of precision
    		while(!differencePrecision){
    			System.out.println("middleCandidate = " + middleCandidate + " highCandidate = " + highCandidate + " lowCandidate = " + lowCandidate);
    			if(middleCandidatePower < radicand){
    				lowCandidate = middleCandidate;
    			} else {
    				highCandidate = middleCandidate;
    			}
    		middleCandidate = (highCandidate + lowCandidate)/2;
    		middleCandidatePower = power(middleCandidate, index);
    		differencePrecision = checkPrecision(highCandidate, lowCandidate, degree);
    		}
    		return middleCandidate;
    	}
    	//return 0 if radicand is less than or equal to 0
    	return 0;    	
    }
    
    /**
     * This method determines whether or not a string can be parsed into an int
     * @param s a string that will be tested to determine whether or not it can be parsed
     * @return boolean determining whether or not the string can be parsed into an int
     * 
     */
    public static boolean isInt(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
        	//return false if exception is thrown
            return false; 
        }
        // return true if number can be parsed
        return true;
    }
    /**
     * This method determines whether or not a string can be parsed into a double
     * @param s a string that will be tested to determine whether or not it can be parsed
     * @return boolean determining whether or not the string can be parsed into a double
     * 
     */

    public static boolean isDouble(String s) {
    	//implement try-catch architecture for parsing double
        try { 
            Double.parseDouble(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // return true if number can be parsed
        return true;
    }

    /**
     * Main method.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	
    	//ask user to enter the radicand
    	System.out.println("Please enter the radicand, or the number that you wish to find the root of, or press 'q' to quit:");
    	String radicandString = in.readLine();
    	boolean isDouble = false;
    	//while loop to check if value can be turned into a double
    	while(!isDouble){
        	isDouble = isDouble(radicandString);
        	if(!isDouble){
        		//exit if user wishes to quit
        		if(radicandString.equals("q")){
        			System.exit(0);
        		}
        		//ask user to re-enter value
        		System.out.println("The number that you entered is not a real number. Please enter a new radicand in real-number form or enter 'q' to quit");
        		radicandString = in.readLine();
        	}
    	}   
    	//turn radicand into a double
    	double radicand = Double.parseDouble(radicandString);
    	
    	//print statement asking user to enter root and read in the string
    	System.out.println("Please enter the index, or n for the nth-root, in integer form or press 'q' to quit:");
    	String indexString = in.readLine();
    	//while loop to check if value can be turned into an integer
    	boolean isInt = false;
    	while(!isInt){
    		isInt = isInt(indexString);
    		if(!isInt){
    			//exit if participant wishes to quit
    			if(indexString.equals("q")){
    				System.exit(0);
    			}
    			//ask user for new root
    			System.out.println("The number that you entered is not an integer. Please enter a new index in integer form or enter 'q' to quit");
    			indexString = in.readLine();
    		}
    	}
    	//convert indexString from a string to an int, root
    	int index = Integer.parseInt(indexString);


    	//ask user to enter the number of degrees of precision
        System.out.println("Please enter the desired level of precision, a positive integer greater than 1 and less than 17, or press 'q' to quit:");
    	String degreesString = in.readLine();
    	isInt = false;
    	//while loop to check if value can be turned into an integer
    	while(!isInt){
    		isInt = isInt(degreesString);
    		if(!isInt){
    			//exit if user wishes to quit
    			if(degreesString.equals("q")){
    				System.exit(0);
    			}
    			//ask user to re-enter value
    			System.out.println("The number that you entered is not an integer. Please enter a new degree of precision greater than 1 in integer form or press 'q' to quit:");
    			degreesString = in.readLine();
    			//make sure that the value is between 1 and 16 inclusive
    		} else {
    			int TempValue = Integer.parseInt(degreesString);
    			if(TempValue < 1 || TempValue > 16){
    				System.out.println("The number that you entered is not in range, Please enter a new degree of precision greater than 0 or less than 17 in integer form or press 'q' to quit:");
    				degreesString = in.readLine();
    				isInt = false;
    			}
    		}
    		
    	}

    	int degrees = Integer.parseInt(degreesString);
    	double root = 0;
    	if(radicand < 0 && index %2 == 1 ){
    		radicand = radicand * -1;
    		root = root(index, radicand, degrees);
    		root = -1 * root;
    		System.out.println("The root is: " + root);
    	} else if(radicand < 0 && index % 4 == 0) {
    		radicand = radicand * -1;
    		root = root(index, radicand, degrees);
    		root = -1 * root;
    		String rootString = "i * " + Double.toString(root);
    		System.out.println("The root is: " + rootString);
    	} else if(radicand < 0 && index % 2 == 0){
    		radicand = radicand * -1;
    		root = root(index, radicand, degrees);
    		String rootString = Double.toString(root);
    		rootString = "i * " + rootString;
    		System.out.println("The root is: " + rootString);
    	} else{
    	root = root(index, radicand, degrees);
    	System.out.println("The root is: " + root);
    	}
        /*
         * Close input stream
         */
        in.close();
    }

}
