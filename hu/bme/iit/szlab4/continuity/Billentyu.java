package hu.bme.iit.szlab4.continuity;

public class Billentyu {
	//j1 j�t�kos balra billenty�je
	public boolean j1_jobb;
	//j1 j�t�kos jobbra billenty�je
	public boolean j1_bal;
	//j1 j�t�kos ugr�s billenty�je
	public boolean j1_fel;
	//j1 j�t�kos les billenty�je
	public boolean j1_le;
	//j1 j�t�kos n�zetv�lt�s billenty�je
	public boolean j1_nezet;
	//j2 j�t�kos balra billenty�je
	public boolean j2_jobb;
	//j2 j�t�kos jobbra billenty�je
	public boolean j2_bal;
	//j2 j�t�kos ugr�s billenty�je
	public boolean j2_fel;
	//j2 j�t�kos les billenty�je
	public boolean j2_le;
	//j2 j�t�kos n�zetv�lt�s billenty�je
	public boolean j2_nezet;
	
	// �res konstrukor
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
