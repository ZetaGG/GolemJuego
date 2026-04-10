package golem;

import java.util.UUID;

public class Sala {
    // Usamos String porque el ID puede ser muy grande
    public static String idPartidaActual = "-1";
    public static boolean esHost = false;
    public static String nombreSala = "";

    // También es útil guardar aquí al usuario actual para usarlo en el Golem
    public static String usernameActual = "";
    public static UUID userIdActual = null;
}