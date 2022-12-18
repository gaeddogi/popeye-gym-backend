package com.hy.popeyegym.utils;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class pagination {

    private int limit;
    private int currentPage;
    private int totalCount;
    private int barSize;
    private int startPage;
    private int endPage;

    @Builder
    public pagination(int limit, int currentPage, int totalCount, int barSize) {
        this.limit = limit;
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.barSize = barSize;

        this.startPage = (int) Math.floor(currentPage-1 / barSize)  * barSize + 1;
        this.endPage = startPage -1 + barSize;
    }
}
