package comicia.board.controller;

import comicia.board.dto.BoardDTO;
import comicia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board/save")
    public String boardSave() {
        return "/boardPages/boardSave";
    }

    @PostMapping("/board/save")
    public String save(BoardDTO boardDTO) {
        Long savedId = boardService.save(boardDTO);
        System.out.println(savedId);
        return "index";
    }

    /*
        rest api
        /board/10 => 10번 글
        /board/20 => 20번 글
        /member/5 => 5번 회원

        3페이지에 있는 15번 글
        /board/3/15 => 페이지값을 경로를 넣는 것은 부합함(오류가 새기거나 문제가 생기지는 않는다)
        /board/15?page=3 => 기존대로 쿼리스트링 방식을 씀
    * */

    @GetMapping("/board")
    public String findAll(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        Page<BoardDTO> boardDTOList = boardService.findAll(page); // 리스트타입이 아니라 페이지타입
        model.addAttribute("boardList", boardDTOList);
        // 목록 하단에 보여줄 페이지 번호
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1)) < boardDTOList.getTotalPages() ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardPages/boardList";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        boardService.increaseHits(id); // 조회수증가
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/boardDetail";
    }

    @GetMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        Long id = boardDTO.getId();
        BoardDTO boardOne = boardService.findById(id);
        model.addAttribute("boardOne", boardOne);
        System.out.println(boardOne);
        return "boardPages/boardUpdate";
    }

    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("컨트롤러에 값이 잘 들어오는가?" + boardDTO);
        boardService.update(boardDTO);
        return "boardPages/boardList";
    }

    // 주소료 요청
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect/board";
    }

    // axiow로 delete요청
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByAxios(@PathVariable("id") Long id) {
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 악시오스로 업데이트 하는 방법. 놓침
//    @PutMapping("/{id}")
//    public ResponseEntity update(@RequestBody BoardDTO boardDTO) {
//        boardService.update(boardDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
