

/*Classe para ser usado como auxiliar em ambiente local ou homologacao para testes.,
 Isso garantirá que novos inserts não causem conflitos de chave primária,
 independentemente de como os dados foram inseridos (via script ou aplicação).
 * 
 */

package com.emagalha.desafio_api.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
public class SequenceInitializer {

    private static final Logger logger = LoggerFactory.getLogger(SequenceInitializer.class);

    @PersistenceContext
    private EntityManager em;

    // Mapeamento de tabelas para suas sequências e colunas ID
    private static final Map<String, SequenceConfig> TABLE_SEQUENCE_MAP = Map.of(
        "cidade", new SequenceConfig("seq_cidade", "cid_id"),
        "endereco", new SequenceConfig("seq_endereco", "end_id"),
        "pessoa", new SequenceConfig("seq_pessoa", "pes_id"),
        "foto_pessoa", new SequenceConfig("seq_foto_pessoa", "fp_id"),
        "unidade", new SequenceConfig("seq_unidade", "unid_id"),
        "lotacao", new SequenceConfig("seq_lotacao", "lot_id")
    );

    @Scheduled(initialDelay = 3000, fixedRate = Long.MAX_VALUE)
    @Transactional
    public void initializeSequences() {
        try {
            // 1. Cria as sequências se não existirem
            createSequencesIfNotExists();
            
            // 2. Vincula as sequências às tabelas
            bindSequencesToTables();
            
            // 3. Atualiza as sequências com os IDs máximos atuais
            updateSequencesToMaxIds();
            
        } catch (Exception e) {
            logger.error("Falha crítica no SequenceInitializer", e);
        }
    }

    private void createSequencesIfNotExists() {
        TABLE_SEQUENCE_MAP.forEach((table, config) -> {
            try {
                em.createNativeQuery(
                    "CREATE SEQUENCE IF NOT EXISTS " + config.sequenceName + 
                    " START WITH 1 INCREMENT BY 1"
                ).executeUpdate();
                logger.info("Sequência {} verificada/criada", config.sequenceName);
            } catch (Exception e) {
                logger.error("Falha ao criar sequência {}", config.sequenceName, e);
            }
        });
    }

    private void bindSequencesToTables() {
        TABLE_SEQUENCE_MAP.forEach((table, config) -> {
            try {
                // Verifica se a tabela existe
                String checkTableExistsQuery = String.format(
                    "SELECT to_regclass('%s') IS NOT NULL", table
                );
                Boolean tableExists = (Boolean) em.createNativeQuery(checkTableExistsQuery)
                                                .getSingleResult();

                if (Boolean.TRUE.equals(tableExists)) {
                    // Altera a coluna para usar a sequência
                    String sql = String.format(
                        "ALTER TABLE %s ALTER COLUMN %s SET DEFAULT nextval('%s')",
                        table, config.columnName, config.sequenceName
                    );

                    em.createNativeQuery(sql).executeUpdate();
                    logger.info("Sequência {} vinculada a {}.{}", 
                              config.sequenceName, table, config.columnName);
                }
            } catch (Exception e) {
                logger.error("Falha ao vincular sequência {} a {}.{}", 
                           config.sequenceName, table, config.columnName, e);
            }
        });
    }

    private void updateSequencesToMaxIds() {
        TABLE_SEQUENCE_MAP.forEach((table, config) -> {
            try {
                // Verifica se a tabela existe
                Boolean tableExists = (Boolean) em.createNativeQuery(
                    "SELECT to_regclass('" + table + "') IS NOT NULL"
                ).getSingleResult();

                if (Boolean.TRUE.equals(tableExists)) {
                    // Obtém o ID máximo atual na tabela
                    String maxIdQuery = String.format(
                        "SELECT COALESCE(MAX(%s), 0) FROM %s", 
                        config.columnName, table
                    );
                    Number maxId = (Number) em.createNativeQuery(maxIdQuery)
                                             .getSingleResult();

                    // Atualiza a sequência para o próximo valor após o máximo atual
                    if (maxId != null && maxId.longValue() > 0) {
                        String updateSeqQuery = String.format(
                            "SELECT setval('%s', %d, true)", 
                            config.sequenceName, maxId.longValue()
                        );
                        em.createNativeQuery(updateSeqQuery).getSingleResult();
                        
                        logger.info("Sequência {} atualizada para o valor máximo {} da tabela {}", 
                                  config.sequenceName, maxId, table);
                    }
                }
            } catch (Exception e) {
                logger.error("Falha ao atualizar sequência {} para a tabela {}", 
                           config.sequenceName, table, e);
            }
        });
    }

    // Classe auxiliar para armazenar configuração de sequência
    private static class SequenceConfig {
        String sequenceName;
        String columnName;

        SequenceConfig(String sequenceName, String columnName) {
            this.sequenceName = sequenceName;
            this.columnName = columnName;
        }
    }
}