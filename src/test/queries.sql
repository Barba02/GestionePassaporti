insert into slot(sede, datetime, stato, tipo) values ('QUESTURA_VERONA', '2021-12-01 14:30:15', 'LIBERO', 'RILASCIO');
insert into cittadino(anagrafica_cf, password, cie, figli_minori, diplomatico) values ('BRBFPP02R01E349J', 'P@ssw0rd', 'CA68583IM', false, false);
insert into anagrafica(cf, nome, cognome, data_nascita, luogo_nascita, nazionalita) values ('BRBFPP02R01E349J', 'Filippo', 'Barbieri', '2002-10-01', 'Isola della Scala', 'Italiana');

select * from slot;
select * from cittadino;
select * from anagrafica;
