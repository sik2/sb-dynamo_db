package com.ll.sbdynamodb.domain.post.post.service;

import com.ll.sbdynamodb.domain.post.post.entity.DmPost;
import com.ll.sbdynamodb.domain.post.post.repository.DmPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DmPostService {
    private final DmPostRepository dmPostRepository;

    public DmPost write(String subject) {
        return dmPostRepository.save(
                DmPost
                        .builder()
                        .subject(subject)
                        .build()
        );
    }

    public List<DmPost> findAll() {
        return dmPostRepository.findAll();
    }

    public Optional<DmPost> findById(String id) {
        return dmPostRepository.findById(id);
    }
}