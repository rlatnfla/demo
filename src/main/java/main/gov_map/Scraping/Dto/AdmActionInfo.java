package main.gov_map.Scraping.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AdmActionInfo {
    private String admActionNum;
    private String businessType;
    private String licenseNum;
    private String shopName;
    private String representativeName;
    private String roadAddress;
    private String parcelAddress;
    private String penalty;
    private String confirmationDate;
    private String startDateString;
    private String endDateString;
    private String notice;
}
