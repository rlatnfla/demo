package main.gov_map.Domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import main.gov_map.Scrapping.Dto.ViolationInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "violation_details")
@NoArgsConstructor
public class ViolationDetails {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(nullable = false)
    private LocalDate violationDate;

    @Column(length = 500)
    private String violationDetails;
    private String legalBasis;
    private String violationReason;
    private String violationLocation;

    //==생성자==//
    private ViolationDetails(ViolationInfo violationInfo) {
        this.violationDate = parseDate(violationInfo.getViolationDate());
        this.violationDetails = violationInfo.getViolationDetails();
        this.legalBasis = violationInfo.getLegalBasis();
        this.violationReason = violationInfo.getViolationReason();
        this.violationLocation = violationInfo.getViolationLocation();
    }

    //==생성 메서드==//
    public static ViolationDetails createViolationDetails(ViolationInfo violationInfo) {
        return new ViolationDetails(violationInfo);
    }

    //==연관관계 메서드==//
    public void setShop(Shop shop) {
        this.shop = shop;
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
