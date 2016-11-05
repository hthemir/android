package com.example.hugo.marsrovers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText edtPlateauX;
    private EditText edtPlateauY;
    private Button btnOK;
    private EditText edtX;
    private EditText edtY;
    private Spinner listViewDirecoes;
    private EditText edtComandos;
    private Button btnAdicionar;
    private ListView listViewResultados;
    private ArrayAdapter<Rover> adaptador;
    private int adapterLayout = android.R.layout.simple_list_item_1;

    Coordenada aux = new Coordenada(0,0);
    char direcao;
    char[] comandos;
    Plateau plateau;
    ArrayList<Rover> lista;
    String[] direcoes =  new String[] { "N", "E", "S", "W"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPlateauX = (EditText)findViewById(R.id.editPlateauX);
        edtPlateauY = (EditText)findViewById(R.id.editPlateauY);
        btnOK = (Button)findViewById(R.id.btnOK);
        edtX = (EditText) findViewById(R.id.editX);
        edtY = (EditText) findViewById(R.id.editY);
        listViewDirecoes = (Spinner) findViewById(R.id.listViewDirecoes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,direcoes);
        listViewDirecoes.setAdapter(adapter);

        edtComandos = (EditText) findViewById(R.id.editComandos);
        btnAdicionar = (Button)findViewById(R.id.btnAdicionar);
        listViewResultados = (ListView)findViewById(R.id.listViewResultados);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux.setX(Integer.parseInt(edtPlateauX.getText().toString()));
                aux.setY(Integer.parseInt(edtPlateauY.getText().toString()));
                plateau = new Plateau(new Coordenada(0, 0),aux);
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux.setX(Integer.parseInt(edtX.getText().toString()));
                aux.setY(Integer.parseInt(edtY.getText().toString()));
                direcao = listViewDirecoes.getSelectedItem().toString().charAt(0);
                comandos = edtComandos.toString().toCharArray();

                Rover rover = new Rover(aux,direcao);
                for(char c:comandos){
                    rover.comandar(c);
                }
                lista.add(rover);

                adaptador = new ArrayAdapter<Rover>(MainActivity.this,adapterLayout,lista);
                listViewResultados.setAdapter(adaptador);


            }
        });

    }
}
