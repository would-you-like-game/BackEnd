package com.gamecrew.gamecrew_project.domain.JoinPlayer.dto.response;

import com.gamecrew.gamecrew_project.domain.JoinPlayer.entity.JoinPlayer;
import lombok.Getter;

@Getter
public class JoinPlayerResponseDto {
    private Long joinPlayerId;
    private String nickName;
    private String email;
    private String crewOwnerEmail;
//    private String crew;
    private boolean isAccepted;
    private boolean postUser;

    public JoinPlayerResponseDto(JoinPlayer joinPlayer, boolean isOwner) {
        this.nickName = joinPlayer.getNickname();
        this.email = joinPlayer.getUserEMail();
        this.isAccepted = joinPlayer.getIsAccepted();
//        this.crew = joinPlayer.getCrewRecruitmentEntity().getCrewName();
        this.joinPlayerId = joinPlayer.getJoinPlayerId();
        this.crewOwnerEmail = joinPlayer.getUserEMail();
        this.postUser = isOwner;

    }
}
