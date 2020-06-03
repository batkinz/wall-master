package hu.bme.iit.szlab4.continuity;

public class Billentyu {
	//j1 játékos balra billentyűje
	public boolean j1_jobb;
	//j1 játékos jobbra billentyűje
	public boolean j1_bal;
	//j1 játékos ugrás billentyűje
	public boolean j1_fel;
	//j1 játékos les billentyűje
	public boolean j1_le;
	//j1 játékos nézetváltás billentyűje
	public boolean j1_nezet;
	//j2 játékos balra billentyűje
	public boolean j2_jobb;
	//j2 játékos jobbra billentyűje
	public boolean j2_bal;
	//j2 játékos ugrás billentyűje
	public boolean j2_fel;
	//j2 játékos les billentyűje
	public boolean j2_le;
	//j2 játékos nézetváltás billentyűje
	public boolean j2_nezet;
	
	// üres konstrukor
	public Billentyu(){
		j1_jobb=false;
		j1_bal=false;
		j1_fel=false;
		j1_le=false;
		j1_nezet=false;
		j2_jobb=false;
		j2_bal=false;
		j2_fel=false;
		j2_le=false;
		j2_nezet=false;
	}
	
	
	//konstrukor
	Billentyu(boolean j1jobb, boolean j1bal,boolean j1fel,boolean j1le,boolean j1nezet,boolean j2jobb, boolean j2bal,boolean j2fel,boolean j2le,boolean j2nezet){
		j1_jobb=j1jobb;
		j1_bal=j1bal;
		j1_fel=j1fel;
		j1_le=j1le;
		j1_nezet=j1nezet;
		j2_jobb=j2jobb;
		j2_bal=j2bal;
		j2_fel=j2fel;
		j2_le=j2le;
		j2_nezet=j2nezet;
	}

	public boolean isAllFalse(){
		return !(j1_jobb || j1_bal || j1_fel || j1_le || j1_nezet || j2_jobb || j2_bal || j2_fel || j2_le || j2_nezet);
	}
	
}
