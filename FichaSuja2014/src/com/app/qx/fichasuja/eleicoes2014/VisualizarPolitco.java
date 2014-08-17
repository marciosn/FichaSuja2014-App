package com.app.qx.fichasuja.eleicoes2014;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class VisualizarPolitco extends Activity {

	private TextView gestorTV, processoTV, municipioTV, natureza_processoTV,
			exercicioTV, nota_improbidadeTV, codigo_gestorTV,
			codigo_municipioVT;
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
		setContentView(R.layout.visualizar_politco);

		Intent i = getIntent();

		gestorTV = (TextView) findViewById(R.id.gestor);
		processoTV = (TextView) findViewById(R.id.processo);
		municipioTV = (TextView) findViewById(R.id.municipio);
		natureza_processoTV = (TextView) findViewById(R.id.natureza_processo);
		exercicioTV = (TextView) findViewById(R.id.exercicio);
		nota_improbidadeTV = (TextView) findViewById(R.id.nota_improbidade);
		codigo_gestorTV = (TextView) findViewById(R.id.codigo_gestor);
		codigo_municipioVT = (TextView) findViewById(R.id.codigo_municipio);

		gestorTV.setText(i.getExtras().getString(GESTOR));
		processoTV.setText(i.getExtras().getString(PROCESSO));
		municipioTV.setText(i.getExtras().getString(MUNICIPIO));
		natureza_processoTV.setText(i.getExtras().getString(NATUREZA_PROCESSO));
		exercicioTV.setText(i.getExtras().getString(EXERCICIO));
		nota_improbidadeTV.setText(i.getExtras().getString(NOTA_IMPROBIDADE));
		codigo_gestorTV.setText(i.getExtras().getString(CODIG0_GESTOR));
		codigo_municipioVT.setText(i.getExtras().getString(CODIGO_MUNICIPIO));

	}

}
