-- https://trello.com/c/0QzaRizB

SET AUTOCOMMIT=0;
START TRANSACTION;

use bomweb_teste;

-- Deleta as empresas que não operam em 2018
delete FROM usuarios_perfis where id_usuario in (select id FROM usuarios where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete FROM usuarios where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104);

delete from linha_vigencia_tipos_linha_tipos_linha_tipos_veiculo where linha_tipo_linha_id  in 
(select a.id from linha_vigencia_tipos_linha a inner join linha_vigencia b where a.linhaVigencia_id  = b.id and b.empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from linha_vigencia_tipos_linha where linhaVigencia_id  in (select id from linha_vigencia where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from tarifas where secao_id in 
(select a.id from secoes a inner join linha_vigencia b where a.linhaVigencia_id = b.id and b.empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from bom_secao where secao_id in 
(select a.id from secoes a inner join linha_vigencia b where a.linhaVigencia_id = b.id and b.empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from secoes where linhaVigencia_id in (select id from linha_vigencia where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from bom_linha where linha_vigencia_id in (select id from linha_vigencia where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from linha_vigencia where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104);

-- boms
delete from justificativas where bom_linha_id in 
(select a.id from bom_linha a inner join bom b where a.bom_id = b.id and b.empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from bom_secao where bom_linha_id in 
(select a.id from bom_linha a inner join bom b where a.bom_id = b.id and b.empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104));

delete from bom_linha where bom_id in (select id from bom where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104) );

delete from bom where empresa_id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104); 

-- Empresas
delete FROM empresas where id in (3,8,18,32,34,41,45,49,63,82,90,97,100,101,104);

-- ajustar a data de inicio de vigência para 2018
update empresas set inicio_vigencia_bom = '2018-01-01';
update linha_vigencia set data_inicio = '2018-01-01';

-- apagar as tarifas anteriores a 2017
delete  from tarifas  WHERE STR_TO_DATE(inicioVigencia, '%Y-%m-%d') < '2017-01-01';

-- ajustar as tarifas de 2017 para 01/01/2018?
-- ajustar as tarifas de 2018 para qual data?
-- update tarifas set inicioVigencia = '2018-01-01';
-- update tarifas_aud set inicioVigencia = '2018-01-01';

-- apagar todos os bons
delete from bom_secao;

delete from bom_linha;

delete from bom;

-- apagar as com data de termino preenchida
delete from linha_vigencia_tipos_linha_tipos_linha_tipos_veiculo where linha_tipo_linha_id  in 
(select a.id from linha_vigencia_tipos_linha a inner join linha_vigencia b where a.linhaVigencia_id  = b.id  and b.data_termino is not null);

delete from linha_vigencia_tipos_linha where linhaVigencia_id  in (select id from linha_vigencia where data_termino is not null);

delete from tarifas where fimVigencia is not null;

delete from tarifas where secao_id in 
(select a.id from secoes a inner join linha_vigencia b where a.linhaVigencia_id = b.id and b.data_termino is not null);

delete from tarifas where secao_id in (select id from secoes where data_termino is not null);

delete from secoes where data_termino is not null;

delete from secoes where linhaVigencia_id in (select id from linha_vigencia where data_termino is not null);

delete from linha_vigencia where data_termino is not null;

INSERT INTO database_version ( version ) VALUES ( 41 );
COMMIT;
	