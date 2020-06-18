package com.institution.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomAuditing  {

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date lastModified;
}
