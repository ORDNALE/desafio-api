INSERT INTO pessoa (pes_id, pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai) VALUES
(1, 'Rafael Silva', '1980-01-01', 'Masculino', 'Maria Silva', 'Lucas Silva'),
(2, 'Ana Souza', '1990-05-15', 'Feminino', 'Clara Souza', 'Carlos Souza'),
(3, 'Pedro Oliveira', '1975-12-10', 'Masculino', 'Fernanda Oliveira', 'Paulo Oliveira')
ON CONFLICT (pes_id) DO NOTHING;