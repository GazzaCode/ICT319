package MLP;

public class TrainingData 
{

	float vals[] = new float[45];
	float targets[] = new float[0];

    public TrainingData() {}
    
    public TrainingData(float[] iVals, float[] iOuts)
    {
        vals = iVals;
        targets = iOuts;
    }
	    
}
