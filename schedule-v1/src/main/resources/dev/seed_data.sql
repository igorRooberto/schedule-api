-- =========================================
-- 1. CLIENTES
-- =========================================
INSERT INTO tb_client (name, email, telephone)
VALUES
    ('João da Silva', 'joao@email.com', '62999999991'),
    ('Maria Oliveira', 'maria@email.com', '62999999992'),
    ('Pedro Santos', 'pedro@email.com', '62999999993');


-- =========================================
-- 2. PROFISSIONAIS
-- =========================================
INSERT INTO tb_professional (name, telephone, profession)
VALUES
    ('Carlos Oliveira', '62988888891', 'Barbeiro'),
    ('Ana Souza', '62988888892', 'Cabeleireira'),
    ('Marcos Lima', '62988888893', 'Barbeiro');


-- =========================================
-- 3. SERVIÇOS
-- =========================================
INSERT INTO tb_service (name, price, duration_in_minutes)
VALUES
    ('Corte de Cabelo', 50.00, 30),
    ('Barba', 25.00, 15),
    ('Corte + Barba', 65.00, 45);


-- =========================================
-- 4. PROFISSIONAIS QUE EXECUTAM SERVIÇOS
-- =========================================
INSERT INTO tb_professional_service (professional_id, service_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 3),
    (3, 1),
    (3, 2);


-- =========================================
-- 5. DISPONIBILIDADE DOS PROFISSIONAIS
-- =========================================
INSERT INTO tb_professional_availability
(professional_id, shift_id, day_of_week)
VALUES
    -- Carlos: segunda, quarta e sexta
    (1, 1, 'MONDAY'),
    (1, 2, 'MONDAY'),
    (1, 1, 'WEDNESDAY'),
    (1, 2, 'FRIDAY'),

    -- Ana: terça, quinta e sábado
    (2, 1, 'TUESDAY'),
    (2, 2, 'TUESDAY'),
    (2, 1, 'THURSDAY'),
    (2, 2, 'SATURDAY'),

    -- Marcos: segunda, quarta e sexta
    (3, 2, 'MONDAY'),
    (3, 3, 'WEDNESDAY'),
    (3, 1, 'FRIDAY'),
    (3, 3, 'FRIDAY');