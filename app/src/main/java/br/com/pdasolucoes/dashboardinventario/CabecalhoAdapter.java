package br.com.pdasolucoes.dashboardinventario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PDA on 20/06/2017.
 */

public class CabecalhoAdapter extends RecyclerView.Adapter<CabecalhoAdapter.MyViewHolder> {

    private Context context;
    private String[] lista, referencia;

    public CabecalhoAdapter(Context context, String[] lista) {
        this.context = context;
        this.lista = lista;
        referencia = context.getResources().getStringArray(R.array.cabecalho);
    }

    @Override
    public CabecalhoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cabecalho_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        mvh.setIsRecyclable(false);

        return mvh;
    }

    @Override
    public void onBindViewHolder(CabecalhoAdapter.MyViewHolder holder, int position) {

        if (lista[position] != null) {
            tvDado.setText(lista[position]);
        } else {
            tvDado.setText("Indisponível");
        }
        tvReferenciaDado.setText(referencia[position]);
    }

    @Override
    public int getItemCount() {
        return lista.length;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public TextView tvDado, tvReferenciaDado;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            tvDado = (TextView) itemView.findViewById(R.id.tvDado);
            tvReferenciaDado = (TextView) itemView.findViewById(R.id.tvReferenciaDado);
        }
    }
}
