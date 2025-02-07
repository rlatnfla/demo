package main.gov_map.Service;

import lombok.RequiredArgsConstructor;
import main.gov_map.Repository.ShopRepository;
import main.gov_map.Scrapping.Scraper.MainScraper;
import main.gov_map.Scrapping.Scraper.RequestRegionURL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final MainScraper mainScraper;

    /**
     * 지역 단위로 스크래핑후 db에 반영,
     * ViolationDetails db 중복 처리 문제 해결후 작성 해야 함
     * @param url
     */
    public void exampleService(RequestRegionURL url){

    }
}
