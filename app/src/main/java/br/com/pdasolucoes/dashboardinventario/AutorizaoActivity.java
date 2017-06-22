package br.com.pdasolucoes.dashboardinventario;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AutorizaoActivity extends AppCompatActivity {

    private Button btEntrar;
    private EditText editAutorizacao;
    private String textAutorizacao;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorizao);

        btEntrar = (Button) findViewById(R.id.btEntrar);
        editAutorizacao = (EditText) findViewById(R.id.editAuotorizacao);
        SharedPreferences preferences = getSharedPreferences("inventario", MODE_PRIVATE);

        if (preferences != null) {
            textAutorizacao = preferences.getString("autorizao", "");
            if (textAutorizacao != "") {
                AsyncAutorizar task = new AsyncAutorizar();
                task.execute();
            }
        }

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AutorizaoActivity.this);
                builder.setView(R.layout.splash_popup);
                dialog = builder.create();

                textAutorizacao = editAutorizacao.getText().toString();

                if (textAutorizacao.length()!=0 && textAutorizacao.toString()!=""){
                    editAutorizacao.setText("");

                    AsyncAutorizar task = new AsyncAutorizar();
                    task.execute();
                }else{
                    Toast.makeText(AutorizaoActivity.this,"Digite a autorização",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public class AsyncAutorizar extends AsyncTask {

        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            return params;
        }

        @Override
        protected void onPostExecute(Object o) {
            Intent i = new Intent(AutorizaoActivity.this, DashBoardActivity.class);


            SharedPreferences preferences = getSharedPreferences("inventario", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("autorizacao",textAutorizacao);
            editor.commit();

            startActivity(i);
            finish();

            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            dialog.dismiss();
            super.onProgressUpdate(values);
        }
    }

}
