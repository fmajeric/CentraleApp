package com.example.centraleapp;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements Runnable{
	List<Immeuble> listImmeubles = new ArrayList<Immeuble>();
	private ProgressDialog mprogressDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mprogressDialog = new ProgressDialog(this);
		// Message de la barre de progression
		mprogressDialog.setMessage("Chargement en cours...");
		// Style de la barre de progression(STYLE_HORIZONTAL ou STYLE_SPINNER)
		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// Affichage de la barre de progression
		mprogressDialog.show();

		Thread thread = new Thread(this);
		thread.start();
		this.remplirListeImmeubles();
		Intent intent_listeImmeublesActivity = new Intent(MainActivity.this, ListeImmeublesActivity.class);
		startActivity(intent_listeImmeublesActivity);
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int i = msg.what;
			switch (i) 
			{ 
				case 1: mprogressDialog.setMessage("remplissage 1 terminé..."); mprogressDialog.incrementProgressBy(10); break;  
				case 2: mprogressDialog.setMessage("remplissage 2 terminé..."); mprogressDialog.incrementProgressBy(10);break; 
				case 3: mprogressDialog.setMessage("remplissage 3 terminé..."); mprogressDialog.incrementProgressBy(10);break; 
				case 4: mprogressDialog.setMessage("remplissage 4 terminé..."); mprogressDialog.incrementProgressBy(10);break;
				case 5: mprogressDialog.setMessage("remplissage 5 terminé..."); mprogressDialog.incrementProgressBy(10);break;
				case 6: mprogressDialog.setMessage("remplissage 6 terminé..."); mprogressDialog.incrementProgressBy(10);break;
				case 7: mprogressDialog.setMessage("remplissage 7 terminé..."); mprogressDialog.incrementProgressBy(10);break;
				case 8: mprogressDialog.setMessage("remplissage 8 terminé..."); mprogressDialog.incrementProgressBy(10);break;
				case 9: mprogressDialog.setMessage("remplissage 9 terminé..."); mprogressDialog.incrementProgressBy(10);break;
				case 10: mprogressDialog.setMessage("remplissage 10 terminé..."); mprogressDialog.incrementProgressBy(10);break;
				default: 
					// Fermer le message
					mprogressDialog.dismiss();
			}
		}
	};
	public void run() {
		try {
			Thread.sleep(5000);
			handler.sendEmptyMessage(1);
			Thread.sleep(3000);
			handler.sendEmptyMessage(2);
			Thread.sleep(5000);
			handler.sendEmptyMessage(3);
			Thread.sleep(2000);
			handler.sendEmptyMessage(4);
			Thread.sleep(2000);
			handler.sendEmptyMessage(5);
			Thread.sleep(2000);
			handler.sendEmptyMessage(6);
			Thread.sleep(2000);
			handler.sendEmptyMessage(7);
			Thread.sleep(2000);
			handler.sendEmptyMessage(8);
			Thread.sleep(2000);
			handler.sendEmptyMessage(9);
			Thread.sleep(2000);
			handler.sendEmptyMessage(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

private void remplirListeImmeubles() {
	listImmeubles.clear();
	HttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet("http://cci.corellis.eu/pois.php");
	try {
		HttpResponse response = httpClient.execute(httpGet);
		if (response != null) {
			String line = "";
			InputStream inputStream = response.getEntity().getContent();
			line = convertStreamToString(inputStream);
			try {
				JSONObject jsonObject = new JSONObject(line);
				JSONArray jsonArray = jsonObject.getJSONArray("results");
				Toast.makeText(this,
						"Number of entries" + jsonArray.length() + "",
						Toast.LENGTH_LONG).show();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject objet = jsonArray.getJSONObject(i);
					Immeuble immeuble = new Immeuble(
							objet.getString("nom"),
							objet.getString("quartier"),
							objet.getString("secteur"),
							objet.getString("small_image"),
							objet.getString("informations"),
							objet.getString("lon"),
							objet.getString("lat"));
											listImmeubles.add(immeuble);
					Log.w("TAG", objet.toString());
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "Unable to complete the request",
					Toast.LENGTH_LONG).show();
		}
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	Database.setImmeuble(listImmeubles);
	Database.initialiserFavoris(this);
}

private String convertStreamToString(InputStream inputStream) {
	String ligne = "";
	StringBuilder total = new StringBuilder();
	BufferedReader rd = new BufferedReader(new InputStreamReader(
			inputStream));
	try {
		while ((ligne = rd.readLine()) != null) {
			total.append(ligne);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	return total.toString();
}
}