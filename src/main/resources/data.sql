-- =========================
-- ESPECIALIDADES
-- =========================

INSERT INTO especialidade (nome) VALUES ('Cardiologia');
INSERT INTO especialidade (nome) VALUES ('Dermatologia');
INSERT INTO especialidade (nome) VALUES ('Pediatria');
INSERT INTO especialidade (nome) VALUES ('Ortopedia');
INSERT INTO especialidade (nome) VALUES ('Neurologia');
INSERT INTO especialidade (nome) VALUES ('Oftalmologia');
INSERT INTO especialidade (nome) VALUES ('Psiquiatria');
INSERT INTO especialidade (nome) VALUES ('Ginecologia');
INSERT INTO especialidade (nome) VALUES ('Medicina Geral');

-- =========================
-- UTILIZADOR ADMIN
-- =========================

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Administrador', 'admin@test.pt', '1234', '1988-01-01', '910000000', 'Rua do Administrador', 'ADMIN');

-- =========================
-- UTILIZADORES MÉDICOS
-- =========================

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Médico 1', 'medico1@test.pt', '1234', '1980-01-01', '912345678', 'Rua do Médico 1', 'MEDICO');

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Médico 2', 'medico2@test.pt', '1234', '1985-02-15', '912345679', 'Rua do Médico 2', 'MEDICO');

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Médico 3', 'medico3@test.pt', '1234', '1978-06-20', '912345680', 'Rua do Médico 3', 'MEDICO');


-- =========================
-- MÉDICOS
-- =========================

INSERT INTO medico (especialidade, utilizador_id)
SELECT 'Cardiologia', id FROM utilizador WHERE email = 'medico1@test.pt';

INSERT INTO medico (especialidade, utilizador_id)
SELECT 'Pediatria', id FROM utilizador WHERE email = 'medico2@test.pt';

INSERT INTO medico (especialidade, utilizador_id)
SELECT 'Dermatologia', id FROM utilizador WHERE email = 'medico3@test.pt';


-- =========================
-- UTILIZADORES PACIENTES
-- =========================

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Paciente 1', 'paciente1@test.pt', '1234', '2000-03-10', '913111111', 'Rua do Paciente 1', 'PACIENTE');

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Paciente 2', 'paciente2@test.pt', '1234', '1995-07-25', '913222222', 'Rua do Paciente 2', 'PACIENTE');

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Paciente 3', 'paciente3@test.pt', '1234', '2002-11-05', '913333333', 'Rua do Paciente 3', 'PACIENTE');


-- =========================
-- PACIENTES
-- =========================

INSERT INTO paciente (utilizador_id)
SELECT id FROM utilizador WHERE email = 'paciente1@test.pt';

INSERT INTO paciente (utilizador_id)
SELECT id FROM utilizador WHERE email = 'paciente2@test.pt';

INSERT INTO paciente (utilizador_id)
SELECT id FROM utilizador WHERE email = 'paciente3@test.pt';


-- =========================
-- UTILIZADORES SECRETÁRIAS
-- =========================

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Secretária 1', 'secretaria1@test.pt', '1234', '1990-04-12', '914111111', 'Rua da Secretária 1', 'SECRETARIA');

INSERT INTO utilizador (nome, email, senha, data_nascimento, telefone, endereco, tipo)
VALUES ('Secretária 2', 'secretaria2@test.pt', '1234', '1992-09-18', '914222222', 'Rua da Secretária 2', 'SECRETARIA');


-- =========================
-- SECRETÁRIAS
-- =========================

INSERT INTO secretaria (utilizador_id)
SELECT id FROM utilizador WHERE email = 'secretaria1@test.pt';

INSERT INTO secretaria (utilizador_id)
SELECT id FROM utilizador WHERE email = 'secretaria2@test.pt';


-- =========================
-- DISPONIBILIDADES DO MÉDICO 1
-- =========================

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-06', '09:00:00', '10:00:00', TRUE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico1@test.pt';

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-06', '10:00:00', '11:00:00', FALSE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico1@test.pt';

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-06', '11:00:00', '12:00:00', TRUE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico1@test.pt';

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-06', '15:00:00', '16:00:00', FALSE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico1@test.pt';


