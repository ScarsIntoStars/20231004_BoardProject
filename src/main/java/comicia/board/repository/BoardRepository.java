package comicia.board.repository;

import comicia.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
