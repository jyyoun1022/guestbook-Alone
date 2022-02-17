package org.zerock.guestbook.dto;


import com.querydsl.core.types.dsl.DateTimeOperation;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JpaRepository에서 페이지 처리 결과를 Page타입으로 반환해주면 Service에서 이를 처리하기 위해 만들어진 클래스 입니다.
 * Page의 객체들을 DTO 객체로 변환해 자료구조로 담아줍니다.
 * 화면 출력에 필요한 페이지 정보들을 구성해줍니다.
 */
@Data
public class PageResultDTO<DTO,EN> {

    /**
     * Page를 화면에서 사용하기 쉬운 DTO 리스트 등으로 변환해준다.
     */
    private List<DTO> dtoList;  //DTO 리스트

    private int totalPage;  //총 페이지 번호

    private int page;   //현재 페이지 번호

    private int size;   //목록 사이즈

    private int start,end;  //시작 페이지 번호, 끝 페이지 번호

    private boolean prev, next; //이전, 다음

    private List<Integer> pageList; //페이지 번호 목록

    public PageResultDTO(Page<EN>result , Function<EN,DTO>fn) {

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());

    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;   //0부터시작하므로 1을 더해줍니다.
        this.size = pageable.getPageSize();

        /**
         * temp end page
         * 끝번호를 미리 계산하는 이유 : 시작번호를 계산하는데 수월하게 하기위해
         */
        int tempEnd = (int)(Math.ceil(page / 10.00)) * 10;

        start = tempEnd - 9;

        prev= start >1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
