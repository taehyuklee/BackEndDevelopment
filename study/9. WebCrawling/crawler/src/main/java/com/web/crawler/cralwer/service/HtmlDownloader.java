package com.web.crawler.cralwer.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class HtmlDownloader {

    public void downloadHTML(String urlString, String outputPath) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);  // 리디렉션 자동 처리

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine).append("\n");
                    }
                    // 파일에 저장
                    Files.write(Paths.get(outputPath), content.toString().getBytes(StandardCharsets.UTF_8));
                    System.out.println("HTML 다운로드 완료: " + outputPath);
                }
            } else {
                System.out.println("HTTP 요청 실패. 응답 코드: " + responseCode);
                String errorStream = readErrorStream(connection);
                System.out.println("에러 내용: " + errorStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String readErrorStream(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder errorContent = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                errorContent.append(inputLine).append("\n");
            }
            return errorContent.toString();
        }
    }


}
