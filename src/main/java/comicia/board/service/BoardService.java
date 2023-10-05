package comicia.board.service;

import comicia.board.dto.BoardDTO;
import comicia.board.entity.BoardEntity;
import comicia.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Long save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.saveToEntity(boardDTO);
        Long savedId = boardRepository.save(boardEntity).getId();
        return savedId;
    }

    public Page<BoardDTO> findAll(int page) {
        int pageLimit = 5;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        return null;
    }



//        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // 맨 뒤 id는 엔티티의 이름과 같아야 함
//        List<BoardDTO> boardDTOList = new ArrayList<>();
//        for (BoardEntity boardEntity : boardEntityList) {
//            BoardDTO boardDTO = BoardDTO.saveToDTO(boardEntity);
//            boardDTOList.add(boardDTO);
//        }
        // boardEntity는 반복변수(entity 객체)

        // 요즘 스타일은 아래쪽
        // boardEntityList.forEach(boardEntity -> {
        // boardDTOList.add(BoardDTO.saveToDTO(boardEntity);
        // )}

//        return boardDTOList;
//    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            return BoardDTO.saveToDTO(boardEntity);
        } else {
            return null;
        }
    }


    /**
     * 서비스 클래스 매서드에서 @Transactinal 붙이는 경우
     * 1. jpql로 작성된 매서드 호출할 때
     * 2. 부모엔티티에서 자식엔티티를 바로 호출할 때
     * */
    @Transactional // jpql을 사용하면 얘를 붙여줘야 됨
    public void increaseHits(Long id) {
        boardRepository.increaseHits(id);
    }

    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntityUpdate = BoardEntity.saveToEntity(boardDTO);
        boardRepository.save(boardEntityUpdate);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
