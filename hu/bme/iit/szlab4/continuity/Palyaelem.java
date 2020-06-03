package hu.bme.iit.szlab4.continuity;

public abstract class Palyaelem {
	// hozzáér-e a tile valamelyik oldalához
	protected boolean bal,jobb,fent,lent;
	// vízszintes kiterjedés
	protected int meret_h;
	// függőleges kiterjedés
	protected int meret_v;
	// a pályaelem baj alsó csücske
	protected Pozicio palyaElemPoz;
	
	public abstract boolean interakt(Jatekos j);
	
	public int getMeretH(){
		return meret_h;
	}
	
	public int getMeretV(){
		return meret_v;
	}
	
	public Pozicio getKoord(){
		return new Pozicio(palyaElemPoz.x,palyaElemPoz.y);
	}
	
	// kiszámolja, hogy a játékos metszi-e a pályaelemet
	public boolean metszette(Jatekos j){
		
		/*A játékos a 4 koordinátáját kiszámoljuk
		 * - alapértelmezetten a középső 
		 * az átláthatóság érdekében:
		 *  
		 *  jp4------jp3
		 *  |        |
		 *  |        |
		 *  |        |
		 *  |        |
		 *  |        |
		 *  jp1------jp2
		 * 
		 */
	
		 
		
		// játékos aktuális koordinátája
		Pozicio jAkt = j.getAktKoord();
		int jMeret = j.getMeret()/2;
		
		// bal alsó sarka
		Pozicio jp1 = new Pozicio(jAkt.x-jMeret,jAkt.y-2*jMeret);
		// jobb felső sarka
		Pozicio jp3 = new Pozicio(jAkt.x+jMeret,jAkt.y+2*jMeret);

		
		// a pályaelem bal alsó sarka
		Pozicio balalso = new Pozicio(palyaElemPoz.x-meret_h/2,palyaElemPoz.y-meret_v/2);
						
		// a pályaelem jobb felső sarka
		Pozicio jobbfelso = new Pozicio(palyaElemPoz.x+meret_h/2, palyaElemPoz.y+meret_v/2);
		boolean xmetszi = false;
		boolean ymetszi = false;
		int atfed = 0;
		//vízszintesen
		if (jp1.x < balalso.x) {
	        atfed = balalso.x - jp3.x;
	    } else {
	        atfed = jp1.x - jobbfelso.x;
	    }
		
		if(atfed <= 0)
			xmetszi = true;
		
		//függőlegesen
		if (jp1.y < balalso.y) {
	        atfed = balalso.y - jp3.y;
	    } else {
	        atfed = jp1.y - jobbfelso.y;
	    }
		
		if(atfed <= 0)
			ymetszi = true;
		
			
		return (ymetszi & xmetszi);
	}

	public void setMeret_h(int h){
		meret_h = h;
	}
	
	public void setMeret_v(int v){
		meret_v = v;
	}
	
	public void setPozicio(Pozicio poz){
		palyaElemPoz = new Pozicio(poz.x,poz.y);
	}
	
	public boolean getBal(){
		return bal;
	}
	
	public boolean getJobb(){
		return jobb;
	}
	
	public boolean getFent(){
		return fent;
	}
	
	public boolean getLent(){
		return lent;
	}
	
	public void setJobb(boolean jobb) {
		this.jobb = jobb;
	}
	
	public void setBal(boolean bal) {
		this.bal = bal;
	}
	
	public void setFent(boolean fent) {
		this.fent = fent;
	}
	
	public void setLent(boolean lent) {
		this.lent = lent;
	}
}
