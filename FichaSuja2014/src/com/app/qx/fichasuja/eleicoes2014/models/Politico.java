package com.app.qx.fichasuja.eleicoes2014.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Politico implements Parcelable{
	private String gestor;
	private String municipio;
	private String natureza_processo;
	private String nota_improbidade;
	private double processo;
	private double exercicio;
	private double codigo_gestor;
	private double codigo_municipio;
	
	public Politico() {
		/*gestor = "";
		municipio = "";
		natureza_processo = "";
		nota_improbidade = "";
		processo = 0;
		exercicio = 0;
		codigo_gestor = 0;
		codigo_municipio = 0;*/
	}
	public Politico(Parcel parcel) {
		this.gestor = parcel.readString();
		this.municipio = parcel.readString();
		this.natureza_processo = parcel.readString();
		this.nota_improbidade = parcel.readString();
		this.processo = parcel.readDouble();
		this.exercicio = parcel.readDouble();
		this.codigo_gestor = parcel.readDouble();
		this.codigo_municipio = parcel.readDouble();
	}
	

	public String getGestor() {
		return gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNatureza_processo() {
		return natureza_processo;
	}

	public void setNatureza_processo(String natureza_processo) {
		this.natureza_processo = natureza_processo;
	}

	public String getNota_improbidade() {
		return nota_improbidade;
	}

	public void setNota_improbidade(String nota_improbidade) {
		this.nota_improbidade = nota_improbidade;
	}

	public double getProcesso() {
		return processo;
	}

	public void setProcesso(double processo) {
		this.processo = processo;
	}

	public double getExercicio() {
		return exercicio;
	}

	public void setExercicio(double exercicio) {
		this.exercicio = exercicio;
	}

	public double getCodigo_gestor() {
		return codigo_gestor;
	}

	public void setCodigo_gestor(double codigo_gestor) {
		this.codigo_gestor = codigo_gestor;
	}

	public double getCodigo_municipio() {
		return codigo_municipio;
	}

	public void setCodigo_municipio(double codigo_municipio) {
		this.codigo_municipio = codigo_municipio;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(gestor);
		dest.writeString(municipio);
		dest.writeString(natureza_processo);
		dest.writeString(nota_improbidade);
		dest.writeDouble(processo);
		dest.writeDouble(exercicio);
		dest.writeDouble(codigo_gestor);
		dest.writeDouble(codigo_municipio);
	}
public static final Parcelable.Creator<Politico> CREATOR = new Creator<Politico>() {
		
		@Override
		public Politico[] newArray(int size) {
			return new Politico[size];
		}
		
		@Override
		public Politico createFromParcel(Parcel source) {
			return new Politico(source);
		}
	};

}
