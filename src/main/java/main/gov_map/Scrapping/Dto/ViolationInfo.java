package main.gov_map.Scrapping.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ViolationInfo {
    private String violationDate;
    private String violationDetails;
    private String legalBasis;
    private String violationReason;
    private String violationLocation;

    @Override
    public String toString() {
        return "ViolationInfo{" +
                "violationDate='" + violationDate + '\'' +
                ", violationDetails='" + violationDetails + '\'' +
                ", legalBasis='" + legalBasis + '\'' +
                ", violationReason='" + violationReason + '\'' +
                ", violationLocation='" + violationLocation + '\'' +
                '}';
    }
}
