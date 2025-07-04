package com.javaproject.springshop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.javaproject.springshop.dto.ImageDto;
import com.javaproject.springshop.model.Image;

public interface IImageService {
    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);

    void updateImage(MultipartFile file, Long idImage);
    

}
