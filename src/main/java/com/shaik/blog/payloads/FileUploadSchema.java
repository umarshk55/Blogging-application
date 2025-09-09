package com.shaik.blog.payloads;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;

public class FileUploadSchema {
    @Schema(type = "string", format = "binary", description = "Image file to upload")
    public MultipartFile image;
}
