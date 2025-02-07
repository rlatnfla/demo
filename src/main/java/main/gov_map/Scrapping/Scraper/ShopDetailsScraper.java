package main.gov_map.Scrapping.Scraper;

import lombok.RequiredArgsConstructor;
import main.gov_map.Scrapping.Dto.ShopDetails;
import main.gov_map.Scrapping.Dto.ShopDetailsRequestParam;
import main.gov_map.Scrapping.Parser.ShopDetailsParser;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component("shopDetailsScraper")
@RequiredArgsConstructor
public class ShopDetailsScraper {

    private final ShopDetailsParser shopDetailsParser;

    public ShopDetails process(String requestURL, ShopDetailsRequestParam param) {
        String responseBody = sendRequest(requestURL, param);
        ShopDetails shopDetails = htmlParse(responseBody);

        return shopDetails;
    }

    private ShopDetails htmlParse(String responseBody){
        return shopDetailsParser.parse(responseBody);
    }

    private String sendRequest(String requestURL, ShopDetailsRequestParam param) {
        String requestBody = param.getRequestBody();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<byte[]> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofByteArray());
            return new String(response.body(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("ShopDetailsScraper 가 요청을 보내는데 실패했습니다.");
        }
    }
}
