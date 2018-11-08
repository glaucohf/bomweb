--############################################################################################################
--################################################## Perfis ##################################################
--############################################################################################################

insert into perfis(id, nome) values(1, 'Detro');
insert into perfis(id, nome) values(2, 'Empresa');
insert into perfis(id, nome) values(3, 'Empresas');
insert into perfis(id, nome) values(4, 'Detro_Admin');
insert into perfis(id, nome) values(5, 'Detro_nivel_1');
insert into perfis(id, nome) values(6, 'Detro_nivel_2');
insert into perfis(id, nome) values(7, 'Detro_nivel_3');
insert into perfis(id, nome) values(8, 'Detro_aud');

--############################################################################################################
--################################################ Permissões ################################################
--############################################################################################################

insert into permissoes(recurso, acao, active) values('Empresa', 'listar', 1);
insert into permissoes(recurso, acao, active) values('Empresa', 'incluir', 1);
insert into permissoes(recurso, acao, active) values('Empresa', 'editar', 1);
insert into permissoes(recurso, acao, active) values('Empresa', 'excluir', 1);

insert into permissoes(recurso, acao, active) values('Linha', 'listar', 1);
insert into permissoes(recurso, acao, active) values('Linha', 'incluir', 1);
insert into permissoes(recurso, acao, active) values('Linha', 'editar', 1);
insert into permissoes(recurso, acao, active) values('Linha', 'excluir', 1);
insert into permissoes(recurso, acao, active) values('Linha', 'historico', 1);

insert into permissoes(recurso, acao, active) values('Tarifa', 'listar', 1);
insert into permissoes(recurso, acao, active) values('Tarifa', 'incluir', 1);
insert into permissoes(recurso, acao, active) values('Tarifa', 'editar', 1);
insert into permissoes(recurso, acao, active) values('Tarifa', 'excluir',1);
insert into permissoes(recurso, acao, active) values('Tarifa', 'upload', 1);
insert into permissoes(recurso, acao, active) values('Tarifa', 'historico', 1);

insert into permissoes(recurso, acao, active) values('TipoVeiculo', 'listar', 1);
insert into permissoes(recurso, acao, active) values('TipoVeiculo', 'incluir', 1);
insert into permissoes(recurso, acao, active) values('TipoVeiculo', 'excluir', 1);

insert into permissoes(recurso, acao, active) values('TipoLinha', 'listar', 1);
insert into permissoes(recurso, acao, active) values('TipoLinha', 'incluir', 1);
insert into permissoes(recurso, acao, active) values('TipoLinha', 'excluir', 1);

insert into permissoes(recurso, acao, active) values('Usuario', 'listar', 1);
insert into permissoes(recurso, acao, active) values('Usuario', 'incluir', 1);
insert into permissoes(recurso, acao, active) values('Usuario', 'editar', 1);
insert into permissoes(recurso, acao, active) values('Usuario', 'excluir', 1);
insert into permissoes(recurso, acao, active) values('Usuario', 'redefinir senha', 1);

insert into permissoes(recurso, acao, active) values('Log', 'listar', 1);

insert into permissoes(recurso, acao, active) values('Permissao', 'listar', 1);

insert into permissoes(recurso, acao, active) values('Bom', 'listar', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'visualizar', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'novo', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'preencher', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'fechar', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'reabrir', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'importar', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'exportar', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'pendentes', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'exportar pendentes', 1);
insert into permissoes(recurso, acao, active) values('Bom', 'recriar linhas', 1);


insert into permissoes(recurso, acao, active) values('Relatorio', 'exportar', 1);

insert into permissoes(recurso, acao, active) values('Configuracao', 'listar', 1);
insert into permissoes(recurso, acao, active) values('Configuracao', 'bom', 1);

--############################################################################################################
--################################################### URI ####################################################
--############################################################################################################

insert into uris(path, open) values('/empresa/list', 0);
insert into uris(path, open) values('/linha/list', 0);

--###########################################################################################################
--############################################ Perfis x Permissões ###########################################
--############################################################################################################

--# Perfis -> Empresas

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Linha' and acao = 'historico'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Tarifa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Tarifa' and acao = 'historico'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Usuario' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Log' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Relatorio' and acao = 'exportar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Bom' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Bom' and acao = 'visualizar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Bom' and acao = 'pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresas'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'listar'), 1);


