package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.app.qx.fichasuja.eleicoes2014.adapter.Adapter;
import com.app.qx.fichasuja.eleicoes2014.models.MunicipioObj;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class MarkerMunicipio extends Activity implements OnItemClickListener{
	private TextView nomeTV;
	private String municipioNome;
	private PegarMunicipios municipio = new PegarMunicipios();
	private ProgressDialog dialog;
	private List<Politico> politicosDoMunicipio = new ArrayList<Politico>();
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
		setContentView(R.layout.marker_municipio);
		
		//tamanho total de politicos é 8167
		
		if((municipio.getPoliticos().size() <= 0)){
			municipio.pegarJSON();
			Toast.makeText(MarkerMunicipio.this, "Carregando JSON", Toast.LENGTH_LONG).show();
		}
		clear();
		listView = (ListView) findViewById(R.id.listaMunicipios);
		listView.setOnItemClickListener(this);
		adapter = new Adapter(this, politicosDoMunicipio);
		
		Intent i = getIntent();
		nomeTV = (TextView) findViewById(R.id.NomeMunicipio);
		municipioNome = i.getExtras().getString("NomeMunicipio");
		nomeTV.setText(municipioNome);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Wait...");
		dialog.show();
		waitTimer();
	}	
	public void exibi(){
		int i = 0;
		i = municipio.getPoliticos().size();
		/*if(i == 0){
			Toast.makeText(MarkerMunicipio.this, "wait for loading...", Toast.LENGTH_LONG).show();
		}
		else{
		String tam = String.valueOf(i);
		Toast.makeText(MarkerMunicipio.this, tam, Toast.LENGTH_SHORT).show();
		}*/
	}
	
	public void pegaMunicipio(View view){
		String nomeM = municipioNome.toUpperCase();
		
		if(politicosDoMunicipio.size() == 0){
		for(Politico p : municipio.getPoliticos()){
			if(p.getMunicipio().contains(nomeM)){
				politicosDoMunicipio.add(p);
			}
		}
		listView.setAdapter(adapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
        adapter.notifyDataSetChanged();
	}
		String tam = String.valueOf(politicosDoMunicipio.size());
		Toast.makeText(MarkerMunicipio.this, "O Municipio tem "+tam, Toast.LENGTH_SHORT).show();
	}
	public void waitTimer(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				hideDialog();
			}
		}, 10 * 1000);
	}

	private void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	public void clear(){
		for(Politico p: politicosDoMunicipio){
			politicosDoMunicipio.remove(p);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
