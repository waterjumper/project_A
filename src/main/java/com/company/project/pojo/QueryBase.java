package com.company.project.pojo;

import lombok.Data;

@Data
public abstract class QueryBase {

    private int page = 1;

    private int limit = 10;
}
