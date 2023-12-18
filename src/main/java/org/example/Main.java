package org.example;

import static spark.Spark.*;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        ArrayList<String> listaDeCodigos = new ArrayList<>();
        Gson gson = new Gson();
        listaDeCodigos.add("1234567890");


        JFrame frame = new JFrame("Controle de Acesso servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());
        JLabel codigoLabel = new JLabel("Codigo de acesso: ");
        JTextField codigoTextField = new JTextField(20);

        port(3000);
        post("/api/verificarCodigo", (request, response) -> {
            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);
            CodigoDeAcesso codigo = gson.fromJson(corpoRequest, CodigoDeAcesso.class);
            codigoTextField.setText(codigo.codigo);

            if (listaDeCodigos.contains(codigo.codigo)) {
                return "{\"ACK\": \n \"1\"}";

            } else {
                return "{\"ACK\": \n \"0\"}";
            }

        });
        post("/api/cadastrarCodigo", (request, response) -> {
            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);
            CodigoDeAcesso codigo = gson.fromJson(corpoRequest, CodigoDeAcesso.class);
            listaDeCodigos.add(codigo.codigo);

            return "Codigo de acesso cadastrado com sucesso: " + codigo.codigo;
        });
        frame.add(codigoLabel);
        frame.add(codigoTextField);

        frame.setVisible(true);
    }

    private static class CodigoDeAcesso {
        String codigo;
    }

}
