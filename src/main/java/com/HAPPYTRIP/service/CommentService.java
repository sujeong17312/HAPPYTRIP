package com.HAPPYTRIP.service;

import com.HAPPYTRIP.domain.Board;
import com.HAPPYTRIP.domain.Comment;
import com.HAPPYTRIP.domain.Member;
import com.HAPPYTRIP.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    //조회
    public Comment getComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new RuntimeException("comment not found");
        }
    }

    //추가
    public Comment create(Board board, String content, Member member) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBoard(board);
        comment.setMemberId(member);
        commentRepository.save(comment);
        return comment;
    }

    //수정
    public void update(Long id, String content) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment c = optionalComment.get();
            c.setContent(content);
            commentRepository.save(c);
        } else {
            throw new EntityNotFoundException("댓글을 찾을 수 없습니다.");
        }
    }

    //삭제
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}