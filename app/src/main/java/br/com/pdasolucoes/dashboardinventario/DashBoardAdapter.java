package br.com.pdasolucoes.dashboardinventario;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PDA on 20/06/2017.
 */

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    private Context context;
    private String[] lista, referencia;

    public DashBoardAdapter(Context context, String[] lista) {
        this.context = context;
        this.lista = lista;
        referencia = context.getResources().getStringArray(R.array.dashboard);
    }

    @Override
    public DashBoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_dashboard_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        mvh.setIsRecyclable(false);

        return mvh;
    }

    @Override
    public void onBindViewHolder(DashBoardAdapter.MyViewHolder holder, int position) {

        if (lista != null) {
            tvDado.setText(lista[position]);
        } else {
            tvDado.setText("Indisponível");
        }

        tvReferencia.setText(referencia[position]);
    }

    @Override
    public int getItemCount() {
        return lista.length;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public TextView tvDado, tvReferencia;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            tvDado = (TextView) itemView.findViewById(R.id.tvDado);
            tvReferencia = (TextView) itemView.findViewById(R.id.tvReferenciaDado);
        }
    }
}
