package main.gov_map.Scraping.Parser;

import lombok.extern.slf4j.Slf4j;
import main.gov_map.Scraping.Dto.AdmActionHandlerInfo;
import main.gov_map.Scraping.Dto.AdmActionInfo;
import main.gov_map.Scraping.Dto.ShopDetails;
import main.gov_map.Scraping.Dto.ViolationInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("shopDetailsParser")
@Slf4j
public class ShopDetailsParser {

    public ShopDetails parse(String responseBody) {
        Document doc = Jsoup.parse(responseBody);
        Elements table = doc.select("table");
        Element admActionTableBody = table.get(0).selectFirst("tbody");
        Elements violationInfoTable = new Elements(table.subList(1, table.size() - 1));
        Element admActionHandlerTableBody = table.get(table.size() - 1).selectFirst("tbody");

        AdmActionInfo admActionInfo = parseAdmActionInfo(admActionTableBody);
        List<ViolationInfo> violationInfoList = parseViolationInfo(violationInfoTable);
        AdmActionHandlerInfo admActionHandlerInfo = parseAdmActionHandlerInfo(admActionHandlerTableBody);

        return new ShopDetails(admActionInfo, violationInfoList, admActionHandlerInfo);
    }

    private AdmActionInfo parseAdmActionInfo(Element admActionTableBody) {
        Elements admActionTr = admActionTableBody.select("tr");

        String admActionNum = admActionTr.get(0).selectFirst("th:contains(행정처분번호) + td").text();
        String businessType = admActionTr.get(1).selectFirst("th:contains(업종명) + td").text();
        String licenseNum = admActionTr.get(1).selectFirst("th:contains(인허가번호) + td").text();
        String shopName = admActionTr.get(2).selectFirst("th:contains(업소명) + td").text();
        String representativeName = admActionTr.get(2).selectFirst("th:contains(대표자명) + td").text();

        //==도로명 주소, 지번 주소 파싱==//
        String address = admActionTr.get(3).selectFirst("th:contains(소재지) + td").text();
        Pattern roadPattern = Pattern.compile("도로명\\s*:\\s*(.*)");
        Pattern parcelPattern = Pattern.compile("지번명\\s*:\\s*(.*)");

        Matcher roadMatcher = roadPattern.matcher(address);
        Matcher parcelMatcher = parcelPattern.matcher(address);

        String roadAddress = roadMatcher.find() ? roadMatcher.group(1).trim() : "도로명 주소 없음";
        String parcelAddress = parcelMatcher.find() ? parcelMatcher.group(1).trim() : "지번 주소 없음";
        //==end==//

        Element admAction = admActionTr.get(4).selectFirst("th:contains(행정처분) + td");

        String fullText = admAction.html();
        String[] lines = fullText.split("<br>");

        String penalty = "", confirmationDate = "", period = "", notice = "";

        for (String line : lines) {
            line = line.trim(); // 앞뒤 공백 제거
            if (line.startsWith("처분사항")) {
                penalty = line.replace("처분사항 :", "").trim();
            } else if (line.startsWith("처분확정일자")) {
                confirmationDate = line.replace("처분확정일자 :", "").trim();
            } else if (line.startsWith("처분기간")) {
                period = line.replace("처분기간 :", "").trim();
            } else if (line.startsWith("안내사항")) {
                notice = line.replace("안내사항 :", "").trim();
            }
        }

        //==시작 날짜, 종료 날짜 파싱==//
        String startDateString = "";
        String endDateString = "";

        if (!period.isEmpty()) {
            String periodString = period.replace("처분기간 :", "").trim();

            // "~"만 있는 경우는 둘 다 공백 유지
            if (!periodString.equals("~")) {
                String[] dates = periodString.split("~");

                if (dates.length == 2) {
                    startDateString = dates[0].trim();
                    endDateString = dates[1].trim();
                } else if (dates.length == 1) {
                    if (periodString.endsWith("~")) { // "YYYY-MM-DD ~" 형태 (종료일 없음)
                        startDateString = dates[0].trim();
                    } else if (periodString.startsWith("~")) { // "~ YYYY-MM-DD" 형태 (시작일 없음)
                        endDateString = dates[0].trim();
                    }
                }
            }
        }
        //==end==//

        return new AdmActionInfo(admActionNum, businessType, licenseNum, shopName, representativeName, roadAddress, parcelAddress, penalty, confirmationDate, startDateString, endDateString, notice);
    }

    private List<ViolationInfo> parseViolationInfo(Elements violationInfoTable){

        List<ViolationInfo> violationInfoList = new ArrayList<>();

        for (Element violationInfo : violationInfoTable) {
            Element violationInfoTableBody = violationInfo.selectFirst("tbody");

            Element violationRow = violationInfoTableBody.selectFirst("tr td");

            String[] lines = violationRow.html().split("<br>");

            String violationDate = "", violationDetails = "", legalBasis = "", violationReason = "", violationLocation = "";

            for (String line : lines) {
                line = line.trim(); // 앞뒤 공백 제거
                if (line.startsWith("위반일자")) {
                    violationDate = line.replace("위반일자 :", "").trim();
                } else if (line.startsWith("위반사항")) {
                    violationDetails = line.replace("위반사항 :", "").trim();
                } else if (line.startsWith("법적근거")) {
                    legalBasis = line.replace("법적근거 :", "").trim();
                } else if (line.startsWith("위반사유")) {
                    violationReason = line.replace("위반사유 :", "").trim();
                } else if (line.startsWith("위반장소")) {
                    violationLocation = line.replace("위반장소 :", "").trim();
                }
            }

            violationInfoList.add(new ViolationInfo(violationDate, violationDetails, legalBasis, violationReason, violationLocation));
        }

        return violationInfoList;
    }

    private AdmActionHandlerInfo parseAdmActionHandlerInfo(Element admActionHandlerTableBody) {

        try {
            Elements admActionHandlerInfoTr = admActionHandlerTableBody.select("tr");

            Element departmentElement = admActionHandlerInfoTr.get(0).selectFirst("th:contains(처리부서) + td");
            Element officerElement = admActionHandlerInfoTr.get(0).selectFirst("th:contains(담당자) + td");
            Element phoneElement = admActionHandlerInfoTr.get(1).selectFirst("th:contains(전화번호) + td");
            Element emailElement = admActionHandlerInfoTr.get(1).selectFirst("th:contains(이메일) + td");

            String department = (departmentElement != null) ? departmentElement.text() : "N/A";
            String officerName = (officerElement != null) ? officerElement.text() : "N/A";
            String phoneNum = (phoneElement != null) ? phoneElement.text() : "N/A";
            String email = (emailElement != null) ? emailElement.text() : "N/A";

            return new AdmActionHandlerInfo(department, officerName, phoneNum, email);
        } catch (Exception e) {
            log.warn("오류: {}", e.getMessage());
            return new AdmActionHandlerInfo("N/A", "N/A", "N/A", "N/A");
        }

    }
}
