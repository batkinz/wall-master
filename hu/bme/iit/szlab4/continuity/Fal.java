package hu.bme.iit.szlab4.continuity;

public class Fal extends Palyaelem{
	
	@Override
	public boolean interakt(Jatekos j){
		korrekcio(j);
		return false;		
	}	
	
	// korrig�lja a j�t�kos poz�ci�j�t
	
	public boolean korrekcio(Jatekos j){

		// melyik falakat metszette a j�t�kos
		boolean jobb = false;
		boolean bal = false ;
		boolean fent = false;
		boolean lent = false;
		
		int jMeret = j.getMeret()/2;
		
		// A fal sarkai
		// bal als� sarka
		Pozicio ba = new Pozicio(palyaElemPoz.x-meret_h/2,palyaElemPoz.y-meret_v/2);
		// jobb als� sarka
		Pozicio ja = new Pozicio(palyaElemPoz.x+meret_h/2,palyaElemPoz.y-meret_v/2);
		// jobb fels� sarka
		Pozicio jf = new Pozicio(palyaElemPoz.x+meret_h/2,palyaElemPoz.y+meret_v/2);
		// bal fels� sarka
		Pozicio bf = new Pozicio(palyaElemPoz.x-meret_h/2,palyaElemPoz.y+meret_v/2);
		
		if (j.getXSeb()>0){
		//	System.out.println("L�L");
		}
		
		// Az �j poz�ci� (double)
		double ujx;
		double ujy;
		ujx = j.getAktKoord().x;
		ujy = j.getAktKoord().y;
		
		// A r�gi poz�ci�
		Pozicio regipoz = new Pozicio();
		regipoz.x = j.getAktKoord().x - j.getXSeb();
		regipoz.y = j.getAktKoord().y - j.getUgras();
		// Az �j poz�ci�
		Pozicio ujpoz = new Pozicio();
		ujpoz.x = j.getAktKoord().x;
		ujpoz.y = j.getAktKoord().y;
			
		// A korrig�lt poz�ci�
		Pozicio korr = new Pozicio();
		
		double[] ujkoord = new double[2];
		
		int i = 0;
		while (this.metszes(ujx, ujy, j)){
			ujkoord = visszatol(regipoz, ujx, ujy, j);
			ujx = ujkoord[0];
			ujy = ujkoord[1];
			jobb |= ujkoord[2]==1;
			bal |= ujkoord[3]==1;
			fent |= ujkoord[4]==1;
			lent |= ujkoord[5]==1;
			//System.out.println(++i);
		}
		 
		// az algoritmus v�g�n kerek�teni kell a double-t int-re
		// mivel azt szeretn�nk, hogy a double koorin�t�t "min�l messzebbre kerek�ts�k" a p�lyaelemt�l, ez�rt
		// megn�zz�k, hogy honnan te�tk�zt�nk a fallal, �s ennek f�ggv�ny�ben csonkolunk/adunk hozz�
		
		korr.x = (int)ujx;
		korr.y = (int)ujy;
		
		// A j�t�kos sarkai
		// bal als� sarka
		Pozicio j_ba = new Pozicio(korr.x-jMeret,korr.y-2*jMeret);
		// bal fels� sarka
		Pozicio j_bf = new Pozicio(korr.x-jMeret,korr.y+2*jMeret);
		// jobb als� sarka
		Pozicio j_ja = new Pozicio(korr.x+jMeret,korr.y-2*jMeret);
		// jobb fels� sarka
		Pozicio j_jf = new Pozicio(korr.x+jMeret,korr.y+2*jMeret);
		
		// fel�lr�l est�nk
		if (regipoz.x == ujpoz.x && (regipoz.y>ujpoz.y)){
			j.setUgras(0);
			j.setUgrik(false);
			korr.y += 1;
			//System.out.println("fel�l");
		} else
		// alulr�l ugrottunk 
		if (regipoz.x == ujpoz.x && (regipoz.y<ujpoz.y)){
			j.setUgras(0);
			//System.out.println("alul");
		} else
		// jobbr�l ment�nk
		if (regipoz.y == ujpoz.y && (regipoz.x>ujpoz.x)){
			j.setXSeb(0);
			korr.x += 1;
		//	if (meret_v == 100) System.out.println("jobb");
		} else
		// balr�l ment�nk
		if (regipoz.y == ujpoz.y && (regipoz.x<ujpoz.x)){
			j.setXSeb(0);
		//	System.out.println("bal");
		} else			
		// jobb fel�lr�l ment�nk
		if ((regipoz.y>ujpoz.y) && (regipoz.x>ujpoz.x)){
			
			if (fent && !jobb){
				j.setUgras(0);
				j.setUgrik(false);
			//	if (meret_v == 100) System.out.println("fent !jobb");
			} else
			if (!fent && jobb){
				j.setXSeb(0);
			//	System.out.println("!fent jobb");
			} else
			if (fent && jobb){
				//System.out.println("fent jobb");
				if (j_ba.y>=jf.y){
					j.setUgras(0);
				} else {
					j.setXSeb(0);
				}
			}
			
			korr.x += 1;
			korr.y += 1;
		//	System.out.println("jobbfel�l");
		} else
		// jobb alulr�l ment�nk
		if ((regipoz.y<ujpoz.y) && (regipoz.x>ujpoz.x)){
			if (lent && !jobb){
				j.setUgras(0);
			} else
			if (!lent && jobb){
				j.setXSeb(0);				
			} else
			if (lent && jobb){
				j.setXSeb(0);
			}
			
			
			korr.x += 1;
		//	System.out.println("jobbalul");
		} else
		// bal fel�lr�l ment�nk
		if ((regipoz.y>ujpoz.y) && (regipoz.x<ujpoz.x)){
			if (fent && !bal){
				j.setUgras(0);
				j.setUgrik(false);
			} else
			if (!fent && bal){
				j.setXSeb(0);			
			} else
			if (fent && bal){
				if(j_ja.y>=bf.y){
					j.setUgras(0);
				} else {
					j.setXSeb(0);
				}
			}
		//	System.out.println("balfel�l");
			korr.y += 1;
		} else 
		// bal alulr�l ment�nk
		if ((regipoz.y<ujpoz.y) && (regipoz.x<ujpoz.x)){
			if (lent && !bal){
				j.setUgras(0);
			} else
			if (!lent && bal){
				j.setXSeb(0);				
			} else
			if (lent && bal){
				j.setXSeb(0);
			}
		//	System.out.println("balalul");
		}
		
		//System.out.println(meret_v);
		korr.x += j.getXSeb();
		korr.y += j.getUgras();
		
		j.setAktKoord(korr);
		
		return false;
	}
	
	
	// metszi-e a jatekos az uj koordinataival a vizsg�lt palyaelemet
	boolean metszes(double ujx, double ujy, Jatekos j){

		// a p�lyaelem bal als� sarka
		Pozicio balalso = new Pozicio(palyaElemPoz.x-meret_h/2,palyaElemPoz.y-meret_v/2);	
		// a p�lyaelem jobb fels� sarka
		Pozicio jobbfelso = new Pozicio(palyaElemPoz.x+meret_h/2, palyaElemPoz.y+meret_v/2);
		// a j�t�kos bal als� sarka
		double bax = ujx - j.getMeret()/2;
		double bay = ujy - j.getMeret();
		// a j�t�kos jobb fels� sarka
		double jfx = ujx + j.getMeret()/2;
		double jfy = ujy + j.getMeret();
		// horizont�lis �tfed�s
		boolean xmetszi = false;
		// vertik�lis �tfed�s
		boolean ymetszi = false;
		
		double atfed = 0;
		//v�zszintesen
		if (bax < balalso.x) {
	        atfed = balalso.x - jfx;
	    } else {
	        atfed = bax - jobbfelso.x;
	    }
		
		if(atfed <= 0)
			xmetszi = true;
		
		//f�gg�legesen
		if (bay < balalso.y) {
	        atfed = balalso.y - jfy;
	    } else {
	        atfed = bay - jobbfelso.y;
	    }
		
		if(atfed <= 0)
			ymetszi = true;
		// metszik egym�st, ha vertik�lisan �s horizont�lisan �sszel�gnak
		return (ymetszi & xmetszi);
	}
	
