package main.gov_map.Scraping.Scraper;

import lombok.RequiredArgsConstructor;
import main.gov_map.Scraping.Dto.ShopDetails;
import main.gov_map.Scraping.Dto.ShopDetailsRequestParam;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("mainScraper")
@RequiredArgsConstructor
public class MainScraper {

    private final ShopTableScraper shopTableScraper;
    private final ShopDetailsScraper shopDetailsScraper;

    public List<ShopDetails> process(RequestRegionURL url) {
        String requestURL = url.getUrl();
        List<ShopDetailsRequestParam> params = shopTableScraper.process(requestURL);
        List<ShopDetails> shopDetailsList = new ArrayList<>();

        for (ShopDetailsRequestParam param : params) {
            ShopDetails shopDetails = shopDetailsScraper.process(requestURL, param);
            shopDetailsList.add(shopDetails);
        }

        return shopDetailsList;
    }
}
