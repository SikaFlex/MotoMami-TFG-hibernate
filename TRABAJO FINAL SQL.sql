CREATE TABLE MM_PROVIDERS (
    id VARCHAR(250) primary KEY,
    codigoProveedor VARCHAR(250) ,
    name VARCHAR(250),
    dateIni DATE,
    dateEnd DATE,
    swiact INT,
    PRIMARY KEY (id)
);

-- Primera tabla 
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
    statusProcess VARCHAR(10), --STATUS PROCCES N-> nO HA SIDO PROCESADO, E, P 
    operation VARCHAR(250),--  New, Update   -- N -> no ha sido procesado  -- E-->  -- P--> Procesado
    resource VARCHAR(20),
    FOREIGN KEY (codProv) REFERENCES MM_PROVIDERS(id),
	FOREIGN KEY (codExternal) REFERENCES MM_Customer(DNI)

);

select * from MM_INTERFACE 
where codExternal = @dni
and codProv = @CodProv
and contJson;

--
CREATE TABLE MM_TRANSLATION (
id int,
idProv varchar(250),
cod_ext varchar(250),
cod_int varchar(250),
date_ini date,
date_end date,
FOREIGN KEY (idProv) REFERENCES MM_PROVIDERS(id)
);


use mm_agb;

-- CREATE TABLE CUSTOMERS
CREATE TABLE IF NOT EXISTS MM_Customer (
    DNI VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    first_surname VARCHAR(100),
    last_surname VARCHAR(100),
    email VARCHAR(100),
    birth_date DATE,
    postal_code VARCHAR(20),
    street_type VARCHAR(50),
    city VARCHAR(100),
    number VARCHAR(20),
    phone VARCHAR(20),
    gender varchar(20),
    licence_type VARCHAR(50),
    operation VARCHAR(10)
);
-- PARTS
CREATE TABLE MM_PARTS(
	ID_PROVEEDOR varchar(100) PRIMARY KEY,
    id varchar(100),
    codigoExterno varchar(100),
    internalCod varchar(100),
    descripcion varchar(100)
);




















select * from MM_INTERFACE where codExternal = @DNI;

	
use mm_agb;
-- Relacion  id de provider -> idProv

Select * from MM_PROVIDERS;


-- QUERY COMPROBAR PROVIDERS ACTIVOS
select * from MM_PROVIDERS 
where swiact = 1
and ifnull(@param_date,current_date()) BETWEEN dateIni AND ifnull(dateEnd,'2099-12-31')
and codigoProveedor = ifnull(@p_prov, codigoProveedor);


use mm_agb;

select * from MM_PROVIDERS
where swiact = 1 and ifnull(null,current_date()) BETWEEN dateIni AND ifnull(null,'2099-12-31') and codigoProveedor = ifnull(@p_prov, codigoProveedor);



-- and codigoProveedor = @p_Prov and 

