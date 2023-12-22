package intr.job.assessment.Wakanda;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import intr.job.assessment.AbstractionLayer.FileRequester;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class FileRequesterWakandaImpl implements FileRequester {

    @Override
    public <T> List<T> getListOfPayments(String localFolderPath, Class<T> clazz) {
        try {
            File file = getNewestTodaysFile(localFolderPath);
            if (file == null) {
                log.info("NOT FOUND payout csv list in folder path: " + localFolderPath);
                return null;
            }

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new FileReader(file))
                    .withType(clazz)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .build();

            csvToBean.getCapturedExceptions().forEach((exception) ->
                    log.error("Error while reading csv file: " + exception.getMessage()));

            return csvToBean.parse();
        } catch (Exception e) {
            log.error("Error while reading csv file: " + e.getMessage());
        }
        return null;
    }

    private File getNewestTodaysFile(String localFolderPath) {
        File folder = new File(localFolderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            log.error("Invalid folder path.");
            return null;
        }

        File[] csvFiles = folder.listFiles((dir, name) -> name.endsWith(".csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            log.info("No CSV files found in the specified folder.");
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        File selectedFile;
        HashMap<Date, File> fileMap = new HashMap<>();

        for (File csvFile : csvFiles) {
            String fileName = csvFile.getName();
            String[] parts = fileName.split("_");

            if (parts.length != 4 || !parts[3].endsWith(".csv"))
                continue;

            String fileDate = parts[2] + "_" + parts[3].replace(".csv", "");
            try {
                Date fileDateTime = dateFormat.parse(fileDate);
                fileMap.put(fileDateTime, csvFile);
            } catch (ParseException e) {
                log.error("Error while parsing file date: " + e.getMessage());
            }
        }

        if (fileMap.isEmpty()) {
            log.info("No CSV file found with date format 'yyyyMMdd_HHmmss'.");
            return null;
        }

        selectedFile = fileMap.keySet().stream().max(Date::compareTo).map(fileMap::get).orElse(null);
        if (selectedFile != null)
            log.info("Selected CSV file: " + selectedFile.getAbsolutePath());

        return selectedFile;
    }
}
