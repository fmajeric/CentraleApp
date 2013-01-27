package com.example.centraleapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ImmeubleListAdapter extends BaseAdapter {
	List<Immeuble> listImmeuble;
	LayoutInflater inflater;
	
	public ImmeubleListAdapter(Context context, List<Immeuble> objects){
		inflater = LayoutInflater.from(context);
		this.listImmeuble = objects;
	}

	@Override
	public int getCount() {
		return listImmeuble.size();
	}

	@Override
	public Immeuble getItem(int position) {
		return listImmeuble.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null){
			Log.v("test","convertView is null");
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listeimmeuble, null);
			holder.tvNom = (TextView) convertView.findViewById(R.id.textView1);
			holder.tvAdresse = (TextView) convertView.findViewById(R.id.textView2);
			holder.ivsmallimage = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.ivfavoriimage = (ImageView) convertView.findViewById(R.id.imageView2);
			convertView.setTag(holder);
		}
		else{
			Log.v("test", "convertView is not null");
			holder = (ViewHolder) convertView.getTag();
		}

		Immeuble Immeuble = listImmeuble.get(position);
		holder.tvNom.setText(Immeuble.getNom());
		holder.tvAdresse.setText(Immeuble.getQuartier() + " - " + Immeuble.getSecteur());
		holder.ivsmallimage.setImageBitmap(downloadImage(Immeuble.getUrlimage()));
		if(Immeuble.getFavoris()){
			holder.ivfavoriimage.setImageResource(R.drawable.defacto_tab_favoris_w);
		}
		else{
			holder.ivfavoriimage.setImageResource(R.drawable.defacto_tab_favoris);
		}
		return convertView;
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
    		Log.e("Erreur","Erreur lors de la r�cup�ration de l'image : " + e.getMessage().toString());
    	}
    	return bm;
    }
	
	private class ViewHolder{
		TextView tvNom;
		TextView tvAdresse;
		ImageView ivsmallimage;
		ImageView ivfavoriimage;
	}
}