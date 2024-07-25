package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.exceptions.NotFoundException;
import com.example.fitnessonline.model.dto.Image;
import com.example.fitnessonline.repositories.ImageEntityRepository;
import com.example.fitnessonline.services.ImageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final ModelMapper modelMapper;
    private final ImageEntityRepository imageEntityRepository;
    public ImageServiceImpl(ModelMapper modelMapper, ImageEntityRepository imageEntityRepository) {
        this.modelMapper = modelMapper;
        this.imageEntityRepository = imageEntityRepository;
    }

    @Override
    public List<Image> getAllByProgramId(Integer id) {
        return imageEntityRepository.getAllByProgram_Id(id).stream().map(a->modelMapper.map(a, Image.class)).collect(Collectors.toList());

    }


    @Override
    public Image findById(Integer id) {
        return modelMapper.map(imageEntityRepository.findById(id).orElseThrow(NotFoundException::new), Image.class);
    }


}
