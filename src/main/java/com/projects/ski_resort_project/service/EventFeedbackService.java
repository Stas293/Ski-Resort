package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.EventFeedbackReadDto;
import com.projects.ski_resort_project.dto.FeedbackCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.EventFeedback;
import com.projects.ski_resort_project.repository.jpa.EventFeedbackRepository;
import com.projects.ski_resort_project.repository.jpa.EventRepository;
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
public class EventFeedbackService {
    private final EventFeedbackRepository eventFeedbackRepository;
    private final Mapper<FeedbackCreateDto, EventFeedback> eventFeedbackCreateMapper;
    private final Mapper<EventFeedback, EventFeedbackReadDto> eventFeedbackMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    public void save(FeedbackCreateDto feedback, Long eventId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Feedback has errors: {}", bindingResult.getAllErrors());
            return;
        }
        EventFeedback resortFeedback = eventFeedbackCreateMapper.map(feedback);
        resortFeedback.setEvent(eventRepository.findById(eventId).orElseThrow());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        resortFeedback.setUser(userRepository.findByUsername(name).orElseThrow());
        eventFeedbackRepository.save(resortFeedback);
    }

    public Page<EventFeedbackReadDto> getFeedBacks(int limit, int page, String search, String sorting) {
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.valueOf(sorting.toUpperCase()), "dateCreated")
        );
        return eventFeedbackRepository.findAllByMessageContainingIgnoreCase(search, pageRequest)
                .map(eventFeedbackMapper::map);
    }

    public Page<EventFeedbackReadDto> getFeedBacks(Long id, int limit, int page, String search, String sorting) {
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.valueOf(sorting.toUpperCase()), "dateCreated")
        );
        return eventFeedbackRepository.findAllByMessageContainingIgnoreCaseAndEventId(search, id, pageRequest)
                .map(eventFeedbackMapper::map);
    }
}
