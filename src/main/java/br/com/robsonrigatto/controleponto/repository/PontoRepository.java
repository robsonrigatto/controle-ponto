package br.com.robsonrigatto.controleponto.repository;

import br.com.robsonrigatto.controleponto.entity.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PontoRepository extends JpaRepository<Ponto, Integer> {

    @Query("select p from Ponto p where p.aluno.id = :idAluno order by p.dataHoraBatida asc")
    List<Ponto> findAllByIdAluno(Integer idAluno);

    @Query(value = "select * FROM ponto where id_aluno = :idAluno order by data_hora_batida DESC LIMIT 1", nativeQuery = true)
    Optional<Ponto> findLastPontoByIdAluno(Integer idAluno);
}
