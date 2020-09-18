package com.optus.counterapi.counter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@ApiModel
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CounterResponse {

    @ApiModelProperty(position = 1,required = true)
    private Map<String, Integer> counts;

}
