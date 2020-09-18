package com.optus.counterapi.counter;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin
public class CounterController {
    private static final String MEDIA_TYPE = "application/json";
    private static final String MEDIA_TYPE_CSV = "text/csv";

    private final CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @RequestMapping(value = "/counter-api/search", method = RequestMethod.POST, produces = MEDIA_TYPE)
    @ApiOperation("Search paragraph and gives back count for reach text")
    @ResponseStatus(HttpStatus.OK)
    public CounterResponse searchTextCount(@Valid @RequestBody final CounterRequest counterRequest) {
        return this.counterService.searchTextCount(counterRequest);
    }

    @RequestMapping(value = "counter-api/top/{count}", method = RequestMethod.GET, produces = MEDIA_TYPE_CSV)
    @ApiOperation("Search paragraph and gives back text with top count")
    @ResponseStatus(HttpStatus.OK)
    public void searchTopText(@PathVariable final Integer count,
                              HttpServletResponse response) throws IOException {

        this.counterService.searchTopText(count, response);
    }

}