-- =========================
-- DISPONIBILIDADES DO MÉDICO 2
-- =========================

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-07', '09:00:00', '10:00:00', TRUE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico2@test.pt';

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-07', '12:00:00', '13:00:00', FALSE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico2@test.pt';

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-07', '16:00:00', '17:00:00', TRUE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico2@test.pt';


-- =========================
-- DISPONIBILIDADES DO MÉDICO 3
-- =========================

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-08', '09:00:00', '10:00:00', TRUE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico3@test.pt';

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-08', '12:00:00', '13:00:00', FALSE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico3@test.pt';

INSERT INTO disponibilidade (data, hora_inicio, hora_fim, ocupada, medico_id)
SELECT '2026-07-08', '13:00:00', '14:00:00', TRUE, m.id
FROM medico m
JOIN utilizador u ON m.utilizador_id = u.id
WHERE u.email = 'medico3@test.pt';


-- =========================
-- CONSULTAS MARCADAS E CANCELADAS
-- =========================

INSERT INTO consulta (data, estado, hora_inicio, hora_fim, disponibilidade_id, medico_id, paciente_id)
SELECT d.data, 'Marcada', d.hora_inicio, d.hora_fim, d.id, m.id, p.id
FROM disponibilidade d
JOIN medico m ON d.medico_id = m.id
JOIN utilizador um ON m.utilizador_id = um.id
JOIN utilizador up ON up.email = 'paciente1@test.pt'
JOIN paciente p ON p.utilizador_id = up.id
WHERE um.email = 'medico1@test.pt'
AND d.data = '2026-07-06'
AND d.hora_inicio = '09:00:00';

INSERT INTO consulta (data, estado, hora_inicio, hora_fim, disponibilidade_id, medico_id, paciente_id)
SELECT d.data, 'Marcada', d.hora_inicio, d.hora_fim, d.id, m.id, p.id
FROM disponibilidade d
JOIN medico m ON d.medico_id = m.id
JOIN utilizador um ON m.utilizador_id = um.id
JOIN utilizador up ON up.email = 'paciente2@test.pt'
JOIN paciente p ON p.utilizador_id = up.id
WHERE um.email = 'medico1@test.pt'
AND d.data = '2026-07-06'
AND d.hora_inicio = '11:00:00';

INSERT INTO consulta (data, estado, hora_inicio, hora_fim, disponibilidade_id, medico_id, paciente_id)
SELECT d.data, 'Cancelada', d.hora_inicio, d.hora_fim, d.id, m.id, p.id
FROM disponibilidade d
JOIN medico m ON d.medico_id = m.id
JOIN utilizador um ON m.utilizador_id = um.id
JOIN utilizador up ON up.email = 'paciente3@test.pt'
JOIN paciente p ON p.utilizador_id = up.id
WHERE um.email = 'medico1@test.pt'
AND d.data = '2026-07-06'
AND d.hora_inicio = '15:00:00';

INSERT INTO consulta (data, estado, hora_inicio, hora_fim, disponibilidade_id, medico_id, paciente_id)
SELECT d.data, 'Marcada', d.hora_inicio, d.hora_fim, d.id, m.id, p.id
FROM disponibilidade d
JOIN medico m ON d.medico_id = m.id
JOIN utilizador um ON m.utilizador_id = um.id
JOIN utilizador up ON up.email = 'paciente1@test.pt'
JOIN paciente p ON p.utilizador_id = up.id
WHERE um.email = 'medico2@test.pt'
AND d.data = '2026-07-07'
AND d.hora_inicio = '09:00:00';

INSERT INTO consulta (data, estado, hora_inicio, hora_fim, disponibilidade_id, medico_id, paciente_id)
SELECT d.data, 'Cancelada', d.hora_inicio, d.hora_fim, d.id, m.id, p.id
FROM disponibilidade d
JOIN medico m ON d.medico_id = m.id
JOIN utilizador um ON m.utilizador_id = um.id
JOIN utilizador up ON up.email = 'paciente2@test.pt'
JOIN paciente p ON p.utilizador_id = up.id
WHERE um.email = 'medico2@test.pt'
AND d.data = '2026-07-07'
AND d.hora_inicio = '12:00:00';

