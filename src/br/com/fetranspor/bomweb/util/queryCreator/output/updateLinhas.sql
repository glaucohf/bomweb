SET FOREIGN_KEY_CHECKS=0;
SET AUTOCOMMIT=0;
START TRANSACTION;
insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '101008005', '2013-05-06 00:00:00', '2013-05-06', 1, 'Castelo', 'Monjolos', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '2013-05-06 00:00:00', 'Castelo', 'Monjolos', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-06', 0, 7.15, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '101008006', '2013-05-06 00:00:00', '2013-05-06', 1, 'Castelo', 'Marambaia', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '2013-05-06 00:00:00', 'Castelo', 'Marambaia', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-06', 0, 7.15, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 45;
update tarifas set fimVigencia = '2012-01-01' where id = 56;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 151;
update tarifas set fimVigencia = '2012-01-01' where id = 1007;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 151;
update tarifas set fimVigencia = '2012-01-01' where id = 1008;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 153;
update tarifas set fimVigencia = '2012-01-01' where id = 1011;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 154;
update tarifas set fimVigencia = '2012-01-01' where id = 1020;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 154;
update tarifas set fimVigencia = '2012-01-01' where id = 1021;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 158;
update tarifas set fimVigencia = '2012-01-01' where id = 1039;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 158;
update tarifas set fimVigencia = '2012-01-01' where id = 1040;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 163;
update tarifas set fimVigencia = '2012-01-01' where id = 1102;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 163;
update tarifas set fimVigencia = '2012-01-01' where id = 1103;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 165;
update tarifas set fimVigencia = '2012-01-01' where id = 1119;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 166;
update tarifas set fimVigencia = '2012-01-01' where id = 1135;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 166;
update tarifas set fimVigencia = '2012-01-01' where id = 1136;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 167;
update tarifas set fimVigencia = '2012-01-01' where id = 1141;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 167;
update tarifas set fimVigencia = '2012-01-01' where id = 1142;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 173;
update tarifas set fimVigencia = '2012-01-01' where id = 1164;
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, linha_id, ponto_inicial, ponto_final, tipoLigacao, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150015000', '1993-06-25 00:00:00', '2013-02-07', 42, 172, 'Casimiro de Abreu', 'Rio das Ostras', 'Linha', 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '0', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio das Ostras', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-02-07', 0, 8.35, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 173;
update tarifas set fimVigencia = '2012-01-01' where id = 1165;
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '1', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-02-07', 0, 3.15, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 173;
update tarifas set fimVigencia = '2012-01-01' where id = 1166;
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '2', '1993-06-25 00:00:00', 'Rio Dourado', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-02-07', 0, 5.2, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 173;
update tarifas set fimVigencia = '2012-01-01' where id = 1167;
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '3', '1993-06-25 00:00:00', 'Barra de São João', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-02-07', 0, 2.1, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 173;
update tarifas set fimVigencia = '2012-01-01' where id = 1168;
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '4', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Barra de São João', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-02-07', 0, 6.25, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 173;
update tarifas set fimVigencia = '2012-01-01' where id = 1169;
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '5', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Trevo de Palmital', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-02-07', 0, 4.35, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 173;
update tarifas set fimVigencia = '2012-01-01' where id = 1170;
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '6', '1993-06-25 00:00:00', 'Trevo de Palmital', 'Barra de São João', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-02-07', 0, 1.9, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 174;
update tarifas set fimVigencia = '2012-01-01' where id = 1171;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 174;
update tarifas set fimVigencia = '2012-01-01' where id = 1172;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 175;
update tarifas set fimVigencia = '2012-01-01' where id = 1173;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 175;
update tarifas set fimVigencia = '2012-01-01' where id = 1174;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 175;
update tarifas set fimVigencia = '2012-01-01' where id = 1175;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 334;
update tarifas set fimVigencia = '2012-01-01' where id = 1746;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 334;
update tarifas set fimVigencia = '2012-01-01' where id = 1747;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 334;
update tarifas set fimVigencia = '2012-01-01' where id = 1748;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 334;
update tarifas set fimVigencia = '2012-01-01' where id = 1749;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 334;
update tarifas set fimVigencia = '2012-01-01' where id = 1750;

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '133001005', '2013-06-20 00:00:00', '2013-06-20', 28, 'Cabuçu', 'Coelho Neto', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '2013-06-20 00:00:00', 'Cabuçu', 'Coelho Neto', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-06-20', 0, 3.2, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 459;
update tarifas set fimVigencia = '2012-01-01' where id = 2074;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 572;
update tarifas set fimVigencia = '2012-01-01' where id = 2262;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 572;
update tarifas set fimVigencia = '2012-01-01' where id = 2263;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 572;
update tarifas set fimVigencia = '2012-01-01' where id = 2264;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 572;
update tarifas set fimVigencia = '2012-01-01' where id = 2265;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 572;
update tarifas set fimVigencia = '2012-01-01' where id = 2266;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 573;
update tarifas set fimVigencia = '2012-01-01' where id = 2267;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 573;
update tarifas set fimVigencia = '2012-01-01' where id = 2268;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 573;
update tarifas set fimVigencia = '2012-01-01' where id = 2269;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 789;
update tarifas set fimVigencia = '2012-01-01' where id = 2705;

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150016000', '1993-06-25 00:00:00', '2013-05-13', 42, 'Casimiro de Abreu', 'Rocha Leão', 'Linha', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rocha Leão', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 5, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.15, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '02', '1993-06-25 00:00:00', 'Rio Dourado', 'Rocha Leão', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 1.9, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150017000', '1993-06-25 00:00:00', '2013-05-13', 42, 'Casimiro de Abreu', 'Mar do Norte', 'Linha', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Mar do Norte', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 12.4, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.15, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '02', '1993-06-25 00:00:00', 'Rio das Ostras', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.4, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '03', '1993-06-25 00:00:00', 'Barra de São João', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.3, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '04', '1993-06-25 00:00:00', 'Mar do Norte', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.75, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '05', '1993-06-25 00:00:00', 'Barra de São João', 'Mar do Norte', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.65, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150017001', '1996-10-24 00:00:00', '2013-05-13', 42, 'Barra de São João', 'Mar do Norte', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1996-10-24 00:00:00', 'Barra de São João', 'Mar do Norte', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.65, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1996-10-24 00:00:00', 'Barra de São João', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.3, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150017002', '1997-01-30 00:00:00', '2013-05-13', 42, 'Casimiro de Abreu', 'Fazenda Cantagalo', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1997-01-30 00:00:00', 'Casimiro de Abreu', 'Fazenda Cantagalo', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 13.85, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1997-01-30 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.15, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '02', '1997-01-30 00:00:00', 'Rio das Ostras', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.4, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '03', '1997-01-30 00:00:00', 'Barra de São João', 'Fazenda Cantagalo', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 6.9, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '04', '1997-01-30 00:00:00', 'Fazenda Cantagalo', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.95, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150017003', '1993-12-15 00:00:00', '2013-05-13', 42, 'Casimiro de Abreu', 'Mar do Norte', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-12-15 00:00:00', 'Casimiro de Abreu', 'Mar do Norte', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 12.75, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1993-12-15 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.15, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '02', '1993-12-15 00:00:00', 'Rio das Ostras', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.4, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '03', '1993-12-15 00:00:00', 'Barra de São João', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.3, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '04', '1993-12-15 00:00:00', 'Mar do Norte', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.75, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '05', '1993-12-15 00:00:00', 'Barra de São João', 'Mar do Norte', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.65, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '06', '1993-12-15 00:00:00', 'Casimiro de Abreu', 'Verão Vermelho', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 9.5, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150018000', '1993-06-25 00:00:00', '2013-05-13', 42, 'Casimiro de Abreu', 'Rio das Ostras', 'Linha', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio das Ostras', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 6.95, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.15, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '02', '1993-06-25 00:00:00', 'Rio das Ostras', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.55, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150018001', '1993-06-25 00:00:00', '2013-05-13', 42, 'Casimiro de Abreu', 'Rio das Ostras', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio das Ostras', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 8.1, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.15, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '02', '1993-06-25 00:00:00', 'Rio das Ostras', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.7, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150018002', '1993-06-25 00:00:00', '2013-05-13', 42, 'Casimiro de Abreu', 'Rio das Ostras', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio das Ostras', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 8.35, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1993-06-25 00:00:00', 'Casimiro de Abreu', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 3.15, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '02', '1993-06-25 00:00:00', 'Rio das Ostras', 'Rio Dourado', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 5.2, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '03', '1993-06-25 00:00:00', 'Barra de São João', 'Rio das Ostras', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.3, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '04', '1993-06-25 00:00:00', 'Barra de São João', 'Casimiro de Abreu', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 7.7, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150019000', '1993-06-25 00:00:00', '2013-05-13', 42, 'Barra de São João', 'Nova Cidade', 'Linha', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-06-25 00:00:00', 'Barra de São João', 'Nova Cidade', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 4.15, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150020000', '1993-06-25 00:00:00', '2013-05-13', 42, 'Barra de São João', 'Costa Azul', 'Linha', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1993-06-25 00:00:00', 'Barra de São João', 'Costa Azul', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.3, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '150020001', '1994-12-15 00:00:00', '2013-05-13', 42, 'Barra de São João', 'Mariléia', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '1994-12-15 00:00:00', 'Barra de São João', 'Mariléia', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.7, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '01', '1993-12-15 00:00:00', 'Barra de São João', 'Costa Azul', (select MAX(id) FROM linha_vigencia));
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-05-13', 0, 2.3, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '03', '2013-01-18 00:00:00', 'Barra Mansa', 'Resende', 990);
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-01-18', 0, 8, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '04', '2013-01-18 00:00:00', 'Resende', 'Floriano', 990);
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-01-18', 0, 3.7, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '204001003', '2013-01-11 00:00:00', '2013-01-11', 86, 'Parque São Vicente', 'Central', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '2013-01-11 00:00:00', 'Parque São Vicente', 'Central', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-01-11', 0, 8.15, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '204007006', '2013-01-11 00:00:00', '2013-01-11', 86, 'Saracuruna', 'Central', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '2013-01-11 00:00:00', 'Saracuruna', 'Central', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-01-11', 0, 6.3, LAST_INSERT_ID());

