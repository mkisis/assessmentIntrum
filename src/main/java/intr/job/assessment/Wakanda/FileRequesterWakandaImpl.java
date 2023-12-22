package intr.job.assessment.Wakanda;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import intr.job.assessment.AbstractionLayer.FileRequester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.List;

@Slf4j
public class FileRequesterWakandaImpl implements FileRequester {

    @Override
    public <T> List<T> getListOfPayments(String localFilePath, Class<T> clazz) {
        try {
            File file = new File(localFilePath);
            if (!file.exists()) {
                log.info("NOT FOUND payout csv list in file path: " + localFilePath);
                return null;
            }
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new FileReader(localFilePath))
                    .withType(clazz)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .build();

            csvToBean.getCapturedExceptions().forEach((exception) ->
                    log.error("Error while reading csv file: " + exception.getMessage()));

            return csvToBean.parse();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while reading csv file: " + e.getMessage());
        }
        return null;
    }
}
