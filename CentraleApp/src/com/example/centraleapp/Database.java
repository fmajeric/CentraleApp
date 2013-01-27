package com.example.centraleapp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



public abstract class Database {
	private static List<Immeuble> immeubles;
	private static SharedPreferences preferences;
	private static SharedPreferences.Editor preferencesEditor;

	public static List<Immeuble> getImmeuble() {
		return immeubles;
	}

	public static void setImmeuble(List<Immeuble> immeuble) {
		Database.immeubles = immeuble;
	}

	public static Immeuble getImmeuble(String nom){
		for (Immeuble immeuble : immeubles) {
			if (immeuble.getNom().equals(nom)){
				return immeuble;
			}
		}
		return null;
	}
	
	public static void setFavoris(String nom, boolean etatfavori) {
		Immeuble immeuble = getImmeuble(nom);
		immeuble.setFavoris(etatfavori);
		preferencesEditor.putBoolean(immeuble.getNom(), immeuble.getFavoris());
		preferencesEditor.apply();
	}

	public static void initialiserFavoris(Activity activite) {
		preferences = activite.getPreferences(Context.MODE_PRIVATE);
		preferencesEditor = preferences.edit();
		for (Immeuble immeuble : immeubles) {
			String cle = immeuble.getNom();
			boolean etatfavori = preferences.getBoolean(cle, false);
			setFavoris(immeuble.getNom(), etatfavori);
		}
	}

}