package com.example.telas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int NOVO_PRODUTO = 10;
    final int EDITAR_PRODUTO = 11;
    ListView lista;
    ArrayList<Produto> produtos = new ArrayList<Produto>();
    ProdutoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.listaProdutos);
        adapter = new ProdutoAdapter(produtos);
        lista.setAdapter(adapter);
    }


    public void novo(View v){
        Intent intencao = new Intent(this, TelaProduto.class);
        startActivityForResult(intencao, NOVO_PRODUTO);

    }

    class ProdutoAdapter extends ArrayAdapter<Produto> {
        List<Produto> products;

        public ProdutoAdapter(List<Produto> produtos) {
            super(MainActivity.this, R.layout.item_lista_produto,
                    produtos);
            products = produtos;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(int pos, View recicled, ViewGroup parent) {
            if (recicled == null) {
                recicled = getLayoutInflater().inflate(R.layout.item_lista_produto,
                        null);
            }
            Produto prod = products.get(pos);
            ((TextView) recicled.findViewById(R.id.il_produto)).setText(
                    prod.getDescricao());
            ((TextView) recicled.findViewById(R.id.il_codigo)).setText(
                    String.valueOf(prod.getCodigo()));
            ((TextView) recicled.findViewById(R.id.il_estoque)).setText(
                    String.valueOf(prod.getEstoque()));
            ((TextView) recicled.findViewById(R.id.il_valor)).setText(
                    String.valueOf(prod.getValor()));
            ((TextView) recicled.findViewById(R.id.il_nomeFornecedor)).setText(
                    prod.getNomeFornecedor());
            ((TextView) recicled.findViewById(R.id.il_telefoneFornecedor)).setText(
                    prod.getTelefoneFornecedor());
            return recicled;
        }
    }

    @Override
    public void onSaveInstanceState( Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("produtos", produtos);
    }

    @Override
    public void onActivityResult(int codigo, int status, Intent dados){
        if(codigo == NOVO_PRODUTO){
            if(status == RESULT_OK) {
                Produto p = (Produto) dados.getSerializableExtra("produto");
                produtos.add(p);
                adapter.notifyDataSetChanged();
                // adicioanar na lista de produto, atualizar o adapter
                Toast.makeText(this, "Produto " + p.getDescricao()
                + " recebido.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(codigo, status, dados);
    }

    private void editar(Produto p){
        Intent editar = new Intent(this, TelaProduto.class);
        Produto pr = new Produto();
        p.setCodigo(1); p.setDescricao("Teste 01");
        editar.putExtra("prodEdicao", pr);
        startActivityForResult(editar, EDITAR_PRODUTO);
    }
}