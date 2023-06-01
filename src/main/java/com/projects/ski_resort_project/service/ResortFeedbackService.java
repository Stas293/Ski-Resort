package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.FeedbackCreateDto;
import com.projects.ski_resort_project.dto.ResortFeedbackReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.ResortFeedback;
import com.projects.ski_resort_project.repository.jpa.ResortFeedbackRepository;
import com.projects.ski_resort_project.repository.jpa.ResortRepository;
import com.projects.ski_resort_project.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResortFeedbackService {
    private final Mapper<ResortFeedback, ResortFeedbackReadDto> resortFeedbackMapper;
    private final Mapper<FeedbackCreateDto, ResortFeedback> resortFeedbackCreateMapper;
    private final ResortFeedbackRepository resortFeedbackRepository;
    private final UserRepository userRepository;
    private final ResortRepository resortRepository;
    
    public Page<ResortFeedbackReadDto> getFeedBacks(int limit, int page, String search, String sorting) {
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.valueOf(sorting.toUpperCase()), "dateCreated")
        );
        return resortFeedbackRepository.findAllByMessageContainingIgnoreCase(search, pageRequest)
                .map(resortFeedbackMapper::map);
    }

    @Transactional
    public void save(FeedbackCreateDto feedback, Long resortId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Feedback has errors: {}", bindingResult.getAllErrors());
            return;
        }
        ResortFeedback resortFeedback = resortFeedbackCreateMapper.map(feedback);
        resortFeedback.setResort(resortRepository.findById(resortId).orElseThrow());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        resortFeedback.setUser(userRepository.findByUsername(name).orElseThrow());
        resortFeedbackRepository.save(resortFeedback);
    }

    public Page<ResortFeedbackReadDto> getFeedBacks(Long id, int limit, int page, String search, String sorting) {
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.valueOf(sorting.toUpperCase()), "dateCreated")
        );
        return resortFeedbackRepository.findAllByMessageContainingIgnoreCaseAndResortId(search, id, pageRequest)
                .map(resortFeedbackMapper::map);
    }
}
