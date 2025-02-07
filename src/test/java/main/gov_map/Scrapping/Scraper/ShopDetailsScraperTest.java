package main.gov_map.Scrapping.Scraper;

import main.gov_map.Scrapping.Dto.ShopDetails;
import main.gov_map.Scrapping.Dto.ShopDetailsRequestParam;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopDetailsScraperTest {

    @Autowired
    MainScraper mainScraper;

    @Test
    public void request() throws Exception {
        //given
        RequestRegionURL requestURL = RequestRegionURL.DONGJAK;

        //when
        List<ShopDetails> shopDetailsList = mainScraper.process(requestURL);

        //then
        for (ShopDetails shopDetails : shopDetailsList) {
            System.out.println("shopDetails = " + shopDetails);
        }
    }

}