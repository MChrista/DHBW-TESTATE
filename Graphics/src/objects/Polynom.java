package objects;

public class Polynom {
	static final double EPS=1e-8;
	private double p[];

	public Polynom(double a[]) {
		setP((double[])a.clone()); reduce();
	}
	void reduce() {
		int size;
		for(size=getP().length-1;size>0 && Math.abs(getP()[size])<EPS;size--);
		if(++size<getP().length) {
			double q[]=new double[size];
			for(int i=0;i<size;i++) q[i]=getP()[i];
			setP(q);
		}
	}
	public Polynom add(Polynom b) {
		int n=Math.max(this.getP().length,b.getP().length);
		double q[]=new double[n];
		for(int i=0;i<n;i++) {
			if(i<this.getP().length) q[i]+=this.getP()[i];
			if(i<b.getP().length) q[i]+=b.getP()[i];
		}
		return new Polynom(q);
	}
	public Polynom multiply(Polynom b) {
		double q[]=new double[this.getP().length+b.getP().length-1];
		for(int i=0;i<this.getP().length;i++) for(int j=0;j<b.getP().length;j++)
			q[i+j]+=this.getP()[i]*b.getP()[j];
		return new Polynom(q);
	}
	/* newton's interpolation method: for n pairs (x,f(x)), return n-degree polynomial
           lowest degree earliest in returned array */
	/*
	public static double[] newton(double f[],double x[]) {
		Polynom r=new Polynom(new double[]{f[0]});
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
		return r.p;
	}
	*/
	public double[] getP() {
		for(int i=0; i<p.length; i++) {
			System.out.println(p[i]);
		}
		return p;
	}
	public void setP(double p[]) {
		this.p = p;
	}
}