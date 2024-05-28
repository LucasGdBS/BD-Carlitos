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
	id_cliente int auto_increment,
	telefone_1 varchar(50) unique,
	telefone_2 varchar(50) unique,
	cep varchar(20),
	rua varchar(100),
	bairro varchar(100),
	numero varchar(100),
	complemento varchar(250),
	nome varchar(200),
	constraint clientes_pk primary key (id_cliente)
);

create table produto(
	id_produto int AUTO_INCREMENT,
	nome varchar(200),
	preco decimal(10, 2),
	constraint produto_pk primary key (id_produto)
);

create table pedido(
	codigo_nota_fiscal int not null,
	dt_pedido date,
	forma_pagamento varchar(100),
	taxa_entrega float,
	desconto float,
	qnt_produto int,
	num_pedido int,
	id_cliente int,
	produto_id int,
	atendente_cpf varchar(15),
	constraint pedido_pk primary key (num_pedido, id_cliente, produto_id),
	constraint fk_clientes_id foreign key (id_cliente) references clientes(id_cliente) on delete cascade,
	constraint fk_produto_id_produto foreign key (produto_id) references produto(id_produto) on delete cascade,
	constraint fk_atendentes_fk_funcionario foreign key (atendente_cpf) references atendentes(cpf) on delete set null,
	constraint forma_pagamento_ck check (forma_pagamento in ('crédito', 'débito', 'dinheiro', 'pix'))
);

create table ingredientes(
	nome varchar(50),
	dt_validade date,
	quantidade int,
	codigo int AUTO_INCREMENT,
	tipo_alimento varchar(20),
	constraint tipo_alimento_ck check (tipo_alimento in ('Porcionado', 'Fatiado', 'Unitario')),
	constraint ingredients_pk primary key (codigo)
);

create table ingredientes_produto(
	produto_id int,
	codigo_ingrediente int,
    quantidade int,
	constraint pk_ingredietes_produto primary key (produto_id, codigo_ingrediente),
	constraint fk_produto_id_produto2 foreign key (produto_id) references produto(id_produto) on delete cascade,
	constraint fk_ingredientes_codigo foreign key (codigo_ingrediente) references ingredientes(codigo) on delete cascade
);

create table dependentes(
    id_dependente int auto_increment,
    nome varchar(100) NOT NULL,
    data_nascimento date,
    relacao varchar(50),  -- Filho, Cônjuge, Progenitores
    cpf_funcionario varchar(15),
    constraint dependente_pk primary key (id_dependente, cpf_funcionario),
    constraint fk_funcionario_cpf3 foreign key (cpf_funcionario) references funcionario(cpf) on delete cascade,
    CONSTRAINT relacao_ck CHECK (relacao IN ('Filho', 'Conjuge', 'Progenitores'))
);

-- Tabela de Backup
create table pedidos_bkup(
    num_pedido int,
    codigo_nota_fiscal int,
    desconto float,
    qnt_produto int,
    id_cliente int,
    produto_id int,
    produto_nome varchar(200),
    atendente_cpf varchar(15)
);

-- Criação de Triggers

delimiter $$
create trigger tr_bloqueio_pedido_por_falta_de_ingredientes
before update on ingredientes
for each row 
begin 
	if new.quantidade < 0 then
	signal sqlstate '45000'
	set MESSAGE_TEXT = 'Erro: Ingredientes para este pedido faltando!';
	end if;
end $$
delimiter ;

delimiter $$
create trigger tr_retirada_ingredientes
before insert on pedido
for each row
begin
    declare done int default false;
    declare ingredientes_codigo int; -- Declare a variável para armazenar o código do ingrediente
    declare cur cursor for
        select distinct i.codigo 
        from ingredientes i
        join ingredientes_produto ip on i.codigo = ip.codigo_ingrediente
        where ip.produto_id = new.produto_id;

    declare continue handler for not found set done = true;

    open cur;
    read_loop: loop
        fetch cur into ingredientes_codigo;
        if done then
            leave read_loop;
        end if;

        -- Atualize a quantidade de ingredientes apenas para os relacionados ao produto no novo pedido
        update ingredientes i
        join ingredientes_produto ip on i.codigo = ip.codigo_ingrediente
        set i.quantidade = i.quantidade - (new.qnt_produto * ip.quantidade)
        where ip.produto_id = new.produto_id
        and i.codigo = ingredientes_codigo; -- Adicionando esta condição para garantir que apenas o ingrediente atual seja atualizado

        -- Saia do loop após atualizar apenas um ingrediente
    end loop read_loop;

    close cur;
end $$
delimiter ;


