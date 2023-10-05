package comicia.board.repository;

import comicia.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    /*
     update board_table set board_hits=board,hits+1 where id=?
     spql(java persistence query language) 쿼리문을 직접 쓰고자 할 때 사용함
    * */
    @Modifying // inset, update, delete를 jpql로 쓰고자 할 때 사용
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    // entity에 쓴 내용 기준으로 쿼리문 씀
    // 테이블 이름이 아니라 entity이름을 씀
    // boardEntity를 b라는 약칭으로 썼음(바꿀 수 있음)
    // 커스텀된 쿼리문을 쓰는 것을 추천함


//    @Query(value = "update board_table set board_hits=board,hits+1 where id=id", nativeQuery = true)
    // 실제 쿼리문을 씀. 그리고 뒤에 네이티브쿼리를 써줘야 됨
    void increaseHits(@Param("id")Long id);

    // select * from board_table where board_title=?    , where => findBy
    List<BoardEntity> findByBoardTitle(String boardTitle);

    // select * from board_table where board_title=? order by id desc
    List<BoardEntity> findByBoardTitleOrderById(String boardTitle);

    // select * from board_table where board_title like '%q%'
    List<BoardEntity> findByBoardTitleContaining(String q); // like => containing

    // select * from board_table where board_writer like '%q%'
    List<BoardEntity> findByBoardWriterContaining(String q);

    // select * from board_table where board_title like '%q%' order by id desc
    List<BoardEntity> findByBoardTitleContainingOrderById(String q);

    // 제목으로 검색한 결과를 page 객체로 리턴
    Page<BoardEntity> findByBoardTitleContaining(String q, Pageable pageable); // 페이지에이블 첫번째 꺼 해야 됨

    // 작성자로 검색한 결과를 page객체로 리턴
    Page<BoardEntity> findByBoardWriterContaining(String q, Pageable pageable);

    // 제목 또는 작성자에 검색어라 포함된 결과 페이징
    // select * from board_table where board_title like '%q%' or board_writer like '%q%' ordrt by id desc // %q%가 값이 같더라도 두 번 써줘야 됨
    Page<BoardEntity> findByBoardTitleContainingOrBoardWriterContaining(String q1, String q2, Pageable pageable);


}
