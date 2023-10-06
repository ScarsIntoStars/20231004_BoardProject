package comicia.board.repository;

import comicia.board.entity.BoardEntity;
import comicia.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardEntity(BoardEntity boardEntity);
}

