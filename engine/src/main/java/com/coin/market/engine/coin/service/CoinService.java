package com.coin.market.engine.coin.service;

import com.coin.market.engine.utility.DateFormatterUtil;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//@Service
public class CoinService {

    public List<String> getListOfCoinMarket() {
        OkHttpClient client = new OkHttpClient();
        List<String> returnObj = new ArrayList<>();

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/market/all")
                .get()
                .addHeader("accept", "application/json")
                .build();

        try(Response response = client.newCall(request).execute()) {

            if (response.body() == null) {
                throw new IOException("Response body is null");
            }

            String resString = response.body().string();

            JSONArray jsonArray = new JSONArray(resString);

            for(int i=0 ;i <jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                // System.out.println(obj.get("market"));
                returnObj.add(String.valueOf(obj.get("market")));
            }

            return returnObj;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public HttpUrl makeHttpUrl(String url, String dateTime, int count){
        return  Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                .addQueryParameter("market", "KRW-XRP")
                .addQueryParameter("to", dateTime)
                .addQueryParameter("count", String.valueOf(count))
                .build();
    }

    public void coinDataCollectorsDays(int iteratorNumbers, String market){

        OkHttpClient client = new OkHttpClient();
//        String strDateTime = "2025-11-01T09:00:00";
        // 시간
        LocalDateTime now = LocalDateTime.now();
        LocalDate targetDate;
        if (now.toLocalTime().isBefore(LocalTime.of(9, 0))) {
            targetDate = now.toLocalDate().minusDays(1); // 전날
        } else {
            targetDate = now.toLocalDate(); // 오늘
        }
        LocalDateTime targetDateTime = LocalDateTime.of(targetDate, LocalTime.of(9, 0));
        String strDateTime = targetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));


        String fileName = String.format("./total_%s.csv", market);
        DateFormatterUtil dateFormatterUtil = new DateFormatterUtil();


        // make csv format
        try (FileWriter writer = new FileWriter(fileName)) {

            // 처음 Header를 넣는다.
            writer.append("candle_date_time_kst,opening_price,trade_price,high_price,change_rate,market,candle_date_time_utc,prev_closing_price,candle_acc_trade_volume,change_price,low_price,candle_acc_trade_price,timestamp\n");


            for(int num=0; num<iteratorNumbers; num++) {

                HttpUrl url = makeHttpUrl("https://api.upbit.com/v1/candles/days", strDateTime, 200);

                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("accept", "application/json")
                        .build();

                try (Response response = client.newCall(request).execute()) {


                    // response가 성공하지 못했을때
                    if (!response.isSuccessful()) {
                        if(response.code() == 429 ){
                            System.out.println("Rate-Limit");
                            num --;
                            Thread.sleep(1000);
                            continue;
                        }
                        throw new IOException("Unexpected code " + response);
                    }

                    // response.body() null 체크
                    if (response.body() == null) {
                        throw new IOException("Response body is null");
                    }

                    String resString = response.body().string(); // ResponseBody는 한 번만 읽을 수 있음
//            System.out.println("Raw JSON: " + resString);

                    // JSONArray로 파싱
                    JSONArray jsonArray = new JSONArray(resString);

                    if (jsonArray.isEmpty()) {
                        System.out.println("No more data available. Stopping iteration.");
                        break; // 반복 종료
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //System.out.println(jsonArray.get(i));
                        JSONObject obj = jsonArray.getJSONObject(i);
                        // candle_date_time_kst,opening_price,trade_price,high_price,change_rate,market,candle_date_time_utc,prev_closing_price,candle_acc_trade_volume,change_price,low_price,candle_acc_trade_price,timestamp
                        writer.append(obj.getString("candle_date_time_kst")).append(",")
                                .append(String.valueOf(obj.get("opening_price"))).append(",")
                                .append(String.valueOf(obj.get("trade_price"))).append(",")
                                .append(String.valueOf(obj.get("high_price"))).append(",")
                                .append(String.valueOf(obj.get("change_rate"))).append(",")
                                .append(obj.getString("market")).append(",")
                                .append(obj.getString("candle_date_time_utc")).append(",")
                                .append(String.valueOf(obj.get("prev_closing_price"))).append(",")
                                .append(String.valueOf(obj.get("candle_acc_trade_volume"))).append(",")
                                .append(String.valueOf(obj.get("change_price"))).append(",")
                                .append(String.valueOf(obj.get("low_price"))).append(",")
                                .append(String.valueOf(obj.get("candle_acc_trade_price"))).append(",")
                                .append(String.valueOf(obj.get("timestamp"))).append(",").append("\n");

                    }

                    JSONObject obj = jsonArray.getJSONObject(0);
                    // System.out.println(obj.getDouble("opening_price"));


                    // 우선
                    LocalDateTime datetime= dateFormatterUtil.stringToDate(strDateTime);
                    LocalDateTime newDateTime = datetime.minusDays(200);
                    System.out.println(newDateTime);

                    strDateTime = dateFormatterUtil.dateToString(newDateTime);

                } catch (Exception e) {
                   throw new RuntimeException(e);
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 역순으로
        try{
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);
            String header = lines.removeFirst(); // header 따로 저장
            Collections.reverse(lines);
            lines.addFirst(header);
            Files.write(path, lines);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public void coinDataCollectorsMins(int iteratorNumbers, String market, int unit){

        OkHttpClient client = new OkHttpClient();
        
        // 시간
        LocalDateTime now = LocalDateTime.now();
        LocalDate targetDate;
        if (now.toLocalTime().isBefore(LocalTime.of(9, 0))) {
            targetDate = now.toLocalDate().minusDays(1); // 전날
        } else {
            targetDate = now.toLocalDate(); // 오늘
        }
        LocalDateTime targetDateTime = LocalDateTime.of(targetDate, LocalTime.of(9, 0));
        String strDateTime = targetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        String fileName = String.format("./total_%s.csv", market);
        DateFormatterUtil dateFormatterUtil = new DateFormatterUtil();


        // make csv format
        try (FileWriter writer = new FileWriter(fileName)) {

            // 처음 Header를 넣는다.
            writer.append("candle_date_time_kst,opening_price,trade_price,high_price,market,candle_date_time_utc,candle_acc_trade_volume,low_price,candle_acc_trade_price,timestamp\n");


            for(int num=0; num<iteratorNumbers; num++) {

                String targetUrl = "https://api.upbit.com/v1/candles/minutes/" + String.valueOf(unit);

                HttpUrl url = makeHttpUrl(targetUrl, strDateTime, 200);

                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("accept", "application/json")
                        .build();

                try (Response response = client.newCall(request).execute()) {


                    // response가 성공하지 못했을때
                    if (!response.isSuccessful()) {
                        if(response.code() == 429 ){
                            System.out.println("Rate-Limit");
                            num --;
                            System.out.println("check of num: " + String.valueOf(num));
                            Thread.sleep(1000);
                            continue;
                        }
                        throw new IOException("Unexpected code " + response);
                    }

                    // response.body() null 체크
                    if (response.body() == null) {
                        throw new IOException("Response body is null");
                    }

                    String resString = response.body().string(); // ResponseBody는 한 번만 읽을 수 있음
//            System.out.println("Raw JSON: " + resString);

                    // JSONArray로 파싱
                    JSONArray jsonArray = new JSONArray(resString);

                    if (jsonArray.isEmpty()) {
                        System.out.println("No more data available. Stopping iteration.");
                        break; // 반복 종료
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //System.out.println(jsonArray.get(i));
                        JSONObject obj = jsonArray.getJSONObject(i);
                        // candle_date_time_kst,opening_price,trade_price,high_price,change_rate,market,candle_date_time_utc,prev_closing_price,candle_acc_trade_volume,change_price,low_price,candle_acc_trade_price,timestamp
                        writer.append(obj.getString("candle_date_time_kst")).append(",")
                                .append(String.valueOf(obj.get("opening_price"))).append(",")
                                .append(String.valueOf(obj.get("trade_price"))).append(",")
                                .append(String.valueOf(obj.get("high_price"))).append(",")
                                .append(obj.getString("market")).append(",")
                                .append(obj.getString("candle_date_time_utc")).append(",")
                                .append(String.valueOf(obj.get("candle_acc_trade_volume"))).append(",")
                                .append(String.valueOf(obj.get("low_price"))).append(",")
                                .append(String.valueOf(obj.get("candle_acc_trade_price"))).append(",")
                                .append(String.valueOf(obj.get("timestamp"))).append(",").append("\n");

                    }

                    JSONObject obj = jsonArray.getJSONObject(0);
                    // System.out.println(obj.getDouble("opening_price"));


                    // 우선
                    LocalDateTime datetime= dateFormatterUtil.stringToDate(strDateTime);
                    LocalDateTime newDateTime = datetime.minusMinutes(200L *unit);
                    System.out.println(newDateTime);

                    strDateTime = dateFormatterUtil.dateToString(newDateTime);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 역순으로
        try{
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);
            String header = lines.removeFirst(); // header 따로 저장
            Collections.reverse(lines);
            lines.addFirst(header);
            Files.write(path, lines);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }


}
