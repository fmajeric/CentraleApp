package com.example.centraleapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class GoogleMapActivity extends MapActivity implements LocationListener {

	MapView maMap = null;
	MapController monControler = null;
	double latitude = 0;
	double longitude = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemaplayout);
		
		latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
		longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));

		maMap = (MapView) findViewById(R.id.myGmap);
		maMap.setBuiltInZoomControls(true);
		GeoPoint point = new GeoPoint(microdegrees(latitude),
				microdegrees(longitude));
		ItemizedOverlayPerso pinOverlay = new ItemizedOverlayPerso(
				getResources().getDrawable(R.drawable.marker));
		pinOverlay.addPoint(point);
		maMap.getOverlays().add(pinOverlay);
		monControler = maMap.getController();
		monControler.setZoom(12);
		monControler.setCenter(point);

		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f,
				this);

	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			Toast.makeText(
					this,
					"Nouvelle position : " + location.getLatitude() + ", "
							+ location.getLongitude(), Toast.LENGTH_SHORT)
					.show();
			monControler.animateTo(new GeoPoint(microdegrees(location
					.getLatitude()), microdegrees(location.getLongitude())));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 100, 0, "Zoom In");
		menu.add(0, 101, 0, "Zoom Out");
		menu.add(0, 102, 0, "Satellite");
		menu.add(0, 103, 0, "Trafic");
		menu.add(0, 104, 0, "Street view");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			monControler.setZoom(maMap.getZoomLevel() + 1);
			break;
		case 101:
			monControler.setZoom(maMap.getZoomLevel() - 1);
			break;
		case 102:
			maMap.setSatellite(!maMap.isSatellite());
			break;
		case 103:
			maMap.setTraffic(!maMap.isTraffic());
			break;
		case 104:
			maMap.setStreetView(!maMap.isStreetView());
			break;
		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(102).setIcon(
				maMap.isSatellite() ? android.R.drawable.checkbox_on_background
						: android.R.drawable.checkbox_off_background);
		menu.findItem(103).setIcon(
				maMap.isTraffic() ? android.R.drawable.checkbox_on_background
						: android.R.drawable.checkbox_off_background);
		menu.findItem(104)
				.setIcon(
						maMap.isStreetView() ? android.R.drawable.checkbox_on_background
								: android.R.drawable.checkbox_off_background);
		return true;
	}

	private int microdegrees(double value) {
		return (int) (value * 1000000);
	}

	public class ItemizedOverlayPerso extends ItemizedOverlay<OverlayItem> {

		private List<GeoPoint> points = new ArrayList<GeoPoint>();

		public ItemizedOverlayPerso(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
		}

		@Override
		protected OverlayItem createItem(int i) {
			GeoPoint point = points.get(i);
			return new OverlayItem(point, "Titre", "Description");
		}

		@Override
		public int size() {
			return points.size();
		}

		public void addPoint(GeoPoint point) {
			this.points.add(point);
			populate();
		}

		public void clearPoint() {
			this.points.clear();
			populate();
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
}
