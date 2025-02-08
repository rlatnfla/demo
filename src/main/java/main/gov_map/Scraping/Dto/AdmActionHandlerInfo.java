package main.gov_map.Scraping.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AdmActionHandlerInfo {
    private String department;
    private String officerName;
    private String phoneNum;
    private String email;

    @Override
    public String toString() {
        return "AcmActionHandlerInfo{" +
                "department='" + department + '\'' +
                ", officerName='" + officerName + '\'' +
                ", phoneNumber='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