delimiter $$
create trigger tr_bkup_pedidos
    before delete on produto
    for each row
begin
    insert into pedidos_bkup (
        num_pedido,
        codigo_nota_fiscal,
        desconto,
        qnt_produto,
        id_cliente,
        produto_id,
        produto_nome,
        atendente_cpf
    ) select
        p.num_pedido,
        p.codigo_nota_fiscal,
        p.desconto,
        p.qnt_produto,
        p.id_cliente,
        p.produto_id,
        old.nome,  -- Nome do produto que está sendo deletado
        p.atendente_cpf
    from
        pedido p
    where
        p.produto_id = old.id_produto;
end $$
delimiter ;

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

-- Criação de views
create view view_detalhes_pedido as
SELECT 
    p.*, 
    c.nome as nome_cliente, 
    p2.nome as nome_produto,
    ((qnt_produto * p2.preco + p.taxa_entrega) * (p.desconto/100)) AS valor_desconto,
    (qnt_produto * p2.preco + p.taxa_entrega) as valor_sem_desconto,
    (qnt_produto * p2.preco + p.taxa_entrega - ((qnt_produto * p2.preco + p.taxa_entrega) * (p.desconto/100))) as valor_total
FROM 
    pedido p
JOIN 
    clientes c ON c.id_cliente = p.id_cliente
JOIN 
    produto p2 ON p2.id_produto = p.produto_id;


-- Povoamento da tabela funcionario
insert into funcionario (cpf, nome, salario) values 
('11111111111', 'Ana Silva', 3500.00),
('22222222222', 'Bruno Costa', 4200.00),
('33333333333', 'Carlos Souza', 3000.00),
('44444444444', 'Diana Lima', 2800.00),
('55555555555', 'Eduardo Pereira', 4500.00),
('66666666666', 'Fernanda Oliveira', 3200.00),
('77777777777', 'Gabriel Santos', 3100.00),
('88888888888', 'Helena Almeida', 3400.00),
('99999999999', 'Igor Martins', 3300.00),
('10101010101', 'Julia Rodrigues', 2900.00),
('11121212121', 'Luis Castro', 3700.00),
('12131415161', 'Mariana Silva', 3600.00),
('13131415161', 'Nina Nascimento', 3800.00),
('14151617181', 'Otavio Costa', 2700.00),
('15161718191', 'Paula Moura', 4000.00);

-- Povoamento da tabela gerente
insert into gerente (cpf) values 
('11111111111'),
('22222222222'),
('66666666666');

-- Povoamento da tabela atendentes
insert into atendentes (cpf, gerente, turno) values 
('33333333333', '11111111111', 'MANHÃ'),
('44444444444', '22222222222', 'NOITE'),
('77777777777', '11111111111', 'MANHÃ'),
('88888888888', '22222222222', 'NOITE'),
('10101010101', '66666666666', 'MANHÃ');

-- Povoamento da tabela motoqueiro
insert into motoqueiro (cpf, gerente_motoqueiro) values 
('55555555555', null),
('99999999999', '55555555555'),
('11121212121', '55555555555');

