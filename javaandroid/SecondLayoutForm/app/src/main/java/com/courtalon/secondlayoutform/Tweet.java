package com.courtalon.secondlayoutform;



public class Tweet {
    private int color;
    private String pseudo;
    private String texte;

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getTexte() {
        return texte;
    }
    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Tweet(int color, String pseudo, String texte) {
        setColor(color);
        setPseudo(pseudo);
        setTexte(texte);
    }
}
