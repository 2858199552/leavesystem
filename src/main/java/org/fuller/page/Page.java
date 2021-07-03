package org.fuller.page;

import lombok.Data;

@Data
public class Page {
    public static final int PAGE_SIZE = 5;
    private int pageNo;
    private static int pageSize = PAGE_SIZE;
    private int pageRecords;
    private int totalPages;
}
