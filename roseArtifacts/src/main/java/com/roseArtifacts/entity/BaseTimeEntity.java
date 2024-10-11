package com.roseArtifacts.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    // 저장할 때 시간 부분을 00:00:00으로 맞추기
    @PrePersist
    public void prePersist() {
        this.regTime = (regTime != null ? regTime : LocalDateTime.now()).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

}