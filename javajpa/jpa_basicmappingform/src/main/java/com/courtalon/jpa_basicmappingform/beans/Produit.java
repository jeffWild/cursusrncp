package com.courtalon.jpa_basicmappingform.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //cette classe est persistée en base
public class Produit {
	private int id;
	private String nom;
	private double prix;
	private double poids;
	private int stock;
	
	// @Id -> cle primaire
	// @GeneratedValue -> géré par la base, ici, autoIncremente
	@Id	@GeneratedValue
	public int getId() {return id;} 
	public void setId(int id) {this.id = id;}
	@Column(length=50)  //la colonne nom fera 50 caracteres
	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}
	@Column(name="PRICE") // le nom de la colonne sera PRICE
	public double getPrix() {return prix;}
	public void setPrix(double prix) {this.prix = prix;}
	public double getPoids() {return poids;}
	public void setPoids(double poids) {this.poids = poids;}
	public int getStock() {return stock;}
	public void setStock(int stock) {this.stock = stock;}

	public Produit() { this(0, "", 0, 0, 0); }
	public Produit(int id, String nom, double prix, double poids, int stock) {
		super();
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.poids = poids;
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Produit [id=" + id + ", nom=" + nom + ", prix=" + prix + ", poids=" + poids + ", stock=" + stock + "]";
	}
	
	
	
	
	
}
