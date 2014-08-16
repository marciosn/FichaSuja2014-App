package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnItemClickListener{
	private static final String TAG = MainActivity.class.getSimpleName();
	private String url = "http://api.tcm.ce.gov.br/tre/1_0/processos_gestores.json";
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		listView = (ListView) findViewById(R.id.lista);
		adapter = new Adapter(this, politicos);
		listView.setAdapter(adapter);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Carregando");
		dialog.show();
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));
		
		JsonArrayRequest politicoReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				Log.d(TAG, response.toString());
				hideDialog();
				for(int i = 0; i < response.length(); i++ ){
					try {
						JSONObject obj = response.getJSONObject(i);
						Politico politico = new Politico();
						
							politico.setGestor(obj.getString(GESTOR));
							double processo = Double.parseDouble(obj.getString(PROCESSO));
							politico.setProcesso(processo);
							politico.setMunicipio(obj.getString(MUNICIPIO));
							politico.setNatureza_processo(obj.getString(NATUREZA_PROCESSO));
							double exercicio = Double.parseDouble(obj.getString(EXERCICIO));
							politico.setExercicio(exercicio);
							politico.setNota_improbidade(obj.getString(NOTA_IMPROBIDADE));
							double codigo_gestor = Double.parseDouble(obj.getString(CODIG0_GESTOR));
							politico.setCodigo_gestor(codigo_gestor);
							double codigo_municipio = Double.parseDouble(obj.getString(CODIGO_MUNICIPIO));
							politico.setCodigo_municipio(codigo_municipio);
						
						
						politicos.add(politico);
						listView.setAdapter(adapter);
				        listView.setCacheColorHint(Color.TRANSPARENT);
				        Toast.makeText(MainActivity.this, politicos.get(0).getGestor(), Toast.LENGTH_LONG).show();
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				adapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {	

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: "+ error.getMessage());
				hideDialog();
				
			}
		}); 
		AppController.getInstance().addToRequestQueue(politicoReq);
	}
	private void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
