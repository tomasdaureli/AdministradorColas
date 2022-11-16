package programa;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class hora {

    public static void main(String[] args) {
        
        Calendar calendario = new GregorianCalendar();

        int hora, minutos;

        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);

        System.out.println(hora+":"+minutos);
        
    }
    
}
