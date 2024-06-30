package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.infra.repository.CommentRepository;
import com.petwellnes.petwellnes_backend.mapper.CommentMapper;
import com.petwellnes.petwellnes_backend.model.dto.postDto.CommentDTO;
import com.petwellnes.petwellnes_backend.model.entity.Comment;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public void comment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post).stream()
                .map(commentMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
