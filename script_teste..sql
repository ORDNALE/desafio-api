-- Inserir Cidades
INSERT INTO cidade (cid_id, cid_nome, cid_uf) VALUES 
(1, 'São Paulo', 'SP'),
(2, 'Rio de Janeiro', 'RJ'),
(3, 'Belo Horizonte', 'MG');

-- Inserir Endereços
INSERT INTO endereco (end_id, end_tipo_logradouro, end_logradouro, end_numero, end_bairro, cid_id) VALUES 
(1, 'Rua', 'Av. Paulista', 1000, 'Bela Vista', 1),
(2, 'Avenida', 'Pres. Vargas', 500, 'Centro', 2),
(3, 'Praça', 'Setor de Autarquias', 1, 'Asa Norte', 3);

-- Inserir Unidades
INSERT INTO unidade (unid_id, unid_nome, unid_sigla) VALUES 
(1, 'Secretaria de Educação', 'SEED'),
(2, 'Secretaria de Saúde', 'SES'),
(3, 'Secretaria de Administração', 'SEAD');

-- Relacionar Unidades com Endereços
INSERT INTO unidade_endereco (unid_id, end_id) VALUES 
(1, 1),
(2, 2),
(3, 3);

-- Inserir Pessoas
INSERT INTO pessoa (pes_id, pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai) VALUES 
(1, 'João Silva', '1980-05-15', 'MASCULINO', 'Maria Silva', 'José Silva'),
(2, 'Maria Oliveira', '1985-08-20', 'FEMININO', 'Ana Oliveira', 'Carlos Oliveira'),
(3, 'Pedro Souza', '1990-03-10', 'MASCULINO', 'Clara Souza', 'Antônio Souza'),
(4, 'Ana Costa', '1975-11-25', 'FEMININO', 'Julia Costa', 'Roberto Costa');

-- Inserir Servidores Efetivos
INSERT INTO servidor_efetivo (pes_id, se_matricula) VALUES 
(1, '2023001'),
(2, '2023002'),
(4, '2023004');

-- Inserir Lotacoes
INSERT INTO lotacao (lot_id, pes_id, unid_id, lot_data_lotacao, lot_data_remocao, lot_portaria) VALUES 
(1, 1, 1, '2020-01-10', NULL, 'Portaria 001/2020'),
(2, 2, 2, '2021-03-15', NULL, 'Portaria 005/2021'),
(3, 4, 1, '2019-05-20', NULL, 'Portaria 010/2019');

-- Inserir Fotos
INSERT INTO foto_pessoa (fp_id, pes_id, fp_data, fp_bucket, fp_hash) VALUES 
(1, 1, '2023-01-05', 'meus-arquivos', 'foto_joao.jpg'),
(2, 2, '2023-01-06', 'meus-arquivos', 'foto_maria.jpg'),
(3, 4, '2023-01-07', 'meus-arquivos', 'foto_ana.jpg');
