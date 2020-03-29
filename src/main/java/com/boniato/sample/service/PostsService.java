package com.boniato.sample.service;

import com.boniato.sample.domain.posts.Posts;
import com.boniato.sample.domain.posts.PostsRepository;
import com.boniato.sample.web.dto.PostsListResponseDto;
import com.boniato.sample.web.dto.PostsResponseDto;
import com.boniato.sample.web.dto.PostsSaveRequestDto;
import com.boniato.sample.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                              .map(PostsListResponseDto::new)
                              .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
     Posts posts = postsRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));

     return new PostsResponseDto(posts);
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));

        postsRepository.delete(posts);
    }

}
