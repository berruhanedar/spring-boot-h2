package com.berru.app.springbooth2.dto;

import lombok.Data;

import java.util.List;

@Data
public class MusicPaginationResponse {
    private List<MusicDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
