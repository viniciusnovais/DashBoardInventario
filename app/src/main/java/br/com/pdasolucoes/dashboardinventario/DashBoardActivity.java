package br.com.pdasolucoes.dashboardinventario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;

import android.graphics.Rect;
import android.media.JetPlayer;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import br.com.pdasolucoes.dashboardinventario.Services.Service;

/**
 * Created by PDA on 20/06/2017.
 */

public class DashBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerViewCabecalho;
    private DashBoardAdapter adapter;
    private CabecalhoAdapter adapterCabecalho;
    private SeekBar progressBar, progressBarVendas;
    private TextView tvPorcentagemDeposito, tvPorcentagemVendas;
    private Handler handler = new Handler();
    private int cnt = 0, value = 0, cnt2 = 0, value2 = 0;
    private List<DashBoardModelo> lista;
    private AlertDialog dialog;

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

        //dashboard cabecalho
        recyclerViewCabecalho = (RecyclerView) findViewById(R.id.recyclerViewCabecalho);

        GridLayoutManager glmCabecalho = new GridLayoutManager(this, 1);
        recyclerViewCabecalho.setHasFixedSize(true);
        recyclerViewCabecalho.setLayoutManager(glmCabecalho);

        progressBar = (SeekBar) findViewById(R.id.progressBarDeposito);
        progressBar.setEnabled(false);
        tvPorcentagemDeposito = (TextView) findViewById(R.id.porcentagemDeposito);

        progressBarVendas = (SeekBar) findViewById(R.id.progressBarPisoVendas);
        progressBarVendas.setEnabled(false);
        tvPorcentagemVendas = (TextView) findViewById(R.id.porcentagemVendas);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.splash_popup);
        dialog = builder.create();

        AsyncDados task = new AsyncDados();
        task.execute();

        runMultipleAsyncTask();

    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runMultipleAsyncTask();
                cnt = 1;
                cnt2 = 1;
                handler.postDelayed(this, 300000);
            }
        }, 300000);
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

    public class AsynDeposito extends AsyncTask<Object, Integer, Integer> {

        private Rect bounds = progressBar.getProgressDrawable().getBounds();
        private int porcetagem;
        SharedPreferences preferences;

        @Override
        protected Integer doInBackground(Object... params) {
            preferences = getSharedPreferences("inventario", MODE_PRIVATE);
            if (preferences.getInt("idInventario", 0) > 0) {
                lista = Service.GetDashBoard(preferences.getInt("idInventario", 0));

                porcetagem = lista.get(0).getPorcentEnderecoDpto();
                for (int i = 0; i <= porcetagem; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    publishProgress(i);
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);

            if (cnt == 0) {
                tvPorcentagemDeposito.setText(values[0] + "%");
                if (values[0] <= 30) {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_default));
                } else if (values[0] <= 60) {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_yellow));
                } else if (values[0] <= 99) {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_blue));
                } else {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_green));
                }

                progressBar.getProgressDrawable().setBounds(bounds);
                progressBar.setProgress(values[0]);
                value = porcetagem;
            } else {
                int d = porcetagem - value;
                int novo_value = value + d;
                tvPorcentagemDeposito.setText(novo_value + "%");

                if (novo_value <= 30) {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_default));
                } else if (novo_value <= 60) {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_yellow));
                } else if (novo_value <= 99) {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_blue));
                } else {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_green));
                }

                progressBar.getProgressDrawable().setBounds(bounds);
                progressBar.setProgress(novo_value);
            }


        }

    }

    public class AsyncVendas extends AsyncTask<Object, Integer, Void> {

        private Rect bounds = progressBar.getProgressDrawable().getBounds();
        private int porcetagem;
        SharedPreferences preferences;

        @Override
        protected Void doInBackground(Object... params) {
            preferences = getSharedPreferences("inventario", MODE_PRIVATE);
            if (preferences.getInt("idInventario", 0) > 0) {
                lista = Service.GetDashBoard(preferences.getInt("idInventario", 0));
                try {
                    porcetagem = lista.get(0).getPorcentEnderecoDpto();
                    for (int i = 0; i <= porcetagem; i++) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        publishProgress(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear().commit();
                    e.printStackTrace();

                    //Toast.makeText(getApplicationContext(), "message", e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            if (cnt2 == 0) {
                tvPorcentagemVendas.setText(values[0] + "%");
                if (values[0] <= 30) {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_default));
                } else if (values[0] <= 60) {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_yellow));
                } else if (values[0] <= 99) {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_blue));
                } else {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_green));
                }

                progressBarVendas.getProgressDrawable().setBounds(bounds);
                progressBarVendas.setProgress(values[0]);
                value2 = porcetagem;
            } else {
                int d = porcetagem - value2;
                int novo_value = value2 + d;
                tvPorcentagemVendas.setText(novo_value + "%");
                if (novo_value <= 30) {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_default));
                } else if (novo_value <= 60) {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_yellow));
                } else if (novo_value <= 99) {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_blue));
                } else {
                    progressBarVendas.setProgressDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.setprogressdrawable_green));
                }

                progressBarVendas.getProgressDrawable().setBounds(bounds);
                progressBarVendas.setProgress(novo_value);
            }

        }
    }

    private void runMultipleAsyncTask() {


        AsynDeposito asyncDeposito = new AsynDeposito();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            asyncDeposito.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            asyncDeposito.execute();
        }

        AsyncVendas asyncVendas = new AsyncVendas();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            asyncVendas.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            asyncVendas.execute();
        }

    }


    public class AsyncDados extends AsyncTask<Object, Integer, List<DashBoardModelo>> {
        SharedPreferences preferences;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<DashBoardModelo> doInBackground(Object... params) {
            preferences = getSharedPreferences("inventario", MODE_PRIVATE);
            if (preferences.getInt("idInventario", 0) > 0) {
                lista = Service.GetDashBoard(preferences.getInt("idInventario", 0));
            }
            return lista;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(List<DashBoardModelo> dashBoardModelos) {
            super.onPostExecute(dashBoardModelos);

            String[] arrayCabecalho = new String[6];
            try {
                arrayCabecalho[0] = dashBoardModelos.get(0).getNomeLider();
                arrayCabecalho[1] = dashBoardModelos.get(0).getData();
                arrayCabecalho[2] = preferences.getInt("idInventario", 0) + " - " + preferences.getString("autorizacao", "");
                arrayCabecalho[3] = String.valueOf(dashBoardModelos.get(0).getTotalColaboradores());
                arrayCabecalho[4] = dashBoardModelos.get(0).getBandeira();
                arrayCabecalho[5] = dashBoardModelos.get(0).getNomeLoja();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            adapterCabecalho = new CabecalhoAdapter(DashBoardActivity.this, arrayCabecalho);
            recyclerViewCabecalho.setAdapter(adapterCabecalho);

            String[] arrayDashBoard = new String[11];

            try {
                arrayDashBoard[0] = String.valueOf(dashBoardModelos.get(0).getPrevisaoPecas());
                arrayDashBoard[1] = String.valueOf(dashBoardModelos.get(0).getTotalPecasRealizado());
                arrayDashBoard[2] = String.valueOf(dashBoardModelos.get(0).getPrevisaoEnderecos());
                arrayDashBoard[3] = String.valueOf(dashBoardModelos.get(0).getTotalDeEnderecos());
                arrayDashBoard[4] = dashBoardModelos.get(0).getHorarioInicioAuditoria();
                arrayDashBoard[5] = dashBoardModelos.get(0).getHorarioFimAuditoria();
                arrayDashBoard[6] = dashBoardModelos.get(0).getHorarioInicioDivergencia();
                arrayDashBoard[7] = dashBoardModelos.get(0).getHorarioFimDivergencia();
                arrayDashBoard[8] = String.valueOf(dashBoardModelos.get(0).getQtdeSku());
                arrayDashBoard[9] = String.valueOf(dashBoardModelos.get(0).getNumeroPaginas());
                arrayDashBoard[10] = String.valueOf(dashBoardModelos.get(0).getQtdeAlteracao());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


            adapter = new DashBoardAdapter(DashBoardActivity.this, arrayDashBoard);
            recyclerView.setAdapter(adapter);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
