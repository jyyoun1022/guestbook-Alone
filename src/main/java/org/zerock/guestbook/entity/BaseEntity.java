package org.zerock.guestbook.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass   //실제 테이블은 이 추상클래스를 상속한 엔티티의 클래스로 테이블이 생성됩니다.
@EntityListeners(value = {AuditingEntityListener.class})    //
@Getter
abstract class BaseEntity {

    @CreatedDate    //Entity가 생성되어 저장될때 시간을 자동저장
    @Column(name = "regDate",updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate   //조회한 Entity값을 변경할 때 시간을 자동저장
    @Column(name = "modDate")
    private LocalDateTime modDate;
}
