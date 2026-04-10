/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package golem;

/**
 *
 * @author evergaster
 */
public class HiloBaile extends Thread {

    crearEscenaGrafica creaEscena;
    public boolean banderaHilo = true;

    public HiloBaile(crearEscenaGrafica escena) {
        creaEscena = escena;
    }

    public void run() {
        while (banderaHilo) {
            try {

                creaEscena.takeTheL();
                Thread.sleep(150);

            } catch (InterruptedException ex) {
                System.out.println("Error:" + ex.getMessage());
            }
        }


    }

}

