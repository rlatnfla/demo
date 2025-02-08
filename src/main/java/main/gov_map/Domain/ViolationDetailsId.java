package main.gov_map.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ViolationDetailsId implements Serializable {

    private Long licenseNum;

    @Column(nullable = false)
    private LocalDate violationDate;

    @Column(length = 500)
    private String violationDetails;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        ViolationDetailsId violationDetailsId = (ViolationDetailsId) obj;
        return Objects.equals(licenseNum, violationDetailsId.licenseNum) &&
                Objects.equals(violationDate, violationDetailsId.violationDate) &&
                Objects.equals(violationDetails, violationDetailsId.violationDetails);

    }

    @Override
    public int hashCode() {
        return Objects.hash(licenseNum, violationDate, violationDetails);
    }
}
