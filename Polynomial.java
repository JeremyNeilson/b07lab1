import java.util.Arrays;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial{
	double[] coef;
	int[] expo;

	public Polynomial(){
		coef = new double[1];
		expo = new int[1];
		coef[0] = 0;
		expo[0] = 0;
	}

	public Polynomial(double[] other, int[] powers){
		coef = new double[other.length];
		expo = new int[powers.length];
		for (int i=0;i<other.length;i++) {
			coef[i] = other[i];
			expo[i] = powers[i];
		}
	}
	
	public Polynomial(File input) throws NumberFormatException, IOException {
		BufferedReader read = new BufferedReader(new FileReader(input));
		String poly = read.readLine();
		poly.replaceAll("-", "+-");
		String[] split = poly.split("//+");
		coef = new double[split.length];
		expo = new int[split.length];
		String[] mini;
		int exponent = 1;
		double coefficient;
		for (int i = 0; i < split.length; i++) {
			mini = split[i].split("x");
			if (mini.length == 1) {
				if (Character.compare(split[i].charAt(0), 'x') == 0)
					exponent = Integer.parseInt(mini[0]);
				else
					exponent = 0;
			}
			if (Character.compare(split[i].charAt(0), 'x') != 0)
				coefficient = Double.parseDouble(mini[0]);
			else
				coefficient = 1;
			if (mini.length != 1)
				exponent = Integer.parseInt(mini[1]);
			coef[i] = coefficient;
			expo[i] = exponent;
		}
	}
	
	public void saveToFile(String name) throws NumberFormatException, IOException {
		FileWriter output = new FileWriter(name, false);
		String[] text = new String[coef.length];
		String s3;
		for (int i = 0; i < coef.length; i++) {
			String s1 = Double.toString(coef[i]);
			String s2 = Integer.toString(expo[i]);
			if (expo[i] == 0) {
				s3 = s1;
			}
			else if (expo[i] == 1) {
				s3 = s1 + "x";
			}
			if (i != 0 && Character.compare(s1.charAt(0), '-') != 0) {
				s3 = "+" + s1 + "x" + s2;
			}
			else
				s3 = s1 + "x" + s2;
			output.write(s3);
		}
	}
	
	public Polynomial add(Polynomial input) {
		int length = expo.length + input.expo.length;
		for (int i = 0; i < coef.length; i++) {
			for (int j = 0; j < input.coef.length; j++) {
				if (expo[i] == input.expo[j]) {
					length = length - 1;
				}
			}
		}
		double summy[] = new double[length];
		int powah[] = new int[length];
		Polynomial sum = new Polynomial(summy, powah);
		for (int i = 0; i < coef.length; i++) {
			sum.expo[i] = expo[i];
			sum.coef[i] = coef[i];
		}
		int a;
		int k = 0;
		for (int i = 0; i < input.coef.length; i++) {
			a = 0;
			for (int j = 0; j < coef.length; j++) {
				if (input.expo[i] == sum.expo[j]) {
					a = 1;
					sum.coef[j] = input.coef[i];
				}
			}
			if (a == 0) {
				sum.expo[coef.length + k] = input.expo[i];
				sum.coef[coef.length + k] = input.coef[i];
				k++;
			}
		}
		return sum;
	}
	
	public double evaluate(double value) {
		double sum = 0;
		for (int i=0;i<coef.length;i++) {
			sum = sum + (coef[i]*Math.pow(value, expo[i]));
		}
		return sum;
	}
	
	public boolean hasRoot(double value) {
		if (evaluate(value) == 0)
			return true;
		return false;
	}
	
	public Polynomial multiply(Polynomial input) {
		int length = input.expo.length * expo.length;
		int newexpo[] = new int[length];
		double newcoef[] = new double[length];
		int counter = 0;
		for (int i = 0; i < expo.length; i++) {
			for (int j = 0; j < input.expo.length; j++) {
				newexpo[counter] = expo[i] + input.expo[j];
				newcoef[counter] = coef[i] * input.coef[j];
				counter++;
			}
		}
		int len = length;
		for (int i = 0; i < length; i++) {
			for (int j = i; j < length; j++) {
				if (newexpo[i] == newexpo[j] && i != j) {
					len--;
				}
			}
		}
		int propexpo[] = new int[len];
		double propcoef[] = new double[len];
		int count = 0;
		for (int i = 0; i < length; i++) {
			int dupl = 0;
			for (int k = 0; k < i+1; k++) {
				if (propexpo[k] == newexpo[i] && k != i) {
					dupl = 1;
					count++;
				}
			}
			if (dupl == 0) {
				propexpo[i - count] = newexpo[i];
				propcoef[i - count] = newcoef[i];
			
				for (int j = i; j < length; j++) {
					if (newexpo[i] == newexpo[j] && i != j) {
						propcoef[i] = propcoef[i] + newcoef[j];
					}
				}
			}
		}
		Polynomial end = new Polynomial(propcoef, propexpo);
		return end;
	}
	
	
}