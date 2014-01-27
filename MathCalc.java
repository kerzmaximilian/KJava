
import java.util.Arrays;

public class MathCalc {
	private static final double PI = 3.14159265359;
	private short[] medianArray;
	private int[] medArray;
	
	public MathCalc() {

	}
	
	public MathCalc(short[] array){
		this.medianArray=array;
	}
	
	public MathCalc(int[] array){
		this.medArray=array;
	}

	public static int[] getMode(int a[]) {
		int maxValue = 0;
		int maxCount = 0;

		for (int i = 0; i < a.length; ++i) {
			int count = 0;
			for (int j = 0; j < a.length; ++j) {
				if ((a[j] > a[i] - 6) && (a[j] < a[i] + 6))
					++count;
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = a[i];
				//System.out.print(maxCount + "|" + maxValue + "\t");
			}
		}
		int[] valCount = new int[2];
		valCount[0] = maxValue;
		valCount[1] = maxCount;
		return valCount;
	}

	public static int getHistoField(int zScore) {
		int histoField = 100;

		if (zScore <= -9) {
			histoField = 0;
		}

		if (zScore == -8) {
			histoField = 1;
		}

		if (zScore == -7) {
			histoField = 2;
		}

		if (zScore == -6) {
			histoField = 3;
		}

		if (zScore == -5) {
			histoField = 4;
		}

		if (zScore == -4) {
			histoField = 5;
		}

		if (zScore == -3) {
			histoField = 6;
		}

		if (zScore == -2) {
			histoField = 7;
		}

		if (zScore == -1) {
			histoField = 8;
		}

		if (zScore == 0) {
			histoField = 9;
		}

		if (zScore == 1) {
			histoField = 10;
		}

		if (zScore == 2) {
			histoField = 11;
		}

		if (zScore == 3) {
			histoField = 12;
		}

		if (zScore == 4) {
			histoField = 13;
		}

		if (zScore == 5) {
			histoField = 14;
		}

		if (zScore == 6) {
			histoField = 15;
		}

		if (zScore == 7) {
			histoField = 16;
		}

		if (zScore == 8) {
			histoField = 17;
		}

		if (zScore >= 9) {
			histoField = 18;
		}
		return histoField;
	}
	
	public short inOutMedian(short pixVal, int index){
		medianArray[index]=pixVal;
		short baseline = getMedian(medianArray);
		return baseline;
	}
	
	public short getMedian(short[] line) {
		short[] pixInter = line;
		Arrays.sort(pixInter);
		short baseline = pixInter[pixInter.length / 2 + 1];
		return baseline;
	}
	
	public int inOutMedian(int pixVal, int index){
		medArray[index]=pixVal;
		int baseline = getMedian(medArray);
		return baseline;
	}
	
	public int getMedian(int[] line) {
		int[] pixInter = line;
		Arrays.sort(pixInter);
		int baseline = pixInter[pixInter.length / 2 + 1];
		return baseline;
	}
	
	public short getMean(){
		short avg=0;
		for(int i=0; i<medianArray.length; i++){
			avg+=medianArray[i];
		}
		avg=(short) (avg/medianArray.length);
		return avg;
	}
	
	public static double getRadian(int degree){
		double rad = (degree*PI)/180;
		return rad;
	}
	
	public static double getRadian(float degree){
		double rad = (degree*PI)/180;
		return rad;
	}
	
}
