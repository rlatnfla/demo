package main.gov_map.Scraping.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShopDetailsRequestParam {
    private final String dispositionCode;

    private final String orgCode;

    private final String year;

    private final String admmSno;

    private final String prefixName;

    public String getRequestBody(){
        return "method=selectAdmmDtl&context=NTIS&jndinm=CafMwWdOpenEJB&methodnm=selectAdmmDtl&" +
                "dispo_raise_wrk_code="+dispositionCode+
                "&org_and_team_code="+orgCode+
                "&yy="+year+
                "&admm_sno="+admmSno+
                "&prefix_name="+prefixName+
                "&strt_date=&end_date=&pageIndex=&pageSize=&category=&field=&keyword=&pageSize1=&strt_yy=&strt_mm=&strt_dd=&end_yy=&end_mm=&end_dd=&pageSize2=\n";
    }

}
