package main.gov_map.Scrapping.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ShopDetails {
    private AdmActionInfo admActionInfo;
    private List<ViolationInfo> violationInfoList;
    private AdmActionHandlerInfo admActionHandlerInfo;

    @Override
    public String toString() {
        return "ShopDetails{" +
                "admActionInfo=" + admActionInfo +
                ", violationInfoList=" + violationInfoList +
                ", admActionHandlerInfo=" + admActionHandlerInfo +
                '}';
    }
}
