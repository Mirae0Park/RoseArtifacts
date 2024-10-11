package com.roseArtifacts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass // JPA 엔티티 클래스가 아니라 공통 속성 및 매핑 정보를 상속하기 위한 추상 클래스임을 나타냄
@EntityListeners(value = { AuditingEntityListener.class })
// AuditingEntityListener은 엔티티의 생성 및 수정 시간을 자동으로 관리하기 위한 리스너를 제공
@Getter
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;   // 등록자

    @LastModifiedBy
    private String modifiedBy;  // 수정자

}
