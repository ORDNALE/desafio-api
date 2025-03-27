package com.emagalha.desafio_api.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SequenceInitializer {

    private static final Logger logger = LoggerFactory.getLogger(SequenceInitializer.class);

    @PersistenceContext
    private EntityManager em;

    @Scheduled(initialDelay = 5000, fixedRate = Integer.MAX_VALUE)
    @Transactional
    public void initializeSequences() {
        try {
            // cria as sequências, Garante o vínculo das sequências com as colunas ID.
            createSequencesIfNotExists();
            bindSequencesToTables();

        } catch (Exception e) {
            logger.error("Falha crítica no SequenceInitializer", e);
        }
    }

    private void createSequencesIfNotExists() {
        String[] sequences = {
            "seq_cidade", "seq_endereco", "seq_pessoa",
            "seq_foto_pessoa", "seq_unidade", "seq_lotacao"
        };

        for (String seq : sequences) {
            try {
                em.createNativeQuery(
                    "CREATE SEQUENCE IF NOT EXISTS " + seq + " START WITH 1 INCREMENT BY 1"
                ).executeUpdate();
                logger.info("Sequência {} verificada/criada", seq);
            } catch (Exception e) {
                logger.error("Falha ao criar sequência {}", seq, e);
            }
        }
    }

    private void bindSequencesToTables() {
        // Configura cada tabela para usar sua sequência correspondente
        bindSequenceToTable("cidade", "cid_id", "seq_cidade");
        bindSequenceToTable("endereco", "end_id", "seq_endereco");
        bindSequenceToTable("pessoa", "pes_id", "seq_pessoa");
        bindSequenceToTable("foto_pessoa", "fp_id", "seq_foto_pessoa");
        bindSequenceToTable("unidade", "unid_id", "seq_unidade");
        bindSequenceToTable("lotacao", "lot_id", "seq_lotacao");
    }

    private void bindSequenceToTable(String tableName, String columnName, String sequenceName) {
        try {
            // Verifica se a tabela existe
            String checkTableExistsQuery = String.format(
                "SELECT to_regclass('%s') IS NOT NULL", tableName
            );
            Boolean tableExists = (Boolean) em.createNativeQuery(checkTableExistsQuery).getSingleResult();

            if (Boolean.TRUE.equals(tableExists)) {
                // Altera a coluna para usar a sequência
                String sql = String.format(
                    "ALTER TABLE %s ALTER COLUMN %s SET DEFAULT nextval('%s')",
                    tableName, columnName, sequenceName
                );

                em.createNativeQuery(sql).executeUpdate();
                logger.info("Sequência {} vinculada a {}.{}", sequenceName, tableName, columnName);
            }
        } catch (Exception e) {
            logger.error("Falha ao vincular sequência {} a {}.{}", sequenceName, tableName, columnName, e);
        }
    }
}