package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.qx.fichasuja.eleicoes2014.adapter.Adapter;
import com.app.qx.fichasuja.eleicoes2014.controller.AppController;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;
import com.app.qx.fichasuja.eleicoes2014.utils.Utils;

public class MainActivity extends Activity implements OnItemClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String url = "http://api.tcm.ce.gov.br/tre/1_0/processos_gestores.json";
	private ProgressDialog dialog;
	private List<Politico> politicos = new ArrayList<Politico>();
	private ListView listView;
	private Adapter adapter;
	private Politico politico;
	private ArrayList<Politico> list;
	private RequestQueue rq;
	private ImageLoader imageLoader;
	private Utils utils;
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
		setContentView(R.layout.activity_main);
				
		listView= (ListView) findViewById(R.id.listaMunicipios);
		/*utils = new Utils(this);
		
		list = getIntent().getParcelableArrayListExtra("politicos");*/
		adapter = new Adapter(this, politicos);
		//listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
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
				listView.setAdapter(adapter);
		        listView.setCacheColorHint(Color.TRANSPARENT);
		        adapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: "+ error.getMessage());
				hideDialog();
				alertProblem();
				
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
	
	private void alertProblem(){
		LayoutInflater li = getLayoutInflater();
		View view = li.inflate(R.layout.alert, null);
		view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 //Toast.makeText(MainActivity.this, "alerta.dismiss()", Toast.LENGTH_SHORT).show();
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
