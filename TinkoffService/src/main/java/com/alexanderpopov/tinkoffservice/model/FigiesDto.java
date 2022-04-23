package com.alexanderpopov.tinkoffservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FigiesDto {
    private List<String> figies;
}
