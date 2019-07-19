package com.dh.webservice.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class AjaxPageRequest implements Pageable {

    private int limit = 0;
    private int offset = 0;

    public AjaxPageRequest(int skip, int offset) {
        // 10, 15
        // 15, 20
        // 20, 25
        System.out.println("skip : " + skip);
        System.out.println("offset : " + offset);
        if (skip < 0)
            throw new IllegalArgumentException("Skip must not be less than zero!");

        if (offset < 0)
            throw new IllegalArgumentException("Offset must not be less than zero!");

        this.limit = offset;
        this.offset = skip;
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable first() {
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

}