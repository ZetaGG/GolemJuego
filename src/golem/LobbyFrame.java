package golem;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.*;
import java.util.UUID;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyFrame extends javax.swing.JFrame {
    private String username;
    private UUID userId;
    private String idPartidaActual = "-1";
    private boolean esHost = false;
    private Timer timerChequeo;
    private JButton btnIniciar;

    // REEMPLAZAR CON TUS DATOS
    private static final String BASE_URL = "https://bcupobqvfqkbxhctakof.supabase.co/rest/v1/";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJjdXBvYnF2ZnFrYnhoY3Rha29mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzU4MzEzNDQsImV4cCI6MjA5MTQwNzM0NH0.CVJBdijL-mE2CdL6rkk7w9aUwaEiU1832XQZ3oGHWWc";

    public LobbyFrame(String username, UUID userId) {
        this.username = username;
        this.userId = userId;
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        System.out.println("DEBUG: Iniciando componentes del Lobby...");
        setTitle("Lobby - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Usamos el layout directamente en el frame
        CardLayout cl = new CardLayout();
        getContentPane().setLayout(cl);

        // PANEL 1: SELECCIÓN
        JPanel pnlSeleccion = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlSeleccion.setBackground(Color.DARK_GRAY); // Color temporal para ver si carga

        JButton btnCrear = new JButton("Crear Nueva Sala (Host)");
        JButton btnUnirse = new JButton("Unirse a Partida");
        JButton btnPrueba = new JButton("Modo Prueba");
        JLabel lblTitulo = new JLabel("Bienvenido, " + username, SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);

        pnlSeleccion.add(lblTitulo);
        pnlSeleccion.add(btnCrear);
        pnlSeleccion.add(btnUnirse);
        pnlSeleccion.add(btnPrueba);

        pnlSeleccion.setPreferredSize(new Dimension(500, 400));

        // PANEL 2: ESPERA
        JPanel pnlEspera = new JPanel(new BorderLayout());
        JLabel lblStatus = new JLabel("Esperando jugadores...", SwingConstants.CENTER);
        btnIniciar = new JButton("Iniciar Partida");
        pnlEspera.add(lblStatus, BorderLayout.CENTER);
        pnlEspera.add(btnIniciar, BorderLayout.SOUTH);
        btnIniciar.addActionListener(e -> iniciarPartida());
        btnIniciar.setVisible(false);

        pnlEspera.setPreferredSize(new Dimension(500, 400));

        // Agregamos con nombres clave
        getContentPane().add(pnlSeleccion, "seleccionar");
        getContentPane().add(pnlEspera, "espera");

        // ACCIONES
        btnCrear.addActionListener(e -> crearSala());
        btnUnirse.addActionListener(e -> unirseASala());
        btnPrueba.addActionListener(e -> modoPrueba());

        pack();

        // Esto obliga a Swing a mostrar la "carta" y refrescar la pantalla
        cl.show(getContentPane(), "seleccionar");
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void crearSala() {
        try {
            // 1. Crear partida en Supabase
            String json = "{\"nombre_sala\": \"Sala de " + username + "\", \"id_host\": \"" + userId + "\", \"estado\": \"esperando\"}";
            HttpResponse<String> resp = enviarPeticion("partidas", "POST", json, "return=representation");

            // 2. Obtener el ID de la partida creada (parsing simple)
            String body = resp.body();
            String idLimpio = body.split("\"id_partida\":")[1].replaceAll("[^0-9]", "");
            this.idPartidaActual = idLimpio;

            // Actualizamos la clase global
            Sala.idPartidaActual = this.idPartidaActual;
            Sala.esHost = true;

            mostrarEspera(true);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void unirseASala() {
        // En un proyecto pro aquí harías un GET para listar salas.
        // Para el ITSU, simplifiquemos: unirse a la última sala abierta.
        try {
            HttpResponse<String> resp = enviarPeticion("partidas?estado=eq.esperando&select=id_partida&order=iniciada_en.desc&limit=1", "GET", null, "");
            if (resp.body().length() > 5) {
                this.idPartidaActual = resp.body().split("\"id_partida\":")[1].split("}")[0];
                enviarPeticion("jugadores_partida", "POST", "{\"id_partida\": " + idPartidaActual + ", \"id_usuario\": \"" + userId + "\"}", "");
                mostrarEspera(false);
            } else {
                JOptionPane.showMessageDialog(this, "No hay salas disponibles.");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void mostrarEspera(boolean soyHost) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "espera");

        if (soyHost) {
            btnIniciar.setVisible(true);
        }

        // Hilo de chequeo constante (Polling)
        timerChequeo = new Timer();
        timerChequeo.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                verificarEstadoPartida();
            }
        }, 0, 2000); // Revisar cada 2 segundos
    }

    private void verificarEstadoPartida() {
        try {
            HttpResponse<String> resp = enviarPeticion("partidas?id_partida=eq." + idPartidaActual + "&select=estado", "GET", null, "");
            if (resp.body().contains("en_curso")) {
                timerChequeo.cancel();
                java.awt.EventQueue.invokeLater(() -> {
                    this.dispose();
                    // REDIRIGIR AL GOLEM
                    // Usamos los datos guardados en Sala
                    new golem(Sala.usernameActual, Sala.userIdActual).setVisible(true);
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void iniciarPartida() {
        // El Host cambia el estado a 'en_curso' y todos los que están escuchando entran
        try {
            enviarPeticion("partidas?id_partida=eq." + idPartidaActual, "PATCH", "{\"estado\": \"en_curso\"}", "");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void modoPrueba() {
        // Modo de prueba: entrar directamente al juego sin lobby
        this.dispose();
        new golem(Sala.usernameActual, Sala.userIdActual).setVisible(true);
    }

    // Método auxiliar para no repetir código de red
    private HttpResponse<String> enviarPeticion(String tabla, String metodo, String json, String prefer) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + tabla))
                .header("apikey", API_KEY)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json");

        if (!prefer.isEmpty()) builder.header("Prefer", prefer);

        if (metodo.equals("POST")) builder.POST(HttpRequest.BodyPublishers.ofString(json));
        else if (metodo.equals("PATCH")) builder.method("PATCH", HttpRequest.BodyPublishers.ofString(json));
        else builder.GET();

        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}