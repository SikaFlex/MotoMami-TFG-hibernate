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

-- CREATE TABLE CUSTOMERS
	
use mm_agb;
-- Relacion  id de provider -> idProv

Select * from MM_PROVIDERS;

-- QUERY COMPROBAR PROVIDERS ACTIVOS
select * from MM_PROVIDERS 
where swiact = 1
and ifnull(@param_date,current_date()) BETWEEN dateIni AND ifnull(dateEnd,'2099-12-31')
and codigoProveedor = ifnull(@p_prov, codigoProveedor);

select * from MM_PROVIDERS
where swiact = 1 and ifnull(null,current_date()) BETWEEN dateIni AND ifnull(null,'2099-12-31') and codigoProveedor = ifnull(@p_prov, codigoProveedor)

-- and codigoProveedor = @p_Prov and 

[where swiact = 1 and ifnull(:p_date,current_date()) BETWEEN dateIni AND ifnull(dateEnd,'2099-12-31') and codigoProveedor = ifnull(:p_prov, codigoProveedor)]