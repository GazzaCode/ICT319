package MLP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

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
		NeuralNetwork nn = new NeuralNetwork(45, 26, 1);

		//temp TrainingData object to store randomly chosen TrainingData
		TrainingData chosen;
		
		//Train the NN with random training data
		for(int i = 0; i < 50000; i++)
		{
			int rnd = new Random().nextInt(data.length);
			chosen = data[rnd];
			
			nn.train(chosen.vals, chosen.targets);
		}
		
		//To label the outputs
		char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		

		
		//Loop through and run through feed forward 
		//algorithm once to see newest output
		//from the NN
		//Display each trained value
		for(int j = 0; j < data.length; j++)
		{
			Matrix m = nn.feedForward(data[j].vals);
			
			//System.out.println(alphabet[j] + " : ");
			
			m.displayMatrix();
		}
		
		
		//TESTING
		Matrix test = nn.feedForward(testingData[5].vals);
		System.out.println(testingData[5].letter[0] + " ");
		test.displayMatrix();
		
		/*for(int j = 0; j < data.length; j++)
		{
			Matrix m = nn.feedForward(data[j].vals);
			
			if(alphabet[j] == 'a' || alphabet[j] == 'e' || alphabet[j] == 'i' || alphabet[j] == 'o' || alphabet[j] == 'u')
			{
				System.out.println(alphabet[j] + " : ");
				
				m.displayAssignment2Matrix();
			}
			
		}*/
		
		/*for(int j = 0; j < data.length; j++)
		{
			Matrix m = nn.feedForward(data[j].vals);
			
			//m.displayMatrix();
			float[][] data = m.getMatrixData();
			for(int i = 0; i < m.rows; i++) 
			{
				if(data[i][0] > 0.9)
				{
					System.out.println(alphabet[j] + " : ");
					System.out.println(data[i][0]);
				}
			}
		}*/
		
		/*Matrix m = nn.feedForward(data[0].vals);//Pass it A
		//Matrix m = nn.feedForward(data[4].vals);//Pass it E
		float[][] data = m.getMatrixData();
		for(int i = 0; i < m.rows; i++) 
		{
			if(data[i][0] > 0.9)
			{
				//System.out.println(alphabet[j] + " : ");
				System.out.println(data[i][0]);
			}
		}*/
		
		//m.displayMatrix();
	}
	
	static void readTrainingData() throws FileNotFoundException
	{
		File file = new File("training.txt"); 
	    Scanner sc = new Scanner(file); 

	    int targetCount = 0;
	    
	    String str = "";

	    float[] array; 
	    
	    for(int k = 0; k < 46; k++)
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
		    float target[] = new float[1]; //5 for 5 outputs
		    str = sc.next();

		    target[0] = Integer.valueOf(str);
		    /*if(Integer.valueOf(str) == 1)
		    {
		    	target[targetCount] = Integer.valueOf(str);
		    	targetCount += 1;
		    }*/
		    
		    //Assign values and targets to TrainingData object
		    data[k] = new TrainingData(vals, target);
	    }
	}

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
		    char target[] = new char[1]; //5 for 5 outputs
		    str = sc.next();

		    target[0] = str.charAt(0);
		    
		    //Assign values and targets to TrainingData object
		    testingData[k] = new TestData(vals, target);
	    }
	}
	
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
