package intr.job.assessment.Wakanda.Models;


import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import java.util.Date;

@Data
public class PaymentInfo {
    @CsvBindByPosition(position = 0)
    private String companyName;

    @CsvBindByPosition(position = 1)
    private String companyTaxNumber;

    @CsvBindByPosition(position = 2)
    private String status;

    @CsvBindByPosition(position = 3)
    @CsvDate("yyyy-MM-dd")
    private Date paymentDate;

    @CsvBindByPosition(position = 4, format = "0,00")
    private String amount;
}
