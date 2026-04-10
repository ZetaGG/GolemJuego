/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package golem;

/**
 *
 * @author evergaster
 */
public class HiloCaminar extends Thread {

    crearEscenaGrafica creaEscena;
    public boolean banderaHilo = true;

    public HiloCaminar(crearEscenaGrafica escena) {
        creaEscena = escena;
    }

    public void run() {
        while (banderaHilo) {
            try {
                
                creaEscena.caminar();
                Thread.sleep(150);
                
            } catch (InterruptedException ex) {
                System.out.println("Error:" + ex.getMessage());
            }
        }
        
        
    }

}
