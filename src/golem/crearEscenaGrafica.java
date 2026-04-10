/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package golem;

//libraries
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import java.util.ArrayList;
import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author User
 */
public class crearEscenaGrafica {

    BranchGroup bgRaiz = new BranchGroup();
    TransformGroup tgMoverHombroI;
    TransformGroup tgMoverCodoI;
    TransformGroup tgMoverHombroD;
    TransformGroup tgMoverCodoD;
    TransformGroup tgMoverCinturaI;
    TransformGroup tgMoverRodillaI;
    TransformGroup tgMoverCinturaD;
    TransformGroup tgMoverRodillaD;
    TransformGroup tgMoverTorso;
    int pasos;

    ArrayList<TransformGroup> tgArray = new ArrayList<>();

    public crearEscenaGrafica() {
        bgRaiz = new BranchGroup();
        color c = new color();
        int paraTextura = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        textura textura = new textura();

        //Cabeza
        Transform3D t3dMoverCabeza = new Transform3D();
        t3dMoverCabeza.set(new Vector3f(.0f, .42f, .05f));
        TransformGroup tgMoverCabeza = new TransformGroup(t3dMoverCabeza);

        //Nariz
        Transform3D t3dMoverNariz = new Transform3D();
        t3dMoverNariz.set(new Vector3f(.0f, -.1f, .28f));
        TransformGroup tgMoverNariz = new TransformGroup(t3dMoverNariz);

        //torso
        Transform3D t3dMoverTorso = new Transform3D();
        t3dMoverTorso.set(new Vector3f(.0f, .0f, .0f));
        tgMoverTorso = new TransformGroup(t3dMoverTorso);
        tgMoverTorso.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        //Hombro izquierdo
        Transform3D t3dMoverHombroI = new Transform3D();
        t3dMoverHombroI.set(new Vector3f(-.35f, .2f, .0f));
        tgMoverHombroI = new TransformGroup(t3dMoverHombroI);
        tgMoverHombroI.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Brazo izquierdo
        Transform3D t3dMoverBI = new Transform3D();
        t3dMoverBI.set(new Vector3f(-.1f, -.25f, .0f));
        TransformGroup tgMoverBI = new TransformGroup(t3dMoverBI);
        //Codo izquierdo
        Transform3D t3dMoverCodoI = new Transform3D();
        t3dMoverCodoI.set(new Vector3f(-.0f, -.25f, .0f));
        tgMoverCodoI = new TransformGroup(t3dMoverCodoI);
        tgMoverCodoI.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Antebrazo izquierdo
        Transform3D t3dMoverAntIz = new Transform3D();
        t3dMoverAntIz.set(new Vector3f(-.0f, -.25f, .0f));
        TransformGroup tgMoverBI2 = new TransformGroup(t3dMoverAntIz);

        //Hombro Derecho
        Transform3D t3dMoverHombroD = new Transform3D();
        t3dMoverHombroD.set(new Vector3f(.35f, .2f, .0f));
        tgMoverHombroD = new TransformGroup(t3dMoverHombroD);
        tgMoverHombroD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Brazo Derecho
        Transform3D t3dMoverBrazoD = new Transform3D();
        t3dMoverBrazoD.set(new Vector3f(.1f, -.25f, .0f));
        TransformGroup tgMoverBrazoD = new TransformGroup(t3dMoverBrazoD);
        //Codo derecho
        Transform3D t3dMoverCodoD = new Transform3D();
        t3dMoverCodoD.set(new Vector3f(-.0f, -.25f, .0f));
        tgMoverCodoD = new TransformGroup(t3dMoverCodoD);
        tgMoverCodoD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Antebrazo derecho
        Transform3D t3dMoverAntebrazoD = new Transform3D();
        t3dMoverAntebrazoD.set(new Vector3f(-.0f, -.25f, .0f));
        TransformGroup tgMoverAntebrazoD = new TransformGroup(t3dMoverAntebrazoD);

        //Coxis
        Transform3D t3dMoverCoxis = new Transform3D();
        t3dMoverCoxis.set(new Vector3f(.0f, -.28f, .0f));
        TransformGroup tgMoverCoxis = new TransformGroup(t3dMoverCoxis);

        //coxis izquierda
        Transform3D t3dMoverCinturaIz = new Transform3D();
        t3dMoverCinturaIz.set(new Vector3f(-.15f, -.21f, .0f));
        tgMoverCinturaI = new TransformGroup(t3dMoverCinturaIz);
        tgMoverCinturaI.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Pierna izquierda
        Transform3D t3dMoverPiernaIz = new Transform3D();
        t3dMoverPiernaIz.set(new Vector3f(-.0f, -.14f, .0f));
        TransformGroup tgMoverPiernaI = new TransformGroup(t3dMoverPiernaIz);
        //Rodilla izquierda
        Transform3D t3dMoverRodillaIz = new Transform3D();
        t3dMoverRodillaIz.set(new Vector3f(.0f, -.15f, .0f));
        tgMoverRodillaI = new TransformGroup(t3dMoverRodillaIz);
        tgMoverRodillaI.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Pantorrilla Izquierda
        Transform3D t3dMoverPantorrillaI = new Transform3D();
        t3dMoverPantorrillaI.set(new Vector3f(-.0f, -.15f, .0f));
        TransformGroup tgMoverPantorrillaI = new TransformGroup(t3dMoverPantorrillaI);

        //coxis derecha
        Transform3D t3dMoverCinturaD = new Transform3D();
        t3dMoverCinturaD.set(new Vector3f(.15f, -.21f, .0f));
        tgMoverCinturaD = new TransformGroup(t3dMoverCinturaD);
        tgMoverCinturaD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Pierna derecha 
        Transform3D t3dMoverPiernaD = new Transform3D();
        t3dMoverPiernaD.set(new Vector3f(.0f, -.14f, .0f));
        TransformGroup tgMoverPiernaD = new TransformGroup(t3dMoverPiernaD);
        //Rodilla derecha
        Transform3D t3dMoverRodillaD = new Transform3D();
        t3dMoverRodillaD.set(new Vector3f(.0f, -.15f, .0f));
        tgMoverRodillaD = new TransformGroup(t3dMoverRodillaD);
        tgMoverRodillaD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Pantorrilla derecha
        Transform3D t3dMoverPantorrillaD = new Transform3D();
        t3dMoverPantorrillaD.set(new Vector3f(.0f, -.15f, .0f));
        TransformGroup tgMoverPantorrillaD = new TransformGroup(t3dMoverPantorrillaD);

        //figuras
        //cajas
        Box bxNariz = new Box(.05f, .1f, .05f, paraTextura, textura.crearTexturas("caraFrente.png"));
        
        Box bxCabeza = new Box(.15f, .20f, .18f, paraTextura, textura.crearTexturas("texture.jpg"));
        Box bxTorso = new Box(.35f, .23f, .2f, c.setColor(22, 22, 186));
        Box bxCoxis = new Box(.17f, .2f, .14f, c.setColor(0, 255, 0));

        Box bxPiernaIz = new Box(.1f, .15f, .1f, c.setColor(64, 224, 208));
        Box bxPantorrillaIz = new Box(.1f, .15f, .1f, c.setColor(255, 0, 0));
        Box bxPiernaD = new Box(.1f, .15f, .1f, c.setColor(64, 224, 208));
        Box bxPantorrillaD = new Box(.1f, .15f, .1f, c.setColor(255, 0, 0));

        Box bxBrazoIz = new Box(.1f, .25f, .1f, c.setColor(64, 224, 208));
        Box bxAntebrazoIz = new Box(.1f, .25f, .1f, c.setColor(255, 0, 0));
        Box bxBrazoD = new Box(.1f, .25f, .1f, c.setColor(64, 224, 208));
        Box bxAntebrazoD = new Box(.1f, .25f, .1f, c.setColor(255, 0, 0));

        //esferas
        Sphere spHombroI = new Sphere(-.01f, c.setColor(64, 224, 208));
        Sphere spCodoI = new Sphere(-.01f, c.setColor(64, 224, 208));
        Sphere spHombroD = new Sphere(-.01f, c.setColor(64, 224, 208));
        Sphere spCodoD = new Sphere(-.01f, c.setColor(64, 224, 208));

        //esferas coxis
        Sphere spCinIz = new Sphere(-.01f, c.setColor(22, 22, 186));
        Sphere spRodIz = new Sphere(-.01f, c.setColor(22, 22, 186));
        Sphere spCinD = new Sphere(-.01f, c.setColor(22, 22, 186));
        Sphere spRodD = new Sphere(-.01f, c.setColor(22, 22, 186));

        //fin figuras
        TransformGroup tgMoverFigura = new TransformGroup();
        tgMoverFigura.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgMoverFigura.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        BoundingSphere mouseBounds = new BoundingSphere(new Point3d(), 1000.0);
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(tgMoverTorso);
        myMouseRotate.setSchedulingBounds(mouseBounds);

        bgRaiz.addChild(tgMoverFigura);
        bgRaiz.addChild(myMouseRotate);
        tgMoverFigura.addChild(tgMoverTorso);
        tgMoverTorso.addChild(bxTorso); //panza

        tgMoverTorso.addChild(tgMoverCabeza);
        tgMoverCabeza.addChild(bxCabeza);
        tgMoverCabeza.addChild(tgMoverNariz);
        tgMoverNariz.addChild(bxNariz);
        tgMoverTorso.addChild(tgMoverCoxis);
        tgMoverCoxis.addChild(bxCoxis);

        //Piernas
        tgMoverCoxis.addChild(tgMoverCinturaI);
        tgMoverCinturaI.addChild(spCinIz);
        tgMoverCinturaI.addChild(tgMoverPiernaI);
        tgMoverPiernaI.addChild(bxPiernaIz);
        tgMoverPiernaI.addChild(tgMoverRodillaI);
        tgMoverRodillaI.addChild(spRodIz);
        tgMoverRodillaI.addChild(tgMoverPantorrillaI);
        tgMoverPantorrillaI.addChild(bxPantorrillaIz);

        tgMoverCoxis.addChild(tgMoverCinturaD);
        tgMoverCinturaD.addChild(spCinD);
        tgMoverCinturaD.addChild(tgMoverPiernaD);
        tgMoverPiernaD.addChild(bxPiernaD);
        tgMoverPiernaD.addChild(tgMoverRodillaD);
        tgMoverRodillaD.addChild(spRodD);
        tgMoverRodillaD.addChild(tgMoverPantorrillaD);
        tgMoverPantorrillaD.addChild(bxPantorrillaD);

        //Brazos
        //Brazo izquierdo
        tgMoverTorso.addChild(tgMoverHombroI);
        tgMoverHombroI.addChild(spHombroI);
        tgMoverHombroI.addChild(tgMoverBI);
        tgMoverBI.addChild(bxBrazoIz);
        tgMoverBI.addChild(tgMoverCodoI);
        tgMoverCodoI.addChild(spCodoI);
        tgMoverCodoI.addChild(tgMoverBI2);
        tgMoverBI2.addChild(bxAntebrazoIz);

        //Brazo derecho
        tgMoverTorso.addChild(tgMoverHombroD);
        tgMoverHombroD.addChild(spHombroD);
        tgMoverHombroD.addChild(tgMoverBrazoD);
        tgMoverBrazoD.addChild(bxBrazoD);
        tgMoverBrazoD.addChild(tgMoverCodoD);
        tgMoverCodoD.addChild(spCodoD);
        tgMoverCodoD.addChild(tgMoverAntebrazoD);
        tgMoverAntebrazoD.addChild(bxAntebrazoD);

        tgArray.add(tgMoverHombroI); //0
        tgArray.add(tgMoverCodoI); //1
        tgArray.add(tgMoverHombroD); //2
        tgArray.add(tgMoverCodoD); //3

        tgArray.add(tgMoverCinturaI); //4
        tgArray.add(tgMoverRodillaI); //5
        tgArray.add(tgMoverCinturaD); //6
        tgArray.add(tgMoverRodillaD); //7
        tgArray.add(tgMoverTorso); //8

        // Objeto central para ganar
        Transform3D t3dMoverObjetivo = new Transform3D();
        t3dMoverObjetivo.set(new Vector3f(0.0f, 0.0f, 0.0f));
        TransformGroup tgMoverObjetivo = new TransformGroup(t3dMoverObjetivo);
        Sphere spObjetivo = new Sphere(0.1f, c.setColor(255, 215, 0)); // Oro

        tgMoverObjetivo.addChild(spObjetivo);
        bgRaiz.addChild(tgMoverObjetivo);
    }

