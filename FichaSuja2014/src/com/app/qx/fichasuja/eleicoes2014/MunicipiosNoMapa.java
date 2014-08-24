package com.app.qx.fichasuja.eleicoes2014;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.app.qx.fichasuja.eleicoes2014.models.MunicipioObj;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("ShowToast")
public class MunicipiosNoMapa extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	
	private GoogleMap mMap;
	private GoogleMap map = null;
	private LocationClient locationClient;
	private static final LatLng CEARA = new LatLng(-4.852330, -39.574646);
	private CriaMunicipios criaM = new CriaMunicipios();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.municipios_no_mapa);
		
		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			map.getUiSettings().setMyLocationButtonEnabled(false);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		setUpLocationClientIfNeeded();
		locationClient.connect();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (locationClient != null) {
			locationClient.disconnect();
		}
	}

	private void setUpMapIfNeeded() {

		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				mMap.setMyLocationEnabled(true);
				//mMap.setOnMyLocationButtonClickListener(this);
				setUpMap();
			}
		}
	}

	private void setUpLocationClientIfNeeded() {
		if (locationClient == null) {
			locationClient = new LocationClient(getApplicationContext(), this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}
	private void setUpMap() {
		
		
		for(MunicipioObj m: criaM.getMunicipios()){
			mMap.addMarker(new MarkerOptions()
			.position(m.getLatLng())
			.title(m.getNomeMunicipio())
			//.snippet(m.getNomeMunicipio())
			.icon(BitmapDescriptorFactory
			.fromResource(R.drawable.marker1)));
		}
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CEARA, 6));
		
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker m) {
				if(!m.getTitle().equals(null)){
					Intent intent = new Intent(MunicipiosNoMapa.this, MarkerMunicipio.class);
					intent.putExtra("NomeMunicipio", m.getTitle());
					startActivity(intent);
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.municipios_no_mapa, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
