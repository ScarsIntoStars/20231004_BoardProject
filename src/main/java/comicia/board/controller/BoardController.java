package comicia.board.controller;

import comicia.board.dto.BoardDTO;
import comicia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
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
    public String boardSave(){
        return "/boardPages/boardSave";
    }

    @PostMapping("/board/save")
    public String save(BoardDTO boardDTO){
        Long savedId = boardService.save(boardDTO);
        System.out.println(savedId);
        return "index";
    }

    @GetMapping("/board/list")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardPages/boardList";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        System.out.println("컨트롤러!!! " + id);
        boardService.increaseHits(id); // 조회수증가
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/boardDetail";
    }

    @GetMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
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