    public void girarTG(int grados, String eje, int tgAux) {
        Transform3D leer = new Transform3D();
        Transform3D girar = new Transform3D();
        TransformGroup tg = new TransformGroup(); //almacena el tg que se lecciona el user
        tg = tgArray.get(tgAux);
        tg.getTransform(leer);

        //tgMoverHombroI.getTransform(leer);
        girar.rotX(Math.PI / 180 * grados);
        if (eje.equals("X")) {
            girar.rotX(Math.PI / 180 * grados);
        }
        if (eje.equals("Y")) {
            girar.rotY(Math.PI / 180 * grados);
        }
        if (eje.equals("Z")) {
            girar.rotZ(Math.PI / 180 * grados);
        }
        leer.mul(girar);

        //tgMoverHombroI.setTransform(leer);
        tg.setTransform(leer);
    }

    public void moverTorso(int tgAux, float x, float y, float z) {
        Transform3D mover = new Transform3D();
        TransformGroup tg = tgArray.get(tgAux);
        // NO usar leer.mul(mover) — eso acumula infinitamente
        mover.set(new Vector3f(x, y, z));
        tg.setTransform(mover);
    }

    public void caminar() {
        pasos++; // mira mira mor :D
        if (pasos < 8 && pasos > 0) {
            // Fase 1: Pierna izq al frente, pierna der atrás
            girarTG(-5, "X", 0);
            girarTG(-5, "X", 1);
            girarTG(5, "X", 4);
            girarTG(5, "X", 5);

            // Brazos se mueven opuestos a las piernas
            girarTG(5, "X", 2);
            girarTG(-5, "X", 3);
            girarTG(-5, "X", 6);
            girarTG(5, "X", 7);
        }

        if (pasos < 15 && pasos > 7) {
            girarTG(5, "X", 0);
            girarTG(5, "X", 1);
            girarTG(-5, "X", 4);
            girarTG(-5, "X", 5);

            girarTG(-5, "X", 2);
            girarTG(5, "X", 3);
            girarTG(5, "X", 6);
            girarTG(-5, "X", 7);
        }
        if (pasos < 22 && pasos > 14) {
            girarTG(5, "X", 0);
            girarTG(-5, "X", 1);
            girarTG(-5, "X", 4);
            girarTG(5, "X", 5);

            girarTG(-5, "X", 2);
            girarTG(-5, "X", 3);
            girarTG(5, "X", 6);
            girarTG(5, "X", 7);
        }
        if (pasos < 29 && pasos > 21) {
            girarTG(-5, "X", 0);
            girarTG(5, "X", 1);
            girarTG(5, "X", 4);
            girarTG(-5, "X", 5);

            girarTG(5, "X", 2);
            girarTG(5, "X", 3);
            girarTG(-5, "X", 6);
            girarTG(-5, "X", 7);
        }
        if (pasos == 28) {
            pasos = 0;
        }
        moverTorso(8, .0f, .0f, .05f);

    }

