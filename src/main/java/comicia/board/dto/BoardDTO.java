package comicia.board.dto;

import comicia.board.entity.BoardEntity;
import comicia.board.util.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder // 객체를 만들 때 코드스타일 중 하나, 기본생성자가 무력화가 됨
@NoArgsConstructor // 얘를 쓰면 빌더가 무력화가 됨
@AllArgsConstructor // 그래서 마지막으로 얘도 추가를 해서 해결
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private int boardHits;
    //    private LocalDateTime createdAt;
    private String createdAt;

    private MultipartFile BoardFile;
    private int fileAttached;
    private String originalFileName;
    private String storedFileName;

    public static BoardDTO saveToDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        ////boardDTO.setCreatedAt(boardEntity.getCreatedAt());
//        String formattedDate = boardEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        boardDTO.setCreatedAt(formattedDate);
        boardDTO.setCreatedAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()));


        // 파일첨부 여부에 따라 파일이름 가져가기
        if (boardEntity.getFileAttached() == 1) {
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
            boardDTO.setFileAttached(1);
        } else {
            boardDTO.setFileAttached(0);
        }
        return boardDTO;


        // 빌더사용, setter가 없음
//        BoardDTO boardDTO = BoardDTO.builder()
//                .id(boardEntity.getId())
//                .boardWriter(boardEntity.getBoardWriter())
//                .boardTitle(boardEntity.getBoardTitle())
//                .boardPass(boardEntity.getBoardPass())
//                .boardContents(boardEntity.getBoardContents())
//                .boardHits(boardEntity.getBoardHits())
//                .createdAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()))
//                .build();
//        return boardDTO;

    }

}