-- Povoamento da tabela clientes
insert into clientes (telefone_1, telefone_2, cep, rua, bairro, numero, complemento, nome) values 
('999999999', '888888888', '52041-000', 'Rua da Aurora', 'Boa Vista', '100', 'Apto 101', 'Alice Ferreira'),
('777777777', '666666666', '52021-000', 'Avenida Conde da Boa Vista', 'Boa Vista', '200', 'Casa', 'Carlos Mendes'),
('555555555', '444444444', '52050-000', 'Rua do Hospício', 'Boa Vista', '300', 'Apto 201', 'Bianca Oliveira'),
('333333333', '222222222', '52011-000', 'Rua Amélia', 'Graças', '400', 'Casa', 'David Moreira'),
('111111111', '000000000', '52051-000', 'Avenida Rui Barbosa', 'Graças', '500', 'Apto 301', 'Eva Gomes'),
('212121212', '343434343', '52061-000', 'Rua da Hora', 'Espinheiro', '600', 'Casa', 'Felipe Silva'),
('454545454', '565656565', '52070-000', 'Rua do Espinheiro', 'Espinheiro', '700', 'Apto 401', 'Gabriela Santos'),
('676767676', '787878787', '52060-000', 'Avenida Norte', 'Rosarinho', '800', 'Casa', 'Henrique Rocha'),
('898989898', '909090909', '52080-000', 'Rua do Futuro', 'Aflitos', '900', 'Apto 501', 'Isabela Costa'),
('101010101', '121212121', '52031-000', 'Rua do Lima', 'Santo Amaro', '1100', 'Apto 601', 'Lara Fernandes'),
('3434343434', '4545454545', '52041-000', 'Rua da Aurora', 'Boa Vista', '1200', 'Casa', 'Marcelo Vieira'),
('5656565656', '6767676767', '52021-000', 'Avenida Conde da Boa Vista', 'Boa Vista', '1300', 'Apto 701', 'Renata Carvalho'),
('7878787878', '8989898989', '52050-000', 'Rua do Hospício', 'Boa Vista', '1400', 'Casa', 'Sofia Araújo'),
('9090909090', '1010101010', '52011-000', 'Rua Amélia', 'Graças', '1500', 'Apto 801', 'Mateus Ribeiro'),
('1010101010', '1111111111', '52051-000', 'Avenida Rui Barbosa', 'Graças', '1600', 'Casa', 'Luiza Barbosa'),
('1313131313', '1414141414', '52061-000', 'Rua da Hora', 'Espinheiro', '1700', 'Apto 901', 'Juliana Correia'),
('1515151515', '1616161616', '52070-000', 'Rua do Espinheiro', 'Espinheiro', '1800', 'Casa', 'Pedro Martins'),
('1717171717', '1818181818', '52060-000', 'Avenida Norte', 'Rosarinho', '1900', 'Apto 1001', 'Mariana Lopes'),
('1919191919', '2020202020', '52080-000', 'Rua do Futuro', 'Aflitos', '2000', 'Casa', 'Felipe Almeida');


-- Povoamento da tabela produto
insert into produto (nome, preco) values 
('Cheeseburger', 15.00),
('Hamburger', 12.00),
('Bacon Burger', 18.00),
('Veggie Burger', 14.00),
('Double Cheeseburger', 20.00),
('Chicken Burger', 17.00),
('Fish Burger', 18.00),
('Bacon Cheese Fries', 15.00),
('Onion Rings', 10.00),
('Chicken Nuggets', 12.00),
('Caesar Salad', 14.00),
('Milkshake Chocolate', 8.00),
('Milkshake Morango', 8.00),
('Milkshake Baunilha', 8.00),
('Coca-Cola Lata', 5.00);

-- Povoamento da tabela ingredientes
insert into ingredientes (nome, dt_validade, quantidade, tipo_alimento) values 
('Pão', '2024-12-31', 100, 'Unitario'),
('Carne', '2024-11-30', 50, 'Porcionado'),
('Queijo', '2024-10-31', 200, 'Fatiado'),
('Alface', '2024-09-30', 150, 'Unitario'),
('Tomate', '2024-08-30', 150, 'Unitario'),
('Pão Integral', '2024-12-31', 100, 'Unitario'),
('Frango', '2024-11-30', 50, 'Porcionado'),
('Peixe', '2024-10-31', 50, 'Porcionado'),
('Bacon', '2024-12-31', 100, 'Fatiado'),
('Batata', '2024-09-30', 200, 'Unitario'),
('Cebola', '2024-09-30', 100, 'Unitario'),
('Molho Caesar', '2024-08-30', 100, 'Unitario'),
('Molho Chocolate', '2024-12-31', 50, 'Unitario'),
('Morango', '2024-09-30', 100, 'Unitario'),
('Baunilha', '2024-12-31', 50, 'Unitario'),
('Refrigerante', '2025-01-31', 200, 'Unitario');

-- Povoamento da tabela ingredientes_produto
insert into ingredientes_produto (produto_id, codigo_ingrediente, quantidade) values
(1, 1, 1), -- Cheeseburger com Pão
(1, 2, 1), -- Cheeseburger com Carne
(1, 3, 1), -- Cheeseburger com Queijo
(2, 1, 1), -- Hamburger com Pão
(2, 2, 1), -- Hamburger com Carne
(3, 1, 1), -- Bacon Burger com Pão
(3, 2, 1), -- Bacon Burger com Carne
(3, 3, 1), -- Bacon Burger com Queijo
(4, 1, 1), -- Veggie Burger com Pão
(4, 4, 1), -- Veggie Burger com Alface
(4, 5, 1), -- Veggie Burger com Tomate
(5, 1, 1),  -- Double Cheeseburger com Pão
(5, 2, 1),  -- Double Cheeseburger com Carne
(5, 3, 1),  -- Double Cheeseburger com Queijo
(5, 4, 1),  -- Double Cheeseburger com Pão Integral
(6, 1, 1),  -- Chicken Burger com Pão
(6, 8, 1),  -- Chicken Burger com Frango
(7, 1, 1),  -- Fish Burger com Pão
(7, 9, 1),  -- Fish Burger com Peixe
(8, 10, 1),  -- Bacon Cheese Fries com Batata
(8, 3, 1),   -- Bacon Cheese Fries com Queijo
(8, 11, 1),  -- Bacon Cheese Fries com Bacon
(9, 12, 1),  -- Onion Rings com Cebola
(10, 8, 1),  -- Chicken Nuggets com Frango
(11, 4, 1),  -- Caesar Salad com Alface
(11, 5, 1),  -- Caesar Salad com Molho Caesar
(12, 6, 1),  -- Milkshake Chocolate com Molho Chocolate
(13, 6, 1),  -- Milkshake Morango com Molho Morango
(14, 6, 1),  -- Milkshake Baunilha com Molho Baunilha
(15, 13, 1);  -- Coca-Cola Lata com Refrigerante

