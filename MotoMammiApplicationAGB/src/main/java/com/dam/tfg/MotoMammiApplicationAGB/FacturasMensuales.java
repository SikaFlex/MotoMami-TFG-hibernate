package com.dam.tfg.MotoMammiApplicationAGB;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FacturasMensuales {
    
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Calcular el retardo inicial para ejecutar la tarea el primer día del próximo mes
        long retardoInicial = calcularRetardoInicial();

        // Programar la tarea para que se ejecute mensualmente
        scheduler.scheduleAtFixedRate(new GenerarFacturasTarea(), retardoInicial, 30, TimeUnit.DAYS);

        System.out.println("Tarea programada para generar las facturas mensuales.");
    }

    static class GenerarFacturasTarea implements Runnable {
        @Override
        public void run() {
            Calendar fechaActual = Calendar.getInstance();
            fechaActual.add(Calendar.MONTH, -1); // Obtener el mes anterior

            int mes = fechaActual.get(Calendar.MONTH) + 1; // Sumar 1 porque los meses van de 0 a 11
            int año = fechaActual.get(Calendar.YEAR);

            String nombreArchivo = String.format("%02d_invoices_CODPROV_%d%02d.csv", mes, año, mes);

            try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreArchivo))) {
                // Aquí iría la lógica para generar el contenido del archivo de facturas
                // Por ejemplo, podrías obtener las facturas de una base de datos y escribirlas en el archivo
                escritor.println("Factura 1");
                escritor.println("Factura 2");
                // ...
                System.out.println("Se ha generado el archivo de facturas: " + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Calcular el retardo inicial para ejecutar la tarea el primer día del próximo mes
    private static long calcularRetardoInicial() {
        Calendar ahora = Calendar.getInstance();
        Calendar proximoMes = Calendar.getInstance();
        proximoMes.add(Calendar.MONTH, 1);
        proximoMes.set(Calendar.DAY_OF_MONTH, 1);
        proximoMes.set(Calendar.HOUR_OF_DAY, 0);
        proximoMes.set(Calendar.MINUTE, 0);
        proximoMes.set(Calendar.SECOND, 0);
        proximoMes.set(Calendar.MILLISECOND, 0);

        return proximoMes.getTimeInMillis() - ahora.getTimeInMillis();
    }
}
