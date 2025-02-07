package main.gov_map.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.gov_map.Scrapping.Dto.AdmActionHandlerInfo;
import main.gov_map.Scrapping.Dto.AdmActionInfo;
import main.gov_map.Scrapping.Dto.ViolationInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop")
@Getter
@NoArgsConstructor
public class Shop {

    // 행정처분 정보
    @Id
    private String licenseNum;      // 인허가 번호

    @Column(nullable = false)
    private String admActionNum;    // 행정 처분 번호

    @Column(nullable = false)
    private String businessType;    // 업종명

    @Column(nullable = false)
    private String shopName;        // 업소명

    private String representativeName;  // 대표자 명

    @Column(nullable = false)
    private String parcelAddress;

    @Column(nullable = false)
    private String roadAddress;

    private String penalty;

    @Column(nullable = false)
    private LocalDate confirmationDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private String notice;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ViolationDetails> violationDetailsList;

    // 행정처분 담당자
    @Column(nullable = false)
    private String department;

    private String officerName;
    private String phoneNum;
    private String email;

    //==생성자==//
    private Shop(AdmActionInfo admActionInfo, AdmActionHandlerInfo admActionHandlerInfo) {
        this.licenseNum = admActionInfo.getLicenseNum();
        this.admActionNum = admActionInfo.getAdmActionNum();
        this.businessType = admActionInfo.getBusinessType();
        this.shopName = admActionInfo.getShopName();
        this.representativeName = admActionInfo.getRepresentativeName();
        this.parcelAddress = admActionInfo.getParcelAddress();
        this.roadAddress = admActionInfo.getRoadAddress();
        this.penalty = admActionInfo.getPenalty();
        this.confirmationDate = parseDate(admActionInfo.getConfirmationDate());
        this.startDate = parseDate(admActionInfo.getStartDateString());
        this.endDate = parseDate(admActionInfo.getEndDateString());
        this.notice = admActionInfo.getNotice();
        this.department = admActionHandlerInfo.getDepartment();
        this.officerName = admActionHandlerInfo.getOfficerName();
        this.phoneNum = admActionHandlerInfo.getPhoneNum();
        this.email = admActionHandlerInfo.getEmail();
        this.violationDetailsList = new ArrayList<>();
    }

    //==생성 메서드==//
    public static Shop createShop(AdmActionInfo admActionInfo, List<ViolationDetails> violationDetailsList, AdmActionHandlerInfo admActionHandlerInfo){
        Shop shop = new Shop(admActionInfo, admActionHandlerInfo);
        for (ViolationDetails violationDetails : violationDetailsList) {
            shop.addViolationDetails(violationDetails);
        }
        return shop;
    }

    //==연관 관계 메서드==//
    private void addViolationDetails(ViolationDetails violationDetails) {
        violationDetailsList.add(violationDetails);
        violationDetails.setShop(this);
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
