package com.example.fitnessonline.services;

import com.example.fitnessonline.model.dto.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    public List<Image> getAllByProgramId(Integer id);

    Image findById(Integer id);

}
