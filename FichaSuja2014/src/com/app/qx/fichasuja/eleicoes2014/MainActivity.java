package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnItemClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String url = "http://api.tcm.ce.gov.br/tre/1_0/processos_gestores.json";
	private ProgressDialog dialog;
	private List<Politico> politicos = new ArrayList<Politico>();
	private ListView listView;
	private Adapter adapter;
	private Politico politico;
	private EditText busca;

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
		setContentView(R.layout.activity_main);
				
		listView = (ListView) findViewById(R.id.lista);
		listView.setOnItemClickListener(this);
		adapter = new Adapter(this, politicos);
		
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
								Log.d(TAG, "GESTORES: "+politicoJSON.get(GESTOR));
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
				listView.setAdapter(adapter);
		        listView.setCacheColorHint(Color.TRANSPARENT);
		        adapter.notifyDataSetChanged();
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_actions, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(MainActivity.this, VisualizarPolitco.class);
		Politico politico = (Politico) parent.getItemAtPosition(position);
		
		String processo = String.valueOf(politico.getProcesso());
		String exercicio = String.valueOf(politico.getExercicio());
		String codigo_gestor = String.valueOf(politico.getCodigo_gestor());
		String codigo_municipio = String.valueOf(politico.getCodigo_municipio());
		
		intent.putExtra(GESTOR, politico.getGestor());
		intent.putExtra(PROCESSO, processo);
		intent.putExtra(MUNICIPIO, politico.getMunicipio());
		intent.putExtra(NATUREZA_PROCESSO, politico.getNatureza_processo());
		intent.putExtra(EXERCICIO, exercicio);
		intent.putExtra(NOTA_IMPROBIDADE, politico.getNota_improbidade());
		intent.putExtra(CODIG0_GESTOR, codigo_gestor);
		intent.putExtra(CODIGO_MUNICIPIO, codigo_municipio);
		startActivity(intent);
	}

}
