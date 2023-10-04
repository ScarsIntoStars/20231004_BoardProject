package comicia.board.dto;

import comicia.board.entity.BoardEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPassword;
    private String boardContents;
    private int boardHits;
    private LocalDateTime createdAt;



    public static BoardDTO saveToDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPassword(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setCreatedAt(boardEntity.getCreatedAt());
        return boardDTO;
    }

}
