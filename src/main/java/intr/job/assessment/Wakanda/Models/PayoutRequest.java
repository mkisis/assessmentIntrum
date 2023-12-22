package intr.job.assessment.Wakanda.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class PayoutRequest {
    private String companyIdentityNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;
    private double paymentAmount;
}
