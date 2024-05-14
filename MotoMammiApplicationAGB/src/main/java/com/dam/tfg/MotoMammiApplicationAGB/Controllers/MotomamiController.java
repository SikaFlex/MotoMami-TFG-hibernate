// package com.dam.tfg.MotoMammiApplicationAGB.Controllers;
// //SERVICIOS DE LAS API

// import org.springframework.beans.factory.annotation.Autowired;

// import com.dam.tfg.MotoMammiApplicationAGB.Services.ProccessService;
// import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;

// @RestController
// public class MotomamiController
// {
//     @Autowired
//     ProccessService pService;


//RNF7-AD La url para obtener la información de los ficheros tienen que ser: “localhost:8080/appInsurance/v1/readInfoFileVCS/{RESOURCE}/{CODPROV}/{DATE}”
//(donde VCS tienen que ser vuestras credenciales)

//
// RNF8-AD La url para obtener la información de los ficheros tienen que ser: “localhost:8080/appInsurance/v1/processInfoFileVCS/{RESOURCE}/{CODPROV}/{DATE}”:
// (donde VCS tienen que ser vuestras credenciales)
// *{RESOURCE}--> indica la fuente de información que se quiere procesar(OBLIGATORIO).
// *{CODPROV}--> indica el código del proveedor que queremos procesar la información(OPCIONAL), en caso de vacío se obtienen de todos los proveedores activos.
// *{DATE}--> indica la fecha que queremos procesar la información(OPCIONAL), en caso de vacío se obtiene la fecha actual de sistema.


// LLAMAR LA PROCESO QUE SE EJECUTA UNA VEZ AL MES 

//     @RequestMapping(value =("/readInfo/{resource}/{codprov}/{date}"),
//                     method = RequestMethod.GET,
//                     produces = "application/json")
//     String callProcessReadInfo(@PathVariable String resource,
//                                @PathVariable String codprov,
//                                @PathVariable String date//"20240423"
//                                ){
//         try{
//             System.out.println("\nEsta tarea se lanza cada 15 segundos");
//             pService.readFileInfo(Constants.PARTS,codprov,date);
           

//         } catch (Exception e){
//             System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
//         }
//         System.out.println("El valor de resource es: " + resource);
//         return "Buenos dias";
//     }
// }