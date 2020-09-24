/*DROP TABLE IF EXISTS monedas;

CREATE TABLE monedas (
  codigo CHAR(3) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  simbolo VARCHAR(5) NOT NULL,
  tipocambio DECIMAL(10,6) NOT NULL,
  estado INT DEFAULT 1
);
 */
INSERT INTO moneda (codigo, nombre, simbolo, tipocambio, estado) VALUES
  ('PEN', 'Soles Peruanos', 'S/', 3.5561, 1),
  ('USD', 'Dolares Estadounidense	', '$', 1, 1),
  ('ARS', 'Pesos Argentinos', '$', 72.23, 1),
  ('MXN', 'Pesos Mexicanos', '$', 12.5434, 1),
  ('EUR', 'Euro', '€', 0.8923, 1),
  ('JPY', 'Yen Japones', '￥', 2.0987, 1),
  ('CNY', 'Yuan Chino', 'CN¥', 13.9834, 1),
  ('CLP', 'Pesos chilenos', 'CL$', 54.1343, 1);