package com.example.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TelaProduto extends AppCompatActivity {

    EditText edCodigo, edDescricao, edPreco, edEstoque, edFornecedor, edTelefoneFornec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_produto);
        edCodigo = (EditText) findViewById(R.id.edCodigo);
        edDescricao = (EditText) findViewById(R.id.edDescricao);
        edPreco = (EditText) findViewById(R.id.edPreco);
        edEstoque = (EditText) findViewById(R.id.edEstoque);
        edFornecedor = (EditText) findViewById(R.id.edFornecedor);
        edTelefoneFornec = (EditText) findViewById(R.id.edTelefoneFornec);
        Intent it = getIntent();
        Produto p = (Produto) it.getSerializableExtra("prodEdicao");
        if(p != null){
            edCodigo.setText(""+p.getCodigo());
            edCodigo.setEnabled(false);
            edDescricao.setText(p.getDescricao());
        }
    }

    public void confirmar(View v){
        Produto p = new Produto();
        p.setCodigo(Integer.parseInt(edCodigo.getText().toString()));
        p.setDescricao(edDescricao.getText().toString());
        p.setValor(Integer.parseInt(edPreco.getText().toString()));
        p.setEstoque(Integer.parseInt(edEstoque.getText().toString()));
        p.setNomeFornecedor(edFornecedor.getText().toString());
        p.setTelefoneFornecedor(edTelefoneFornec.getText().toString());
        Intent resposta = new Intent();
        resposta.putExtra("produto", p);
        setResult(RESULT_OK, resposta);
        finish();
    }

    public void cancelar(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}