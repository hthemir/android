package com.example.hugo.cadastroaluno;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;

public class actCadastro extends AppCompatActivity {

    private Button btnSalvarDados;
    private CadastroHelper helper;
    private Aluno alunoParaSerAlterado = null;
    private String localArquivo;
    private static final int FAZER_FOTO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro);

        helper = new CadastroHelper(this);
        btnSalvarDados = (Button)findViewById(R.id.btnSalvarDados);
        helper.getFoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //localArquivo = Environment.getDataDirectory() + "/" + System.currentTimeMillis() + ".jpg";
                localArquivo = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg";
                File arquivo = new File(localArquivo);
                Uri localFoto = Uri.fromFile(arquivo);
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT,localFoto);
                startActivityForResult(irParaCamera,FAZER_FOTO);
            }
        });
        alunoParaSerAlterado = (Aluno) getIntent().getSerializableExtra("ALUNO_SELECIONADO");
        if(alunoParaSerAlterado!= null){
            helper.setAluno(alunoParaSerAlterado);
        }
        btnSalvarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno = helper.getAluno();
                AlunoDAO dao = new AlunoDAO(actCadastro.this);
                if(aluno.getId()==null) {
                    dao.cadastrar(aluno);
                }else{
                    dao.alterar(aluno);
                }
                dao.close();
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FAZER_FOTO){
            if(resultCode == Activity.RESULT_OK){
                helper.carregarFoto(this.localArquivo);
            } else {
                localArquivo = null;
            }
        }
    }
}
