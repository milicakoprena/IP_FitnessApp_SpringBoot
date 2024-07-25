package com.example.fitnessonline.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageRequest {
    @NotBlank
    private String imageUrl;

    private Integer programId;
}
