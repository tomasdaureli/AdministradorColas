package programa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import api.AdministradorDeColasTDA;
import impl.AdminColasLD;

public class principal {

    public static void main(String[] args) throws InterruptedException {
        
        Random random = new Random();
        Calendar calendario = new GregorianCalendar();
        int hora, minutos;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        AdministradorDeColasTDA cola = new AdminColasLD();
        cola.inicializarCola(16);

        // Acolamos los pacientes
        for (int i = 0; i < 6; i++){
            cola.acolar(cola.recuperarPrioridad(random.nextInt(3)));
        }

        // Llamados
        while(!cola.colaVacia()){
            int puesto = random.nextInt(cola.cantidadDePuestos())+1;
            int id = cola.desacolar(puesto);
            String ticket = cola.generarTicket(id);
            System.out.println("Ticket: " + ticket + "\t -> Puesto: " + puesto + "\t Hora: " + hora + ":" + minutos);
            Thread.sleep(4000);
        }

    }

}
