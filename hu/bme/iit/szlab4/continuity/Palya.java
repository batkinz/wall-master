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
	
	//Áttekintő nézetben mozgatja a Tile-okat
	public void attekintoMozgat(Billentyu b) {
		if(b.isAllFalse()){
			return;
		}
		
		boolean megvan = false; //segédváltozó a keresésekhez
		int i = 0;				//ciklusváltozó a keresésekhez
		Tile tempT = null;		//segédváltozó mozgatható Tile kereséséhez és mozgatásához
		Tile uresT = null;		//kell majd az üres Tile is
		
		while(!megvan){					//üres Tile megkeresése
			uresT = tilelista.get(i);	//kiveszünk egyet
			megvan = uresT.getUres();		//megnézzük, hogy üres-e, ha igen kész is vagyunk
			
			i++;						//ha mégsem, nézzük a következőt
		}
		
		for(int j = 0; j < jatekosok.size(); j++){
			if(!jatekosok.get(j).getReszletes()){		//ha áttekintő nézetben van
				megvan = false;					//segédváltozók előkészítése
				int irany = 0;		//aktuálisan vizsgált Játékos billentyűi alapján meghatározott irány
									//fel = 1, le = 2, bal = 3, jobb = 4
				if(j == 0){			//irány beállítása a Tile-ok mozgathatóságának vizsgálatához
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
				while(!megvan && (i < tilelista.size())){		//első mozgatható Tile megkeresése és mozgatása (egyszerre egyet mozgatunk,
									//a többivel a következő tick-ben foglalkozunk	
					tempT = tilelista.get(i);
					megvan = tileMehet(uresT, tempT, irany);
					if(megvan)		//ha megvan el is mozgatjuk
						tileMozgat(uresT, tempT);
					
					i++;			//ha nincs meg nézzük a következőt
				}
			}
		}
	}
	
	public boolean compareTile(Tile forras, Tile cel) {//Tile-ok bal alsó sarkát tároljuk, minden koordináta 
														//a megszokott koordinátarednszer szerint mozog
														//egy Tile 300 széles és 500 magas		
		
		if (cel.getUres()){
			return false;
		}	
		
		int x = 0;	//segédváltozók
		int y = 0;
		
		boolean sflag = false; // segédflag a pontvizsgálathoz
		
		List<Boolean> forrasRes = new ArrayList<Boolean>(); 
		List<Boolean> celRes = new ArrayList<Boolean>(); 
		Palyaelem temp;
		Pozicio tempPoz = new Pozicio();
		
		if(forras.getKoord().x == cel.getKoord().x){//egymás felett vannak
			if(forras.getKoord().y < cel.getKoord().y){//a cél van felül
				for(int i = 0; i < cel.getSzelesseg(); i++){
					x = i;
					
					y = cel.getMagassag()-1;	//forrás tetején lévőket nézzük
					sflag = false;
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getFent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
							tempPoz.y = temp.getKoord().y - temp.getMeretV()/2;
							
							if( ((tempPoz.x <= x) && ((tempPoz.x + temp.getMeretH()) >= x)) 
									&& ((tempPoz.y <= y) && ((tempPoz.y + temp.getMeretV()) >= y))){
								sflag = true;
								break;
							}
						}
					}
					
					forrasRes.add(sflag);
					
					y = 0;	//cél alján lévőket nézzük
					sflag = false;
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getLent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
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
			} else {								//a forrás van felül
				for(int i = 0; i < cel.getSzelesseg(); i++){
					x = i;
					sflag = false;
					y = 0;	//forrás alján lévőket nézzük
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getLent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
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
					y = cel.getMagassag()-1;	//cél tetején lévőket nézzük
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getFent()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
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
		} else if(forras.getKoord().y == cel.getKoord().y){//egymás mellett vannak
			if(forras.getKoord().x < cel.getKoord().x){//a cél van jobbra
				for(int i = 0; i < cel.getMagassag(); i++){
					y = i;
					sflag = false;
					x = cel.getSzelesseg()-1;	//forrás jobb oldalán lévőket nézzük
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getJobb()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
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
					x = 0;	//cél bal oldalán lévőket nézzük
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getBal()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
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
			} else {								//a forrás van jobbra
				for(int i = 0; i < cel.getMagassag(); i++){
					y = i;
					sflag = false;
					x = 0;	//forrás bal oldalán lévőket nézzük
					for(int j = 0; j < forras.getListaHossz(); j++){
						temp = forras.getPalyaelem(j);
						if(temp.getBal()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
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
					x = cel.getSzelesseg()-1;	//cél jobb oldalán lévőket nézzük
					for(int j = 0; j < cel.getListaHossz(); j++){
						temp = cel.getPalyaelem(j);
						if(temp.getJobb()){
							tempPoz.x = temp.getKoord().x - temp.getMeretH()/2;	//vizsgált Pályaelem bal alsó sarka
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
		
		//miután végignézük a megfelelő széleket és elmentettük minden egyes pontra azt, hogy van-e ott Pályaelem vagy nincs
		//végignézzük a két szomszédos szél eredményhalmazát, ha egyeznek, átmehet a Játékos, ha nem, akkor pedig nem
		
			
			for(int i = 0; i < forrasRes.size(); i++){
				if(forrasRes.get(i) != celRes.get(i)){
					return false;
				}
			}
		
		return true;
	}

	public DPalya init(int pszam) throws Exception{
		
		// AJTÓ MÉRETEI
		// Ajtó szélessége
		Jatekos seged = new Jatekos(5);
		int ajto_x = seged.getMeret();
		// Ajtó magassága
		int ajto_y = 2*seged.getMeret();
	

		// KULCS MÉRETEI
		// Kulcs szélessége
		int kulcs_x = 30;
		// Kulcs magassága
		int kulcs_y = 16;
		
		palyaSzam = pszam;

		//System.out.println(pszam);
		jatekosok = new ArrayList<Jatekos>();
		tilelista = new ArrayList<Tile>();
		
		Tile tempT = null;		//segédváltozók
		Tile kezdo = null;
		
		DTile tempDT = null;
		DJatekos tempDJ = null;
		DPalya tempDP = new DPalya();
		List<DTile> dtList = new ArrayList<DTile>();
		List<DJatekos> djList = new ArrayList<DJatekos>();
		
		FileReader fr = null;	//segédváltozók a beolvasáshoz	
		BufferedReader br = null;
		String line = null;
		String[] splitline = null;
		Pozicio tempPoz = null;
		
		File f = new File(Palya.class.getProtectionDomain().getCodeSource().getLocation().getPath()); //pályákat tartalmazó mappa megnyitása
		f = f.getParentFile();
		File dir = new File(f.getPath() + "\\palyak");	//megvan
		
		File[] children = dir.listFiles();		//pályák mappái
		File aktTeszt = null;					//aktuális pálya
		
		int m = 0;
		if(children == null){
			throw new Exception("Olvasasi hiba, a konyvtár nem letezik.");
		} else {			
			for(; m < children.length; m++){	//aktuális teszteset megkeresése
				if(!children[m].getName().equals(".svn")){
					if(Integer.parseInt(children[m].getName()) == pszam){
						aktTeszt = children[m];
						break;
					}
				}
			}
			if(palyaSzamMax == 0){//0ára van inicializálva, az első init növeli valamennyire, minden további békénhagyja
				for(int k = 0; k < children.length; k++){
					if((!children[k].getName().equals(".svn")) && (!children[k].getName().equals("mentes.txt"))){
						palyaSzamMax++;
					}
				}
			}
		}	
		
		children = aktTeszt.listFiles();		//aktuális teszteset mappájának tartalma
												//(n+1 fájl, ahol n a Tile-ok száma, a +1 pedig a Játékosokat leíró fájl)
		
		for(int i = 0; i < children.length; i++){	//minden fájlra, ami nem a Játékosokat leíró
			if(children[i].getName().equals(".svn")){
				
			} else if(children[i].getName().equals("jatekos.txt")){
				m = i;
			} else {				
				fr = new FileReader(children[i]);
				br = new BufferedReader(fr);
				
				if(children[i].getName().equals("tile1.txt")){	//pozício "beolvasása"
					tempT = new Tile(1);	//aktuális fájlban tárolt Tile
					tempPoz = new Pozicio(0, 1);
				} else if(children[i].getName().equals("tile2.txt")){
					tempT = new Tile(2);	//aktuális fájlban tárolt Tile
					tempPoz = new Pozicio(1, 1);
				} else if(children[i].getName().equals("tile3.txt")){
					tempT = new Tile(3);	//aktuális fájlban tárolt Tile
					tempPoz = new Pozicio(0, 0);
				}
				
				tempT.setKoord(tempPoz);	//beállítása
				
				line = br.readLine();	//beolvassuk kezdő-e				
				if(Integer.parseInt(line) == 1){//beállítjuk és elmentjük melyik az
					tempT.setKezdo(true);
					kezdo = tempT;
				}				
				else tempT.setKezdo(false);
				
				while (true) {
					line = br.readLine();		//soronként tároljuk őket
					if(line == null) break;		//ha vége a file-nak kilépünk
					splitline = line.split(" ");
		
					Palyaelem tempP = null;		//segédváltozó
					
					if(splitline[0].equals("a")){	//példányosítás első paramétertől függően
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
					
					//feltöltés pozícióval és mérettel
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
					
					tempT.init(tempP);	//átadás a Tile-nak
				}
				tilelista.add(tempT);	//felvesszük az elkészült Tile-t a listába
				tempDT = new DTile(tempT);
				dtList.add(tempDT);
			}
		}

		
		tempT = new Tile(4);		//üres Tile létrehozása
		tempPoz = new Pozicio(1, 0);
		tempT.setKoord(tempPoz);
		tempT.setKezdo(false);
		tempT.setUres();
		tilelista.add(tempT);	//felvesszük az elkészült Tile-t a listába

		fr = new FileReader(children[m]);	//Játékosok bolvasása
		br = new BufferedReader(fr);
		
		m = 0;
		while (true) {	//csak annyit példányosítunk ahány van a fájlban
			line = br.readLine();				
			if(line == null) break;				//ha vége a file-nak kilépünk
			splitline = line.split(" ");
			
			Jatekos tempJ = new Jatekos(m+1);	//létrehozás
			tempPoz = new Pozicio(Integer.parseInt(splitline[0]), Integer.parseInt(splitline[1]));	//beolvasás
			tempJ.setKezdoKoord(tempPoz);	//beállítás
			tempJ.setAktKoord(tempPoz);		//aktKoord = kezdoKoord
			tempJ.setKezdoTile(kezdo);	//kezdő Tile beállítása
			tempJ.setAktTile(kezdo);		//létrehozáskor aktTile = kezdoTile
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
	
	//A két Játékos ugrását kezeli
	public void mozgatLep(Billentyu b) {
		for(int i = 0; i < jatekosok.size(); i++){
			if(jatekosok.get(i).getReszletes()){ 	//i. Játékos részletes nézetben van					
				jatekosok.get(i).reszletesMozgat(b);
			}
		}
	}
	
	//A két Játékos ugrását kezeli 
	public void mozgatUgras(Billentyu b) {
		for(int i = 0; i < jatekosok.size(); i++){
			if(jatekosok.get(i).getReszletes())	//i. Játékos részletes nézetben van
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
		for(Jatekos j : jatekosok){		//minden Játékosra
			for(Tile t : tilelista){	//megnézzük melyik Tile-al
				if(j.getAktKoord().y-j.getMeret() < 0 && j.getAktTile().getKoord().y == 0) { j.meghal(); }
				if(t.metszette(j)){		
					//ütközött
					if(t.interakt(j, compareTile(j.getAktTile(), t))){	//ha ütközött, kezeljük a hasonlítás eredményének függvényében
						j.meghal();		//ha true-val tér vissza az ütközés kezelője, akkor a Játékosnak meg kell halnia
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
		if(irany == 1){								//irány alapján kiszámoljuk, hogy adott irányban milyen koordinátán kell lennie 
			temp = ures.getKoord();					//a mozgatandó Tile-nak
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
		
		if( (temp.x == mozgatando.getKoord().x) && (temp.y == mozgatando.getKoord().y) ){	//megnézzük, hogy tényleg ott van-e
			return true;		//ha ott van akkor mehet
		} else return false;	//ha nem, akkor abba az irányba nem mozdulhatunk
	}

	public void tileMozgat(Tile ures, Tile mozgatando){
		Pozicio temp;	//segédválozó a cseréhez
				
		temp = ures.getKoord();		//a mozgatás csak a 2 Tile cseréje
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
		File f = new File(Palya.class.getProtectionDomain().getCodeSource().getLocation().getPath()); //pályákat tartalmazó mappa megnyitása
		f = f.getParentFile();
		File dir = new File(f.getPath() + "\\palyak");	//megvan
		
		File[] children = dir.listFiles();		//pályák mappái
		
		File mentes = null;					//aktuális pálya
			
		if(children == null){
			throw new Exception("Olvasasi hiba, a fájl nem letezik.");
		} else {			
			for(int m = 0; m < children.length; m++){	//mentés fájl megkeresése
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
		
		FileWriter fw = new FileWriter(mentes);	//segédváltozók a beolvasáshoz	
		PrintWriter pw = new PrintWriter(fw);
		pw.println(palyaSzam);
		pw.close();
	}
	
	public DPalya betoltes() throws Exception{
		File f = new File(Palya.class.getProtectionDomain().getCodeSource().getLocation().getPath()); //pályákat tartalmazó mappa megnyitása
		f = f.getParentFile();
		File dir = new File(f.getPath() + "\\palyak");	//megvan
		
		File[] children = dir.listFiles();		//pályák mappái
		
		File mentes = null;					//aktuális pálya
			
		if(children == null){
			throw new Exception("Olvasasi hiba, a fájl nem letezik.");
		} else {			
			for(int m = 0; m < children.length; m++){	//mentés fájl megkeresése
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
			FileWriter fw = new FileWriter(mentes);	//segédváltozók a beolvasáshoz	
			PrintWriter pw = new PrintWriter(fw);
			pw.println(1);
			pw.close();
		}	
		
		FileReader fr = new FileReader(mentes);	//segédváltozók a beolvasáshoz	
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
		
		// ha nem értünk még a pályák végére, akkor jön a következő, ha végigértünk, akkor betöltjük majd újból az utolsó pályát
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
