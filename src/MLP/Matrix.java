package MLP;

import java.util.Random;

public class Matrix 
{
	public int rows = 0;
	public int cols = 0;
	public float matrix[][];
	public int IMatrix[][];
	
	public Matrix(int iRows, int iCols)
	{
		rows = iRows;
		cols = iCols;
		
		//Initialize matrix
		matrix = new float[rows][cols];
		IMatrix = new int [rows][cols];
		
		//Fill with 0's
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				matrix[i][j] = 0;
			}
		}
		
		for(int k = 0; k < rows; k++)
		{
			for(int p = 0; p < cols; p++)
			{
				IMatrix[k][p] = 0;
			}
		}
	}
	
	
	
	/* @description function to transpose the
	 * matrix of the current Matrix object
	 * @return Matrix result
	 */
	static public Matrix Transpose(Matrix a)
	{
		Matrix result = new Matrix(a.cols, a.rows);
		
		for(int i = 0; i < a.rows; i++)
		{
			for(int j = 0; j < a.cols; j++)
			{
				result.matrix[j][i] = a.matrix[i][j];
			}
		}
		
		return result;
	}
	
	/* @description function to convert an array
	 * to a Matrix object
	 * @return Matrix result
	 */
	public static Matrix fromArray(float[] input)
	{
		Matrix result = new Matrix(input.length, 1);
		
		for(int i = 0; i < input.length; i++)
		{
				result.matrix[i][0] = input[i];
		}
		
		return result;
	}
	
	/* @description function to convert an array
	 * to a Matrix object
	 * @return Matrix result
	 */
	public static float[] toArray(Matrix input)
	{
		float[] result = new float[input.rows];
		
		for(int i = 0; i < result.length; i++)
		{
			result[i] = input.matrix[i][0];
		}
		
		return result;
	}
	
	/*this needs to be APPLY SIGMOID
	public void map(int row, int col)
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				matrix[i][j] += scalar;
			}
		}
	}*/
	
	/* @description function to multiply this matrix
	 * with another matrix
	 * @param Matrix input
	 * @return Matrix result
	 */
	static Matrix Multiply(Matrix a, Matrix b)
	{
		Matrix result = new Matrix(a.rows, b.cols);
		
		if(a instanceof Matrix && b instanceof Matrix)
		{
			if(a.cols == b.rows)
			{
				for(int i = 0; i < result.rows; i++)
				{
					for(int j = 0; j < result.cols; j++)
					{
						float sum = 0;
						//Find dot product of values in col
						for(int k = 0; k < a.cols; k++)
						{
							sum += a.matrix[i][k] * b.matrix[k][j];
						}
						
						result.matrix[i][j] = sum;
					}
				}
				
				return result;
			}
			else
			{
				System.out.println("Inputted matrix rows"
						+ " does not match the matrix cols");
				
				return result;
			}
		}
		else
		{
			System.out.println("Inputted varaible not an instance"
					+ " of Matrix");
			
			return result;
		}
	}
	
	/* @description function to find the hadamard product
	 * of two matrices (Like adding but elements multiplied instead)
	 * NOT MATRIX MULTIPLICATION
	 * @param Matrix matrix
	 */
	public void hadamardProduct(Matrix input)
	{
		if(input instanceof Matrix)
		{		
			if(input.rows == this.rows && input.cols == this.cols)
			{
				for(int i = 0; i < rows; i++)
				{
					for(int j = 0; j < cols; j++)
					{
						matrix[i][j] = matrix[i][j] * input.matrix[i][j];
					}
				}
			}
			else
				System.out.println("Inputted matrix does not share "
						+ "the same amount of rows x cols\n");
		}
		else
			System.out.println("Inputted value not instance of a Matrix\n");

	}
	
	/* @description function to subtract two matrices
	 * together
	 * @param Matrix a, Matrix b
	 */
	static public Matrix subtractMatrix(Matrix a, Matrix b)
	{
		Matrix result = new Matrix(a.rows, a.cols);
		
		if(a.rows == b.rows && a.cols == b.cols)
		{
			for(int i = 0; i < a.rows; i++)
			{
				for(int j = 0; j < a.cols; j++)
				{
					result.matrix[i][j] = a.matrix[i][j] - b.matrix[i][j];
				}
			}
			
			return result;
		}
		else
		{
			System.out.println("Class: Matrix, method: subtractMatrix "
					+ "The number of rows and columns "
					+ "do to match");
		}
			
		return result;
	}
	
	/* @description function to add two matrices
	 * together
	 * @param Matrix matrix
	 */
	public void addMatrix(Matrix input)
	{
		if(input instanceof Matrix)
		{		
			if(input.rows == this.rows && input.cols == this.cols)
			{
				for(int i = 0; i < rows; i++)
				{
					for(int j = 0; j < cols; j++)
					{
						matrix[i][j] = matrix[i][j] + input.matrix[i][j];
					}
				}
			}
			else
				System.out.println("Inputted matrix does not share "
						+ "the same amount of rows x cols\n");
		}
		else
			System.out.println("Inputted value not instance of a Matrix\n");
	}
	
	public float[][] getMatrixData()
	{
		return matrix;
	}
	
	/* @description find a random float between
	 * a min and max value
	 * @param min
	 * @param max
	 */
	private float randomNumber(float min, float max)
	{
		Random r = new Random();
		float random = min + r.nextFloat() * (max - min);

		return random;
	}
	
	/* @description randomizes all floats
	 * in the matrix between a certain range
	 */
	public void randomize(float min, float max)
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				matrix[i][j] = randomNumber(min, max);
			}
		}
	}
	
	/* @description function to multiply a matrix
	 * by a scalar
	 * @param scalar
	 */
	public void multiplyScalar(float scalar)
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				matrix[i][j] *= scalar;
			}
		}
	}
	
	/* @description function to add a matrix
	 * by a scalar
	 * @param scalar
	 */
	public void addScalar(float scalar)
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				matrix[i][j] += scalar;
			}
		}
	}
	
	//Display details about the matrix
	public String toString()
	{
		String str;
		
		str = "Rows : " + Integer.toString(rows) + " " + 
				"Cols : " + Integer.toString(cols) + "\n\n";
		
		return str;
	}
	
	public void displayMatrix()
	{
		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		        System.out.print(matrix[i][j] + " ");
		    }
		    System.out.println("");
		}
		System.out.println("\n");
	}
	
	public void displayIMatrix()
	{
		//String firstNumberAsString = String.format ("%.0f", firstNumber);
		
		for (int i = 0; i < IMatrix.length; i++) {
		    for (int j = 0; j < IMatrix[i].length; j++) {
		        System.out.print(String.format ("%.0000f", IMatrix[i][j] )+ " ");
		    }
		    System.out.println("");
		}
		System.out.println("\n");
	}
	
}
