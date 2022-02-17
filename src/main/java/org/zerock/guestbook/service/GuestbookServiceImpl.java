package org.zerock.guestbook.service;

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
import org.zerock.guestbook.repository.GuestBookRepository;

import java.util.function.Function;


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

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook,GuestbookDTO> fn = (entity ->
                entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }
}
