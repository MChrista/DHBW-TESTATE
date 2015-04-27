package algorithms;

import objects.Polynom;

public class NewtonInterpolation {
	public double[] newton(double f[],double x[]) {
		Polynom r = new Polynom(new double[]{f[0]});
		int n=f.length;
		double a[][]=new double[n][n];
		for(int i=0;i<n;i++) a[0][i]=f[i];
		for(int i=1;i<n;i++) {
			for(int j=0;j<n-i;j++) a[i][j]=(a[i-1][j+1]-a[i-1][j])/(x[j+i]-x[j]);
			Polynom m=new Polynom(new double[]{a[i][0]});
			for(int j=0;j<i;j++) {
				m=m.multiply(new Polynom(new double[]{-x[j],1}));
			}
			r=r.add(m);
		}
		//System.out.println(r.getP());
		return r.getP();
	}
}