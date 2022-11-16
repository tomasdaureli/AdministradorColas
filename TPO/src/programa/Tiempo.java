package programa;

public class Tiempo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Paciente 1 -> Puesto 2");
        Thread.sleep(4000);
        System.out.println("Paciente 2 -> Puesto 1");
        Thread.sleep(4000);
        System.out.println("Paciente 3 -> Puesto 1");
        Thread.sleep(4000);
        System.out.println("Finaliza el programa.");
        
    }
}
