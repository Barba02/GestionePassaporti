insert into anagrafica values ('BRBFPP02R01E349J');
insert into cittadino values ('2002-10-01', false, false, false, 'BRBFPP02R01E349J', 'Barbieri', 'Isola della Scala', 'Italiana', 'Filippo', 'psw', 'ts');
insert into slot values ('2021-12-01 14:30:15', 'QUESTURA_VERONA', 'LIBERO', 'RILASCIO');
insert into prenotazione values ('2021-12-01 14:30:15', 'BRBFPP02R01E349J');

select * from anagrafica;
select * from cittadino;
select * from slot;
select * from prenotazione;
