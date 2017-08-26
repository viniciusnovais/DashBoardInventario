package br.com.pdasolucoes.dashboardinventario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by PDA on 17/08/2017.
 */

public class ConfiguracoesActivity extends AppCompatActivity {

    private EditText editServidor, editDiretotio, editFilial;
    private Button btnSalvar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        editServidor = (EditText) findViewById(R.id.editServidor);
        editDiretotio = (EditText) findViewById(R.id.editDiretorioVirtual);
        editFilial = (EditText) findViewById(R.id.editFilial);
        btnSalvar = (Button) findViewById(R.id.btSalvar);

        final SharedPreferences preferences = getSharedPreferences("CONFIG", MODE_PRIVATE);

        if (preferences != null) {
            editServidor.setText(preferences.getString("servidor",""));
            editDiretotio.setText(preferences.getString("diretorio",""));
            editFilial.setText(preferences.getInt("filial",-1)+"");
        }


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("servidor", editServidor.getText().toString());
                editor.putString("diretorio", editDiretotio.getText().toString());
                editor.putInt("filial", Integer.parseInt(editFilial.getText().toString()));
                editor.commit();

                Intent i = new Intent(ConfiguracoesActivity.this,AutorizaoActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
