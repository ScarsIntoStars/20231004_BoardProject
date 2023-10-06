package comicia.board.service;

import comicia.board.dto.BoardDTO;
import comicia.board.entity.BoardEntity;
import comicia.board.entity.BoardFileEntity;
import comicia.board.repository.BoardFileRepository;
import comicia.board.repository.BoardRepository;
import comicia.board.util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부파일 없음
            BoardEntity boardEntity = BoardEntity.saveToEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            return savedId;
        } else {
            // 첨부파일 있음
            BoardEntity boardEntity = BoardEntity.saveToEntityWithFile(boardDTO);
            // 게시글 저장처리 후 엔티티 가져옴 (아이디가 아니라 엔티티 전체를 가져옴)
            BoardEntity savedEntity = boardRepository.save(boardEntity);
            // 파일 이름 처리, 파일 로컬에 저장 등
            // DTO에 담긴 파일 꺼내기
            MultipartFile boardFile = boardDTO.getBoardFile();
            // 업로드한 파일 이름
            String originalFileName = boardFile.getOriginalFilename();
            // 저장용 파일 이름
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            // 저장경로+파일이름 준비
            String savePath = "D:\\springboot_img\\" + storedFileName;
            // 파일 폴더에 저장
            boardFile.transferTo(new File(savePath));
            // 파일 정보 board_file_table에 저장
            // 파일 정보 저장을 위한 BoardFileEntity 생성
            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(savedEntity, originalFileName, storedFileName);
            boardFileRepository.save(boardFileEntity);
            return savedEntity.getId();
        }

    }


    public Page<BoardDTO> findAll(int page, String type, String q) {
        page = page - 1; // db에서 page가 0부터 시작하기 때문
        int pageLimit = 5;
        Page<BoardEntity> boardEntities = null;

        if (q.equals("")) {
            // 전체목록 출력
            boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            if (type.equals("boardTitle")) {
                boardEntities = boardRepository.findByBoardTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            } else if (type.equals("boardWriter")) {
                boardEntities = boardRepository.findByBoardWriterContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
        }


        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .boardHits(boardEntity.getBoardHits())
                        .createdAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()))
                        .build());
        return boardList;
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
    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
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
     */
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
