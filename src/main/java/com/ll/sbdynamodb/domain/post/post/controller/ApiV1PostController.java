package com.ll.sbdynamodb.domain.post.post.controller;

import com.ll.sbdynamodb.domain.post.post.entity.DmPost;
import com.ll.sbdynamodb.domain.post.post.service.DmPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final DmPostService dmPostService;

    @GetMapping("/create")
    @ResponseBody
    public DmPost create(
            String subject
    ) {
        return dmPostService.write(subject);
    }

    @GetMapping("")
    @ResponseBody
    public List<DmPost> posts() {
        return dmPostService.findAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public DmPost post(@PathVariable String id) {
        return dmPostService.findById(id).orElse(null);
    }
}
