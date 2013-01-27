package com.example.centraleapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class POIActivity extends Activity implements OnClickListener{
	
	Button bouton1 = null;
	Button bouton2 = null;
	Button bouton3 = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.immeuble);
		((TextView) findViewById(R.id.textView1)).setText(getIntent()
				.getStringExtra("nom"));
		((TextView) findViewById(R.id.textView2)).setText(getIntent()
				.getStringExtra("quartier"));
		((TextView) findViewById(R.id.textView3)).setText(getIntent()
				.getStringExtra("secteur"));
		((TextView) findViewById(R.id.textView5)).setText(getIntent()
				.getStringExtra("description"));
		((ImageView) findViewById(R.id.imageView1))
				.setImageBitmap(downloadImage(getIntent().getStringExtra(
						"urlimage")));
		bouton1 = (Button) findViewById(R.id.button1);
		bouton1.setOnClickListener(this);
		bouton2 = (Button) findViewById(R.id.button2);
		bouton2.setOnClickListener(this);
		bouton3 = (Button) findViewById(R.id.button3);
		bouton3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == bouton1) {
			if(!Database.getImmeuble(getIntent().getStringExtra("nom")).getFavoris()){
				Database.setFavoris(getIntent()
						.getStringExtra("nom"), true);
				Toast.makeText(getApplicationContext(),
						"Ce POI a été ajouté à vos favoris", Toast.LENGTH_SHORT)
						.show();
			}
			else{
				Database.setFavoris(getIntent()
						.getStringExtra("nom"), false);
				Toast.makeText(getApplicationContext(),
						"Ce POI a été enlevé de vos favoris", Toast.LENGTH_SHORT)
						.show();	
			}
			
		}
		if (v == bouton2) {
			Toast.makeText(getApplicationContext(),
					"Fonctionnalité non disponible", Toast.LENGTH_SHORT).show();
		}
		
		if (v == bouton3) {
			Toast.makeText(getApplicationContext(),
					"Zoom sur le batiment", Toast.LENGTH_SHORT).show();
			Intent intent_map = new Intent(POIActivity.this, GoogleMapActivity.class);
			intent_map.putExtra("longitude", Database.getImmeuble(getIntent().getStringExtra("nom")).getLongitude());
			intent_map.putExtra("latitude", Database.getImmeuble(getIntent().getStringExtra("nom")).getLatitude());
			startActivity(intent_map);
		}
	}

	public static Bitmap downloadImage(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			Log.e("Erreur", "Erreur lors de la récupération de l'image : "
					+ e.getMessage().toString());
		}
		return bm;
	}
}