package com.coin.market.engine.utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ExcelMaker {

    public void makeCsvFrame() throws IOException {


    }

    public void exportToCsv(JSONArray jsonArray, List<String> headers, String fileName) throws IOException {

        try (FileWriter writer = new FileWriter(fileName)){

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < headers.size(); i++) {
                sb.append(headers.get(i));
                if (i != headers.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
            writer.append(sb.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                writer.append(obj.getString("candle_date_time_utc")).append(",")
                        .append(String.valueOf(obj.getDouble("opening_price"))).append(",")
                        .append(String.valueOf(obj.getDouble("high_price"))).append(",")
                        .append(String.valueOf(obj.getDouble("low_price"))).append(",")
                        .append(String.valueOf(obj.getDouble("trade_price"))).append("\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
