package com.emagalha.desafio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emagalha.desafio_api.dto.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.ServidorUnidadeDTO;
import com.emagalha.desafio_api.entity.ServidorEfetivo;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long> {
    Page<ServidorEfetivo> findByUnidade(Integer unidade, Pageable pageable);

    boolean existsByMatricula(String matricula);
    @Query("SELECT COUNT(l) > 0 FROM Lotacao l WHERE l.pessoa.id = :pessoaId")
    boolean existsLotacaoByPessoaId(@Param("pessoaId") Integer pessoaId);

    @Query("""
        SELECT NEW com.emagalha.desafio_api.dto.ServidorUnidadeDTO(
            p.nome,
            FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', p.dataNascimento) - 
            CASE WHEN FUNCTION('MONTH', CURRENT_DATE) < FUNCTION('MONTH', p.dataNascimento) 
                 OR (FUNCTION('MONTH', CURRENT_DATE) = FUNCTION('MONTH', p.dataNascimento) 
                 AND FUNCTION('DAY', CURRENT_DATE) < FUNCTION('DAY', p.dataNascimento))
            THEN 1 ELSE 0 END,
            u.nome,
            CASE WHEN fp.hash IS NOT NULL 
                 THEN CONCAT('http://localhost:9000/meus-arquivos/', fp.hash) 
                 ELSE NULL END
        )
        FROM ServidorEfetivo se
        JOIN se.pessoa p
        JOIN p.lotacoes l
        JOIN l.unidade u
        LEFT JOIN p.fotos fp
        WHERE u.id = :unidadeId
        AND l.dataRemocao IS NULL        
        ORDER BY p.nome
    """)
    Page<ServidorUnidadeDTO> findServidoresByUnidade(
        @Param("unidadeId") Integer unidadeId, 
        Pageable pageable
    );

    @Query("""
        SELECT NEW com.emagalha.desafio_api.dto.EnderecoFuncionalDTO(
            p.nome,
            u.nome,
            e.logradouro,
            COALESCE(e.numero, 'S/N'),
            e.bairro,
            c.nome,
            c.uf
        )
        FROM ServidorEfetivo se
        JOIN se.pessoa p
        JOIN p.lotacoes l
        JOIN l.unidade u
        JOIN u.enderecos ue
        JOIN ue.endereco e
        JOIN e.cidade c
        WHERE UPPER(p.nome) LIKE UPPER(CONCAT('%', :nomeParcial, '%'))
        AND l.dataRemocao IS NULL
        ORDER BY p.nome
    """)
    Page<EnderecoFuncionalDTO> findEnderecoFuncionalByNomeServidor(
        @Param("nomeParcial") String nomeParcial, Pageable pageable
    );
}