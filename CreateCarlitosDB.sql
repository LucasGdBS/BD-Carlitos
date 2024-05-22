-- SCRIPT CRIAÇÃO
CREATE DATABASE CARLITOSDB;
USE CARLITOSDB;

-- Criação das tabelas e seus relacionamentos
create table funcionario(
	cpf varchar(15),
	nome varchar(100) not null,
	salario float,
	constraint funcionario_pk primary key (cpf)
);

create table gerente(
	cpf varchar(15),
	constraint gerente_pk primary key (cpf),
	constraint fk_funcionario_cpf foreign key (cpf) references funcionario(cpf) on delete cascade
);

create table atendentes(
	cpf varchar(15),
	gerente varchar(15),
	turno varchar(10),
	constraint atendente_pk primary key (cpf),
	constraint fk_funcionario_cpf2 foreign key (cpf) references funcionario(cpf) on delete cascade,
	constraint fk_gerente_fk_funcionario_cpf foreign key (gerente) references gerente(cpf) on delete cascade,
	constraint projeto_turno_ck check (turno in ('MANHÃ', 'NOITE'))
);

create table motoqueiro(
	cpf varchar(15),
	gerente_motoqueiro varchar(15),
	constraint motoqueiro_pk primary key (cpf),
	constraint fk_gerente foreign key (gerente_motoqueiro) references motoqueiro(cpf) on delete cascade,
	constraint fk_motoqueiro_funcionario foreign key (cpf) references funcionario(cpf) on delete cascade
);

create table clientes(
	telefone varchar(50),
	cep varchar(20),
	rua varchar(100),
	bairro varchar(100),
	numero varchar(100),
	complemento varchar(250),
	nome varchar(200),
	constraint clientes_pk primary key (telefone)
);

create table produto(
	id_produto int AUTO_INCREMENT,
	nome varchar(200),
	preco decimal(10, 2),
	constraint produto_pk primary key (id_produto)
);

create table pedido(
	codigo_nota_fiscal int unique,
	valor_total float,
	dt_pedido date,
	forma_pagamento varchar(100),
	taxa_entrega float,
	desconto float,
	qnt_produto int,
	num_pedido int,
	telefone_cliente varchar(50),
	produto_id int,
	atendente_cpf varchar(15),
	constraint pedido_pk primary key (num_pedido, telefone_cliente, produto_id, atendente_cpf),
	constraint fk_clientes_telefone foreign key (telefone_cliente) references clientes(telefone) on delete cascade,
	constraint fk_produto_id_produto foreign key (produto_id) references produto(id_produto) on delete cascade,
	constraint fk_atendentes_fk_funcionario foreign key (atendente_cpf) references atendentes(cpf) on delete cascade
);

create table ingredientes(
	nome varchar(50),
	dt_validade date,
	quantidade int,
	valor float, 
	codigo int AUTO_INCREMENT,
	tipo_alimento varchar(20),
	constraint tipo_alimento_ck check (tipo_alimento in ('Porcionado', 'Fatiado', 'Unitario')),
	constraint ingredients_pk primary key (codigo)
);

create table ingredientes_produto(
	produto_id int,
	codigo_ingrediente int,
	constraint pk_ingredietes_produto primary key (produto_id, codigo_ingrediente),
	constraint fk_produto_id_produto2 foreign key (produto_id) references produto(id_produto) on delete cascade,
	constraint fk_ingredientes_codigo foreign key (codigo_ingrediente) references ingredientes(codigo) on delete cascade
);

-- Criação de Triggers
delimiter $$
create trigger tr_before_insert_atendentes
before insert on atendentes
for each row 
begin
	declare cpf_exists_gerente int;
	declare cpf_exists_motoqueiro int;

	select count(*) into cpf_exists_gerente
	from gerente g where g.cpf = new.cpf;
	
	select count(*) into cpf_exists_motoqueiro
	from motoqueiro m where m.cpf = new.cpf;
	
	if cpf_exists_gerente > 0 or cpf_exists_motoqueiro > 0 then
	signal sqlstate '45000'
	set MESSAGE_TEXT = 'Erro: O CPF já está cadastrado como gerente ou como motoqueiro';
	end if;
end $$
delimiter ;

delimiter $$
create trigger tr_before_insert_gerente
before insert on gerente
for each row 
begin
	declare cpf_exists_atendentes int;
	declare cpf_exists_motoqueiro int;

	select count(*) into cpf_exists_atendentes
	from atendentes a where a.cpf = new.cpf;
	
	select count(*) into cpf_exists_motoqueiro
	from motoqueiro m where m.cpf = new.cpf;
	
	if cpf_exists_atendentes > 0 or cpf_exists_motoqueiro > 0 then
	signal sqlstate '45000'
	set MESSAGE_TEXT = 'Erro: O CPF já está cadastrado como atendente ou como motoqueiro';
	end if;
end $$
delimiter ;

delimiter $$
create trigger tr_before_insert_motoqueiro
before insert on motoqueiro
for each row 
begin
	declare cpf_exists_gerente int;
	declare cpf_exists_atendentes int;

	select count(*) into cpf_exists_gerente
	from gerente g where g.cpf = new.cpf;
	
	select count(*) into cpf_exists_atendentes
	from atendentes a where a.cpf = new.cpf;
	
	if cpf_exists_gerente > 0 or cpf_exists_atendentes > 0 then
	signal sqlstate '45000'
	set MESSAGE_TEXT = 'Erro: O CPF já está cadastrado como gerente ou como atendente';
	end if;
end $$
delimiter ;