--# Perfis -> Empresa

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Empresa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Linha' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'visualizar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'novo'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'preencher'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'fechar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'importar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Bom' and acao = 'recriar linhas'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Relatorio' and acao = 'exportar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Tarifa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Empresa'), (select id from permissoes where recurso = 'Usuario' and acao = 'listar'), 1);


--# Perfis -> Detro

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Empresa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Empresa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Empresa' and acao = 'excluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Linha' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Linha' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Linha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Linha' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Tarifa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Tarifa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Tarifa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Tarifa' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Tarifa' and acao = 'upload'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Tarifa' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Usuario' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Usuario' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Usuario' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Usuario' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Usuario' and acao = 'redefinir senha'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Log' and acao = 'listar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Bom' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Bom' and acao = 'visualizar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Bom' and acao = 'reabrir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Bom' and acao = 'pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar pendentes'), 1);


insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Relatorio' and acao = 'exportar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Configuracao' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro'), (select id from permissoes where recurso = 'Configuracao' and acao = 'bom'), 1);


--# Perfis -> Detro_nivel_1

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Empresa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Empresa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Empresa' and acao = 'excluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Linha' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Linha' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Linha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Linha' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Tarifa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Tarifa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Tarifa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Tarifa' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Tarifa' and acao = 'upload'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Tarifa' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Usuario' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Usuario' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Usuario' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Usuario' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Usuario' and acao = 'redefinir senha'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Log' and acao = 'listar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Bom' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Bom' and acao = 'visualizar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Bom' and acao = 'reabrir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Bom' and acao = 'pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar pendentes'), 1);


insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Relatorio' and acao = 'exportar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Configuracao' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_1'), (select id from permissoes where recurso = 'Configuracao' and acao = 'bom'), 1);


--# Perfis -> Detro_nivel_2

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Empresa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Empresa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Empresa' and acao = 'excluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Linha' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Linha' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Linha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Linha' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Tarifa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Tarifa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Tarifa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Tarifa' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Tarifa' and acao = 'upload'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Tarifa' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Usuario' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Usuario' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Usuario' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Usuario' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Usuario' and acao = 'redefinir senha'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Log' and acao = 'listar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Bom' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Bom' and acao = 'visualizar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Bom' and acao = 'reabrir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Bom' and acao = 'pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar pendentes'), 1);


insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Relatorio' and acao = 'exportar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Configuracao' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_2'), (select id from permissoes where recurso = 'Configuracao' and acao = 'bom'), 1);

--# Perfis -> Detro_nivel_3

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Empresa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Empresa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Empresa' and acao = 'excluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Linha' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Linha' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Linha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Linha' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Tarifa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Tarifa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Tarifa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Tarifa' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Tarifa' and acao = 'upload'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Tarifa' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Usuario' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Usuario' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Usuario' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Usuario' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Usuario' and acao = 'redefinir senha'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Log' and acao = 'listar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Bom' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Bom' and acao = 'visualizar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Bom' and acao = 'reabrir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Bom' and acao = 'pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar pendentes'), 1);


insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Relatorio' and acao = 'exportar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Configuracao' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_nivel_3'), (select id from permissoes where recurso = 'Configuracao' and acao = 'bom'), 1);


--# Perfis -> Detro_Admin

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Empresa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Empresa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Empresa' and acao = 'excluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Linha' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Linha' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Linha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Linha' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Tarifa' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Tarifa' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Tarifa' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Tarifa' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Tarifa' and acao = 'upload'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Tarifa' and acao = 'historico'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Usuario' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Usuario' and acao = 'incluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Usuario' and acao = 'editar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Usuario' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Usuario' and acao = 'redefinir senha'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'TipoVeiculo' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'excluir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'TipoLinha' and acao = 'incluir'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Log' and acao = 'listar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Permissao' and acao = 'listar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Bom' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Bom' and acao = 'visualizar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Bom' and acao = 'reabrir'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Bom' and acao = 'pendentes'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Bom' and acao = 'exportar pendentes'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Relatorio' and acao = 'exportar'), 1);

insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Configuracao' and acao = 'listar'), 1);
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_Admin'), (select id from permissoes where recurso = 'Configuracao' and acao = 'bom'), 1);


--# Perfis -> Detro_aud
insert into perfis_permissoes(id_perfil, id_permissao, active) values((select id from perfis where nome = 'Detro_aud'), (select id from permissoes where recurso = 'Log' and acao = 'listar'), 1);


--############################################################################################################
--############################################ Perfis x PermissÃµes ###########################################
--############################################################################################################

insert into uris_permissao(id_uri, id_permissao) values((select id from uris where path = '/empresa/list'), (select id from permissoes where recurso = 'Empresa' and acao = 'listar'));
insert into uris_permissao(id_uri, id_permissao) values((select id from uris where path = '/linha/list'), (select id from permissoes where recurso = 'Linha' and acao = 'listar'));

--############################################################################################################
--############################################### Configurações ##############################################
--############################################################################################################

insert into configuracoes(id, nome, valor) values(1, 'Dias p/ fechar o BOM', '25');
insert into configuracoes(id, nome, valor) values(2, 'Dias p/ fechar o BOM após reabertura', '25');
insert into configuracoes(id, nome, valor) values(3, 'Data Início do Sistema', '01/04/2011');
insert into configuracoes(id, nome, valor) values(4, 'E-mail do Detro', 'dtpa@detro.rj.gov.br');

--# Usuario
insert into usuarios(nome, login, senha, ativo, active, trocar_senha, email) values ('Detro', 'detro', '47FE2839BD29BE2FB83F5C65FA35071F5B0E9594AE32ECAB87225A96A4371553', 1, 1, 0, 'dtpa@detro.rj.gov.br');
insert into usuarios(nome, login, senha, ativo, active, trocar_senha, email) values ('Detro_aud', 'detro_aud', '47FE2839BD29BE2FB83F5C65FA35071F5B0E9594AE32ECAB87225A96A4371553', 1, 1, 0, 'dtpa@detro.rj.gov.br');
insert into usuarios(nome, login, senha, ativo, active, trocar_senha, email) values ('Detro_nivel_1', 'detro_nivel_1', '47FE2839BD29BE2FB83F5C65FA35071F5B0E9594AE32ECAB87225A96A4371553', 1, 1, 0, 'dtpa@detro.rj.gov.br');
insert into usuarios(nome, login, senha, ativo, active, trocar_senha, email) values ('Detro_nivel_2', 'detro_nivel_2', '47FE2839BD29BE2FB83F5C65FA35071F5B0E9594AE32ECAB87225A96A4371553', 1, 1, 0, 'dtpa@detro.rj.gov.br');
insert into usuarios(nome, login, senha, ativo, active, trocar_senha, email) values ('Detro_nivel_3', 'detro_nivel_3', '47FE2839BD29BE2FB83F5C65FA35071F5B0E9594AE32ECAB87225A96A4371553', 1, 1, 0, 'dtpa@detro.rj.gov.br');
insert into usuarios(nome, login, senha, ativo, active, trocar_senha, email) values ('Detro_Admin', 'detro_admin', '47FE2839BD29BE2FB83F5C65FA35071F5B0E9594AE32ECAB87225A96A4371553', 1, 1, 0, 'dtpa@detro.rj.gov.br');

insert into usuarios_perfis(id_usuario, id_perfil, active) values((select id from usuarios where login = 'detro'), 1, 1);
insert into usuarios_perfis(id_usuario, id_perfil, active) values((select id from usuarios where login = 'detro_admin'), 4, 1);
insert into usuarios_perfis(id_usuario, id_perfil, active) values((select id from usuarios where login = 'detro_nivel_1'), 5, 1);
insert into usuarios_perfis(id_usuario, id_perfil, active) values((select id from usuarios where login = 'detro_nivel_2'), 6, 1);
insert into usuarios_perfis(id_usuario, id_perfil, active) values((select id from usuarios where login = 'detro_nivel_3'), 7, 1);
insert into usuarios_perfis(id_usuario, id_perfil, active) values((select id from usuarios where login = 'detro_aud'), 8, 1);





