package ie.wtsanshou.eventbooking

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import ie.wtsanshou.eventbooking.model.Partners

import java.nio.file.Files
import java.nio.file.Paths
import java.text.DateFormat
import java.text.SimpleDateFormat

class Util {

    private static final String FILE_PATH = "src/test/resources/testData/partners.json"
    private static int YEAR_2019 = 2019 - 1900;
    private static int APRIL = 3
    private static final String DatePattern = "yyyy-MM-dd"

    public static final DateFormat dateFormatter = new SimpleDateFormat(DatePattern);
    public static Date DATE1 = new Date(YEAR_2019, APRIL, 8)
    public static Date DATE2 = new Date(YEAR_2019, APRIL, 9)
    public static Date DATE3 = new Date(YEAR_2019, APRIL, 10)
    public static Date DATE4 = new Date(YEAR_2019, APRIL, 11)
    public static Date DATE5 = new Date(YEAR_2019, APRIL, 12)

    static Partners getTestPartners() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.setDateFormat(dateFormatter)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)))
        return objectMapper.readValue(json, Partners.class)
    }
}
