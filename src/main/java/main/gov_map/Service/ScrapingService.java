package main.gov_map.Service;

import lombok.RequiredArgsConstructor;
import main.gov_map.Domain.Shop;
import main.gov_map.Domain.ViolationDetails;
import main.gov_map.Repository.ShopRepository;
import main.gov_map.Scraping.Dto.AdmActionHandlerInfo;
import main.gov_map.Scraping.Dto.AdmActionInfo;
import main.gov_map.Scraping.Dto.ShopDetails;
import main.gov_map.Scraping.Dto.ViolationInfo;
import main.gov_map.Scraping.Scraper.MainScraper;
import main.gov_map.Scraping.Scraper.RequestRegionURL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapingService {

    private final ShopRepository shopRepository;
    private final MainScraper mainScraper;

    /**
     * 지역 단위로 스크래핑후 db에 반영
     * @param url
     */
    public void scrapeAndJoinByRegion(RequestRegionURL url){

        List<ShopDetails> shopDetailsList = mainScraper.process(url);

        for (ShopDetails shopDetails : shopDetailsList) {
            AdmActionInfo admActionInfo = shopDetails.getAdmActionInfo();
            List<ViolationInfo> violationInfoList = shopDetails.getViolationInfoList();
            AdmActionHandlerInfo admActionHandlerInfo = shopDetails.getAdmActionHandlerInfo();

            List<ViolationDetails> violationDetailsList = new ArrayList<>();

            for (ViolationInfo violationInfo : violationInfoList) {
                ViolationDetails violationDetails = ViolationDetails.createViolationDetails(violationInfo, Long.parseLong(admActionInfo.getLicenseNum()));
                violationDetailsList.add(violationDetails);
            }

            Shop shop = Shop.createShop(admActionInfo, violationDetailsList, admActionHandlerInfo);
            shopRepository.save(shop);
        }

    }
}
