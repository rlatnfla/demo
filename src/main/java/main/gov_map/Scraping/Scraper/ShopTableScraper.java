package main.gov_map.Scraping.Scraper;

import lombok.RequiredArgsConstructor;
import main.gov_map.Scraping.Dto.ShopDetailsRequestParam;
import main.gov_map.Scraping.Parser.ShopTableParser;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component("shopTableScraper")
@RequiredArgsConstructor
public class ShopTableScraper {

    private final ShopTableParser shopTableParser;


    /**
     * 지역 코드를 입력받고 해당 지역의 행정처분 테이블을 전부 스크래핑후 상세 정보를 얻기위한 요청 패러미터를 dto 리스트로 가공후 반환
     */
    public List<ShopDetailsRequestParam> process(String requestURL){
        int index = 1;
        List<ShopDetailsRequestParam> allShopDetailsParam = new ArrayList<>();

        while (true) {
            String responseBody = sendRequest(requestURL, index);

            List<ShopDetailsRequestParam> parsedResult = htmlParse(responseBody);
            if(parsedResult.isEmpty()){
                break;
            }

            allShopDetailsParam.addAll(parsedResult);
            index++;
        }

        return allShopDetailsParam;
    }

    private List<ShopDetailsRequestParam> htmlParse(String responseBody){
        return shopTableParser.parse(responseBody);
    }

    private String sendRequest(String requestURL, int index){
        String requestBody = "method=selectListAdmm&context=NTIS&jndinm=CafMwWdOpenEJB&methodnm=selectListAdmm&dispo_raise_wrk_code=&org_and_team_code=&yy=&admm_sno=&prefix_name=&strt_date=20250101&end_date=20250131&pageIndex=" + index + "&pageSize=20&category=&field=&keyword=&pageSize1=20&strt_yy=2025&strt_mm=01&strt_dd=01&end_yy=2025&end_mm=01&end_dd=31&pageSize2=20\n";

        HttpRequest request =  HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<byte[]> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofByteArray());
            return new String(response.body(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("ShopTableScraper 가 요청을 보내는데 실패했습니다.");
        }
    }
}
