-- Inserir Cidades
INSERT INTO cidade (cid_nome, cid_uf) VALUES 
('São Paulo', 'SP'),
('Rio de Janeiro', 'RJ'),
('Belo Horizonte', 'MG');

-- Inserir Endereços
INSERT INTO endereco (end_tipo_logradouro, end_logradouro, end_numero, end_bairro, cid_id) VALUES 
('Rua', 'Av. Paulista', 1000, 'Bela Vista', 1),
('Avenida', 'Pres. Vargas', 500, 'Centro', 2),
('Praça', 'Setor de Autarquias', 1, 'Asa Norte', 3);

-- Inserir Unidades
INSERT INTO unidade (unid_nome, unid_sigla) VALUES 
('Secretaria de Educação', 'SEED'),
('Secretaria de Saúde', 'SES'),
('Secretaria de Administração', 'SEAD');

-- Relacionar Unidades com Endereços
INSERT INTO unidade_endereco (unid_id, end_id) VALUES 
(1, 1),
(2, 2),
(3, 3);

-- Inserir Pessoas
INSERT INTO pessoa (pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai) VALUES 
('João Silva', '1980-05-15', 'MASCULINO', 'Maria Silva', 'José Silva'),
('Maria Oliveira', '1985-08-20', 'FEMININO', 'Ana Oliveira', 'Carlos Oliveira'),
('Pedro Souza', '1990-03-10', 'MASCULINO', 'Clara Souza', 'Antônio Souza'),
('Ana Costa', '1975-11-25', 'FEMININO', 'Julia Costa', 'Roberto Costa');

-- Inserir Servidores Efetivos
INSERT INTO servidor_efetivo (pes_id, se_matricula) VALUES 
(1, '2023001'),
(2, '2023002'),
(4, '2023004');

-- Inserir Lotacoes
INSERT INTO lotacao (pes_id, unid_id, lot_data_lotacao, lot_data_remocao, lot_portaria) VALUES 
(1, 1, '2020-01-10', NULL, 'Portaria 001/2020'),
(2, 2, '2021-03-15', NULL, 'Portaria 005/2021'),
(4, 1, '2019-05-20', NULL, 'Portaria 010/2019');

-- Inserir Fotos
INSERT INTO foto_pessoa (pes_id, fp_data, fp_bucket, fp_hash) VALUES 
(1, '2023-01-05', 'meus-arquivos', 'foto_joao.jpg'),
(2, '2023-01-06', 'meus-arquivos', 'foto_maria.jpg'),
(4, '2023-01-07', 'meus-arquivos', 'foto_ana.jpg');
