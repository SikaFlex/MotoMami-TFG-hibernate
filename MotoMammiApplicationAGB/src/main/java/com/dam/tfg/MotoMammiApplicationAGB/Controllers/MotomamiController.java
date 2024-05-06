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