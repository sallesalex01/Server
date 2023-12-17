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
        listaDeCodigos.add("123456");

        port(3000);

        post("/api/verificarCodigo", (request, response) -> {
            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);
            CodigoDeAcesso codigo = gson.fromJson(corpoRequest, CodigoDeAcesso.class);

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
    }

    private static class CodigoDeAcesso {
        String codigo;
    }

}
