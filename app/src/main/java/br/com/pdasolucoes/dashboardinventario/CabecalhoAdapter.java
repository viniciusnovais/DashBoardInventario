package br.com.pdasolucoes.dashboardinventario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by PDA on 20/06/2017.
 */

public class CabecalhoAdapter extends RecyclerView.Adapter<CabecalhoAdapter.MyViewHolder> {

    private Context context;
    private List<Integer> lista;

    public CabecalhoAdapter(Context context, List<Integer> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public CabecalhoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cabecalho_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(CabecalhoAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