	// karakter visszatol�sa
	double[] visszatol(Pozicio regi, double ujx, double ujy, Jatekos j){
		double[] ret = {ujx, ujy, 1, 1, 1, 1};
		// SZ�NK�D:
		// [0] - ujx
		// [1] - ujy
		// [2] - jobb
		// [3] - bal
		// [4] - fent
		// [5] - lent
		double deltaX = j.getAktKoord().x-regi.x;
		double deltaY = j.getAktKoord().y-regi.y;
		
		// A fal sarkai
		// bal als� sarka
		Pozicio ba = new Pozicio(palyaElemPoz.x-meret_h/2,palyaElemPoz.y-meret_v/2);
		// jobb als� sarka
		Pozicio ja = new Pozicio(palyaElemPoz.x+meret_h/2,palyaElemPoz.y-meret_v/2);
		// jobb fels� sarka
		Pozicio jf = new Pozicio(palyaElemPoz.x+meret_h/2,palyaElemPoz.y+meret_v/2);
		// bal fels� sarka
		Pozicio bf = new Pozicio(palyaElemPoz.x-meret_h/2,palyaElemPoz.y+meret_v/2);	
		
		ret[2] = falatmetszett(ja,jf,ujx,ujy,j)? 1 : 0;
		ret[3] = falatmetszett(ba,bf,ujx,ujy,j)? 1 : 0;
		ret[4] = falatmetszett(bf,jf,ujx,ujy,j)? 1 : 0;
		ret[5] = falatmetszett(ba,ja,ujx,ujy,j)? 1 : 0;
		
		// ha csak X ir�nyban mozgott
		if (deltaY == 0 && deltaX!= 0){
			ujx -= 0.05*deltaX;
			ret[0] = ujx;
			ret[1] = ujy;
			return ret;
		}
		// ha csak Y ir�nyban mozgott
		if (deltaX == 0&& deltaY!= 0){
			ujy -= 0.05*deltaY;
			ret[0] = ujx;
			ret[1] = ujy;
			return ret;
		}
		// ha nem mozgott
		if (deltaX == 0 && deltaY==0){
			ujy += 1;
			ret[0] = ujx;
			ret[1] = ujy;	
		}
		
		// visszatoljuk a karaktert egy picit
		ujx -= 0.05*deltaX;
		ujy -= 0.05*deltaY;
		ret[0] = ujx;
		ret[1] = ujy;	
		return ret;
	}
	
