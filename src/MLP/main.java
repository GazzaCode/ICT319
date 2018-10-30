package MLP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class main {

	Matrix[] Alphabet = new Matrix[26];
	//Matrix[][] trainingData = new Matrix[26][26];
	
	static TrainingData[] data = new TrainingData[26];
	
	public static void main(String[] args) 
	{
		
		//Read training data
		try {
			readTrainingData();
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
		for(int j = 0; j < data.length; j++)
		{
			Matrix m = nn.feedForward(data[j].vals);
			
			System.out.println(alphabet[j] + " : ");
			
			m.displayMatrix();
		}
		
	}
	
	static void readTrainingData() throws FileNotFoundException
	{
		File file = new File("training.txt"); 
	    Scanner sc = new Scanner(file); 

	    String str = "";

	    float[] array; 
	    
	    for(int k = 0; k < 26; k++)
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
		    float target[] = new float[1];
		    str = sc.next();
		    
		    //assign target to training data output
		    target[0] = Integer.valueOf(str);
		    
		    //Assign values and targets to TrainingData object
		    data[k] = new TrainingData(vals, target);
	    }
	}

	static void readAlphabet() throws FileNotFoundException
	{
		File file = new File("Alphabet.txt"); 
	    Scanner sc = new Scanner(file); 

	    Matrix[] Alphabet = new Matrix[26];
	    
	    
	   // Matrix matrix = new Matrix(5, 9);
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
