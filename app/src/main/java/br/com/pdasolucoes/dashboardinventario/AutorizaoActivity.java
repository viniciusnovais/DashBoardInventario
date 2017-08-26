package br.com.pdasolucoes.dashboardinventario;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import br.com.pdasolucoes.dashboardinventario.Services.Service;

public class AutorizaoActivity extends AppCompatActivity {

    private Button btEntrar, btConfig;
    private EditText editAutorizacao;
    private String textAutorizacao;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorizao);

        btEntrar = (Button) findViewById(R.id.btEntrar);
        editAutorizacao = (EditText) findViewById(R.id.editAuotorizacao);
        btConfig = (Button) findViewById(R.id.btConfiguraoes);
        SharedPreferences preferences = getSharedPreferences("inventario", MODE_PRIVATE);
        SharedPreferences preferences1Config = getSharedPreferences("CONFIG", MODE_PRIVATE);

        if (preferences1Config.getString("servidor", "").equals("")
                || preferences1Config.getString("diretorio", "").equals("")) {
            Intent i = new Intent(AutorizaoActivity.this, ConfiguracoesActivity.class);
            startActivity(i);
        }

        if (preferences != null) {
            textAutorizacao = preferences.getString("autorizacao", "");
            if (textAutorizacao != "") {
                AsyncAutorizar task = new AsyncAutorizar();
                task.execute();
            }
        }

        btConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AutorizaoActivity.this, ConfiguracoesActivity.class);
                startActivity(i);
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AutorizaoActivity.this);
                builder.setView(R.layout.splash_popup);
                dialog = builder.create();

                textAutorizacao = editAutorizacao.getText().toString();

                if (textAutorizacao.length() != 0 && textAutorizacao.toString() != "") {
                    editAutorizacao.setText("");

                    AsyncAutorizar task = new AsyncAutorizar();
                    task.execute();
                } else {
                    Toast.makeText(AutorizaoActivity.this, "Digite a autorização", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public class AsyncAutorizar extends AsyncTask {

        int idInventario = 0;

        @Override
        protected void onPreExecute() {
            Service.ERROR = "";
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            SharedPreferences preferences = getSharedPreferences("CONFIG", MODE_PRIVATE);
            idInventario = Service.GetInventario(textAutorizacao, preferences);


            return params;
        }


        @Override
        protected void onPostExecute(Object o) {

            if (Service.ERROR.equals("")) {
                if (idInventario <= 0) {
                    dialog.dismiss();
                    Toast.makeText(AutorizaoActivity.this, "Autorização inválida", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Intent i = new Intent(AutorizaoActivity.this, DashBoardActivity.class);


                    SharedPreferences preferences = getSharedPreferences("inventario", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("autorizacao", textAutorizacao);
                    editor.putInt("idInventario", idInventario);
                    editor.commit();

                    startActivity(i);
                    finish();
                }
            } else {
                dialog.dismiss();
                Toast.makeText(AutorizaoActivity.this, Service.ERROR, Toast.LENGTH_SHORT).show();
                Toast.makeText(AutorizaoActivity.this, "Verifique as configurações ou tente outra autorização", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(o);
        }


    }

}
