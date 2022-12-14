package impl;

import api.AdministradorDeColasTDA;
import apis.ColaPrioridadTDA;
import apis.ConjuntoTDA;
import apis.DiccionarioMultipleTDA;
import apis.DiccionarioSimpleTDA;

public class AdminColasLD implements AdministradorDeColasTDA{

    private int cantPuestos;
    public static int idElemento = 0;
    private ConjuntoTDA pacientes;
    private ColaPrioridadTDA colaPacientes;
    private DiccionarioSimpleTDA turnos, ticketId;
    private DiccionarioMultipleTDA historial;

    
    public void inicializarCola(int cantidad){
        cantPuestos = cantidad;
        pacientes = new ConjuntoLD();
        colaPacientes = new ColaPrioridadLD();
        turnos = new DicSimpleL();
        historial = new DicMultipleL();
        ticketId = new DicSimpleL();
        pacientes.inicializarConjunto();
        colaPacientes.inicializarCola();
        turnos.inicializarDiccionario();
        ticketId.inicializarDiccionario();
        historial.inicializarDiccionario();
    }

    public int acolar(int demora){
        idElemento++;
        pacientes.agregar(idElemento);
        colaPacientes.acolarPrioridad(idElemento, demora);
        return idElemento;
    }

    public void acolar(int idElemento, int demora){
    //     if(!pacientes.pertenece(idElemento)){
    //         pacientes.agregar(idElemento);
    //         colaPacientes.acolarPrioridad(idElemento, demora);
    //     }
    //     else {
    //         System.out.println("El paciente ya esta en la cola.");
    //     }
    }

    public int desacolar(int puesto){
        int ticket = -1;
        if(puesto > 0 && puesto <= cantPuestos && !colaPacientes.colaVacia()){
            ticket = colaPacientes.primero();
            int prioridad = colaPacientes.prioridad();
            ticketId.agregar(ticket, prioridad);
            turnos.agregar(ticket, puesto);
            historial.agregar(puesto, ticket);
            colaPacientes.desacolar();
        }
        return ticket;
    }

    public int cantidadDePuestos(){
        return cantPuestos;
    }

    public int proximo(){
        return colaPacientes.primero();
    }

    public int posicionXelemento(int idElemento){
        // Recibe un identificador y devuelve su posicion en la cola
        int posicion = 0;
        int indice = -1;
        ColaPrioridadTDA aux = new ColaPrioridadLD();
        // Paso los elementos a una cola auxiliar y busco el elemento recibido
        while(!colaPacientes.colaVacia()){
            int valor = colaPacientes.primero();
            int prioridad = colaPacientes.prioridad();
            aux.acolarPrioridad(valor, prioridad);
            if (valor == idElemento){
                indice = posicion;
            }
            colaPacientes.desacolar();
            posicion++;
        }
        // Restauro cola original
        while(!aux.colaVacia()){
            int valor = aux.primero();
            int prioridad = aux.prioridad();
            colaPacientes.acolarPrioridad(valor, prioridad);
            aux.desacolar();
        }
        return indice;
    }

    public DiccionarioMultipleTDA elementos(){
        // Devuelve diccionario con los elementos encolados y su posicion en la cola
        DiccionarioMultipleTDA elementos = new DicMultipleL();
        ColaPrioridadTDA aux = new ColaPrioridadLD();
        // Paso los elementos a una cola auxiliar
        while(!colaPacientes.colaVacia()){
            int valor = colaPacientes.primero();
            int prioridad = colaPacientes.prioridad();
            aux.acolarPrioridad(valor, prioridad);
            colaPacientes.desacolar();
        }
        // Agrego los elementos y su posicion al diccionario y restauro la cola original
        while(!aux.colaVacia()){
            int valor = aux.primero();
            int prioridad = aux.prioridad();
            elementos.agregar(valor, posicionXelemento(valor));
            colaPacientes.acolarPrioridad(valor, prioridad);
            aux.desacolar();
        }
        return elementos;
    }

    public ConjuntoTDA atendidosXpuesto(int puesto){
        // Recibe el identificador de un puesto y retorna los elementos atendidos por ese puesto
        ConjuntoTDA atendidos = new ConjuntoLD();
        // Valida que el puesto sea correcto
        if (puesto > 0 && puesto <= cantPuestos){
            atendidos = historial.recuperar(puesto);
        }
        return atendidos;
    }

    public ColaPrioridadTDA programacion(){
        // Devuelve una copia de la cola prioridad
        ColaPrioridadTDA copia = new ColaPrioridadLD();
        ColaPrioridadTDA aux = new ColaPrioridadLD();
        // Paso los elementos a una cola auxiliar
        while(!colaPacientes.colaVacia()){
            int valor = colaPacientes.primero();
            int prioridad = colaPacientes.prioridad();
            aux.acolarPrioridad(valor, prioridad);
            colaPacientes.desacolar();
        }
        // Paso los elementos a la cola copia y restauro la cola original
        while(!aux.colaVacia()){
            int valor = aux.primero();
            int prioridad = aux.prioridad();
            colaPacientes.acolarPrioridad(valor, prioridad);
            copia.acolarPrioridad(valor, prioridad);
            aux.desacolar();
        }
        return copia;
    }

    public String generarTicket(int idElemento){
        String ticket;
        String prefijo = recuperarPrefijo(ticketId.recuperar(idElemento));
        ticket = prefijo + idElemento;
        return ticket;
    }

    public String recuperarPrefijo(int prioridad) {
        String prefijo = "";
        switch(prioridad) {
            case 10:
                prefijo = "GUA";
                break;
            case 20:
                prefijo = "ENF";
                break;
            case 40:
                prefijo = "ODO";
                break;
        }
        return prefijo;
    }

    public int recuperarPrioridad(int opcion) {
        int prioridad = -1;
        switch(opcion) {
            case 0:
                prioridad = 10;
                break;
            case 1:
                prioridad = 20;
                break;
            case 2:
                prioridad = 40;
                break;
        }
        return prioridad;
    }

    public boolean colaVacia() {
        return colaPacientes.colaVacia();
    }
}
