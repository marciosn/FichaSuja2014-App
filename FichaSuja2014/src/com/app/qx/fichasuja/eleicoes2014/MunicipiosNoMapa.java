package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.qx.fichasuja.eleicoes2014.models.CriaMunicipios;
import com.app.qx.fichasuja.eleicoes2014.models.MunicipioObj;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("ShowToast")
public class MunicipiosNoMapa extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	private static final String url = "http://marciosn.github.io/JSON/json/processos_gestores.json";
	private GoogleMap mMap;
	private GoogleMap map = null;
	private LocationClient locationClient;
	private static final LatLng CEARA = new LatLng(-4.852330, -39.574646);
	private CriaMunicipios criaM = new CriaMunicipios();
	private ArrayList<Politico> politicos;
	private RequestQueue rq;
	
	private static final String GESTOR = "gestor";
	private static final String PROCESSO = "processo";
	private static final String MUNICIPIO = "municipio";
	private static final String NATUREZA_PROCESSO = "natureza_processo";
	private static final String EXERCICIO = "exercicio";
	private static final String NOTA_IMPROBIDADE = "nota_improbidade";
	private static final String CODIG0_GESTOR = "codigo_gestor";
	private static final String CODIGO_MUNICIPIO = "codigo_municipio";
	private static final String CONTENT = "_content";
	private static final String RSP = "rsp";
	private Politico politico;
	private ProgressDialog dialog;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.municipios_no_mapa);
		getJSON();
		
		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			map.getUiSettings().setMyLocationButtonEnabled(false);
		}
	}
	private void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
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
		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CEARA, 6));
		
		CameraPosition cameraPosition = new CameraPosition.Builder().target(CEARA).zoom(5).bearing(0).tilt(90).build();
		CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
		
		//map.moveCamera(update);
		map.animateCamera(update, 4000, new CancelableCallback(){
			@Override
			public void onCancel() {
				Log.i("Script", "CancelableCallback.onCancel()");
			}

			@Override
			public void onFinish() {
				Log.i("Script", "CancelableCallback.onFinish()");
			}
		});
		
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker m) {
				if(!m.getTitle().equals(null)){
					intent = new Intent(MunicipiosNoMapa.this, MarkerMunicipio.class);
					intent.putParcelableArrayListExtra("politicos", politicos);
					intent.putExtra("NomeMunicipio", m.getTitle());
					startActivity(intent);
				}
				return false;
			}
		});
	}
	
	private void getJSON(){
		
		rq = Volley.newRequestQueue(MunicipiosNoMapa.this);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Carregando...");
		dialog.show();
		JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d("PROBLEM", "onResponse: "+response.toString());
				hideDialog();
				
				for(int i = 0; i < response.length(); i++ ){
					try {
						
						JSONObject object = response.getJSONObject(RSP);
						JSONArray array = object.getJSONArray(CONTENT);
						
							for(int j=0; j < array.length();j++){
								politico = new Politico();
								JSONObject politicoJSON = new JSONObject(array.getString(j));
								politico.setGestor(politicoJSON.getString(GESTOR));
								double processo = Double.parseDouble(politicoJSON.getString(PROCESSO));
								politico.setProcesso(processo);
								politico.setMunicipio(politicoJSON.getString(MUNICIPIO));
								politico.setNatureza_processo(politicoJSON.getString(NATUREZA_PROCESSO));
								double exercicio = Double.parseDouble(politicoJSON.getString(EXERCICIO));
								politico.setExercicio(exercicio);
								politico.setNota_improbidade(politicoJSON.getString(NOTA_IMPROBIDADE));
								double codigo_gestor = Double.parseDouble(politicoJSON.getString(CODIG0_GESTOR));
								politico.setCodigo_gestor(codigo_gestor);
								double codigo_municipio = Double.parseDouble(politicoJSON.getString(CODIGO_MUNICIPIO));
								politico.setCodigo_municipio(codigo_municipio);
								politicos.add(politico);
				
							}
							
							intent = new Intent(MunicipiosNoMapa.this, MarkerMunicipio.class);
							intent.putParcelableArrayListExtra("politicos", politicos);
							
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("PROBLEM", "Error: "+ error.getMessage());
				hideDialog();
			}
		});
		request.setTag("tag");
		rq.add(request);
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