INSERT INTO consulta (data, estado, hora_inicio, hora_fim, disponibilidade_id, medico_id, paciente_id)
SELECT d.data, 'Marcada', d.hora_inicio, d.hora_fim, d.id, m.id, p.id
FROM disponibilidade d
JOIN medico m ON d.medico_id = m.id
JOIN utilizador um ON m.utilizador_id = um.id
JOIN utilizador up ON up.email = 'paciente3@test.pt'
JOIN paciente p ON p.utilizador_id = up.id
WHERE um.email = 'medico3@test.pt'
AND d.data = '2026-07-08'
AND d.hora_inicio = '13:00:00';


-- =========================
-- EXAMES MARCADOS E CANCELADOS
-- =========================

INSERT INTO exame (consulta_id, paciente_id, medico_id, tipo, descricao, resultado, data_pedido, estado)
SELECT c.id, c.paciente_id, c.medico_id, 'Análises Clínicas', 'Hemograma completo', 'Pendente', '2026-06-30', 'Marcado'
FROM consulta c
JOIN paciente p ON c.paciente_id = p.id
JOIN utilizador up ON p.utilizador_id = up.id
WHERE up.email = 'paciente1@test.pt'
AND c.data = '2026-07-06'
AND c.hora_inicio = '09:00:00';

INSERT INTO exame (consulta_id, paciente_id, medico_id, tipo, descricao, resultado, data_pedido, estado)
SELECT c.id, c.paciente_id, c.medico_id, 'Raio-X', 'Raio-X ao tórax', 'Pendente', '2026-06-30', 'Marcado'
FROM consulta c
JOIN paciente p ON c.paciente_id = p.id
JOIN utilizador up ON p.utilizador_id = up.id
WHERE up.email = 'paciente2@test.pt'
AND c.data = '2026-07-06'
AND c.hora_inicio = '11:00:00';

INSERT INTO exame (consulta_id, paciente_id, medico_id, tipo, descricao, resultado, data_pedido, estado)
SELECT c.id, c.paciente_id, c.medico_id, 'Ecografia', 'Ecografia abdominal', 'Cancelado', '2026-06-30', 'Cancelado'
FROM consulta c
JOIN paciente p ON c.paciente_id = p.id
JOIN utilizador up ON p.utilizador_id = up.id
WHERE up.email = 'paciente3@test.pt'
AND c.data = '2026-07-06'
AND c.hora_inicio = '15:00:00';


-- =========================
-- RECEITAS REGISTADAS E CANCELADAS
-- =========================

INSERT INTO receita (consulta_id, paciente_id, medico_id, medicamento, dosagem, instrucoes, data_emissao, estado)
SELECT c.id, c.paciente_id, c.medico_id, 'Paracetamol', '500mg', 'Tomar 1 comprimido de 8 em 8 horas durante 3 dias.', '2026-06-30', 'Registada'
FROM consulta c
JOIN paciente p ON c.paciente_id = p.id
JOIN utilizador up ON p.utilizador_id = up.id
WHERE up.email = 'paciente1@test.pt'
AND c.data = '2026-07-06'
AND c.hora_inicio = '09:00:00';

INSERT INTO receita (consulta_id, paciente_id, medico_id, medicamento, dosagem, instrucoes, data_emissao, estado)
SELECT c.id, c.paciente_id, c.medico_id, 'Ibuprofeno', '400mg', 'Tomar após as refeições, se necessário.', '2026-06-30', 'Registada'
FROM consulta c
JOIN paciente p ON c.paciente_id = p.id
JOIN utilizador up ON p.utilizador_id = up.id
WHERE up.email = 'paciente2@test.pt'
AND c.data = '2026-07-06'
AND c.hora_inicio = '11:00:00';

INSERT INTO receita 
(consulta_id, paciente_id, medico_id, medicamento, dosagem, instrucoes, data_emissao, estado)
SELECT c.id, c.paciente_id, c.medico_id, 'Amoxicilina', '875mg', 'Receita cancelada pelo médico.', '2026-06-30', 'Cancelada'
FROM consulta c
JOIN paciente p ON c.paciente_id = p.id
JOIN utilizador up ON p.utilizador_id = up.id
WHERE up.email = 'paciente3@test.pt'
AND c.data = '2026-07-06'
AND c.hora_inicio = '15:00:00';