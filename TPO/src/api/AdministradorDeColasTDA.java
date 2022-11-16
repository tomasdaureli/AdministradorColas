package api;

import apis.ColaPrioridadTDA;
import apis.ConjuntoTDA;
import apis.DiccionarioMultipleTDA;

public interface AdministradorDeColasTDA {
    void inicializarCola(int cantidad);
    int acolar(int demora);
    void acolar(int idElemento, int demora);
    int desacolar(int puesto);
    int cantidadDePuestos();
    int proximo();
    int posicionXelemento(int idElemento);
    DiccionarioMultipleTDA elementos();
    ConjuntoTDA atendidosXpuesto(int puesto);
    ColaPrioridadTDA programacion();
    String recuperarPrefijo(int prioridad);
    int recuperarPrioridad(int opcion);
    String generarTicket(int idElemento);
    boolean colaVacia();
}
