
CREATE DATABASE mm_agb;
use mm_agb;


CREATE TABLE MM_PROVIDERS (
    id VARCHAR(250) ,
    codigoProveedor VARCHAR(250) primary KEY ,
    name VARCHAR(250),
    dateIni DATE,
    dateEnd DATE,
    swiact INT
);



CREATE TABLE MM_INTERFACE (
    id VARCHAR(250) PRIMARY KEY,
    idProv varchar(250) ,  
    codExternal VARCHAR(250), -- HACE REFERENCIA AL ID DE LAS OTRAS TABLAS mm_cuTOMERS  ->   DNI VARCHAR(20) PRIMARY KEY, VEHICLES->MATRICULA , ID-> PROVEEDOR
    codProv VARCHAR(250),
    contJson LONGTEXT,
    createDate TIMESTAMP,
    lastUpdate TIMESTAMP,
    createBy VARCHAR(250),
    updateBy VARCHAR(250),
    codError VARCHAR(20),
    errorMessage VARCHAR(4000),
    statusProcess VARCHAR(10),   -- STATUS PROCCES N-> nO HA SIDO PROCESADO, E, P 
    operation VARCHAR(250),  --  New, Update   -- N -> no ha sido procesado  -- E-->  -- P--> Procesado
    resource VARCHAR(20),
    FOREIGN KEY (codProv) REFERENCES MM_PROVIDERS(codigoProveedor)
);




-- CREATE TABLE CUSTOMERS
CREATE TABLE MM_CUSTOMER (
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
    licence_type VARCHAR(50)
);

-- PARTS
CREATE TABLE MM_PARTS(
id varchar(100) PRIMARY KEY,
codigoExterno varchar(100),
internalCod varchar(100),
descripcion varchar(100),
dateNotification date,
matricula varchar(100),
idInvoice varchar(100),
dniVehicle varchar(100),
FOREIGN KEY (dniVehicle) REFERENCES MM_VEHICLES(dniUsuario)
);

-- INVOICE (FACTURAS)
CREATE TABLE MM_INVOICES(
id varchar(100) PRIMARY KEY,
DNI varchar(9), -- REFERENCIA A LA TABLA CUSTOMER
matricula varchar(200), -- REFERENCIA A LA TABLA VEHICLES
fechaCobro DATE
);

-- VEHICLES
CREATE TABLE MM_VEHICLES (
id varchar(100) PRIMARY KEY,
matricula varchar(100),  
tipoVehiculo varchar(100),
marcaVehiculo varchar(100),
modelo varchar(100),
color varchar(100),       
dniUsuario varchar(100),
FOREIGN KEY (dniUsuario) REFERENCES MM_CUSTOMER(DNI)   
);





-- Esta tabla pretende traducir cosas como la calle de los customer (en la clase estan los atributos que tenemos que comprobar)
CREATE TABLE MM_TRANSLATION_AUX (
id int PRIMARY KEY auto_increment,
idProv varchar(250), -- con lo que buscar
cod_ext varchar(250), -- como le llega desde el json ej: Rojo
cod_int varchar(250), -- como debe salir -> Red
date_ini date,
date_end date,
FOREIGN KEY (idProv) REFERENCES MM_PROVIDERS(codigoProveedor)    -- Se traduce por cada provider es decir tiene que tener tambien los campos de customer a traducir
);



-- insert EXAMPLE 
INSERT INTO MM_PROVIDERS (id, codigoProveedor, name, dateIni, dateEnd, swiact) VALUES
('1', 'CAIX', 'La Caixa', '2022-01-01', '2028-12-31', 1),
('2', 'BBVA', 'BBVA', '2022-01-01', '2028-12-31', 1),
('3', 'ING', 'ING', '2022-01-01', '2028-12-31', 1);


-- CAIXA
INSERT INTO MM_TRANSLATION_AUX (idProv, cod_ext, cod_int, date_ini, date_end) VALUES
('CAIX', 'plaza', 'Square', '2022-01-01', '2028-12-31'),
('CAIX', 'calle', 'Street', '2022-01-01', '2028-12-31'),
('CAIX', 'hombre', 'Male', '2022-01-01', '2028-12-31'),
('CAIX', 'mujer', 'Female', '2022-01-01', '2028-12-31'),
('CAIX', 'm', 'Male', '2022-01-01', '2028-12-31'),
('CAIX', 'f', 'Female', '2022-01-01', '2028-12-31'),
('CAIX', 'h', 'Male', '2022-01-01', '2028-12-31'),
('CAIX', 'm', 'Female', '2022-01-01', '2028-12-31'),
('CAIX', 'palma', 'Palma', '2022-01-01', '2028-12-31'),
('CAIX', 'londres', 'London', '2022-01-01', '2028-12-31'),
('CAIX', 'madrid', 'Madrid', '2022-01-01', '2028-12-31'),
('CAIX', 'b', 'Class B', '2022-01-01', '2028-12-31'),
('CAIX', 'a', 'Class A', '2022-01-01', '2028-12-31'),
('CAIX', 'a1', 'Class A1', '2022-01-01', '2028-12-31'),
('CAIX', 'a2', 'Class A2', '2022-01-01', '2028-12-31');




-- BBVA
INSERT INTO MM_TRANSLATION_AUX (idProv, cod_ext, cod_int, date_ini, date_end) VALUES
('BBVA', 'plaza', 'Sq', '2022-01-01', '2022-12-31'),
('BBVA', 'calle', 'St', '2022-01-01', '2022-12-31'),
('BBVA', 'hombre', 'Male', '2022-01-01', '2022-12-31'),
('BBVA', 'mujer', 'Female', '2022-01-01', '2022-12-31'),
('BBVA', 'm', 'Male', '2022-01-01', '2022-12-31'),
('BBVA', 'f', 'Female', '2022-01-01', '2022-12-31'),
('BBVA', 'h', 'Male', '2022-01-01', '2022-12-31'),
('BBVA', 'm', 'Female', '2022-01-01', '2022-12-31'),
('BBVA', 'palma', 'PMI', '2022-01-01', '2022-12-31'),
('BBVA', 'londres', 'LHR', '2022-01-01', '2022-12-31'),
('BBVA', 'madrid', 'MAD', '2022-01-01', '2022-12-31'),
('BBVA', 'b', 'Class B', '2022-01-01', '2022-12-31'),
('BBVA', 'a', 'Class A', '2022-01-01', '2022-12-31'),
('BBVA', 'a1', 'Class A1', '2022-01-01', '2022-12-31'),
('BBVA', 'a2', 'Class A2', '2022-01-01', '2022-12-31');




-- VEHICLES
INSERT INTO mm_vehicles (id, matricula, tipoVehiculo,marcaVehiculo,modelo,color,dniUsuario)
VALUES					(1111,'ABC1234','Coche','Toyota','Corolla','Rojo','07178855S');


select * from MM_TRANSLATION_AUX
where idProv='CAIX' and cod_ext ='calle';





