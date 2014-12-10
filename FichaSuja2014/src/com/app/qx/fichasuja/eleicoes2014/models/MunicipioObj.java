package com.app.qx.fichasuja.eleicoes2014.models;

import com.google.android.gms.maps.model.LatLng;


public class MunicipioObj {
	private String nomeMunicipio;
	private double lat;
	private double lng;
	private LatLng latLng;
	
	public MunicipioObj(String nomeMunicipio, double lat, double lng){
		this.nomeMunicipio = nomeMunicipio;
		this.lat = lat;
		this.lng = lng;
		this.latLng = new LatLng(lat, lng);
	}
	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	public LatLng getLatLng() {
		return latLng;
	}
	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

}
