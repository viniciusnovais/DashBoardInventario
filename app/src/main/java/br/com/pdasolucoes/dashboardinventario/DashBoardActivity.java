package br.com.pdasolucoes.dashboardinventario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PDA on 20/06/2017.
 */

public class DashBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerViewCabecalho;
    private DashBoardAdapter adapter;
    private CabecalhoAdapter adapterCabecalho;
    List<Integer> lista = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        setContentView(R.layout.activity_dashboard);


        //dashboard principal
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager llm = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        for (int i = 0; i < 15; i++) {
            lista.add(i);
        }

        adapter = new DashBoardAdapter(this, lista);
        recyclerView.setAdapter(adapter);

        //dashboard cabecalho
        recyclerViewCabecalho = (RecyclerView) findViewById(R.id.recyclerViewCabecalho);

        GridLayoutManager glmCabecalho = new GridLayoutManager(this, 1);
        recyclerViewCabecalho.setHasFixedSize(true);
        recyclerViewCabecalho.setLayoutManager(glmCabecalho);

        adapterCabecalho = new CabecalhoAdapter(this, lista);
        recyclerViewCabecalho.setAdapter(adapterCabecalho);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.btSair:
                SharedPreferences preferences = getSharedPreferences("inventario", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear().commit();

                Intent i = new Intent(DashBoardActivity.this, AutorizaoActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
