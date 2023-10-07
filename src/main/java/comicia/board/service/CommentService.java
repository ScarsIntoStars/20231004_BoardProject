package comicia.board.service;

import comicia.board.dto.CommentDTO;
import comicia.board.entity.BoardEntity;
import comicia.board.entity.CommentEntity;
import comicia.board.repository.BoardRepository;
import comicia.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

//    public void commentSave(CommentDTO commentDTO) {
//        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException());
//        CommentEntity commentEntity = CommentEntity.saveToEntity(commentDTO, boardEntity);
//
//        commentRepository.save(commentEntity).getId();

    public Long save(CommentDTO commentDTO) {
        System.out.println("commentService : " + commentDTO);
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.saveToEntity(commentDTO, boardEntity);
        return commentRepository.save(commentEntity).getId();
    }

    public List<CommentDTO> findByBoardId(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());
        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntity(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList) {
            commentDTOList.add(CommentDTO.toSaveDTO(commentEntity));
        }
        return commentDTOList;
    }
}
