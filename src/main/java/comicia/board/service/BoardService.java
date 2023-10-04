package comicia.board.service;

import comicia.board.dto.BoardDTO;
import comicia.board.entity.BoardEntity;
import comicia.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Long save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.saveToEntity(boardDTO);
        Long savedId = boardRepository.save(boardEntity).getId();
        return savedId;
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            BoardDTO boardDTO = BoardDTO.saveToDTO(boardEntity);
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }
}
