package com.drumer32.explorewithme.storage;

import com.drumer32.explorewithme.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentStorage extends JpaRepository<Comment, Integer> {
}
