package com.example.telas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener  {

    final int NOVO_PRODUTO = 10;
    final int EDITAR_PRODUTO = 11;
    ListView lista;
    ArrayList<Produto> produtos = new ArrayList<Produto>();
    ProdutoAdapter adapter;
    List<Integer> selecionados = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.listaProdutos);
        adapter = new ProdutoAdapter(produtos);
        lista.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);
        lista.setOnItemLongClickListener( this );
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
            if(selecionados.contains(pos)) {
                recicled.setBackgroundResource(android.R.color.holo_purple);
            }
            else{
                recicled.setBackgroundResource(android.R.color.white);
            }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        if ( selecionados.contains( pos ) ) {
            selecionados.remove( new Integer( pos)  );
        } else {
            selecionados.add( pos );
        }
        adapter.notifyDataSetChanged();
    }

    public void excluir(View v){
        if (lista.getChoiceMode() == AbsListView.CHOICE_MODE_SINGLE) {
            int posicao = lista.getCheckedItemPosition();
            if (posicao == -1) {
                Toast.makeText(this, "Selecione a tarefa!",
                        Toast.LENGTH_SHORT).show();
            } else {
                produtos.remove(posicao);
                adapter.notifyDataSetChanged();
                lista.clearChoices();
            }
        } else {
            if (lista.getCheckedItemCount() > 0) {
                SparseBooleanArray sels = lista.getCheckedItemPositions();
                List<Produto> aRemover = new LinkedList<Produto>();
                for (int i = 0; i < produtos.size(); i++) {
                    if ( sels.get(i) ) {
                        aRemover.add( produtos.get(i) );
                    }
                }
                produtos.removeAll( aRemover );
                lista.clearChoices();
                selecionados.clear();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void editar(Produto p){
        Intent editar = new Intent(this, TelaProduto.class);
        Produto pr = new Produto();
        p.setCodigo(1); p.setDescricao("Teste 01");
        editar.putExtra("prodEdicao", pr);
        startActivityForResult(editar, EDITAR_PRODUTO);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView,
                                   View view, int pos,
                                   long id) {
        if (lista.getCheckedItemCount() > 1) {
            Toast.makeText(this, "Apenas um item pode ser editado!", Toast.LENGTH_LONG);
        } else {
            Produto prod = produtos.get(pos);
            Intent editar = new Intent(this, TelaProduto.class);
            editar.putExtra("prodEdicao", prod);
            startActivityForResult(editar, EDITAR_PRODUTO);
        }

        return false;
    }


}