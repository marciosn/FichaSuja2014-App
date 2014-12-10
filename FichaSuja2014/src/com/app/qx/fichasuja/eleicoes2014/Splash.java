package com.app.qx.fichasuja.eleicoes2014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Splash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}
	
	public void lista(View view){
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    	startActivity(intent);
	}
	public void mapa(View view){
		Intent intent = new Intent(getApplicationContext(), MunicipiosNoMapa.class);
    	startActivity(intent);
	}
}

