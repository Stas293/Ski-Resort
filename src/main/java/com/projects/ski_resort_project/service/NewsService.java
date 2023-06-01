package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.NewsCreateDto;
import com.projects.ski_resort_project.dto.NewsReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.News;
import com.projects.ski_resort_project.repository.mongo.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;
    private final ImageService imageService;
    private final Mapper<News, NewsReadDto> newsReadDtoMapper;
    private final Mapper<NewsCreateDto, News> newsCreateDtoMapper;

    public Page<NewsReadDto> getNews(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("date").descending()
        );
        return newsRepository.findAll(pageable)
                .map(newsReadDtoMapper::map);
    }

    public Optional<byte[]> findImage(String id) {
        return newsRepository.findById(id)
                .map(News::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

    public Page<NewsReadDto> getNewsPage(int limit, int page, String search, String sorting) {
        Pageable pageable = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.valueOf(sorting), "date")
        );
        return newsRepository.findAllByTitleIsLikeIgnoreCase(search, pageable)
                .map(newsReadDtoMapper::map);
    }

    public Optional<NewsReadDto> getNews(String id) {
        return newsRepository.findById(id)
                .map(newsReadDtoMapper::map);
    }

    @Transactional
    public NewsReadDto addNews(NewsCreateDto eventDto) {
        News event = newsCreateDtoMapper.map(eventDto);
        uploadImage(eventDto.image());
        event.setDate(LocalDate.now());
        newsRepository.save(event);
        return newsReadDtoMapper.map(event);
    }

    private void uploadImage(MultipartFile image) {
        Optional.ofNullable(image)
                .filter(file -> !file.isEmpty())
                .ifPresent(this::saveImage);
    }

    @SneakyThrows
    private void saveImage(MultipartFile multipartFile) {
        imageService.upload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
    }
}
