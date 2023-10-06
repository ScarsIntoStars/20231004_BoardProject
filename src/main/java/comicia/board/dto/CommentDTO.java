package comicia.board.dto;

import comicia.board.entity.CommentEntity;
import comicia.board.util.UtilClass;
import lombok.Data;


@Data

public class CommentDTO  {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private String createdAt;

    public static CommentDTO toSaveDTO(CommentEntity commentEntity){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDTO.setCreatedAt(UtilClass.dateTimeFormat(commentEntity.getCreatedAt()));

        return commentDTO;
    }
}
