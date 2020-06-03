package hu.bme.iit.szlab4.continuity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Palya {
	private List<Jatekos> jatekosok;
	
	private List<Tile> tilelista;
	
	private int palyaSzamMax;
	private int palyaSzam;
	private boolean vege;
	
	public Palya(){
		palyaSzamMax = 0;
		palyaSzam = 0;
		vege = false;
	}
	
	public boolean getVege(){
		return vege;
	}
	
	public List<Jatekos> getJatekos(){
		return jatekosok;
	}
	
	public List<Tile> getTile(){
		return tilelista;
	}
	
	public int getPalyaSzam(){
		return palyaSzam;
		
	}
	
	//�ttekint� n�zetben mozgatja a Tile-okat
	public void attekintoMozgat(Billentyu b) {
		if(b.isAllFalse()){
			return;
		}
		
		boolean megvan = false; //seg�dv�ltoz� a keres�sekhez
		int i = 0;				//ciklusv�ltoz� a keres�sekhez
		Tile tempT = null;		//seg�dv�ltoz� mozgathat� Tile keres�s�hez �s mozgat�s�hoz
		Tile uresT = null;		//kell majd az �res Tile is
		
		while(!megvan){					//�res Tile megkeres�se
			uresT = tilelista.get(i);	//kivesz�nk egyet
			megvan = uresT.getUres();		//megn�zz�k, hogy �res-e, ha igen k�sz is vagyunk
			
			i++;						//ha m�gsem, n�zz�k a k�vetkez�t
		}
		
		for(int j = 0; j < jatekosok.size(); j++){
			if(!jatekosok.get(j).getReszletes()){		//ha �ttekint� n�zetben van
				megvan = false;					//seg�dv�ltoz�k el�k�sz�t�se
				int irany = 0;		//aktu�lisan vizsg�lt J�t�kos billenty�i alapj�n meghat�rozott ir�ny
									//fel = 1, le = 2, bal = 3, jobb = 4
				if(j == 0){			//ir�ny be�ll�t�sa a Tile-ok mozgathat�s�g�nak vizsg�lat�hoz
					if(b.j1_fel){
						irany = 1;
					} else if(b.j1_le){
						irany = 2;
					} else if(b.j1_bal){
						irany = 3;
					} else if(b.j1_jobb){
						irany = 4;
					}
				} else {
					if(b.j2_fel){
						irany = 1;
					} else if(b.j2_le){
						irany = 2;
					} else if(b.j2_bal){
						irany = 3;
					} else if(b.j2_jobb){
						irany = 4;
					}
				}
				
				i = 0;
				while(!megvan && (i < tilelista.size())){		//els� mozgathat� Tile megkeres�se �s mozgat�sa (egyszerre egyet mozgatunk,
									//a t�bbivel a k�vetkez� tick-ben foglalkozunk	
					tempT = tilelista.get(i);
					megvan = tileMehet(uresT, tempT, irany);
					if(megvan)		//ha megvan el is mozgatjuk
						tileMozgat(uresT, tempT);
					
					i++;			//ha nincs meg n�zz�k a k�vetkez�t
				}
			}
		}
	}
	
	public boolean compareTile(Tile forras, Tile cel) {//Tile-ok bal als� sark�t t�roljuk, minden koordin�ta 
														//a megszokott koordin�tarednszer szerint mozog
														//egy Tile 300 sz�les �s 500 magas		
		
		if (cel.getUres()){
			return false;
		}	
		
		int x = 0;	//seg�dv�ltoz�k
		int y = 0;
		
		boolean sflag = false; // seg�dflag a pontvizsg�lathoz
		
		List<Boolean> forrasRes = new ArrayList<Boolean>(); 
		List<Boolean> celRes = new ArrayList<Boolean>(); 
		Palyaelem temp;
		Pozicio tempPoz = new Pozicio();
		
		if(forras.getKoord().x == cel.getKoord().x){//egym�s felett vannak
			if(forras.getKoord().y < cel.getKoord().y){//a c�l van fel�l
				for(int i = 0; i < cel.getSzelesseg(); i++){
					x = i;
					
					y = cel.getMagassag()-1;	//forr�s tetej�n l�v�ket n�zz�k
					sflag = false;
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getFent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					
					forrasRes.add(sflag);
					
					y = 0;	//c�l alj�n l�v�ket n�zz�k
					sflag = false;
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getLent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					
					celRes.add(sflag);
				}
			} else {								//a forr�s van fel�l
				for(int i = 0; i < cel.getSzelesseg(); i++){
					x = i;
					sflag = false;
					y = 0;	//forr�s alj�n l�v�ket n�zz�k
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getLent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					forrasRes.add(sflag);
					sflag = false;
					y = cel.getMagassag()-1;	//c�l tetej�n l�v�ket n�zz�k
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getFent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					celRes.add(sflag);
				}							
			}
		} else if(forras.getKoord().y == cel.getKoord().y){//egym�s mellett vannak
			if(forras.getKoord().x < cel.getKoord().x){//a c�l van jobbra
				for(int i = 0; i < cel.getMagassag(); i++){
					y = i;
					sflag = false;
					x = cel.getSzelesseg()-1;	//forr�s jobb oldal�n l�v�ket n�zz�k
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getJobb()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					forrasRes.add(sflag);
					sflag = false;
					x = 0;	//c�l bal oldal�n l�v�ket n�zz�k
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getBal()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					celRes.add(sflag);
				}
			} else {								//a forr�s van jobbra
				for(int i = 0; i < cel.getMagassag(); i++){
					y = i;
					sflag = false;
					x = 0;	//forr�s bal oldal�n l�v�ket n�zz�k
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getBal()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					forrasRes.add(sflag);
					sflag = false;
					x = cel.getSzelesseg()-1;	//c�l jobb oldal�n l�v�ket n�zz�k
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getJobb()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsg�lt P�lyaelem bal als� sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}	
					celRes.add(sflag);
				}
			}
		}
		
		//miut�n v�gign�z�k a megfelel� sz�leket �s elmentett�k minden egyes pontra azt, hogy van-e ott P�lyaelem vagy nincs
		//v�gign�zz�k a k�t szomsz�dos sz�l eredm�nyhalmaz�t, ha egyeznek, �tmehet a J�t�kos, ha nem, akkor pedig nem
		
			
			for(int i = 0; i < forrasRes.size(); i++){
				if(forrasRes.get(i) != celRes.get(i)){
					return false;
				}
			}
		
		return true;
	}

	public DPalya init(int pszam) throws Exception{
		
		// AJT� M�RETEI
		// Ajt� sz�less�ge
		Jatekos seged = new Jatekos(5);
		int ajto_x = seged.getMeret();
		// Ajt� magass�ga
		int ajto_y = 2*seged.getMeret();
	

		// KULCS M�RETEI
		// Kulcs sz�less�ge
		int kulcs_x = 30;
		// Kulcs magass�ga
		int kulcs_y = 16;
		
		palyaSzam = pszam;

		//System.out.println(pszam);
		jatekosok = new ArrayList<Jatekos>();
		tilelista = new ArrayList<Tile>();
		
		Tile tempT = null;		//seg�dv�ltoz�k
		Tile kezdo = null;
		
		DTile tempDT = null;
		DJatekos tempDJ = null;
		DPalya tempDP = new DPalya();
		List<DTile> dtList = new ArrayList<DTile>();
		List<DJatekos> djList = new ArrayList<DJatekos>();
		
		FileReader fr = null;	//seg�dv�ltoz�k a beolvas�shoz	
		BufferedReader br = null;
		String line = null;
		String[] splitline = null;
		Pozicio tempPoz = null;
		
		File f = new File(Palya.class.getProtectionDomain().getCodeSource().getLocation().getPath()); //p�ly�kat tartalmaz� mappa megnyit�sa
		f = f.getParentFile();
		File dir = new File(f.getPath() + "\\palyak");	//megvan
		
		File[] children = dir.listFiles();		//p�ly�k mapp�i
		File aktTeszt = null;					//aktu�lis p�lya
		
		int m = 0;
		if(children == null){
			throw new Exception("Olvasasi hiba, a konyvt�r nem letezik.");
		} else {			
			for(; m < children.length; m++){	//aktu�lis teszteset megkeres�se
				if(!children[m].getName().equals(".svn")){
					if(Integer.parseInt(children[m].getName()) == pszam){
						aktTeszt = children[m];
						break;
					}
				}
			}
			if(palyaSzamMax == 0){//0�ra van inicializ�lva, az els� init n�veli valamennyire, minden tov�bbi b�k�nhagyja
				for(int k = 0; k < children.length; k++){
					if((!children[k].getName().equals(".svn")) && (!children[k].getName().equals("mentes.txt"))){
						palyaSzamMax++;
					}
				}
			}
		}	
		
		children = aktTeszt.listFiles();		//aktu�lis teszteset mapp�j�nak tartalma
												//(n+1 f�jl, ahol n a Tile-ok sz�ma, a +1 pedig a J�t�kosokat le�r� f�jl)
		
		for(int i = 0; i < children.length; i++){	//minden f�jlra, ami nem a J�t�kosokat le�r�
			if(children[i].getName().equals(".svn")){
				
			} else if(children[i].getName().equals("jatekos.txt")){
				m = i;
			} else {				
				fr = new FileReader(children[i]);
				br = new BufferedReader(fr);
				
				if(children[i].getName().equals("tile1.txt")){	//poz�cio "beolvas�sa"
					tempT = new Tile(1);	//aktu�lis f�jlban t�rolt Tile
					tempPoz = new Pozicio(0, 1);
				} else if(children[i].getName().equals("tile2.txt")){
					tempT = new Tile(2);	//aktu�lis f�jlban t�rolt Tile
					tempPoz = new Pozicio(1, 1);
				} else if(children[i].getName().equals("tile3.txt")){
					tempT = new Tile(3);	//aktu�lis f�jlban t�rolt Tile
					tempPoz = new Pozicio(0, 0);
				}
				
				tempT.setKoord(tempPoz);	//be�ll�t�sa
				
				line = br.readLine();	//beolvassuk kezd�-e				
				if(Integer.parseInt(line) == 1){//be�ll�tjuk �s elmentj�k melyik az
					tempT.setKezdo(true);
					kezdo = tempT;
				}				
				else tempT.setKezdo(false);
				
				while (true) {
					line = br.readLine();		//soronk�nt t�roljuk �ket
					if(line == null) break;		//ha v�ge a file-nak kil�p�nk
					splitline = line.split(" ");
		
					Palyaelem tempP = null;		//seg�dv�ltoz�
					
					if(splitline[0].equals("a")){	//p�ld�nyos�t�s els� param�tert�l f�gg�en
						tempP = new Ajto( Integer.parseInt(splitline[5]) );
						tempP.setMeret_h(ajto_x);
						tempP.setMeret_v(ajto_y);
					} else if(splitline[0].equals("f")){
						tempP = new Fal();
						tempP.setMeret_h(Integer.parseInt(splitline[3]));
						tempP.setMeret_v(Integer.parseInt(splitline[4]));
					} else if(splitline[0].equals("k")){
						tempP = new Kulcs();
						tempP.setMeret_h(kulcs_x);
						tempP.setMeret_v(kulcs_y);
					}
					
					//felt�lt�s poz�ci�val �s m�rettel
					tempPoz = new Pozicio(Integer.parseInt(splitline[1]), Integer.parseInt(splitline[2]));
					tempP.setPozicio(tempPoz);
					
					if(tempP.getKoord().y + tempP.getMeretV()/2 >= tempT.getMagassag()-1){
						tempP.setFent(true);
					}
					
					if(tempP.getKoord().y - tempP.getMeretV()/2 <= 0){
						tempP.setLent(true);
					}
					
					if(tempP.getKoord().x + tempP.getMeretH()/2 >= tempT.getSzelesseg()-1){
						tempP.setJobb(true);
					}
					
					if(tempP.getKoord().x - tempP.getMeretH()/2 <= 0){
						tempP.setBal(true);
					}
					
					tempT.init(tempP);	//�tad�s a Tile-nak
				}
				tilelista.add(tempT);	//felvessz�k az elk�sz�lt Tile-t a list�ba
				tempDT = new DTile(tempT);
				dtList.add(tempDT);
			}
		}

		
		tempT = new Tile(4);		//�res Tile l�trehoz�sa
		tempPoz = new Pozicio(1, 0);
		tempT.setKoord(tempPoz);
		tempT.setKezdo(false);
		tempT.setUres();
		tilelista.add(tempT);	//felvessz�k az elk�sz�lt Tile-t a list�ba

		fr = new FileReader(children[m]);	//J�t�kosok bolvas�sa
		br = new BufferedReader(fr);
		
		m = 0;
		while (true) {	//csak annyit p�ld�nyos�tunk ah�ny van a f�jlban
			line = br.readLine();				
			if(line == null) break;				//ha v�ge a file-nak kil�p�nk
			splitline = line.split(" ");
			
			Jatekos tempJ = new Jatekos(m+1);	//l�trehoz�s
			tempPoz = new Pozicio(Integer.parseInt(splitline[0]), Integer.parseInt(splitline[1]));	//beolvas�s
			tempJ.setKezdoKoord(tempPoz);	//be�ll�t�s
			tempJ.setAktKoord(tempPoz);		//aktKoord = kezdoKoord
			tempJ.setKezdoTile(kezdo);	//kezd� Tile be�ll�t�sa
			tempJ.setAktTile(kezdo);		//l�trehoz�skor aktTile = kezdoTile
			jatekosok.add(tempJ);
			tempDJ = new DJatekos(tempJ);
			djList.add(tempDJ);
			m++;
		}
		
		tempDP.setdtileok(dtList);
		tempDP.setdjatekosok(djList);
		br.close();
		vege = false;
		return tempDP;
	}
	
	//A k�t J�t�kos ugr�s�t kezeli
	public void mozgatLep(Billentyu b) {
		for(int i = 0; i < jatekosok.size(); i++){
			if(jatekosok.get(i).getReszletes()){ 	//i. J�t�kos r�szletes n�zetben van					
				jatekosok.get(i).reszletesMozgat(b);
			}
		}
	}
	
	//A k�t J�t�kos ugr�s�t kezeli 
	public void mozgatUgras(Billentyu b) {
		for(int i = 0; i < jatekosok.size(); i++){
			if(jatekosok.get(i).getReszletes())	//i. J�t�kos r�szletes n�zetben van
				jatekosok.get(i).ugras(b);	// ugrik...
		}
	}
	
	public void utkozesVizsgalat() {
		utkozesVizsgalatPalyaelem();
		utkozesVizsgalatTile();
	}
	
	public void utkozesVizsgalatPalyaelem() {
		for(int i = 0; i < jatekosok.size(); i++){
			Tile tempT = jatekosok.get(i).getAktTile();
			int c = 0;
			for(int j = 0; j < tempT.getListaHossz(); j++, c++){
				Palyaelem tempP = tempT.getPalyaelem(j);
				//System.out.println(c);
				if(tempP.metszette(jatekosok.get(i))){
					if(tempP.interakt(jatekosok.get(i))){
						vege = true;
					}
					if(tempP.getClass().equals(Fal.class)) j=-1;
				}
			}
		}
	}
	
	
	public void utkozesVizsgalatTile() {
		for(Jatekos j : jatekosok){		//minden J�t�kosra
			for(Tile t : tilelista){	//megn�zz�k melyik Tile-al
				if(j.getAktKoord().y-j.getMeret() < 0 && j.getAktTile().getKoord().y == 0) { j.meghal(); }
				if(t.metszette(j)){		
					//�tk�z�tt
					if(t.interakt(j, compareTile(j.getAktTile(), t))){	//ha �tk�z�tt, kezelj�k a hasonl�t�s eredm�ny�nek f�ggv�ny�ben
						j.meghal();		//ha true-val t�r vissza az �tk�z�s kezel�je, akkor a J�t�kosnak meg kell halnia
					}
				}
			}
		}
	}
		
	public boolean tileMehet(Tile ures, Tile mozgatando, int irany) {
		if(ures==null || mozgatando==null || irany==0){
			return false;
		}
		Pozicio temp = null;
		//fel = 1, le = 2, bal = 3, jobb = 4
		if(irany == 1){								//ir�ny alapj�n kisz�moljuk, hogy adott ir�nyban milyen koordin�t�n kell lennie 
			temp = ures.getKoord();					//a mozgatand� Tile-nak
			temp.y = temp.y - 1;					
		} else if(irany == 2){
			temp = ures.getKoord();
			temp.y = temp.y + 1;		
		} else if(irany == 3){
			temp = ures.getKoord();
			temp.x = temp.x + 1;		
		} else if(irany == 4){
			temp = ures.getKoord();
			temp.x = temp.x - 1;		
		}
		
		if( (temp.x == mozgatando.getKoord().x) && (temp.y == mozgatando.getKoord().y) ){	//megn�zz�k, hogy t�nyleg ott van-e
			return true;		//ha ott van akkor mehet
		} else return false;	//ha nem, akkor abba az ir�nyba nem mozdulhatunk
	}

	public void tileMozgat(Tile ures, Tile mozgatando){
		Pozicio temp;	//seg�dv�loz� a cser�hez
				
		temp = ures.getKoord();		//a mozgat�s csak a 2 Tile cser�je
		ures.setKoord(mozgatando.getKoord());
		mozgatando.setKoord(temp);
	}

	public void mozgat(Billentyu b){
		mozgatUgras(b);
		mozgatLep(b);
		attekintoMozgat(b);
		if (b.j1_nezet){
			if(jatekosok.size()>0){
				jatekosok.get(0).setReszletes(!jatekosok.get(0).getReszletes());
			}
		}
		if (b.j2_nezet){
			if(jatekosok.size()>1){
				jatekosok.get(1).setReszletes(!jatekosok.get(1).getReszletes());
			}
		}
	}

	public void mentes() throws Exception{
		File f = new File(Palya.class.getProtectionDomain().getCodeSource().getLocation().getPath()); //p�ly�kat tartalmaz� mappa megnyit�sa
		f = f.getParentFile();
		File dir = new File(f.getPath() + "\\palyak");	//megvan
		
		File[] children = dir.listFiles();		//p�ly�k mapp�i
		
		File mentes = null;					//aktu�lis p�lya
			
		if(children == null){
			throw new Exception("Olvasasi hiba, a f�jl nem letezik.");
		} else {			
			for(int m = 0; m < children.length; m++){	//ment�s f�jl megkeres�se
				if(!children[m].getName().equals(".svn")){
					if(children[m].getName().equals("mentes.txt")){
						mentes = children[m];
						break;
					}
				}
			}
		}	
		
		if(mentes == null){
			mentes = new File(dir.getPath() + "\\mentes.txt");
			mentes.createNewFile();
		}
		
		FileWriter fw = new FileWriter(mentes);	//seg�dv�ltoz�k a beolvas�shoz	
		PrintWriter pw = new PrintWriter(fw);
		pw.println(palyaSzam);
		pw.close();
	}
	
	public DPalya betoltes() throws Exception{
		File f = new File(Palya.class.getProtectionDomain().getCodeSource().getLocation().getPath()); //p�ly�kat tartalmaz� mappa megnyit�sa
		f = f.getParentFile();
		File dir = new File(f.getPath() + "\\palyak");	//megvan
		
		File[] children = dir.listFiles();		//p�ly�k mapp�i
		
		File mentes = null;					//aktu�lis p�lya
			
		if(children == null){
			throw new Exception("Olvasasi hiba, a f�jl nem letezik.");
		} else {			
			for(int m = 0; m < children.length; m++){	//ment�s f�jl megkeres�se
				if(!children[m].getName().equals(".svn")){
					if(children[m].getName().equals("mentes.txt")){
						mentes = children[m];
						break;
					}
				}
			}
		}	
		
		if(mentes == null){
			mentes = new File(dir.getPath() + "\\mentes.txt");
			mentes.createNewFile();
			FileWriter fw = new FileWriter(mentes);	//seg�dv�ltoz�k a beolvas�shoz	
			PrintWriter pw = new PrintWriter(fw);
			pw.println(1);
			pw.close();
		}	
		
		FileReader fr = new FileReader(mentes);	//seg�dv�ltoz�k a beolvas�shoz	
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		
		line = br.readLine();
		palyaSzam = Integer.parseInt(line);
		br.close();
		return init(palyaSzam);	
	}

	public DPalya ujJatek() throws Exception{
		palyaSzam = 1;
		return init(palyaSzam);
	}

	public DPalya kovPalya() throws Exception{
		
		// ha nem �rt�nk m�g a p�ly�k v�g�re, akkor j�n a k�vetkez�, ha v�gig�rt�nk, akkor bet�ltj�k majd �jb�l az utols� p�ly�t
		if(palyaSzam<palyaSzamMax){
			palyaSzam++;
		}
		
		//vege=false;
		return init(palyaSzam);
	}

	public DPalya resetPalya() throws Exception{
		return init(palyaSzam);
	}

	public int getAktPalyaSzam(){
		return palyaSzam;
	}
	
	public int getPalyakSzama(){
		return palyaSzamMax;
	}

}