	boolean falatmetszett(Pozicio p1, Pozicio p2, double ujx, double ujy, Jatekos j){
		int i = 0;
		if (meret_v == 100){
			i++;
		}
		
		// j�t�kos aktu�lis koordin�t�ja
		int jMeret = j.getMeret()/2;
		
		double j_bax = ujx-jMeret;
		double j_bay = ujy-2*jMeret;
		
		double j_jfx = ujx+jMeret;
		double j_jfy = ujy+2*jMeret;
		
		Pozicio balalso = p1;
		Pozicio jobbfelso = p2;
		
		
		// horizont�lis �tfed�s
		boolean xmetszi = false;
		// vertik�lis �tfed�s
		boolean ymetszi = false;
		
		double atfed = 0;
		//v�zszintesen
		if (j_bax <= balalso.x) {
	        atfed = balalso.x - j_jfx;
	    } else {
	        atfed = j_bax - jobbfelso.x;
	    }
		
		if(atfed <= 0)
			xmetszi = true;
		
		//f�gg�legesen
		if (j_bay <= balalso.y) {
	        atfed = balalso.y - j_jfy;
	    } else {
	        atfed = j_bay - jobbfelso.y;
	    }
		
		if(atfed <= 0)
			ymetszi = true;
		// metszik egym�st, ha vertik�lisan �s horizont�lisan �sszel�gnak
		return (ymetszi & xmetszi);
		
	}	
	
}