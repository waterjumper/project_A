package com.company.project.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ReviewListResponse
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListResponse {

    private Long id;

    private String review;

    private Integer star;

}
