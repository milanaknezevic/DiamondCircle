package com.example.diamondcircle;

import com.example.diamondcircle.model.Boja;
import com.example.diamondcircle.model.Figura;
import com.example.diamondcircle.model.mapa.Polje;

import static com.example.diamondcircle.model.GameService.dimenzija;
import static com.example.diamondcircle.model.GameService.velicina;

public class MatricaZaPrikaz {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static MatricaZaPrikaz[] matricaPOM=new MatricaZaPrikaz[dimenzija*dimenzija];

    //public static Object[] matricaPOM= new Object[49];
    private boolean imaFiguruPOM=false;
    private boolean imaRupuPOM=false;
    private boolean imaBonusPOM=false;

    private static int boja=0;

    public MatricaZaPrikaz()
    {
       // matricaPOM = new MatricaZaPrikaz[64];
    }

   public static MatricaZaPrikaz[] getMatricaPOM() {
        return matricaPOM;
    }

    public static void setMatricaPOM(MatricaZaPrikaz[] matricaPOM) {
        MatricaZaPrikaz.matricaPOM = matricaPOM;
    }



    public boolean isImaFiguruPOM() {
        return imaFiguruPOM;
    }

    public void setImaFiguruPOM(boolean imaFiguruPOM) {
        this.imaFiguruPOM = imaFiguruPOM;
    }

    public boolean isImaRupuPOM() {
        return imaRupuPOM;
    }

    public void setImaRupuPOM(boolean imaRupuPOM) {
        this.imaRupuPOM = imaRupuPOM;
    }

    public boolean isImaBonusPOM() {
        return imaBonusPOM;
    }

    public void setImaBonusPOM(boolean imaBonusPOM) {
        this.imaBonusPOM = imaBonusPOM;
    }

    public static void printMatrica()
    {
        System.out.println();
        System.out.println();
        int x=0;
        if(dimenzija ==7)
        {
            x=49;
        }
        else if(dimenzija ==8)
        {
            x=64;
        }
        else if(dimenzija ==9)
        {
            x=81;
        }
        else{
            x=100;
        }


        for(int i=0;i<x;i++)
        {
            System.out.print(matricaPOM[i]);
            if((i+1)%dimenzija==0)
            {
                System.out.println();
            }

        }
    }


    public static void postaviFiguruNaMatricu(Polje polje, Figura figura)
    {
        Boja b=figura.getBoja();


        if(b.equals(Boja.CRVENA))
        {
            boja=1;
        } else  if(b.equals(Boja.ZUTA))
        {
            boja=2;
        }
        else  if(b.equals(Boja.PLAVA))
        {
            boja=3;
        }
        else
        {
            boja=4;
        }

        matricaPOM[polje.getElement().getX()].setImaFiguruPOM(true);
 }
    public static void skloniFiguruSaMatrice(Polje p)
    {
        matricaPOM[p.getElement().getX()].setImaFiguruPOM(false);
    }


    public static void postaviCrneRupeNaMatricu(Polje polje)
    {
       matricaPOM[polje.getElement().getX()].setImaRupuPOM(true);
    }


    public static void postaviBonusNaMatricu(Polje polje)
    {
        matricaPOM[polje.getElement().getX()].setImaBonusPOM(true);
    }
    public static void skloniBonusSaMatrice(Polje polje)
    {
        matricaPOM[polje.getElement().getX()].setImaBonusPOM(false);
    }
    public static void skloniCrneRupeSaMatrice(Polje polje)
    {
       matricaPOM[polje.getElement().getX()].setImaRupuPOM(false);
    }

    @Override
    public String toString()
    {

        return "[" + (isImaRupuPOM() && isImaBonusPOM() && isImaFiguruPOM() ? ANSI_RED + "Rupa Bonus Figura" + ANSI_RESET :  isImaRupuPOM() && isImaBonusPOM() && !isImaFiguruPOM()? ANSI_RED + "Rupa Bonus" + ANSI_RESET : isImaRupuPOM() && !isImaBonusPOM() && isImaFiguruPOM()? ANSI_RED +  "Rupa Figura" + ANSI_RESET:  !isImaRupuPOM() && isImaBonusPOM() && isImaFiguruPOM()? ANSI_RED +  "Bonus Figura" + ANSI_RESET:  isImaRupuPOM() && !isImaBonusPOM() && !isImaFiguruPOM()? ANSI_RED +  "Rupa" +ANSI_RESET:  !isImaRupuPOM() && isImaBonusPOM() && !isImaFiguruPOM()? ANSI_RED + "Bonus" +ANSI_RESET:  !isImaRupuPOM() && !isImaBonusPOM() && isImaFiguruPOM()? ANSI_RED + "Figura" + ANSI_RESET: "Nista" ) + "]";
    }

}
