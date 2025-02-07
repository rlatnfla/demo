package main.gov_map.Repository;

import main.gov_map.Domain.Shop;
import main.gov_map.Domain.ViolationDetails;
import main.gov_map.Scrapping.Dto.AdmActionHandlerInfo;
import main.gov_map.Scrapping.Dto.AdmActionInfo;
import main.gov_map.Scrapping.Dto.ShopDetails;
import main.gov_map.Scrapping.Dto.ViolationInfo;
import main.gov_map.Scrapping.Scraper.MainScraper;
import main.gov_map.Scrapping.Scraper.RequestRegionURL;
import main.gov_map.Scrapping.Scraper.ShopDetailsScraper;
import main.gov_map.Scrapping.Scraper.ShopTableScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ShopRepositoryTest {

    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MainScraper mainScraper;


    @Test
    public void repositorySaveTest() throws Exception {
        //given
        List<ShopDetails> shopDetailsList = mainScraper.process(RequestRegionURL.SEOCHO);

        //when
        for (ShopDetails shopDetails : shopDetailsList) {
            AdmActionInfo admActionInfo = shopDetails.getAdmActionInfo();
            List<ViolationInfo> violationInfoList = shopDetails.getViolationInfoList();
            AdmActionHandlerInfo admActionHandlerInfo = shopDetails.getAdmActionHandlerInfo();

            List<ViolationDetails> violationDetailsList = new ArrayList<>();

            for (ViolationInfo violationInfo : violationInfoList) {

                violationDetailsList.add(ViolationDetails.createViolationDetails(violationInfo));
            }

            Shop shop = Shop.createShop(admActionInfo, violationDetailsList, admActionHandlerInfo);
            shopRepository.save(shop);
        }

        //then
    }

}