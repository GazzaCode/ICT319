package MLP;

public class NeuralNetwork 
{
	private int inputNodes;
	private int hiddenNodes;
	private int outputNodes;
	
	private Matrix weights_ih; //Weights for inputs -> hidden nodes
	private Matrix weights_ho; //Weights for hidden -> output nodes
	
	private Matrix bias_ih; //Biases for inputs -> hidden nodes
	private Matrix bias_ho; //Biases for inputs -> hidden nodes

	private float learningRate = (float) 0.1;
	
	/* @description Constructor to create a new neural network with specific
	 * amount of input, hidden and output layers.
	 * @param inputs 
	 * @param hidden
	 * @param output
	 */
	public NeuralNetwork(int inputs, int hidden, int output)
	{
		inputNodes = inputs;
		hiddenNodes = hidden;
		outputNodes = output;
		
		//Creates layers based on number of nodes
		weights_ih = new Matrix(hiddenNodes, inputNodes);
		weights_ho = new Matrix(outputNodes, hiddenNodes);
		
		//Randomize the weights between 0 and 1
		weights_ih.randomize((float)-0.5, (float)0.5);
		weights_ho.randomize((float)-0.5, (float)0.5);
		
		//Create bias matrix based on number of nodes
		bias_ih = new Matrix(hiddenNodes, 1);
		bias_ho = new Matrix(outputNodes, 1);
		
		//Randomize the biases
		bias_ih.randomize((float)-0.5, (float)0.5);
		bias_ho.randomize((float)-0.5, (float)0.5);
	}
	
	/* @description method to return the sigmoid of a specific element
	 * @param inputs 
	 * @param hidden
	 * @param output
	 */
	public float sigmoid(float x)
	{
		return ((float) (1/(1+Math.exp(-x))));
	}
	
	/* @description method to return the derivative of a
	 * sepecific sigmoid element
	 * @param inputs 
	 * @param hidden
	 * @param output
	 */
	public float dSigmoid(float y)
	{
		return y * (1 - y);
	}
	
	public Matrix feedForward(float[] tInput)
	{
		//Convert inputed data array to a matrix object
		Matrix inputs = Matrix.fromArray(tInput);
		//Multiply input -> hidden layer weights with the input
		Matrix hidden = Matrix.Multiply(weights_ih, inputs);
		//Add the bias to the already multiplied input -> hidden output
		hidden.addMatrix(bias_ih);
		
		//Apply sigmoid function to each element of the ih_output
		for(int i = 0; i < hidden.rows; i++)
		{
			for(int j = 0; j < hidden.cols; j++)
			{
				hidden.matrix[i][j] = sigmoid(hidden.matrix[i][j]);
			}
		}
		
		//END OF HIDDEN LAYER
		
		
		//OUTPUT LAYER
		
		//Multiply hidden -> output layer weights with the output
		//of the input -> hidden output
		Matrix outputs = Matrix.Multiply(weights_ho, hidden);
		//Add the bias to the already multiplied hidden -> output output
		outputs.addMatrix(bias_ho);
		
		//Apply sigmoid function to each element of the ho_output
		for(int i = 0; i < outputs.rows; i++)
		{
			for(int j = 0; j < outputs.cols; j++)
			{
				outputs.matrix[i][j] = sigmoid(outputs.matrix[i][j]);
			}
		}
		
		//END OF OUTPUT LAYER
		
		return(outputs);
	}
	

	
	public void train(float[] iInputs, float[] iTargets)
	{	
		//-------------------copied from feedForward------------------------------
		
		//Convert inputed data array to a matrix object
		Matrix inputs = Matrix.fromArray(iInputs);
		//Multiply input -> hidden layer weights with the input
		Matrix hidden = Matrix.Multiply(weights_ih, inputs);
		//Add the bias to the already multiplied input -> hidden output
		hidden.addMatrix(bias_ih);
		
		//Apply sigmoid function to each element of the ih_output
		for(int i = 0; i < hidden.rows; i++)
		{
			for(int j = 0; j < hidden.cols; j++)
			{
				hidden.matrix[i][j] = sigmoid(hidden.matrix[i][j]);
			}
		}
		
		//END OF HIDDEN LAYER
		
		
		
		
		

		//OUTPUT LAYER
		
		//Multiply hidden -> output layer weights with the output
		//of the input -> hidden output
		Matrix outputs = Matrix.Multiply(weights_ho, hidden);
		//Add the bias to the already multiplied hidden -> output output
		outputs.addMatrix(bias_ho);
		
		//Apply sigmoid function to each element of the ho_output
		for(int i = 0; i < outputs.rows; i++)
		{
			for(int j = 0; j < outputs.cols; j++)
			{
				outputs.matrix[i][j] = sigmoid(outputs.matrix[i][j]);
			}
		}
		
		//END OF OUTPUT LAYER
		
		
		//-------------------copied from feedForward------------------------------

		//Convert targets to a Matrix
		Matrix targets = Matrix.fromArray(iTargets);
		
		
		
		//OUTPUT
		
		//Output layer error
		
		//Subtract outputs FROM the targets to get output error values
		Matrix outputErrors = Matrix.subtractMatrix(targets, outputs);
		
		
		
		//Calculate Gradient
		
		//map outputs with derivative sigmoid method
		for(int i = 0; i < outputs.rows; i++)
		{
			for(int j = 0; j < outputs.cols; j++)
			{
				outputs.matrix[i][j] = dSigmoid(outputs.matrix[i][j]);
			}
		}
		Matrix gradients = outputs;
		
		gradients.hadamardProduct(outputErrors);
		gradients.multiplyScalar(learningRate);

		
		
		//Calculate hidden -> output deltas
		
		Matrix hidden_t = Matrix.Transpose(hidden);
		Matrix weights_ho_deltas = Matrix.Multiply(gradients, hidden_t);
		
		//Adjust the weights by deltas
		weights_ho.addMatrix(weights_ho_deltas);
		//Adjust bias by its deltas
		bias_ho.addMatrix(gradients);
		
		//END OF OUTPUT
		
		
		
		//HIDDEN
		
		//Hidden layer error
		
		//transpose the hidden -> output weights
		Matrix weights_ho_t = Matrix.Transpose(weights_ho);
		//Multiply transposed hidden -> output weights by the output errors
		//to find hidden layer error values
		Matrix hiddenErrors = Matrix.Multiply(weights_ho_t, outputErrors);
		
		
		
		//calculate hidden gradient
		
		//map hiddens with derivative sigmoid method
		for(int i = 0; i < hidden.rows; i++)
		{
			for(int j = 0; j < hidden.cols; j++)
			{
				hidden.matrix[i][j] = dSigmoid(hidden.matrix[i][j]);
			}
		}
		Matrix hiddenGradient = hidden;
		
		
		hiddenGradient.hadamardProduct(hiddenErrors);		
		hiddenGradient.multiplyScalar(learningRate);	
		
		
		
		//Calculate input -> deltas
		Matrix inputs_t = Matrix.Transpose(inputs);		
		Matrix weights_ih_deltas = Matrix.Multiply(hiddenGradient, inputs_t);
		
		weights_ih.addMatrix(weights_ih_deltas);
		bias_ih.addMatrix(hiddenGradient);
		//END OF HIDDEN
		
		
		
		//ho_output.displayMatrix();
		//targets.displayMatrix();
		//inputs.displayIMatrix();
		//hidden.displayIMatrix();
		//outputErrors.displayMatrix();
		//hiddenErrors.displayMatrix();
		
	}

}







