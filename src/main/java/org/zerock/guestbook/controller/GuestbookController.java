package org.zerock.guestbook.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor    //자동 주입을 위한 어노테이션
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list (PageRequestDTO pageRequestDTO, Model model){

        log.info("list........"+pageRequestDTO);

        model.addAttribute("result",service.getList(pageRequestDTO));
    }

    /**
     * 화면을 보여줍니다.
     */
    @GetMapping("/register")
    public void register(){
        log.info("register get.....");
    }

    /**
     * 처리 후 목록 페이지로 이동합니다.
     * 1. RedirectAttributes() : 단 한번만 화면에서 "msg"라는 이름의 변수를 사용할 수 있도록 처리해줍니다.
     * 2. addFlashAttribute() : 단 한ㅂ너만 데이터를 전달하는 용도로 사용합니다.
     */
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto...."+dto);

        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read","/modify"})
    // 다시 목록 페이지로 돌아가는 데이터를 같이 저장하기 위해 PageRequestDTO를 파라미터로 같이 사용한다
    public void read(Long gno,@ModelAttribute("requestDTO") PageRequestDTO requestDTO,Model model){

        // GET방식으로 gno값을 받아 Model에 GuestbookDTO 객체를 담아 전달한다
        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        //post방식으로 gno 값을 전달하고 삭제후
        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);

        //다시 목록의 첫 페이지로 이동합니다.
        return "redirect:/guestbook/list";
    }
    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){

        service.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";
    }

}