-- Povoamento da tabela pedido
insert into pedido (codigo_nota_fiscal, dt_pedido, forma_pagamento, taxa_entrega, desconto, qnt_produto, num_pedido, id_cliente, produto_id, atendente_cpf) values 
(1001, '2022-01-24', 'crédito', 5.00, 10.00, 2, 1, 1, 1, '33333333333'),
(1002, '2022-01-24', 'dinheiro', 7.00, 5.00, 1, 2, 2, 2, '44444444444'),
(1003, '2023-01-24', 'pix', 4.00, 15.00, 3, 3, 3, 3, '77777777777'),
(1004, '2023-02-24', 'débito', 6.00, 20.00, 1, 4, 4, 1, '88888888888'),
(1005, '2022-02-24', 'crédito', 5.00, 10.00, 2, 5, 1, 1, '33333333333'),
(1005, '2022-02-24', 'crédito', 5.00, 10.00, 1, 5, 1, 2, '33333333333'),
(1006, '2022-03-25', 'dinheiro', 7.00, 5.00, 1, 6, 2, 3, '44444444444'),
(1006, '2023-03-25', 'dinheiro', 7.00, 5.00, 1, 6, 2, 4, '44444444444'),
(1007, '2023-03-26', 'pix', 4.00, 15.00, 2, 7, 3, 5, '77777777777'),
(1007, '2023-03-26', 'pix', 4.00, 15.00, 1, 7, 3, 6, '77777777777'),
(1008, '2023-04-27', 'débito', 6.00, 20.00, 1, 8, 4, 7, '88888888888'),
(1008, '2023-04-27', 'débito', 6.00, 20.00, 1, 8, 4, 8, '88888888888'),
(1009, '2022-05-28', 'crédito', 5.00, 10.00, 1, 9, 5, 9, '33333333333'),
(1009, '2022-05-28', 'crédito', 5.00, 10.00, 1, 9, 5, 10, '33333333333'),
(1010, '2024-05-29', 'dinheiro', 7.00, 5.00, 1, 10, 6, 11, '44444444444'),
(1010, '2024-05-29', 'dinheiro', 7.00, 5.00, 1, 10, 6, 12, '44444444444'),
(1011, '2024-06-30', 'pix', 4.00, 15.00, 2, 11, 7, 13, '77777777777'),
(1011, '2024-06-30', 'pix', 4.00, 15.00, 1, 11, 7, 14, '77777777777'),
(1012, '2023-05-31', 'débito', 6.00, 20.00, 1, 12, 8, 15, '88888888888'),
(1012, '2022-05-31', 'débito', 6.00, 20.00, 1, 12, 8, 1, '88888888888'),
(1013, '2022-01-01', 'crédito', 5.00, 10.00, 1, 13, 9, 2, '33333333333'),
(1013, '2022-01-01', 'crédito', 5.00, 10.00, 1, 13, 9, 3, '33333333333'),
(1014, '2024-02-02', 'dinheiro', 7.00, 5.00, 1, 14, 10, 4, '44444444444'),
(1014, '2024-02-02', 'dinheiro', 7.00, 5.00, 1, 14, 10, 5, '44444444444');

-- Povoamento da tabela dependentes
insert into dependentes (nome, data_nascimento, relacao, cpf_funcionario) values 
('Mariana Silva', '2010-01-01', 'Filho', '11111111111'),
('Paulo Lima', '2008-02-02', 'Conjuge', '22222222222'),
('Fernanda Souza', '2015-03-03', 'Filho', '33333333333'),
('Ricardo Lima', '2012-04-04', 'Filho', '44444444444');

-- Povoamento da tabela pedidos_bkup - Inicialmente vazia, será preenchida pela trigger quando houver exclusão em pedido
