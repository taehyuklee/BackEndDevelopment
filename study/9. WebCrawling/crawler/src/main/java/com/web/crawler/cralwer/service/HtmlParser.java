package com.web.crawler.cralwer.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class HtmlParser {

    // HTML 다운로드 메서드
    public String downloadHTML(String urlString) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine).append("\n");
                    }
                }
            } else {
                System.out.println("HTTP 요청 실패. 응답 코드: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // URL 추출 메서드
    public Set<String> extractURLs(String html) {
        Set<String> urls = new HashSet<>();
        try {
            Document doc = Jsoup.parse(html);
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String url = link.attr("abs:href");  // 절대 URL을 가져옵니다.
                urls.add(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }


}