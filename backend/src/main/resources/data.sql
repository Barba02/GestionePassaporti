insert ignore into anagrafica(cf, nome, cognome, sesso, data_nascita, luogo_nascita, nazionalita, provincia_nascita) values
('BRBFPP02R01E349J', 'Filippo', 'Barbieri', 'M', '2002-10-01', 'Isola della Scala', 'Italiana', 'VR'),
('RCCPGR99R21L781I', 'Piergiorgio', 'Rocchetto', 'M', '1999-10-21', 'Verona', 'Italiana', 'VR'),
('CNTDRD99M06A465H', 'Edoardo', 'Cantiero', 'M', '1999-08-06', 'Asiago', 'Italiana', 'VI'),
('MRTPRZ72C59F918L', 'Patrizia', 'Martini', 'F', '1972-03-19', 'Nogara', 'Italiana', 'VR');

insert ignore into dipendente(username, nome, cognome, password, sede, disponibilita) values
('amari8', 'Andrea', 'Mariotto', '48b892a785f7fc1a614a7479e2dbe0c5218ac4a185470548b6fdfd445076e177', 'QUESTURA_VERONA', 'MONDAY|TUESDAY'),
('seg123', 'Filippo', 'Segala', '99562f8cb44cc377e05df7703bfe1053163c218f20df0ccae1df1d9d9b97f086', 'QUESTURA_VERONA', 'MONDAY|WEDNESDAY|FRIDAY'),
('gpalmo', 'Giordano', 'Palmoso', 'd26e90b0b4aa8988366893ebac4a5001fddc0d6096d9eef066e0bcad95822eab', 'CONSOLATO_LAS_PALMAS', 'MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY'),
('sbrinz', 'Alice', 'Sacchetto', 'a00d23745f16b42c2409599b0218a704ed725c1bc61823f82f172465cd7d2756', 'QUESTURA_PADOVA', 'TUESDAY|THURSDAY');
