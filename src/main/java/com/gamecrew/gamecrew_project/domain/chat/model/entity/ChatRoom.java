package com.gamecrew.gamecrew_project.domain.chat.model.entity;

import com.gamecrew.gamecrew_project.global.entity.Auditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRoom extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomKey;

    @Column
    private Long sender;

    @Column
    private Long receiver;
}