-- Povoamento da tabela funcionario
INSERT INTO funcionario (cpf, nome, salario) VALUES
('111.222.333-44', 'João Silva', 2500.00),
('222.333.444-55', 'Maria Souza', 2800.00),
('333.444.555-66', 'Pedro Oliveira', 2700.00),
('444.555.666-77', 'Ana Santos', 2600.00),
('555.666.777-88', 'Lucas Pereira', 2900.00),
('666.777.888-99', 'Carlos Ferreira', 2700.00),
('777.888.999-00', 'Amanda Costa', 2600.00);;

-- Povoamento da tabela gerente
INSERT INTO gerente (cpf) VALUES
('111.222.333-44'),
('222.333.444-55');

-- Povoamento da tabela atendentes
INSERT INTO atendentes (cpf, gerente, turno) VALUES
('333.444.555-66', '111.222.333-44', 'MANHÃ'),
('444.555.666-77', '111.222.333-44', 'NOITE'),
('555.666.777-88', '222.333.444-55', 'MANHÃ'),
('666.777.888-99', '222.333.444-55', 'NOITE');

-- Povoamento da tabela motoqueiro
INSERT INTO motoqueiro (cpf, gerente_motoqueiro) VALUES
('777.888.999-00', NULL);

-- Povoamento da tabela clientes
INSERT INTO clientes (telefone, cep, rua, bairro, numero, complemento, nome) VALUES
('9999-8888', '12345-678', 'Rua das Flores', 'Centro', '123', 'Apto 101', 'Carlos Alberto'),
('8888-7777', '54321-876', 'Avenida Principal', 'Bairro Novo', '456', '', 'Mariana Souza'),
('7777-6666', '98765-432', 'Rua do Comércio', 'Centro', '789', 'Loja 03', 'José da Silva'),
('6666-5555', '23456-789', 'Rua dos Girassóis', 'Jardim', '321', '', 'Amanda Oliveira'),
('5555-4444', '87654-321', 'Avenida dos Coqueiros', 'Praia', '654', 'Casa 02', 'Paulo Rocha');

-- Povoamento da tabela produto
INSERT INTO produto (nome, preco) VALUES
('Hamburguer Artesanal', 25.00),
('X-Bacon', 20.00),
('X-Salada', 18.00),
('X-Egg', 22.00),
('Coca-Cola', 5.00),
('Batata Frita', 10.00),
('Sundae', 8.00),
('Água Mineral', 3.00),
('Cheeseburguer', 15.00),
('Milk Shake', 12.00);

-- Povoamento da tabela pedido
INSERT INTO pedido (codigo_nota_fiscal, valor_total, dt_pedido, forma_pagamento, taxa_entrega, desconto, qnt_produto, num_pedido, telefone_cliente, produto_id, atendente_cpf) VALUES
(123456, 25.90, '2024-05-14', 'Cartão de Crédito', 5.00, 0.00, 2, 1, '9999-8888', 1, '333.444.555-66'),
(234567, 18.50, '2024-05-14', 'Dinheiro', 3.00, 0.00, 1, 2, '8888-7777', 2, '444.555.666-77'),
(345678, 22.70, '2024-05-14', 'Cartão de Débito', 4.00, 0.00, 3, 3, '7777-6666', 3, '555.666.777-88'),
(567890, 6.50, '2024-05-14', 'Dinheiro', 1.50, 0.00, 1, 5, '5555-4444', 5, '666.777.888-99');

-- Povoamento da tabela ingredientes
INSERT INTO ingredientes (nome, dt_validade, quantidade, valor, codigo, tipo_alimento) VALUES
('Pão de Hambúrguer', '2024-06-01', 100, 0.50, 1, 'Unitario'),
('Alface', '2024-05-20', 50, 0.20, 2, 'Porcionado'),
('Tomate', '2024-05-18', 40, 0.30, 3, 'Unitario'),
('Queijo Cheddar', '2024-05-25', 30, 0.60, 4, 'Fatiado'),
('Bacon', '2024-05-22', 20, 1.00, 5, 'Fatiado'),
('Carne de Hambúrguer', '2024-05-30', 60, 2.00, 6, 'Unitario'),
('Molho Especial', '2024-05-25', 50, 0.40, 7, 'Porcionado'),
('Coca-Cola Lata', '2024-07-01', 100, 2.50, 8, 'Unitario'),
('Batata Pré-Frita', '2024-05-20', 30, 2.00, 9, 'Unitario'),
('Sorvete de Creme', '2024-06-10', 20, 3.00, 10, 'Unitario');

-- Povoamento da tabela ingredientes_produto
INSERT INTO ingredientes_produto (produto_id, codigo_ingrediente) VALUES
(1, 1), -- Hamburguer Artesanal: Pão de Hambúrguer
(1, 2), -- Hamburguer Artesanal: Alface
(1, 3), -- Hamburguer Artesanal: Tomate
(1, 6), -- Hamburguer Artesanal: Carne de Hambúrguer
(2, 1), -- X-Bacon: Pão de Hambúrguer
(2, 2), -- X-Bacon: Alface
(2, 4), -- X-Bacon: Queijo Cheddar
(2, 5), -- X-Bacon: Bacon
(2, 6), -- X-Bacon: Carne de Hambúrguer
(3, 1), -- X-Salada: Pão de Hambúrguer
(3, 2), -- X-Salada: Alface
(3, 3), -- X-Salada: Tomate
(3, 6), -- X-Salada: Carne de Hambúrguer
(4, 1), -- X-Egg: Pão de Hambúrguer
(4, 2), -- X-Egg: Alface
(4, 3), -- X-Egg: Tomate
(4, 6), -- X-Egg: Carne de Hambúrguer
(4, 10), -- X-Egg: Ovo
(5, 8), -- Coca-Cola: Coca-Cola Lata
(6, 9), -- Batata Frita: Batata Pré-Frita
(7, 10); -- Sundae: Sorvete de Creme