    public void takeTheL() {


        if (pasos == 0) {
            // Brazo IZQ: hombro sube 45° en Z, codo dobla 45° apuntando a cabeza
            girarTG(-135, "Z", 0);    // Hombro izq sube (~45° total en 7 pasos)
            girarTG(-140, "Z", 1);   // Codo izq dobla hacia cabeza
            girarTG(-20, "X", 1);
            // Brazo DER: hombro baja 45° en Z, codo dobla 45° apuntando a cadera
            girarTG(30, "Z", 2);   // Hombro der baja
            girarTG(-90, "Z", 3);    // Codo der dobla hacia cadera
            // Fase 1: Pose inicial de la "L" + pierna izq al frente (0-7)
        }
        pasos++;

        if (pasos > 0 && pasos < 8) {


            girarTG(-12, "Z", 4);  // Cintura izq: pierna se abre lateral
            girarTG(-6,  "X", 4);  // Rodilla izq se abre lateral
            girarTG(5,  "X", 5);  // Rodilla izq dobla
            girarTG(3,   "X", 6);  // Cintura der estable
            girarTG(0,   "X", 7);  // Rodilla der recta

            // Pierna DER quieta
            girarTG(0, "X", 6);
            girarTG(0, "X", 7);
        }

        // Fase 2: Pierna izq baja, pierna der sube al frente (8-14)
        if (pasos > 7 && pasos < 15) {
            // Brazos se mantienen (los grados se cancelan con lo acumulado)
            girarTG(0, "Z", 0);
            girarTG(0, "X", 1);
            girarTG(0, "Z", 2);
            girarTG(0, "X", 3);

            // Pierna IZQ baja (inverso de fase 1)
            girarTG(-6, "X", 4);
            girarTG(6, "X", 5);

            // Pierna DER sube al frente
            girarTG(6, "X", 6);
            girarTG(-6, "X", 7);
        }

        // Fase 3: Pierna der baja, pierna izq vuelve a subir (15-21)
        if (pasos > 14 && pasos < 22) {
            girarTG(0, "Z", 0);
            girarTG(0, "X", 1);
            girarTG(0, "Z", 2);
            girarTG(0, "X", 3);

            // Pierna IZQ sube otra vez
            girarTG(6, "X", 4);
            girarTG(-6, "X", 5);

            // Pierna DER baja
            girarTG(-6, "X", 6);
            girarTG(6, "X", 7);
        }

        // Fase 4: Todo regresa al origen (22-28)
        if (pasos > 21 && pasos < 29) {
            // Brazos regresan: inverso exacto de fase 1
            girarTG(-6, "Z", 0);
            girarTG(6, "X", 1);
            girarTG(6, "Z", 2);
            girarTG(-6, "X", 3);

            // Piernas regresan
            girarTG(-6, "X", 4);
            girarTG(6, "X", 5);
            girarTG(0, "X", 6);
            girarTG(0, "X", 7);
        }

        if (pasos == 28) {
            pasos = 0;
        }
    }

    public Vector3f getPosicionTorso() {
        Transform3D t3d = new Transform3D();
        tgMoverTorso.getTransform(t3d);
        Vector3f pos = new Vector3f();
        t3d.get(pos);
        return pos;
    }
}

