package com.ConversorMoedas;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Moeda moeda = new Moeda();

        System.out.println("Seja bem-vindo ao Conversor de Moeda");
        boolean opcao = true;

        while (opcao){
            System.out.println("Escolha um Opção");
            System.out.println("1 : Dólar =>> peso Argentino");
            System.out.println("2 : Peso Argentino =>> Dólar");
            System.out.println("3 : Dólar =>> Real Brasileiro");
            System.out.println("4 : Real Brasileiro =>> Dólar");
            System.out.println("5 : Dólar =>> peso Colombiano");
            System.out.println("6 : Peso Colombiano =>> Dólar");
            System.out.println("0 : Sair");
            System.out.println();

            int escolha = sc.nextInt();

            switch (escolha){
                case 1:
                    moeda.setMoedaBase("USD");
                    moeda.setMoedaDestino("ARS");
                    System.out.println(moeda.getMoedaBase()+ " -> " + moeda.getMoedaDestino());
                    break;
                case 2:
                    moeda.setMoedaBase("ARS");
                    moeda.setMoedaDestino("USD");
                    System.out.println(moeda.getMoedaBase()+ " -> " + moeda.getMoedaDestino());
                    break;
                case 3:
                    moeda.setMoedaBase("USD");
                    moeda.setMoedaDestino("BRL");
                    System.out.println(moeda.getMoedaBase()+ " -> " + moeda.getMoedaDestino());
                    break;
                case 4:
                    moeda.setMoedaBase("BRL");
                    moeda.setMoedaDestino("USD");
                    System.out.println(moeda.getMoedaBase() + " -> " + moeda.getMoedaDestino());
                    break;
                case 5:
                    moeda.setMoedaBase("USD");
                    moeda.setMoedaDestino("COP");
                    System.out.println(moeda.getMoedaBase()+ " -> " + moeda.getMoedaDestino());

                    break;
                case 6:
                    moeda.setMoedaBase("COP");
                    moeda.setMoedaDestino("USD");
                    System.out.println(moeda.getMoedaBase()+ " -> " + moeda.getMoedaDestino());
                    break;
                case 0:
                    opcao = false;
                    System.out.println("Programa finalizado com sucesso.");
                    break;

            }
            if(!opcao){
                break;
            }

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://v6.exchangerate-api.com/v6/f1f210f16cc9df95a404bfe5/latest/" + moeda.getMoedaBase()))
                    .build();

            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            try {
                /// Converter String → JsonObject
                String respostaJson = response.body();
                JsonElement element = JsonParser.parseString(respostaJson);
                JsonObject object = element.getAsJsonObject();

                JsonObject rates = object.getAsJsonObject("conversion_rates");
                double valor = rates.get(moeda.getMoedaDestino()).getAsDouble();
                System.out.println("1 " + moeda.getMoedaBase() + " = " + valor + " " + moeda.getMoedaDestino());

            }catch (NullPointerException e){
                System.err.println("Erro : opção inválida.");
            }
        }

        sc.close();

    }
}