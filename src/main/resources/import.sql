INSERT INTO algafood2.cozinha (id, nome) VALUES(1, 'Mineira');
INSERT INTO algafood2.cozinha (id, nome) VALUES(2, 'Paulista');

INSERT INTO algafood2.estado (id, nome) VALUES(1, "São Paulo");
INSERT INTO algafood2.cidade (id, nome, estado_id) VALUES(1, "Assis", 1);

INSERT INTO algafood2.restaurante (taxa_frete, cozinha_id, id, nome, data_cadastro, data_atualizacao) VALUES(0, 1, 1, 'Tucuman', utc_timestamp, utc_timestamp);
INSERT INTO algafood2.restaurante (taxa_frete, cozinha_id, id, nome, data_cadastro, data_atualizacao) VALUES(0, 2, 2, 'House 360', utc_timestamp, utc_timestamp);

INSERT INTO algafood2.forma_pagamento (id, descricao) VALUES(1, "Dinheiro");
INSERT INTO algafood2.forma_pagamento (id, descricao) VALUES(2, "PIX");

INSERT INTO algafood2.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1), (2,2), (1,2), (2,1);
