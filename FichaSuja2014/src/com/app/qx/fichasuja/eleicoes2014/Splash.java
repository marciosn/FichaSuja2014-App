package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;
import com.google.android.gms.internal.bu;

public class Splash extends Activity {
	private static final String url = "http://api.tcm.ce.gov.br/tre/1_0/processos_gestores.json";
	private static final String TAG = MainActivity.class.getSimpleName();
	private ProgressDialog dialog;
	private Politico politico;
	private ArrayList<Politico> politicos;
	private RequestQueue rq;
	private AlertDialog alerta;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		/*rq = Volley.newRequestQueue(Splash.this);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Carregando...");
		dialog.show();
		
		JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, "onResponse: "+response.toString());
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
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				Intent intent = new Intent(Splash.this, MainActivity.class);
				intent.putParcelableArrayListExtra("politicos", politicos);
				startActivity(intent);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: "+ error.getMessage());
				hideDialog();
				alertProblem();
			}
		});
		request.setTag("tag");
		rq.add(request);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	private void alertProblem(){
		LayoutInflater li = getLayoutInflater();
		View view = li.inflate(R.layout.alert, null);
		view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 //Toast.makeText(Splash.this, "alerta.dismiss()", Toast.LENGTH_SHORT).show();
	                alerta.dismiss();
				
			}
		});
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("we have a problem!");
		builder.setView(view);
		alerta = builder.create();
		alerta.show();
	}
}

