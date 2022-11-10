package impl;

import api.AdministradorDeColasTDA;
import apis.ColaPrioridadTDA;
import apis.ConjuntoTDA;
import apis.DiccionarioMultipleTDA;
import apis.DiccionarioSimpleTDA;

public class AdminColasLD implements AdministradorDeColasTDA{

    private int cantPuestos;
    private ConjuntoTDA pacientes;
    private ColaPrioridadTDA colaPacientes;
    private DiccionarioSimpleTDA turnos;
    private DiccionarioMultipleTDA historial;

    
    public void inicializarCola(int cantidad){
        cantPuestos = cantidad;
        pacientes = new ConjuntoLD();
        colaPacientes = new ColaPrioridadLD();
        turnos.inicializarDiccionario();
    }

    public int acolar(int demora){
        return 0;
    }

    public void acolar(int idElemento, int demora){
        if(!pacientes.pertenece(idElemento)){
            colaPacientes.acolarPrioridad(idElemento, demora);
        }
    }

    public int desacolar(int puesto){
        int ticket = -1;
        if(puesto > 0 && puesto <= cantPuestos && !colaPacientes.colaVacia()){
            ticket = colaPacientes.primero();
            turnos.agregar(puesto, ticket);
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
            colaPacientes.desacolar();
            if (valor == idElemento){
                indice = posicion;
            }
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
}
