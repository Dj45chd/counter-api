package com.optus.counterapi;

import com.optus.counterapi.counter.CounterRequest;
import com.optus.counterapi.counter.CounterResponse;
import com.optus.counterapi.counter.CounterService;
import com.optus.counterapi.entity.SampleParagraph;
import com.optus.counterapi.repository.SampleParagraphRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CounterServiceTests {

    @InjectMocks
    CounterService counterService;

    @Mock
    SampleParagraphRepository sampleParagraphRepository;

    @Test
    public void shouldFindTextCountInParagraph() {
        CounterRequest counterRequest = CounterRequest.builder().searchText(Arrays.asList("Paragraph", "Test")).build();
        when(sampleParagraphRepository.findAll()).thenReturn(getParagraph());
        CounterResponse result = counterService.searchTextCount(counterRequest);
        assertThat(result.getCounts().get("Paragraph")).isEqualTo(2);
    }

    @Test
    public void shouldFindTopTextInParagraph() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(sampleParagraphRepository.findAll()).thenReturn(getParagraph());
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        counterService.searchTopText(2, response);
        assertThat(response!=null);
        assertThat(stringWriter.toString().contains("Paragraph|2"));
    }

    private List<SampleParagraph> getParagraph() {
        SampleParagraph s1 = SampleParagraph.builder().id(Long.valueOf(1)).text("This Paragraph is a test Paragraph").build();

        return Arrays.asList(s1);
    }
}

