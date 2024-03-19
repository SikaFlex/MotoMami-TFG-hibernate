CREATE TABLE MM_PROVIDERS (
    id VARCHAR(250),
    codigoProveedor VARCHAR(250),
    name VARCHAR(250),
    dateIni DATE,
    dateEnd DATE,
    swiact INT,
    PRIMARY KEY (id)
);

CREATE TABLE MM_INTERFACE (
    id VARCHAR(250),
    idProv varchar(250),
    codExternal VARCHAR(250),
    codProv VARCHAR(250),
    contJson LONGTEXT,
    createDate TIMESTAMP,
    lastUpdate TIMESTAMP,
    createBy VARCHAR(250),
    updateBy VARCHAR(250),
    codError VARCHAR(20),
    errorMessage VARCHAR(4000),
    statusProcess VARCHAR(10),
    operation VARCHAR(250),
    resource VARCHAR(20),
    FOREIGN KEY (idProv) REFERENCES MM_PROVIDERS(id)
);

CREATE TABLE MM_TRANSLATION (
id int,
idProv varchar(250),
cod_ext varchar(250),
cod_int varchar(250),
FOREIGN KEY (idProv) REFERENCES MM_PROVIDERS(id)

);
-- Relacion  id de provider -> idProv


