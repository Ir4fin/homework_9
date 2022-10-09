package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import domain.Hotel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.openqa.selenium.io.Zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckFilesInZip {

    ClassLoader classLoader = CheckFilesInZip.class.getClassLoader();
    static final String zipName = "test.zip";


    @Test
    void pdfInZipTest() throws Exception {
        String pdfFileName = "628741.pdf",
                pdfText = "Страховой полис";

        try (ZipFile zf = new ZipFile(classLoader.getResource(zipName).getPath());
             InputStream entryStream = zf.getInputStream(zf.getEntry(pdfFileName))) {
            PDF pdf = new PDF(entryStream);
            assertThat(pdf.text).contains(pdfText);
        }
    }

    @Test
    void csvInZipTest() throws Exception {
        String csvFileName = "contracts.csv";
        String[] csvColumnNames = new String[]{"number", "car", "driver"},
                csvValuesOne = new String[]{"1", "Ferrari", "Leclerc"},
                csvValuesTwo = new String[]{"2", "Mercedes", "Hamilton"},
                csvValuesThree = new String[]{"3", "Renault", "Alonso"};

        try (ZipFile zf = new ZipFile(classLoader.getResource(zipName).getPath());
             InputStream entryStream = zf.getInputStream(zf.getEntry(csvFileName));
             CSVReader csvReader = new CSVReader(new InputStreamReader(entryStream))) {
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(
                    csvColumnNames,
                    csvValuesOne,
                    csvValuesTwo,
                    csvValuesThree);
        }

    }

    @Test
    void xlsInZipTest() throws Exception {
        String xlsFileName = "calc.xlsx",
                xlsText = "Подразделение, заключившее договор";

        try (ZipFile zf = new ZipFile(classLoader.getResource(zipName).getPath());
             InputStream entryStream = zf.getInputStream(zf.getEntry(xlsFileName))) {
            XLS xls = new XLS(entryStream);
            assertThat(xls.excel.getSheetAt(0)
                    .getRow(4)
                    .getCell(0).getStringCellValue()).isEqualTo(xlsText);

        }

    }

    @Test
    void JacksonJsonTest() throws Exception {

        InputStream is = classLoader.getResourceAsStream("hotel.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Hotel hotel = objectMapper.readValue(is, Hotel.class);

        assertThat(hotel.getHotelName()).isEqualTo("Tenet");


    }
}




