insert ignore into anagrafica(cf, nome, cognome, nato_maschio, data_nascita, luogo_nascita, nazionalita, provincia_nascita) values
('BRBFPP02R01E349J', 'Filippo', 'Barbieri', true, '2002-10-01', 'Isola della Scala', 'Italiana', 'VR'),
('RCCPGR99R21L781I', 'Piergiorgio', 'Rocchetto', true, '1999-10-21', 'Verona', 'Italiana', 'VR'),
('CNTDRD99M06A465H', 'Edoardo', 'Cantiero', true, '1999-08-06', 'Asiago', 'Italiana', 'VI'),
('MRTPRZ72C59F918L', 'Patrizia', 'Martini', false, '1972-03-19', 'Nogara', 'Italiana', 'VR');

insert ignore into dipendente(username, nome, cognome, password, sede, disponibilita) values
('amari8', 'Andrea', 'Mariotto', '59195c6c541c8307f1da2d1e768d6f2280c984df217ad5f4c64c3542b04111a4', 'QUESTURA_VERONA', 'LUN|MAR'),
('seg123', 'Filippo', 'Segala', 'ea42cfa102bd7aac62b7cc8f323802129072eca6c96585421adc1c5ace46c1dd', 'QUESTURA_VERONA', 'LUN|MER|VEN'),
('gpalmo', 'Giordano', 'Palmoso', '6ae6c9e65bfcd71db43c104ca6bd45fbebfdf412523cbbdfa0334bc1f391679f', 'CONSOLATO_LAS_PALMAS', 'LUN|MAR|MER|GIO|VEN');
