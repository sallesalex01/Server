package org.example;

import static spark.Spark.*;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static int totalRequisicoes;
    private static String nomeRequisicao;

    public static void main(String[] args) {
        Gson gson = new Gson();


        port(3000);




        JFrame frame = new JFrame("Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JTextField nomeField = new JTextField();
        nomeField.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Nome:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));

        JLabel responseLabel = new JLabel("Email: ");
        responseLabel.setFont((new Font("Arial", Font.BOLD, 30)));
        JTextField email = new JTextField();
        email.setFont((new Font("Arial", Font.PLAIN, 30)));


        panel.add(label1);
        panel.add(nomeField);
        panel.add(responseLabel);
        panel.add(email);


        frame.add(panel);
        frame.setVisible(true);



        get("/api/:name", (request, response) -> {
            String nome = request.params(":name");
            nomeRequisicao = nome;
            totalRequisicoes++;
            nomeField.setText(nomeRequisicao);
            email.setText(Integer.toString(totalRequisicoes));
            return String.valueOf(totalRequisicoes);
        });
        post("/api", (request, response) -> {
            //JsonElement jsonElement = JsonParser.parseString(corpoRequest)
            //JsonObject jsonObject = jsonElement.getAsJsonObject();
            //jsonObject.get("nome").getAsString();
            //jsonObject.get("email").getAsString();


            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);
            Pessoa pessoa = gson.fromJson(corpoRequest, Pessoa.class);
            nomeField.setText(pessoa.nome);
            email.setText(pessoa.email);

                if(pessoa.nome.equals(pessoa.email)){
                return "{\"ACK\": \n \"1\"}";
            } else {
                return "{\"ACK\": \n \"0\"}";
            }

        });
        post("/api/imc", (request, response) -> {
            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);
            Imc imc = gson.fromJson(corpoRequest, Imc.class);
            int massa = Integer.parseInt(imc.massa);
            double altura = Double.parseDouble(imc.altura);
            double IMC = massa / Math.pow(altura, 2);

            // Formate o IMC com duas casas decimais
            String formattedIMC = String.format("%.2f", IMC);

            return formattedIMC;
        });
    }

    private static class Imc {
        String massa;
        String altura;
    }
    private static class Pessoa {
        String nome;
        String email;
    }





//    private static class usuario {
//        int acesso;
//
//        public usuario(String parametro1) {
//            this.acesso = ++totalRequisicoes;
//        }
//    }
}
