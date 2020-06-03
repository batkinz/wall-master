package hu.bme.iit.szlab4.continuity;

public abstract class Palyaelem {
	// hozz��r-e a tile valamelyik oldal�hoz
	protected boolean bal,jobb,fent,lent;
	// v�zszintes kiterjed�s
	protected int meret_h;
	// f�gg�leges kiterjed�s
	protected int meret_v;
	// a p�lyaelem baj als� cs�cske
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
	
	// kisz�molja, hogy a j�t�kos metszi-e a p�lyaelemet
	public boolean metszette(Jatekos j){
		
		/*A j�t�kos a 4 koordin�t�j�t kisz�moljuk
		 * - alap�rtelmezetten a k�z�ps� 
		 * az �tl�that�s�g �rdek�ben:
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
	
		 
		
		// j�t�kos aktu�lis koordin�t�ja
		Pozicio jAkt = j.getAktKoord();
		int jMeret = j.getMeret()/2;
		
		// bal als� sarka
		Pozicio jp1 = new Pozicio(jAkt.x-jMeret,jAkt.y-2*jMeret);
		// jobb fels� sarka
		Pozicio jp3 = new Pozicio(jAkt.x+jMeret,jAkt.y+2*jMeret);

		
		// a p�lyaelem bal als� sarka
		Pozicio balalso = new Pozicio(palyaElemPoz.x-meret_h/2,palyaElemPoz.y-meret_v/2);
						
		// a p�lyaelem jobb fels� sarka
		Pozicio jobbfelso = new Pozicio(palyaElemPoz.x+meret_h/2, palyaElemPoz.y+meret_v/2);
		boolean xmetszi = false;
		boolean ymetszi = false;
		int atfed = 0;
		//v�zszintesen
		if (jp1.x < balalso.x) {
	        atfed = balalso.x - jp3.x;
	    } else {
	        atfed = jp1.x - jobbfelso.x;
	    }
		
		if(atfed <= 0)
			xmetszi = true;
		
		//f�gg�legesen
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
