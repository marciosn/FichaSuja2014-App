package com.app.qx.fichasuja.eleicoes2014.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.app.qx.fichasuja.eleicoes2014.R;
import com.app.qx.fichasuja.eleicoes2014.controller.AppController;
import com.app.qx.fichasuja.eleicoes2014.models.Politico;
import com.google.android.gms.internal.cn;

@SuppressLint("ViewHolder")
public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Politico> politicos;

	public CustomListAdapter(Context context, List<Politico> pol) {
		inflater = LayoutInflater.from(context);
		this.politicos = pol;
	}

	@Override
	public int getCount() {
		return politicos.size();
	}

	@Override
	public Object getItem(int location) {
		return politicos.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Politico politico = politicos.get(position);
		//view = inflater.inflate(R.layout.item_list, null);
		convertView = inflater.inflate(R.layout.list_row, null);
		//((TextView) view.findViewById(R.id.text)).setText(politico.getGestor());
		((TextView) convertView.findViewById(R.id.itemGestor)).setText(politico.getGestor());
		((TextView) convertView.findViewById(R.id.itemMunicipio)).setText("Natureza do Processo: "+politico.getNatureza_processo());
		return convertView;
	}

}