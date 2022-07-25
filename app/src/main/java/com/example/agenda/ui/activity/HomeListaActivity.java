package com.example.agenda.ui.activity;

import static com.example.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agenda.R;
import com.example.agenda.dao.alunoDAO;
import com.example.agenda.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeListaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Agenda de contatos";

    private final alunoDAO dao = new alunoDAO();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_lista);
        setTitle(TITULO_APPBAR);
        configuraFabNovoAluno();
        configuraLista();

        dao.salva(new Aluno("George", "42072348", "geluiz275@gmail.com"));
        dao.salva(new Aluno("Fram", "4143242142", "geluiz@"));
        dao.salva(new Aluno("Larissa", "5435", "geluiz275@gmail.com54325"));
        dao.salva(new Aluno("Yas", "53452", "geluiz275@gmail.comfsfsa"));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Remover");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
        remove(alunoEscolhido);
        return super.onContextItemSelected(item);
    }

    private void configuraFabNovoAluno() {
        FloatingActionButton botao = findViewById(R.id.activity_home_lista_fab);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(HomeListaActivity.this, FormularioActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaAlunos();
    }

    private void atualizaAlunos() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_home_lista_listView);
        configuraAdapter(listaDeAlunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }


    private void remove(Aluno aluno) {
        dao.remove(aluno);
        adapter.remove(aluno);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno alunosEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);
                abreFormularioModoEditaAluno(alunosEscolhido);
            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioactivity = new Intent(HomeListaActivity.this, FormularioActivity.class);
        vaiParaFormularioactivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(vaiParaFormularioactivity);
    }

    private void configuraAdapter(ListView listaDeAlunos) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDeAlunos.setAdapter(adapter);
    }
}
