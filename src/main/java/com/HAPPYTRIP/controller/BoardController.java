package com.HAPPYTRIP.controller;

import com.HAPPYTRIP.domain.Board;
import com.HAPPYTRIP.domain.Member;
import com.HAPPYTRIP.service.BoardService;
import com.HAPPYTRIP.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardservice;
    private final MemberService memberService;

    //조회
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Board> paging = boardservice.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "/board/boardList";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Board board = boardservice.getBoard(id);
        model.addAttribute("board", board);
        return "/board/boardDetail";
    }

    //추가
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String boardCreate() {
        return "/board/boardForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String boardCreate(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, Principal principal) {
        String username = principal.getName();
        Member member = memberService.getMember(username);
        boardservice.create(title, content, member);
        return "redirect:/board/list";
    }

    //수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id, Model model) {
        Board board = boardservice.getBoard(id);
        model.addAttribute("board", board);
        return "/board/boardUpdate";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String boardUpdate(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, @PathVariable("id") Long id) {
        boardservice.update(id, title, content);
        return "redirect:/board/list";
    }

    //삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable("id") Long id) {
        Board board = boardservice.getBoard(id);
        boardservice.delete(board);
        return "redirect:/board/list";
    }
}