package com.gamecrew.gamecrew_project.domain.JoinPlayer.service;

import com.gamecrew.gamecrew_project.domain.JoinPlayer.dto.response.ApplyResponseDto;
import com.gamecrew.gamecrew_project.domain.JoinPlayer.dto.response.JoinPlayerResponseDto;
import com.gamecrew.gamecrew_project.domain.JoinPlayer.entity.JoinPlayer;
import com.gamecrew.gamecrew_project.domain.JoinPlayer.repository.JoinPlayerRepository;
import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import com.gamecrew.gamecrew_project.domain.post.repository.PostRepository;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoinPlayerService {
    private final PostRepository postRepository;
    private final JoinPlayerRepository joinPlayerRepository;

    @Transactional
    public ApplyResponseDto createJoinApply(Long postId, User user) throws MessagingException {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );

        List<JoinPlayer> joinPlayer = joinPlayerRepository.findByPost(post);

        long count = joinPlayer.stream().filter(x-> x.getUserEMail().equals(user.getEmail())).count();

        if(post.getUser().getEmail().equals(user.getEmail())||count!=0){
            throw new IllegalArgumentException("중복 신청은 불가능합니다");
        }else if(!post.isFullJoin()){
            String nickname = user.getNickname();
            String userEmail = user.getEmail();
            post.recruitCount();
            postRepository.save(post);
            JoinPlayer saveJoinCrew = joinPlayerRepository.save(new JoinPlayer(post, user, nickname, userEmail));
        }else {
            throw new IllegalArgumentException("인원이 가득찼습니다");
        }
        return new ApplyResponseDto(postId,"신청 완료하였습니다");
    }
    @Transactional
    public void acceptJoinApply(String userEmail, Long postId) {
        JoinPlayer joinPlayer = joinPlayerRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("해당 신청이 존재하지 않습니다 +++++"));

        String crewOwnerEmail = joinPlayer.getPost().getUser().getEmail();

        if (crewOwnerEmail.equals(userEmail)){
            joinPlayer.updateAccepted();
            joinPlayerRepository.save(joinPlayer);
        }else{
            throw new IllegalArgumentException("해당 크루게시글 작성자가 아닙니다");
        }
    }


    public List<JoinPlayerResponseDto> getJoinPlayerList(String userEmail, Boolean isApproval) {

        List<Post> postList = postRepository.findAllByUserEmail(userEmail);

        List<Long> postIds = postList.stream()
                .map(Post::getPostId)
                .collect(Collectors.toList());

        List<JoinPlayer> joinPlayers;

        if (isApproval.equals(Boolean.FALSE)) {
            joinPlayers = joinPlayerRepository.findJoinPlayersByPostIn(postList).stream()
                    .filter(joinPlayer -> !joinPlayer.getIsAccepted()).collect(Collectors.toList());
        }else{
            joinPlayers = joinPlayerRepository.findJoinPlayersByPostIn(postList).stream()
                    .filter(joinPlayer -> joinPlayer.getIsAccepted()).collect(Collectors.toList());
        }
        return joinPlayers.stream().map(x-> new JoinPlayerResponseDto(x,false)).collect(Collectors.toList());
    }
}