insert into linhas (active) values(0x01);
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, ponto_inicial, ponto_final, tipoLigacao, linha_id, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '204007007', '2013-01-11 00:00:00', '2013-01-11', 86, 'Saracuruna', 'Central', 'Serviço Complementar', LAST_INSERT_ID(), 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '00', '2013-01-11 00:00:00', 'Saracuruna', 'Central', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-01-11', 0, 13.95, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '03', '2013-04-04 00:00:00', 'Rio de Janeiro', 'Conceição de Jacareí', 1122);
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-04-04', 0, 33.05, LAST_INSERT_ID());

insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (0x01, '05', '2013-04-04 00:00:00', 'Conceição de Jacareí', 'Niterói', 1129);
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (0x01, '2013-04-04', 0, 34.4, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 1132;
update tarifas set fimVigencia = '2012-01-01' where id = 3440;
insert into linha_vigencia (status, active, codigo, data_criacao, data_inicio, empresa_id, linha_id, ponto_inicial, ponto_final, tipoLigacao, piso1AB, piso1BA, piso2AB, piso2BA) values (0, 0x01, '106003000', '2006-05-20 00:00:00', '2013-05-13', 6, 1112, 'Itaboraí', 'Tanguá', 'Linha', 0, 0, 0, 0);
insert into secoes (active, codigo, data_criacao, pontoInicial, pontoFinal, linhaVigencia_id) values (1, '0', '2006-05-20 00:00:00', 'Itaboraí', 'Tanguá', LAST_INSERT_ID());
insert into tarifas (active, inicioVigencia, motivoCriacao, valor, secao_id) values (1, '2013-05-13', 0, 2.8, LAST_INSERT_ID());

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 1133;
update tarifas set fimVigencia = '2012-01-01' where id = 3441;

update linha_vigencia set data_termino = '2012-01-01', duracaoViagemForaPicoAB = NULL, duracaoViagemForaPicoBA = NULL, duracaoViagemPicoAB = NULL, duracaoViagemPicoBA = NULL where id = 1134;
update tarifas set fimVigencia = '2012-01-01' where id = 3442;

SET FOREIGN_KEY_CHECKS=1;
COMMIT;