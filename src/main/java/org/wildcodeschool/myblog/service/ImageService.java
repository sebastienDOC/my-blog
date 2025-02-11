package org.wildcodeschool.myblog.service;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myblog.dto.ImageDTO;
import org.wildcodeschool.myblog.mapper.ImageMapper;
import org.wildcodeschool.myblog.model.Image;
import org.wildcodeschool.myblog.repository.ImageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream()
                .map(imageMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ImageDTO> getImageById(Long id) {
        return imageRepository.findById(id)
                .map(imageMapper::convertToDTO);
    }

    public ImageDTO createImage(ImageDTO imageDTO) {
        Image image = imageMapper.convertToEntity(imageDTO);
        Image savedImage = imageRepository.save(image);
        return imageMapper.convertToDTO(savedImage);
    }

    public Optional<ImageDTO> updateImage(Long id, ImageDTO imageDTO) {
        return imageRepository.findById(id).map(existingImage -> {
            existingImage.setUrl(imageDTO.getUrl());
            Image updatedImage = imageRepository.save(existingImage);
            return imageMapper.convertToDTO(updatedImage);
        });
    }

    public boolean deleteImage(Long id) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
