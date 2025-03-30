package com.emagalha.desafio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emagalha.desafio_api.dto.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.ServidorUnidadeDTO;
import com.emagalha.desafio_api.entity.ServidorEfetivo;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Integer> {

    boolean existsByMatricula(String matricula);
    @Query("SELECT COUNT(l) > 0 FROM Lotacao l WHERE l.pessoa.id = :pessoaId")
    boolean existsLotacaoByPessoaId(@Param("pessoaId") Integer pessoaId);

    @Query("""
        SELECT NEW com.emagalha.desafio_api.dto.EnderecoFuncionalDTO(
            p.nome,
            u.nome,
            e.logradouro,
            COALESCE(CAST(e.numero AS string), 'S/N'),
            e.bairro,
            c.nome,
            c.uf
        )
        FROM ServidorEfetivo se
        JOIN se.pessoa p
        JOIN p.lotacoes l
        JOIN l.unidade u
        JOIN u.enderecos e
        JOIN e.cidade c
        WHERE UPPER(p.nome) LIKE UPPER(CONCAT('%', :nomeParcial, '%'))
        AND l.dataRemocao IS NULL
        ORDER BY p.nome
    """)
    Page<EnderecoFuncionalDTO> findEnderecoFuncionalByNomeServidor(
        @Param("nomeParcial") String nomeParcial, 
        Pageable pageable
    );
    
    @Query(value = """
        SELECT 
            p.pes_nome AS nome,
            EXTRACT(YEAR FROM AGE(CURRENT_DATE, p.pes_data_nascimento)) AS idade,
            u.unid_nome AS unidadeLotacao,
            COALESCE((SELECT fp.fp_bucket FROM foto_pessoa fp WHERE fp.pes_id = p.pes_id ORDER BY fp.fp_data DESC LIMIT 1), '') AS fotografia
        FROM 
            unidade u
        JOIN 
            lotacao l ON u.unid_id = l.unid_id
        JOIN 
            pessoa p ON l.pes_id = p.pes_id
        JOIN 
            servidor_efetivo se ON p.pes_id = se.pes_id
        WHERE 
            u.unid_id = :unidId
            AND l.lot_data_remocao IS NULL
    """,
    countQuery = """
        SELECT COUNT(*)
        FROM 
            unidade u
        JOIN 
            lotacao l ON u.unid_id = l.unid_id
        JOIN 
            pessoa p ON l.pes_id = p.pes_id
        JOIN 
            servidor_efetivo se ON p.pes_id = se.pes_id
        WHERE 
            u.unid_id = :unidId
            AND l.lot_data_remocao IS NULL
    """,
    nativeQuery = true)
    Page<ServidorUnidadeDTO> findServidoresEfetivosPorUnidadeId(
        @Param("unidId") Integer unidId,
        Pageable pageable
    );
}