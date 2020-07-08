package com.institution.model.test;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;



@Data
@AllArgsConstructor
public class Schedule {

    @NotEmpty
    private String subject;

    @NotEmpty
    private  String date;

    @NotEmpty
    private String time;

    public Schedule(){}
}
