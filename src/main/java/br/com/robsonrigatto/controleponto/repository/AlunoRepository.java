package br.com.robsonrigatto.controleponto.repository;

import br.com.robsonrigatto.controleponto.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

}
