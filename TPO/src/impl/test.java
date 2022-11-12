package impl;

import api.AdministradorDeColasTDA;

public class test {
    public static void main(String[] args) {
        
        AdministradorDeColasTDA cola = new AdminColasLD();
        cola.inicializarCola(2);

        cola.acolar(1, 3);
        cola.acolar(1,3);
    }
}
