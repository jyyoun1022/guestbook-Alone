package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;


@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample...")
                .content("Content....")
                .writer("User0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }
    @Test
    void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for(GuestbookDTO dto : resultDTO.getDtoList()){
            System.out.println(dto);
        }

    }
    @Test
    void testList1(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("resultDTO = " + resultDTO.isPrev());
        System.out.println("resultDTO = " + resultDTO.isNext());
        System.out.println("resultDTO = " + resultDTO.getTotalPage());

        System.out.println("=======================================");
        for(GuestbookDTO dto : resultDTO.getDtoList()){
            System.out.println(dto);
        }

        System.out.println("======================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
    @Test
    void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("c")  //검색조건 t,c,w,tc,tcw
                .keyword("C")  //검색 키워드
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: "+resultDTO.getTotalPage());

        System.out.println("================================");

        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("===============================");
        resultDTO.getPageList().forEach(i-> System.out.println(i));
    }

}
