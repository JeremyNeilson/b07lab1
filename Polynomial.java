public class Polynomial{
	double[] coef;

	public Polynomial(){
		coef = new double[1];
		coef[0] = 0;
	}

	public Polynomial(double[] other){
		coef = new double[other.length];
		for (int i=0;i<other.length;i++) {
			coef[i] = other[i];
		}
	}

	public Polynomial add(Polynomial input) {
		int length = Math.max(input.coef.length, coef.length);
		int shorter = Math.min(input.coef.length, coef.length);
		double[] summy = new double[length];
		Polynomial sum = new Polynomial(summy);
		for (int i=0;i<shorter;i++) {
			sum.coef[i] = coef[i]+input.coef[i];
		}
		if (length == coef.length) {
			for (int i=shorter;i<length;i++) {
				sum.coef[i] = coef[i];
			}
		}
		else {
			for (int i=shorter;i<length;i++) {
				sum.coef[i] = input.coef[i];
			}
		}
		return sum;
	}
	
	public double evaluate(double value) {
		double sum = 0;
		for (int i=0;i<coef.length;i++) {
			sum = sum + (coef[i]*Math.pow(value, i));
		}
		return sum;
	}
	
	public boolean hasRoot(double value) {
		if (evaluate(value) == 0)
			return true;
		return false;
	}
}