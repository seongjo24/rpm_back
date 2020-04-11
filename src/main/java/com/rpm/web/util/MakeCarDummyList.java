package com.rpm.web.util;

import com.rpm.web.contents.CarsRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MakeCarDummyList {
    private CarsRepository carsRepository;
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36";
    public String sendPost(String targetUrl, String pageno) throws Exception {
        String jsonResult = "";
        try {

              jsonResult  = Jsoup
                    .connect(targetUrl)
                    .userAgent(USER_AGENT)
                    .data("price_tab_flag", "2")
                    .data("limit", "1")
                    .data("orderFlag", "true")
                    .data("orderby", "n_order:desc")
                    .data("wr_eq_v_usernm", "")
                    .data("pageno", pageno)
                    .ignoreContentType(true)
                    .timeout(5000)
                    .execute()
                    .body();

        } catch( IOException e ) {
            e.printStackTrace();
        }
            return jsonResult;
    }
    public String getCarCount(String targetUrl) throws Exception {
        String result = "";
        try{
            result  = Jsoup
                    .connect(targetUrl)
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .timeout(5000)
                    .execute()
                    .body();
            result.split(",")[0].replaceAll("^[0-9]", "");
    } catch (Exception e) {
        e.printStackTrace();
    }
        return result.split(",")[0].replaceAll("[^0-9]", "");
    }



}
