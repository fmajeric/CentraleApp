package com.example.centraleapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListeImmeublesActivity extends Activity implements
		OnClickListener, TextWatcher {
	List<Immeuble> listImmeubles = new ArrayList<Immeuble>();
	List<Immeuble> listImmeublesaffiche = new ArrayList<Immeuble>();
	Button bouton1 = null;
	Button bouton2 = null;
	Button bouton3 = null;
	ListView listview = null;
	ImmeubleListAdapter Immeublelistadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listeimmeubles);
		listImmeubles = Database.getImmeuble();
		listImmeublesaffiche.addAll(listImmeubles);
		final ListView listview = (ListView) findViewById(R.id.listView1);
		Immeublelistadapter = new ImmeubleListAdapter(this,
				listImmeublesaffiche);
		listview.setAdapter(Immeublelistadapter);

		bouton1 = (Button) findViewById(R.id.button1);
		bouton1.setOnClickListener(this);
		bouton2 = (Button) findViewById(R.id.button2);
		bouton2.setOnClickListener(this);
		bouton3 = (Button) findViewById(R.id.button3);
		bouton3.setOnClickListener(this);
		EditText editText = (EditText) findViewById(R.id.editText1);
		editText.addTextChangedListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent_POI = new Intent(ListeImmeublesActivity.this,
						POIActivity.class);
				Immeuble Immeuble = Immeublelistadapter.getItem(position);
				intent_POI.putExtra("nom", Immeuble.getNom());
				intent_POI.putExtra("quartier", Immeuble.getQuartier());
				intent_POI.putExtra("secteur", Immeuble.getSecteur());
				intent_POI.putExtra("urlimage", Immeuble.getUrlimage());
				intent_POI.putExtra("description", Immeuble.getDescription());
				intent_POI.putExtra("longitude", Immeuble.getLongitude());
				intent_POI.putExtra("latitude", Immeuble.getLatitude());
				startActivity(intent_POI);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.listeimmeubles);
		listImmeubles = Database.getImmeuble();
		listImmeublesaffiche.addAll(listImmeubles);
		final ListView listview = (ListView) findViewById(R.id.listView1);
		Immeublelistadapter = new ImmeubleListAdapter(this,
				listImmeublesaffiche);
		listview.setAdapter(Immeublelistadapter);

		bouton1 = (Button) findViewById(R.id.button1);
		bouton1.setOnClickListener(this);
		bouton2 = (Button) findViewById(R.id.button2);
		bouton2.setOnClickListener(this);
		bouton3 = (Button) findViewById(R.id.button3);
		bouton3.setOnClickListener(this);
		EditText editText = (EditText) findViewById(R.id.editText1);
		editText.addTextChangedListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent_POI = new Intent(ListeImmeublesActivity.this,
						POIActivity.class);
				Immeuble Immeuble = Immeublelistadapter.getItem(position);
				intent_POI.putExtra("nom", Immeuble.getNom());
				intent_POI.putExtra("quartier", Immeuble.getQuartier());
				intent_POI.putExtra("secteur", Immeuble.getSecteur());
				intent_POI.putExtra("urlimage", Immeuble.getUrlimage());
				intent_POI.putExtra("description", Immeuble.getDescription());
				intent_POI.putExtra("longitude", Immeuble.getLongitude());
				intent_POI.putExtra("latitude", Immeuble.getLatitude());
				startActivity(intent_POI);
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == bouton1) {
			Toast.makeText(getApplicationContext(),
					"Vous êtes déjà sur la liste des bâtiments",
					Toast.LENGTH_SHORT).show();
		}
		if (v == bouton2) {
			Toast.makeText(getApplicationContext(),
					"Fonctionnalité non disponible", Toast.LENGTH_SHORT).show();
		}
		if (v == bouton3) {
			Toast.makeText(getApplicationContext(), "Liste des favoris",
					Toast.LENGTH_SHORT).show();
			Intent intent_favori = new Intent(ListeImmeublesActivity.this,
					ListeImmeublesFavoriActivity.class);
			startActivity(intent_favori);
		}
	}

	@Override
	public void afterTextChanged(Editable recherche) {
		listImmeublesaffiche.clear();
		for (Immeuble Immeuble : Database.getImmeuble()) {
			if (Immeuble.getNom().toLowerCase().contains(recherche.toString())) {
				listImmeublesaffiche.add(Immeuble);
			}
		}
		Immeublelistadapter.notifyDataSetChanged();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

}