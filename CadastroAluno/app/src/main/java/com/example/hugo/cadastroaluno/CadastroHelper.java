package com.example.hugo.cadastroaluno;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

public class CadastroHelper{

    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtSite;
    private EditText edtEmail;
    private EditText edtEndereco;
    private SeekBar sbNota;
    private ImageView foto;

    private Aluno aluno;

    public CadastroHelper(actCadastro activity){
        edtNome = (EditText)activity.findViewById(R.id.edtNome);
        edtTelefone = (EditText)activity.findViewById(R.id.edtTelefone);
        edtSite = (EditText)activity.findViewById(R.id.edtSite);
        edtEmail = (EditText)activity.findViewById(R.id.edtEmail);
        edtEndereco = (EditText)activity.findViewById(R.id.edtEndereco);
        sbNota = (SeekBar)activity.findViewById(R.id.sbNota);
        foto = (ImageView)activity.findViewById(R.id.foto);

        aluno = new Aluno();
    }

    public Aluno getAluno(){
        aluno.setNome(edtNome.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());
        aluno.setSite(edtSite.getText().toString());
        aluno.setEmail(edtEmail.getText().toString());
        aluno.setEndereco(edtEndereco.getText().toString());
        aluno.setNota(Double.valueOf(sbNota.getProgress()));

        return aluno;
    }

    public void setAluno(Aluno aluno){
        edtNome.setText(aluno.getNome());
        edtTelefone.setText(aluno.getTelefone());
        edtEndereco.setText(aluno.getEndereco());
        edtSite.setText(aluno.getSite());
        edtEmail.setText(aluno.getEmail());
        sbNota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;

        if(aluno.getFoto() !=null){
            carregarFoto(aluno.getFoto());
        }
    }

    public ImageView getFoto(){
        return foto;
    }

    public void carregarFoto(String localFoto){
        Bitmap imagemFoto = BitmapFactory.decodeFile(localFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto,100,100,true);
        aluno.setFoto(localFoto);
        foto.setImageBitmap(imagemFotoReduzida);
    }




}