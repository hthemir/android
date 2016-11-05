package com.example.hugo.cadastroaluno;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class actMain extends AppCompatActivity {

    private ListView lstAlunos;

    private List<Aluno> listaAlunos;
    private ArrayAdapter<Aluno> adapter;
    private int adapterLayout = android.R.layout.simple_list_item_1;
    private Aluno alunoSelecionado = null;

    private final String TAG = "CADASTRO_ALUNO";
    private final String ALUNOS_KEY = "LISTA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        lstAlunos = (ListView)findViewById(R.id.lstAlunos);

        registerForContextMenu(lstAlunos);

        lstAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
                //Toast.makeText(actMain.this, "Aluno: "+ listaAlunos.get(posicao), Toast.LENGTH_LONG).show();
                Intent form = new Intent(actMain.this, actCadastro.class);
                alunoSelecionado = (Aluno) lstAlunos.getItemAtPosition(posicao);
                form.putExtra("ALUNO_SELECIONADO", alunoSelecionado);
                startActivity(form);
            }
        });
        lstAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int posicao, long id) {
                alunoSelecionado = (Aluno) adapter.getItem(posicao);
                Log.i(TAG, "Aluno selecionado ListView.longClick(): "+ alunoSelecionado.getNome());
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);
        getMenuInflater().inflate(R.menu.menu_contexto, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menuDeletar:
                excluirAluno();
                break;
            case R.id.menuLigar:
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: " + alunoSelecionado.getTelefone()));
                startActivity(intent);
                break;
            case R.id.menuEnviarSMS:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms: " + alunoSelecionado.getTelefone()));
                intent.putExtra("sms_body","Mensagem de boas vindas :-)");
                startActivity(intent);
                break;
            case R.id.menuAcharNoMapa:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?z=14&q=" + alunoSelecionado.getEndereco()));
                intent.putExtra("sms_body", "Mensagem de boas vindas :-)");
                startActivity(intent);
                break;
            case R.id.menuNavegar:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http:" + alunoSelecionado.getSite()));
                startActivity(intent);
                break;
            case R.id.menuEnviarEmail:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {alunoSelecionado.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Falando sobre o curso");
                intent.putExtra(Intent.EXTRA_TEXT, "O curso foi muito legal");
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_novo:
                Intent intent = new Intent(actMain.this,actCadastro.class);
                startActivity(intent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void carregarLista(){
        AlunoDAO dao = new AlunoDAO(this);
        this.listaAlunos = dao.listar();
        dao.close();

        this.adapter = new ArrayAdapter<Aluno>(this,adapterLayout,listaAlunos);
        this.lstAlunos.setAdapter(adapter);
    }

    protected void onResume(){
        super.onResume();
        this.carregarLista();
    }

    private void excluirAluno(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma a exclusão de " + alunoSelecionado.getNome());

        builder.setNegativeButton("Não",null);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlunoDAO dao = new AlunoDAO(actMain.this);
                dao.deletar(alunoSelecionado);
                dao.close();
                carregarLista();
                alunoSelecionado = null;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirmação de operação");
        dialog.show();
    }
}
