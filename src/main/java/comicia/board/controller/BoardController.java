package comicia.board.controller;

import comicia.board.dto.BoardDTO;
import comicia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        List boardDTOList = boardService.findAll();
        model.addAttribute("boardDTOList", boardDTOList);
        return "boardPages/boardList";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Long id) {
        System.out.println("넘어온 id : " + id);
        return "index";
    }
}
