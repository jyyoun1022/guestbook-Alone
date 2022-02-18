package org.zerock.guestbook.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * 목록  처리시 주의사항
 * 1. 화면에서 필요한 목록 데이터에 대한 DTO생성
 * 2. DTO를 Pageable 타입으로 전환
 * 4. 화면에 필요한 페이지 번호 처리
 */


/**
 * 화면에서 전달되는 목록 관련된 데이터에 대한 DTO를 PageRequestDTO라는 이름으로 생성
 * 파라미터를 DTO로 선언하고 나중에 재사용하는 용도
 */
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {


    private int page;
    private int size;
    private  String type;
    private String keyword;

    /**
     * 페이지 번호 등은 기본값을 가지는 것이 좋기 때문에 1과 10이라는 값을 지정합니다.
     */
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    /**
     * 페이지 번호가 0부터 시작한다는 점을 감안하여 1페이지의 경우 0이 될수 있도록 page -1로 작성해줍니다.
     * 정렬은 다양한 상황에서 쓰기위해 별도의 파라미터를 받도록 설게한다.
     */
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);
    }
}
