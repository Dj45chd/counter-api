package com.optus.counterapi.counter;

import com.optus.counterapi.common.CounterConstants;
import com.optus.counterapi.entity.SampleParagraph;
import com.optus.counterapi.repository.SampleParagraphRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class CounterService {
    private static Logger log = LoggerFactory.getLogger(CounterService.class);
    private final SampleParagraphRepository sampleParagraphRepository;
    @Autowired
    public CounterService(SampleParagraphRepository sampleParagraphRepository) {
        this.sampleParagraphRepository = sampleParagraphRepository;
    }

    /**
     * Below function searches for list of text in a paragraph and returns back the count for each text
     * @param counterRequest
     * @return CounterResponse
     */
    public CounterResponse searchTextCount(CounterRequest counterRequest) {
        CounterResponse counterResponse = new CounterResponse();

        try {
            /**
            * Fetches the paragraph from the database and returns map for text and count
             */
            Map<String, Integer> textMap = getTextMapFromDb();
            /**
             * Compares the list of search text with the text in the map and returns the count for each
             * Checks for the text in a case insensitive manner
             */
            counterResponse.setCounts(counterRequest
                    .getSearchText().stream().collect(toMap(s -> s, s -> textMap.containsKey(s)
                            ? textMap.get(s)
                            : textMap.containsKey(s.toLowerCase())
                            ? textMap.get(s.toLowerCase())
                            : Integer.valueOf(0), (a, b) -> b, LinkedHashMap::new)));

            return counterResponse;
        } catch (Exception e) {
            log.error("Error while searching text in paragraph", e.getMessage());
            return counterResponse;
        }
    }

    /**
     * Below function takes count as input and searches for top {count} text
     * @param count
     * @param response
     * @throws IOException
     */
    public void searchTopText(Integer count, HttpServletResponse response) throws IOException {
        Map<String, Integer> textMapFromDbSorted;
        CSVPrinter csvPrinter = null;
        try {
            /**
             * Fetches the paragraph from the database and returns map for text and count
             */
            Map<String, Integer> textMapFromDb = getTextMapFromDb();
            /**
             * Sorting hashmap and getting the top text as per count
             */
            textMapFromDbSorted = textMapFromDb
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                                                     .limit(count)
                                                     .collect(toMap(Map.Entry::getKey,
                                                             Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            /**
             * converting sorted hashmap to csv response
             */
            response.setContentType(CounterConstants.CONTENT_TYPE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + CounterConstants.FILE_NAME + "\"");
            csvPrinter = new CSVPrinter(response.getWriter(),
                    CSVFormat.newFormat(CounterConstants.PIPE));

            for (Map.Entry<String, Integer> entry : textMapFromDbSorted.entrySet())
                csvPrinter.printRecord(entry.getKey(), entry.getValue() + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvPrinter != null)
                csvPrinter.close();
        }
    }

    /**
     * Fetches the all the paragraphs from Database and joins them.
     * Then, converts the paragraph into hashmap of words and count
     * @return Hashmap
     */
    private Map<String, Integer> getTextMapFromDb() {
        List<SampleParagraph> sampleParagraph = sampleParagraphRepository.findAll();
        String string = sampleParagraph.stream().map(SampleParagraph::getText).collect(Collectors.joining(" "));;
        return Pattern.compile("\\W+")
                      .splitAsStream(string)
                      .collect(Collectors.groupingBy(String::toString,
                              Collectors.summingInt(s -> 1)));
    }
}

