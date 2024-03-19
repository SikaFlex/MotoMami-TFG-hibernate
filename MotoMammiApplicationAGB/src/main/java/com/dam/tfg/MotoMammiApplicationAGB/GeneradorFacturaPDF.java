// package com.dam.tfg.MotoMammiApplicationAGB;


// import com.lowagie.text.Document;
// import com.lowagie.text.DocumentException;
// import com.lowagie.text.Font;
// import com.lowagie.text.Paragraph;
// import com.lowagie.text.pdf.BaseFont;
// import com.lowagie.text.pdf.PdfWriter;

// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.text.DateFormat;
// import java.text.SimpleDateFormat;
// import java.util.Date;

// public class GeneradorFacturaPDF {

//     public static void main(String[] args) {
//         String rutaArchivo = "factura.pdf";
//         generarFacturaPDF(rutaArchivo);
//         System.out.println("Factura generada exitosamente: " + rutaArchivo);
//     }

//     public static void generarFacturaPDF(String rutaArchivo) {
//         Document documento = new Document();
//         try {
//             PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
//             documento.open();

//             // Definir la fuente
//             BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//             Font font = new Font(baseFont, 12);

//             // Título
//             Paragraph titulo = new Paragraph("Factura", font);
//             titulo.setAlignment(Paragraph.ALIGN_CENTER);
//             documento.add(titulo);

//             // Fecha
//             DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//             Date date = new Date();
//             Paragraph fecha = new Paragraph("Fecha: " + dateFormat.format(date), font);
//             documento.add(fecha);

//             // Detalles de la factura (ejemplo)
//             Paragraph detalles = new Paragraph("Detalles de la factura:\n", font);
//             detalles.add("Producto 1: 10€\n");
//             detalles.add("Producto 2: 20€\n");
//             documento.add(detalles);

//         } catch (DocumentException | IOException e) {
//             e.printStackTrace();
//         } finally {
//             if (documento != null) {
//                 documento.close();
//             }
//         }
//     }
// }
