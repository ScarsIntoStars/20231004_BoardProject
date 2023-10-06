package comicia.board.entity;

import comicia.board.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.metamodel.IdentifiableType;
import javax.xml.stream.events.Comment;

@Entity
@Setter
@Getter
@Table(name="comment_table")
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String CommentWriter;

    @Column(length = 200, nullable = false)
    private String CommentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;


    public static CommentEntity saveToEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }


}
