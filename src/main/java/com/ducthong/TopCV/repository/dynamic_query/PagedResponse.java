package com.ducthong.TopCV.repository.dynamic_query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;

    public static int calTotalPage(int totalElements, int pageSize) {
        return (int) Math.ceil((double) totalElements / pageSize);
    }
}
