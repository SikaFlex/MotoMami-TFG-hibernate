package com.dam.tfg.MotoMammiApplicationAGB.Tasks;
//proceso que se ejecuta una dependiendo de lo que pongas en propertis sera el tiempo de ejecucion
//scheduler 
    import java.text.SimpleDateFormat;
    import java.util.Date;
    
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.stereotype.Component;
    
    @Component
    public class ReadInfoFile {
    
        private static final Logger log = LoggerFactory.getLogger(ReadInfoFile.class);
    
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
        @Scheduled(cron="${cron.task.customer}")
        public void readCurrentFile() {
          


        }


        
    }
    

