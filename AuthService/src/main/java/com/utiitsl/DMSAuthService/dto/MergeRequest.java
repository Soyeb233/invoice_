package com.utiitsl.DMSAuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MergeRequest {
    private String fileId;
    private String fileName;
}