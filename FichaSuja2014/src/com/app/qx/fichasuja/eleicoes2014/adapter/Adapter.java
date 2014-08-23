package com.app.qx.fichasuja.eleicoes2014.adapter;

import java.util.ArrayList;
import java.util.List;

import com.app.qx.fichasuja.eleicoes2014.R;
import com.app.qx.fichasuja.eleicoes2014.R.id;
import com.app.qx.fichasuja.eleicoes2014.R.layout;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter extends BaseAdapter {
	private ArrayList<Politico> politicos;
	private LayoutInflater inflater;

	public Adapter(Context context, List<Politico> politicos2) {
		this.politicos = (ArrayList<Politico>) politicos2;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return politicos.size();
	}

	@Override
	public Object getItem(int position) {
		return politicos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Politico politico = politicos.get(position);
		//view = inflater.inflate(R.layout.item_list, null);
		view = inflater.inflate(R.layout.list_row, null);
		//((TextView) view.findViewById(R.id.text)).setText(politico.getGestor());
		((TextView) view.findViewById(R.id.itemGestor)).setText(politico.getGestor());
		((TextView) view.findViewById(R.id.itemMunicipio)).setText(politico.getMunicipio());
		return view;
	}

}
