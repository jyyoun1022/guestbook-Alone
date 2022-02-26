package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestBookRepository;

import java.util.Optional;
import java.util.function.Function;

import static org.zerock.guestbook.entity.QGuestbook.*;


@Service
@Log4j2
@RequiredArgsConstructor    //의존성 자동주입
public class GuestbookServiceImpl implements GuestbookService{

private final GuestBookRepository repository;

    /**
     *등록을 하려면 레포지토리에 해야 하기때문에 Entity로 넣어줘야합니다.
     */
    @Override
    public Long register(GuestbookDTO dto) {

        Guestbook entity = dtoToEntity(dto);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리
        //페이징,정렬 = findall // 반환타입 page<entity>
        //클라이언트로부터 온 요청 결과값 = jpa에서 나온 결과값(entity)
        //pageable 타입 파라미터 findall에 넣으면 쿼리가 실행

        //Querydsl 사용(검색결과)후 리스트 페이지
        Page<Guestbook> result = repository.findAll(booleanBuilder,pageable); // Querydsl 사용
        //Guestbook(entity)를 넣으면 GuestbookDTO(dto)로 나온다.
        Function<Guestbook,GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }


    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {

        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()){
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    /**
     * Querydsl 처리
     */
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType(); //검색조건 = title, content, writer

        BooleanBuilder booleanBuilder = new BooleanBuilder();   //조건문 담을 컨테이너 생성(BooleanBuilder는 where조건문에 들어가는 조건들을 넣어주는 컨테이너)

//        QGuestbook qGuestbook = QGuestbook.guestbook;   //큐 도메인 생성    //QGuestbook을 static import처리

        String keyword = requestDTO.getKeyword();   //검색어

        BooleanExpression expression = guestbook.gno.gt(0L);   //gno > 0 조건만 생성

        booleanBuilder.and(expression); //건테이너에 조건문 담기

        if(type==null || type.trim().length()==0){return booleanBuilder;    //조건문이 null 이거나 0이면
    }


        // 검색 조건 작성
        // PageRequestDTO 를 파라미터로 받아 검색조건이 있는 경우 conditionBuilder 변수를 생성해 각 검색조건을 or로 연결해 처리한다
        BooleanBuilder conditionBuilder = new BooleanBuilder(); //컨테이너2

        if(type.contains("t")){ //제목으로 검색   / type=제목,작성자,내용 에 keyword로 검색
            conditionBuilder.or(guestbook.title.contains(keyword));    //
        }
        if(type.contains("c")){ //내용으로 검색
            conditionBuilder.or(guestbook.content.contains(keyword));
        }
        if(type.contains("w")){ //작성자로 검색
            conditionBuilder.or(guestbook.writer.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);//컨테이너1+컨테이너2

        return booleanBuilder;
    }




    }

