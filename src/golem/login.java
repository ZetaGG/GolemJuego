package golem;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

/**
 * Interface de Login y Registro para el proyecto Golem - ITSU.
 * Desarrollado para Martin, Jafet y Everardo.
 */
public class login extends javax.swing.JFrame {

    // Configuración de Supabase
    private static final String SUPABASE_URL = "https://bcupobqvfqkbxhctakof.supabase.co/rest/v1/usuarios";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJjdXBvYnF2ZnFrYnhoY3Rha29mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzU4MzEzNDQsImV4cCI6MjA5MTQwNzM0NH0.CVJBdijL-mE2CdL6rkk7w9aUwaEiU1832XQZ3oGHWWc";

    private JTextField txtUser;
    private JPasswordField txtPass;

    public login() {
        setTitle("Proyecto Golem - Login");
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        txtUser = new JTextField(20);
        txtPass = new JPasswordField(20);
        JButton btnLogin = new JButton("Entrar");
        JButton btnRegister = new JButton("Registrarse");

        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel p1 = new JPanel(new FlowLayout());
        p1.add(new JLabel("Usuario:"));
        p1.add(txtUser);

        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(new JLabel("Password:"));
        p2.add(txtPass);

        JPanel p3 = new JPanel(new FlowLayout());
        p3.add(btnLogin);
        p3.add(btnRegister);

        add(new JLabel("Bienvenido al Cuarto del Golem", SwingConstants.CENTER));
        add(p1);
        add(p2);
        add(p3);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> register());

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void login() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        try {
            HttpClient client = HttpClient.newHttpClient();
            // Filtramos en la URL por username y password_hash para validar
            String url = SUPABASE_URL + "?username=eq." + user + "&password_hash=eq." + pass + "&select=id_usuario";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("apikey", SUPABASE_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Si la respuesta contiene un ID, el login es exitoso
            if (response.body().length() > 5) {
                String idStr = response.body().split("\"id_usuario\":\"")[1].split("\"")[0];

                // 1. Guardamos los datos globalmente en nuestra clase Sala
                Sala.userIdActual = UUID.fromString(idStr);
                Sala.usernameActual = user;

                JOptionPane.showMessageDialog(this, "¡Bienvenido, " + user + "!");

                // 2. REDIRIGIR AL LOBBY
                this.dispose();
                new LobbyFrame(Sala.usernameActual, Sala.userIdActual).setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }

    private void register() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            String json = "{\"username\": \"" + user + "\", \"password_hash\": \"" + pass + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL))
                    .header("apikey", SUPABASE_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_KEY)
                    .header("Content-Type", "application/json")
                    .header("Prefer", "return=minimal")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puedes entrar.");
            } else {
                JOptionPane.showMessageDialog(this, "El usuario ya existe o hubo un error.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new login().setVisible(true));
    }
}