package com.app.qx.fichasuja.eleicoes2014.models;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
	private List<Politico> politicosPorMunicipio = new ArrayList<Politico>();

	public List<Politico> getPoliticosPorMunicipio() {
		return politicosPorMunicipio;
	}

	public void setPoliticosPorMunicipio(List<Politico> politicosPorMunicipio) {
		this.politicosPorMunicipio = politicosPorMunicipio;
	}
	

}
