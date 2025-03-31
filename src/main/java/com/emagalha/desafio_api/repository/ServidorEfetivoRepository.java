package com.emagalha.desafio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emagalha.desafio_api.dto.output.EnderecoFuncionalDTO;
import com.emagalha.desafio_api.dto.output.ServidorUnidadeDTO;
import com.emagalha.desafio_api.entity.ServidorEfetivo;

    
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Integer> {


    boolean existsByMatricula(String matricula);
    
    @Query("SELECT COUNT(l) > 0 FROM Lotacao l WHERE l.pessoa.id = :pessoaId")

    boolean existsLotacaoByPessoaId(@Param("pessoaId") Integer pessoaId);


    @Query(value = """
        SELECT 
            p.pes_nome AS nome,
            EXTRACT(YEAR FROM AGE(CURRENT_DATE, p.pes_data_nascimento)) AS idade,
            u.unid_nome AS unidadeLotacao,
            COALESCE((SELECT fp.fp_hash FROM foto_pessoa fp WHERE fp.pes_id = p.pes_id ORDER BY fp.fp_data DESC LIMIT 1), '') AS fotografia
        FROM pessoa p
        JOIN servidor_efetivo se ON p.pes_id = se.pes_id
        JOIN lotacao l ON p.pes_id = l.pes_id
        JOIN unidade u ON l.unid_id = u.unid_id
        WHERE u.unid_id = :unidId
        AND (l.lot_data_remocao IS NULL OR l.lot_data_remocao > CURRENT_DATE)
        """,
        countQuery = """
        SELECT COUNT(*)
        FROM pessoa p
        JOIN servidor_efetivo se ON p.pes_id = se.pes_id
        JOIN lotacao l ON p.pes_id = l.pes_id
        JOIN unidade u ON l.unid_id = u.unid_id
        WHERE u.unid_id = :unidId
        AND (l.lot_data_remocao IS NULL OR l.lot_data_remocao > CURRENT_DATE)
        """,
        nativeQuery = true)
    Page<ServidorUnidadeDTO> findServidoresEfetivosPorUnidadeId(
        @Param("unidId") Integer unidId,
        Pageable pageable);


        @Query(value = """
            SELECT 
                p.pes_nome AS nomeServidor,
                u.unid_nome AS nomeUnidade,
                e.end_logradouro AS logradouro,
                CASE WHEN e.end_numero IS NULL THEN 'S/N' ELSE CAST(e.end_numero AS TEXT) END AS numero,
                e.end_bairro AS bairro,
                c.cid_nome AS cidade,
                c.cid_uf AS uf
            FROM pessoa p
            JOIN servidor_efetivo se ON p.pes_id = se.pes_id
            JOIN lotacao l ON p.pes_id = l.pes_id
            JOIN unidade u ON l.unid_id = u.unid_id
            JOIN unidade_endereco ue ON u.unid_id = ue.unid_id
            JOIN endereco e ON ue.end_id = e.end_id
            JOIN cidade c ON e.cid_id = c.cid_id
            WHERE UPPER(p.pes_nome) LIKE UPPER('%' || :nomeParcial || '%')
            AND (l.lot_data_remocao IS NULL OR l.lot_data_remocao > CURRENT_DATE)
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM pessoa p
            JOIN servidor_efetivo se ON p.pes_id = se.pes_id
            JOIN lotacao l ON p.pes_id = l.pes_id
            JOIN unidade u ON l.unid_id = u.unid_id
            JOIN unidade_endereco ue ON u.unid_id = ue.unid_id
            JOIN endereco e ON ue.end_id = e.end_id
            JOIN cidade c ON e.cid_id = c.cid_id
            WHERE UPPER(p.pes_nome) LIKE UPPER('%' || :nomeParcial || '%')
            AND (l.lot_data_remocao IS NULL OR l.lot_data_remocao > CURRENT_DATE)
            """,
            nativeQuery = true)
        Page<EnderecoFuncionalDTO> findEnderecoFuncionalByNomeServidor(
            @Param("nomeParcial") String nomeParcial, 
            Pageable pageable);

}