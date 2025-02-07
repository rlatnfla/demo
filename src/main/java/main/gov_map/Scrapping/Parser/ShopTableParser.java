package main.gov_map.Scrapping.Parser;

import main.gov_map.Scrapping.Dto.ShopDetailsRequestParam;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("shopTableParser")
public class ShopTableParser {

    /**
     * ShopDetailsScraper에서 사용할 http 요청 파라미터를 List로 반환
     * 요청이 유효하지 않을시 빈 List 반환 -> 더 나은 예외처리 방법 찾아야 할듯
     */
    public List<ShopDetailsRequestParam> parse(String responseBody) {
        Document doc = Jsoup.parse(responseBody);
        Elements rows = doc.select("table.table tbody tr");

        List<ShopDetailsRequestParam> shopDetailsRequestParamList = new ArrayList<>();

        if (rows.first().select("a").isEmpty()) {
            return new ArrayList<>();
        }

        for (Element row : rows) {
            Element a = row.select("a[href]").first();
            String href = a.attr("href");
            ShopDetailsRequestParam shopDetailsRequestParam = hrefContentParser(href);
            shopDetailsRequestParamList.add(shopDetailsRequestParam);
        }

        return shopDetailsRequestParamList;
    }

    /**
     * Extracted Data:
     * Index 0 : String _dispo_raise_wrk_code
     * Index 1 : int table _org_and_team_code
     * Index 2 : int _yy
     * Index 3 : int _admm_sno
     * Index 4 : String _prefix_name
     * detail 요청 할때 String -> int 타입 변환 필요
     */
    public ShopDetailsRequestParam hrefContentParser(String href){
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(href);
        // HashMap 은 동시성 문제 고려 해야함
        Map<String, String> extractedData = new HashMap<>();

        String[] keys = {
                "_dispo_raise_wrk_code",
                "table _org_and_team_code",
                "_yy",
                "_admm_sno",
                "_prefix_name"
        };

        int index = 0;
        while (matcher.find() && index < keys.length) {
            extractedData.put(keys[index], matcher.group(1));
            index++;
        }

        String dispositionCode = extractedData.get("_dispo_raise_wrk_code");
        String orgCode = extractedData.get("table _org_and_team_code");

        String year = extractedData.get("_yy");
        String admmSno = extractedData.get("_admm_sno");
        String prefixName = extractedData.get("_prefix_name");


        ShopDetailsRequestParam shopDetailsRequestParam = new ShopDetailsRequestParam(dispositionCode, orgCode, year, admmSno, prefixName);

        return shopDetailsRequestParam;
    }
}
