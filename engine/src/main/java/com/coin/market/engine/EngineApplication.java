package com.coin.market.engine;

import com.coin.market.engine.coin.service.CoinService;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

//@SpringBootApplication
@RequiredArgsConstructor
public class EngineApplication {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(EngineApplication.class, args);

        CoinService coinService = new CoinService();

        coinService.coinDataCollectorsDays(20, "KRW-XRP");
//        coinService.coinDataCollectorsMins(400, "KRW-XRP", 60);


        List<String> listone = coinService.getListOfCoinMarket();
        System.out.println(listone);


	}

}
