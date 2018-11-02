package MLP;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class main {

	Matrix[] Alphabet = new Matrix[46];
	//Matrix[][] trainingData = new Matrix[26][26];
	
	static TrainingData[] data = new TrainingData[46];
	static TestData[] testingData = new TestData[130];
	
	public static void main(String[] args) 
	{
		
		//Read training data
		try {
			readTrainingData();
			readTestingData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Create neural network
		NeuralNetwork nn = new NeuralNetwork(45, 26, 5);

		//temp TrainingData object to store randomly chosen TrainingData
		TrainingData chosen;
		
		//Train the NN with random training data
		for(int i = 0; i < 50000; i++)
		{
			int rnd = new Random().nextInt(data.length);
			chosen = data[rnd];
			
			nn.train(chosen.vals, chosen.targets);
		}
		
		//DEBUGGING
		/*//Loop through and run through feed forward 
		//algorithm once to see newest output
		//from the NN
		//Display each trained value
		for(int j = 0; j < data.length; j++)
		{
			Matrix m = nn.feedForward(data[j].vals);
			
			//System.out.println(alphabet[j] + " : ");
			
			m.displayMatrix();
		}*/
		
		//Displays output of each letter in testing data
		for(int j = 0; j < testingData.length; j++)
		{
			System.out.println(testingData[j].letter[0] + " : ");
			Matrix m = nn.feedForward(testingData[j].vals);
			
			//System.out.println(alphabet[j] + " : ");
			
			m.displayMatrix();
		}
		
		//Test type two errors
		float aCalculation = 0;
		float eCalculation = 0;
		float iCalculation = 0;
		float oCalculation = 0;
		float uCalculation = 0;
		
		//Test vowels
		aCalculation = testTypeTwo(nn, 'A'); //send NN instance
		eCalculation = testTypeTwo(nn, 'E');
		iCalculation = testTypeTwo(nn, 'I');
		oCalculation = testTypeTwo(nn, 'O');
		uCalculation = testTypeTwo(nn, 'U');
		
		//Test type 1 errors
		
		float aError = 0;
		float eError = 0;
		float iError = 0;
		float oError = 0;
		float uError = 0;
		float constError = 0;
		
		//Test if any vowels are being passed as other vowels
		aError = testTypeOne(nn, 'A') / 125;
		eError = testTypeOne(nn, 'E') / 125;
		iError = testTypeOne(nn, 'I') / 125;
		oError = testTypeOne(nn, 'O') / 125;
		uError = testTypeOne(nn, 'U') / 125;

		char[] consts = {'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z'};
		
		//Test if any consonants are being passed as vowels
		for(int p = 0; p < consts.length; p++) 
		{
			float test = testTypeOne(nn, consts[p]);
			
			//Add any errors together for constant error result
			constError += test;
		}
		constError = constError / 125;
		
		//Display data in table
		JFrame frame = new JFrame("Frame Title");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    Object rowData[][] = { { "A", aError, aCalculation },
	        { "E", eError, eCalculation },
	    { "I", iError, iCalculation },
	    { "O", oError, oCalculation },
	    { "U", uError, uCalculation },
	    { "Consonants", constError, "-" }};
	    
	    Object columnNames[] = { "Characters", "Type 1 Errors", "Type 2 Errors" };
	    JTable table = new JTable(rowData, columnNames);

	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(300, 160);
	    frame.setTitle("Neural Network Output");
	    frame.setVisible(true);

	}
	
	static float testTypeOne(NeuralNetwork nn, char letter)
	{
		float incorrect = 0.0f;
		
		//Set this to the level of accuracy you want to test
		//Any letter produces an output greater than 0.8
		//that isn't the vowel it is supposed to be will cause an error
		float threshold = 0.8f;

		for(int i = 0; i < testingData.length; i++)
		{
			if(testingData[i].letter[0] == letter)
			{
				Matrix test = nn.feedForward(testingData[i].vals);
				float[][] data = test.getMatrixData();
				
				//The testing data hold what each letter is supposed to represent
				//these are not being used as targets in the NN at all, it is just to
				//identify which letter is being tested.
				if(letter == 'A')
				{
					//loop through the values of each letter
			    	for(int j = 0; j < 5; j++)
			    	{
			    		//If any letter apart from the first row (A row)
			    		//is greater than .9 there is an error
			    		if(data[j][0] > threshold && j != 0)
			    		{
			    			incorrect += 1;
			    		}
			    	}
				} else if (letter == 'E')
				{
					//loop through the values of each letter
			    	for(int j = 0; j < 5; j++)
			    	{
			    		//If any letter apart from the first row (E row)
			    		//is greater than .9 there is an error
			    		if(data[j][0] > threshold && j != 1)
			    		{
			    			incorrect += 1;
			    		}
			    	}
				}else if (letter == 'I')
				{
					//loop through the values of each letter
			    	for(int j = 0; j < 5; j++)
			    	{
			    		//If any letter apart from the first row (I row)
			    		//is greater than .9 there is an error
			    		if(data[j][0] > threshold && j != 2)
			    		{
			    			incorrect += 1;
			    		}
			    	}
				}
				else if (letter == 'O')
				{
					//loop through the values of each letter
			    	for(int j = 0; j < 5; j++)
			    	{
			    		//If any letter apart from the first row (O row)
			    		//is greater than .9 there is an error
			    		if(data[j][0] > threshold && j != 3)
			    		{
			    			incorrect += 1;
			    		}
			    	}
				}
				else if (letter == 'U')
				{
					//loop through the values of each letter
			    	for(int j = 0; j < 5; j++)
			    	{
			    		//If any letter apart from the first row (U row)
			    		//is greater than .9 there is an error
			    		if(data[j][0] > threshold && j != 4)
			    		{
			    			incorrect += 1;
			    		}
			    	}
				}else
				{
					//loop through the values of each letter
			    	for(int j = 0; j < 5; j++)
			    	{
			    		//If any of the rows are > .9 this constanent 
			    		//has been passed as a vowel
			    		if(data[j][0] > threshold)
			    		{
			    			incorrect += 1;
			    		}
			    	}
				}
			}
		}
		
		
		return incorrect;
	}
	
	static float testTypeTwo(NeuralNetwork nn, char letter)
	{
		//variable to track if a vowel was correctly
		//or incorrectly identified.
		float incorrect = 0.0f;
		
		float threshold = 0.95f;//change this to test higher levels of accuracy
		
		//TESTING TYPE 2 errors
		for(int i = 0; i < testingData.length; i++)
		{
			//If its inputted letter
			if(testingData[i].letter[0] == letter)
			{
				//Send the test sample through feedForward (Trained NN)
				Matrix test = nn.feedForward(testingData[i].vals);
				
				float[][] data = test.getMatrixData();
				
				int rowIndex = 0;
				
				//Used to identify the correct row of the neuron outputs
				//to look at
			    switch(testingData[i].letter[0]) {
			    case 'A':
			    	rowIndex = 0;
			    	break;
			    case 'E':
			    	rowIndex = 1;
			    	break;
			    case 'I':
			    	rowIndex = 2;
			    	break;
			    case 'O':
			    	rowIndex = 3;
			    	break;
			    case 'U':
			    	rowIndex = 4;
			    	break;
		    	}
				
				//Test if vowel was identified
				if(data[rowIndex][0] < threshold) 
				{
					incorrect += 1;
				}
			}
		}
		
		return incorrect/5;
	}
	
	//Reads in training data from file and stores it into an array
	//of TrainingData objects
	static void readTrainingData() throws FileNotFoundException
	{
		File file = new File("training.txt"); 
	    Scanner sc = new Scanner(file); 

	    int targetCount = 0;
	    
	    String str = "";
	    String targ = "";

	    float[] array; 
	    
	    //Loop through each training data structure in the file
	    for(int k = 0; k < 46; k++)
	    {
	    	array = new float[45];
	    	
	    	 //Create array and assign each bit to an array
	    	//to be stored as the letter data in the TrainingData object
		    for(int i = 0; i < array.length; i++)
		    {
		    	str = sc.next();
		    	array[i] = Integer.valueOf(str);
		    }
		    
		    //assign array to values of training object
		    float[] vals = array;
		    float target[] = new float[5]; //5 for 5 outputs
		    str = sc.next();
		    
		    //Assigns correct position in output neurons
		    switch(str) {
			    case "A":
			    	targ = sc.next();
			    	target[0] = Integer.valueOf(targ);
			    	break;
			    case "E":
			    	targ = sc.next();
			    	target[1] = Integer.valueOf(targ);
			    	break;
			    case "I":
			    	targ = sc.next();
			    	target[2] = Integer.valueOf(targ);
			    	break;
			    case "O":
			    	targ = sc.next();
			    	target[3] = Integer.valueOf(targ);
			    	break;
			    case "U":
			    	targ = sc.next();
			    	target[4] = Integer.valueOf(targ);
			    	break;
		    }

		    //Assign values and targets to TrainingData object
		    data[k] = new TrainingData(vals, target);
	    }
	}

	//Reads in testing data from file to an array of TestData objects
	static void readTestingData() throws FileNotFoundException
	{
		File file = new File("testing.txt"); 
	    Scanner sc = new Scanner(file); 
	    
	    String str = "";

	    float[] array; 
	    
	    for(int k = 0; k < 130; k++)
	    {
	    	array = new float[45];
	    	
	    	 //Create array and assign each bit to it
		    for(int i = 0; i < array.length; i++)
		    {
		    	str = sc.next();
		    	array[i] = Integer.valueOf(str);
		    }
		    
		    //assign array to values of training object
		    float[] vals = array;
		    char target[] = new char[1]; 
		    str = sc.next();
		    
		    target[0] = str.charAt(0);
		    
		    //Assign values and targets to TrainingData object
		    testingData[k] = new TestData(vals, target);
	    }
	}
	
	//Un-used but read in the alphebet into an array
	static void readAlphabet() throws FileNotFoundException
	{
		File file = new File("Alphabet.txt"); 
	    Scanner sc = new Scanner(file); 

	    Matrix[] Alphabet = new Matrix[26];

	    String str = "";
	 
	    for(int k = 0; k < Alphabet.length; k++)//Each letter
	    {
	    	Alphabet[k] = new Matrix(5, 9);
	    	
	    	for(int i = 0; i < 5; i++)//Each row
		    {
		    	for(int j = 0; j < 9; j++)//Each column
			    {
			    	str = sc.next();
			    	Alphabet[k].matrix[i][j] = Float.valueOf(str);
			    }
		    }
	    }
	    
	    
	}
}
