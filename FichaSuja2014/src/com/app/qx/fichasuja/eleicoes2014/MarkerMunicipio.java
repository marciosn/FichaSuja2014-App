package com.app.qx.fichasuja.eleicoes2014;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.app.qx.fichasuja.eleicoes2014.models.MunicipioObj;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MarkerMunicipio extends Activity {
	private TextView nomeTV;
	private String municipioNome;
	private PegarMunicipios municipio = new PegarMunicipios();
	private ProgressDialog dialog;
	private List<Politico> politicosDoMunicipio = new ArrayList<Politico>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_municipio);
		municipio.pegarJSON();
		
		Intent i = getIntent();
		nomeTV = (TextView) findViewById(R.id.NomeMunicipio);
		municipioNome = i.getExtras().getString("NomeMunicipio");
		nomeTV.setText(municipioNome);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Wait...");
		dialog.show();
		waitTimer();
	}	
	public void exibi(View view){
		int i = 0;
		i = municipio.getPoliticos().size();
		if(i == 0){
			Toast.makeText(MarkerMunicipio.this, "wait for loading...", Toast.LENGTH_LONG).show();
		}
		else{
		String tam = String.valueOf(i);
		Toast.makeText(MarkerMunicipio.this, tam, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void pegaMunicipio(){
		String nomeM = municipioNome.toLowerCase();
		for(Politico p : municipio.getPoliticos()){
			if(p.getMunicipio().equals(nomeM)){
				politicosDoMunicipio.add(p);
			}
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
		}, 8 * 1000);
	}

	private void hideDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marker_municipio, menu);
		return true;
	}
}
