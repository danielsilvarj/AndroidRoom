package com.example.projetoroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import entity.Cliente;
import persistence.ClienteDao;
import persistence.DataBaseDB;

public class MainActivity extends AppCompatActivity {

    EditText textId;
    EditText textNome;
    EditText textEmail;
    LiveData<List<Cliente>> dados;
    Cliente cliente;
    TextView txtResult;


    public DataBaseDB dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = DataBaseDB.getInstance(getApplicationContext());
        dados = dao.getClienteDao().listar();

        textId = findViewById(R.id.textId);
        textNome = findViewById(R.id.textNome);
        textEmail = findViewById(R.id.textEmail);
        txtResult = findViewById(R.id.txtResult);

        this.cliente = new Cliente(null,"","");

        dados.observe(this,(clientes) -> {txtResult.setText(cliente.toString());});

    }

    public void gravar(View v){
        this.cliente = new Cliente(null,textNome.getText().toString(), textEmail.getText().toString());

        new InnerGravar(getApplicationContext(),cliente).execute();
        Toast.makeText(getApplicationContext(),"Dados Grvados", Toast.LENGTH_SHORT).show();

    }

    public void listar(View v){

    }

    public void BuscarCodigo(View v){
        new InnerConsultar(getApplicationContext(),cliente).execute(Integer.valueOf(textId.getText().toString()));
    }

    public class InnerGravar extends AsyncTask<String, String, String>{

        private Context context;
        private Cliente cliente;

        public InnerGravar(Context context, Cliente c){
            this.context = context;
            this.cliente = c;

        }

        protected String doInBackground(String ...strings){
            try {

                dao = DataBaseDB.getInstance(context);
                dao.getClienteDao().insert(this.cliente);
                return this.cliente.toString();

            }catch (Exception ex){
                return "Nao gravado : " + ex.getMessage();
            }
        }
    }

    public class InnerConsultar extends AsyncTask<Integer, Void, Cliente>{

        private Context context;
        private Cliente cliente;

        public InnerConsultar(Context context, Cliente c){

            this.context = context;
            this.cliente = c;
            dao = DataBaseDB.getInstance(context);

        }

        protected Cliente doInBackground(Integer ...id){
            return dao.getClienteDao().listarUm(id[0]);
        }

        protected void onPostExecute(Cliente cliente){
            Toast.makeText(MainActivity.this, cliente.toString(),Toast.LENGTH_SHORT).show();
        }

    }

}
