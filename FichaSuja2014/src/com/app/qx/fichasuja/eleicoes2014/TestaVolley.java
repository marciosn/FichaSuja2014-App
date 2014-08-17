package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class TestaVolley extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String url = "http://api.tcm.ce.gov.br/tre/1_0/processos_gestores.json";
	private ProgressDialog dialog;
	private List<Politico> politicos = new ArrayList<Politico>();
	private ListView listView;
	private Adapter adapter;
	
	private static final String GESTOR = "gestor";
	private static final String PROCESSO = "processo";
	private static final String MUNICIPIO = "municipio";
	private static final String NATUREZA_PROCESSO = "natureza_processo";
	private static final String EXERCICIO = "exercicio";
	private static final String NOTA_IMPROBIDADE = "nota_improbidade";
	private static final String CODIG0_GESTOR = "codigo_gestor";
	private static final String CODIGO_MUNICIPIO = "codigo_municipio";
	private static final String CONTENT = "_content";
	private static final String CONTENT2 = "rsp";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_testa_volley);
		/*dialog = new ProgressDialog(this);
		dialog.setMessage("Carregando");
		dialog.show();*/
	}
	
	public void methodJsonObject(View view){
		JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				hideDialog();
				
				for(int i = 0; i < response.length(); i++ ){	
					try {
						//JSONObject obj = response.getJSONObject(i);
						Politico politico = new Politico();
						
						JSONArray array = response.getJSONArray(CONTENT2);
							for(int j=0; j < array.length();j++){
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

							}
							
						politicos.add(politico);
				        //Toast.makeText(MainActivity.this, politicos.get(0).getGestor(), Toast.LENGTH_LONG).show();
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: "+ error.getMessage());
				hideDialog();
				
			}
		});
		AppController.getInstance().addToRequestQueue(request);
	}
	
	private void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
}