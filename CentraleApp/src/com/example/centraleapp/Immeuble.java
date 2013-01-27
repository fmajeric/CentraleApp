package com.example.centraleapp;

public class Immeuble {
	private String nom;
	private String quartier;
	private String secteur;
	private String urlimage;
	private String description;
	private Boolean favoris;
	private String longitude;
	private String latitude;

	public Immeuble(String nom, String quartier, String secteur,  String urlimage, String description, String longitude, String latitude) {
		super();
		this.nom = nom;
		this.quartier = quartier;
		this.secteur = secteur;
		this.urlimage = urlimage;
		this.description = description;
		this.favoris = false;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	
	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public Boolean getFavoris() {
		return favoris;
	}

	public void setFavoris(Boolean favoris) {
		this.favoris = favoris;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	public String getUrlimage() {
		return urlimage;
	}

	public void setUrlimage(String urlimage) {
		this.urlimage = urlimage;
	}

}
