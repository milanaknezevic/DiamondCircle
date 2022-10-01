package com.example.diamondcircle.model.mapa;

import com.example.diamondcircle.model.Figura;

public class Polje {
    private Element element ;
    private Figura figura;
    private boolean imaBonus;
    private boolean imaRupa;
    private boolean imaFigura;
    public Polje()
    {
        super();
    }

    public Polje(Element element) {
        this.element = element;
        imaBonus=false;
        imaFigura=false;
        figura=null;
        imaRupa=false;
    }

    public void setElement(Element element)
    {
        this.element=element;
    }

    public Element getElement()
    {
        return element;
    }

    public boolean isImaBonus() {
        return imaBonus;
    }

    public void setImaBonus(boolean imaBonus) {
        this.imaBonus = imaBonus;
    }

    public boolean isImaRupa() {
        return imaRupa;
    }

    public void setImaRupa(boolean imaRupa) {
        this.imaRupa = imaRupa;
    }

    public boolean isImaFigura() {
        return imaFigura;
    }

    public void setImaFigura(boolean imaFigura) {
        this.imaFigura = imaFigura;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }

}

