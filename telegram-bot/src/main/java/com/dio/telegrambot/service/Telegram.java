package com.dio.telegrambot.service;

import com.dio.telegrambot.client.FeingClient;
import com.dio.telegrambot.client.JavaHttpClient;
import com.dio.telegrambot.client.RestTemplateClient;
import com.dio.telegrambot.dto.MessageSend;
import com.dio.telegrambot.dto.ResultBotTelegram;
import com.dio.telegrambot.dto.ResultBotTelegramList;
import org.springframework.stereotype.Service;

@Service
public class Telegram {

    private RestTemplateClient restTemplateClient;
    private FeingClient feingClient;
    private JavaHttpClient javaHttpClient;

    public Telegram(RestTemplateClient restTemplateClient, FeingClient feingClient, JavaHttpClient javaHttpClient) {
        this.restTemplateClient = restTemplateClient;
        this.feingClient = feingClient;
        this.javaHttpClient = javaHttpClient;
    }

    public void enviarMensagem(MessageSend mensagem) {
        //HTTP CLIENT
        ResultBotTelegram resultBotTelegramResponseEntity = javaHttpClient.enviarMensagem(mensagem);

        //RestTemplate
        restTemplateClient.enviarMensagem(mensagem);

        //FeingClient
        ResultBotTelegram resultBotTelegram = feingClient.enviarMensagem1(mensagem);
    }

    public ResultBotTelegramList buscarAtualizacao() {
        //HTTP CLIENT
        ResultBotTelegramList resultBotTelegramList = javaHttpClient.buscarAtualizacao();

        //RestTemplate
        ResultBotTelegramList resultBotTelegramList1 = restTemplateClient.buscarAtualizacao();

        //FeingClient
        feingClient.buscaratualizacao();
        return resultBotTelegramList;
    }
}
