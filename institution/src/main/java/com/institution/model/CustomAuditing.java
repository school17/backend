package com.institution.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomAuditing {

    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String lastModified;
}
