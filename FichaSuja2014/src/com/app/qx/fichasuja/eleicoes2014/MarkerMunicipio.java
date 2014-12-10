package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.app.qx.fichasuja.eleicoes2014.adapter.Adapter;
import com.app.qx.fichasuja.eleicoes2014.controller.PegarMunicipios;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;
import com.app.qx.fichasuja.eleicoes2014.utils.Utils;
import com.google.android.gms.internal.l;

@SuppressLint("DefaultLocale")
public class MarkerMunicipio extends Activity implements OnItemClickListener{
	private TextView nomeTV;
	private String municipioNome;
	private PegarMunicipios pegaMunicipio = new PegarMunicipios();
	private ProgressDialog dialog;
	private ArrayList<Politico> politicosPorMunicipio = new ArrayList<Politico>();
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
	private ArrayList<Politico> list;
	private Utils utils;
	private RequestQueue rq;
	private ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_municipio);
		
		
		list = getIntent().getParcelableArrayListExtra("list");
		rq = Volley.newRequestQueue(MarkerMunicipio.this);
		utils = new Utils(this);
		
		/*Intent i = getIntent();
		nomeTV = (TextView) findViewById(R.id.NomeMunicipio);
		municipioNome = i.getExtras().getString("NomeMunicipio");
		nomeTV.setText(municipioNome);*/
		
		if(list != null){			
	        Toast.makeText(MarkerMunicipio.this, "Lista vazia", Toast.LENGTH_SHORT).show();
		}else{
			listView = (ListView) findViewById(R.id.listaMunicipios);
			listView.setAdapter(new Adapter(this, list));
			listView.setCacheColorHint(Color.TRANSPARENT);
	        listView.setOnItemClickListener(this);
		}
}	
	
	public void pegaMunicipio(){
		String nomeM = municipioNome.toUpperCase();
		
		for(Politico p : list){
			if(p.getMunicipio().contains(nomeM)){
				politicosPorMunicipio.add(p);
		}
		adapter = new Adapter(this, politicosPorMunicipio);
		listView.setAdapter(adapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
        adapter.notifyDataSetChanged();
	}
		//String tam = String.valueOf(politicosDoMunicipio.size());
		//Toast.makeText(MarkerMunicipio.this, "O Municipio tem "+tam, Toast.LENGTH_SHORT).show();
	}
	public void test(View view){
		Toast.makeText(MarkerMunicipio.this, "Lista "+list.size(), Toast.LENGTH_SHORT).show();
	}
	public void waitTimer(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				hideDialog();
				
			}
		}, 3 * 1000);
	}

	private void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	public void clear(){
		for(Politico p: politicosPorMunicipio){
			politicosPorMunicipio.remove(p);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.marker_municipio, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(MarkerMunicipio.this, VisualizarPolitco.class);
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
