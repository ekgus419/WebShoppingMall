/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @title AjaxPageRequest Entity를 정의한다.
 * @author cdh
 * @FileName AjaxPageRequest
 * @version https://stackoverflow.com/questions/25008472/pagination-in-spring-data-jpa-limit-and-offset/36365522
 *
 */
public class AjaxPageRequest implements Pageable {

    private int limit = 0;
    private int offset = 0;

    public AjaxPageRequest(int skip, int offset) {
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