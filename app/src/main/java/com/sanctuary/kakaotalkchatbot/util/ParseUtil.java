package com.sanctuary.kakaotalkchatbot.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseUtil {
    public static String getBuff(String serverId, String characterId, String characterName) {
        try {
            // 갱신
            refreshData(serverId, characterId);

            String url = "https://dunfaoff.com/CharacterSearchList.df?server=" + serverId + "&id=" + characterName;
            SSLConnect ssl = new SSLConnect();
            ssl.postHttps(url, 1000, 1000);

            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("div[data-id='" + characterId + "']");
            elements = elements.get(0).select("div.card-footer");
            elements = elements.get(0).select("span");

            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);

                // B 다음으로 오는 문자
                if ("B".equals(element.text())) {
                    return elements.get(i + 1).text();
                }
            }

            /* 던담 코드
            String url = "https://dundam.xyz/view40s?server=" + serverId + "&image=" + characterId;

            SSLConnect ssl = new SSLConnect();
            ssl.postHttps(url, 1000, 1000);

            Document doc = Jsoup.connect(url).get();
            LogUtil.i(url);

            Elements elements = doc.select("td[style='font-weight: bold']");
            return elements.get(0).text();
             */
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return "";
    }

    public static String getDeal(String serverId, String characterId, String characterName) {
        try {
            // 갱신
            refreshData(serverId, characterId);

            String url = "https://dunfaoff.com/CharacterSearchList.df?server=" + serverId + "&id=" + characterName;
            SSLConnect ssl = new SSLConnect();
            ssl.postHttps(url, 1000, 1000);

            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("div[data-id='" + characterId + "']");
            elements = elements.get(0).select("div.card-footer");
            elements = elements.get(0).select("span");

            return elements.get(1).text();

            // 이전 방식 (D 라는 페이지에서는 보이나 오지 않음)
//            for (int i = 0; i < elements.size(); i++) {
//                Element element = elements.get(i);
//
//                LogUtil.i(element.text());
//
//                // D 다음으로 오는 문자
//                if ("D".equals(element.text())) {
//                    return elements.get(i + 1).text();
//                }
//            }

            /* 던담 코드
            String url = "https://dundam.xyz/view40s?server=" + serverId + "&image=" + characterId;

            SSLConnect ssl = new SSLConnect();
            ssl.postHttps(url, 1000, 1000);

            Document doc = Jsoup.connect(url).get();
            LogUtil.i(url);

            Elements elements = doc.select("div[class='alld']");
            return elements.get(elements.size() - 1).text();
             */
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return "";
    }

    public static String getBuffRank(String serverId, String characterId) {
        try {
            String url = "https://dunfaoff.com/SearchResult.df?server=" + serverId + "&characterid=" + characterId;

            SSLConnect ssl = new SSLConnect();
            ssl.postHttps(url, 1000, 1000);

            Document doc = Jsoup.connect(url).get();
            LogUtil.i(url);

            Elements elements = doc.select("div[id='char_rank']");

            String result = "";
            result +=  elements.get(0).text() + "\n";
            result +=  elements.get(elements.size() - 1).text();

            return result;
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return "";
    }

    public static String getDealRank(String serverId, String characterId) {
        try {
            String url = "https://dunfaoff.com/SearchResult.df?server=" + serverId + "&characterid=" + characterId;

            SSLConnect ssl = new SSLConnect();
            ssl.postHttps(url, 1000, 1000);

            Document doc = Jsoup.connect(url).get();
            LogUtil.i(url);

            Elements elements = doc.select("div[id='char_rank']");
            return elements.get(0).text();
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return "";
    }

    private static void refreshData(String serverId, String characterId) {
        String url = "https://dunfaoff.com/SearchResult.df?server=" + serverId + "&characterid=" + characterId;
        SSLConnect ssl = new SSLConnect();
        ssl.postHttps(url, 1000, 1000);
    }
}
