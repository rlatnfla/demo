package main.gov_map.Domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import main.gov_map.Scraping.Dto.ViolationInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "violation_details")
@NoArgsConstructor
public class ViolationDetails {

    @EmbeddedId
    private ViolationDetailsId id;

    @ManyToOne
    @MapsId("licenseNum")
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String legalBasis;
    private String violationReason;
    private String violationLocation;

    //==생성자==//
    private ViolationDetails(ViolationInfo violationInfo, Long licenseNum) {
        this.id = new ViolationDetailsId(licenseNum, parseDate(violationInfo.getViolationDate()), violationInfo.getViolationDetails());
        this.legalBasis = violationInfo.getLegalBasis();
        this.violationReason = violationInfo.getViolationReason();
        this.violationLocation = violationInfo.getViolationLocation();
    }

    //==생성 메서드==//
    public static ViolationDetails createViolationDetails(ViolationInfo violationInfo, Long licenseNum) {
        return new ViolationDetails(violationInfo, licenseNum);
    }

    //==연관관계 메서드==//
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    //==getter 메서드==//
    public LocalDate getViolationDate(){
        return id.getViolationDate();
    }

    public String getViolationDetails(){
        return id.getViolationDetails();
    }

    //==팩토리 메서드==//
    private LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("날짜 변환 실패: " + dateString);
            return null;
        }
    }

}
