package com.example.agenda.dao;

import androidx.annotation.Nullable;

import com.example.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class alunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorDeId = 1;

    public void salva(Aluno aluno) {
        aluno.setId(contadorDeId);
        alunos.add(aluno);
        atualizaIds();
    }

    private void atualizaIds() {
        contadorDeId++;
    }

    public void edita(Aluno aluno) {
        Aluno alunoEcontrado = buscaAlunoPeloId(aluno);
        if (alunoEcontrado != null) {
            int posicaoDoAluno = alunos.indexOf(alunoEcontrado);
            alunos.set(posicaoDoAluno, aluno);
        }
    }

    @Nullable
    private Aluno buscaAlunoPeloId(Aluno aluno) {
        Aluno alunoEcontrado = null;
        for (Aluno a :
                alunos) {
            if (a.getId() == aluno.getId()) {
                alunoEcontrado = a;
            }
        }
        return alunoEcontrado;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void remove(Aluno aluno) {
        Aluno alunoDevolvido = buscaAlunoPeloId(aluno);
        if(alunoDevolvido!= null){
            alunos.remove(alunoDevolvido);
        }
    }
}
