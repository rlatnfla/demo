package main.gov_map.Scrapping.Scraper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RequestRegionURL {
    JONGNO("https://jongno.eminwon.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do","서울특별시 종로구"),
    DONGJAK("https://dongjak.eminwon.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 동작구"),
    JUNGGU("https://eminwon.junggu.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 중구"),
    YONGSAN("https://eminwon.yongsan.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 용산구"),
    SEONGDONG("https://eminwon.sd.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 성동구"),
    GWANGJIN("https://gwangjin.eminwon.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 광진구"),
    DONGDAEMOON("https://eminwon.ddm.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 동대문구"),
    JUNGNANG("https://eminwon.jungnang.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 중랑구"),
    SEONGBUK("https://eminwon.sb.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 성북구"),
    GANGBUK("https://eminwon.gangbuk.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 강북구"),
    DOBONG("https://dobong.eminwon.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 도봉구"),
    NOWON("https://eminwon.nowon.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 노원구"),
    EUNPYEONG("https://eminwon.ep.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 은평구"),
    SEODAEMOON("https://eminwon.sdm.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 서대문구"),
    MAPO("https://eminwon.mapo.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 마포구"),
    YANGCHEON("https://eminwon.yangcheon.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 양천구"),
    GANGSEO("https://eminwon.gangseo.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 강서구"),
    GURO("https://eminwon.guro.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 구로구"),
    GEUMCHEON("https://eminwon.geumcheon.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 금천구"),
    YEONGDEUNGPO("https://eminwon.ydp.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 영등포구"),
    GWANAK("https://eminwon.gwanak.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 관악구"),
    SEOCHO("https://eminwon.seocho.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 서초구"),
    GANGNAM("https://gangnam.eminwon.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 강남구"),
    SONGPA("https://songpa.eminwon.seoul.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 송파구"),
    GANGDONG("https://eminwon.gangdong.go.kr/emwp/gov/mogaha/ntis/web/caf/mwwd/action/CafMwWdOpenAction.do", "서울특별시 강동구"),;

    private final String url;
    private final String regionName;
}
