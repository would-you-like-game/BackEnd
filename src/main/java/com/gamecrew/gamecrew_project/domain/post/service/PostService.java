package com.gamecrew.gamecrew_project.domain.post.service;

import com.gamecrew.gamecrew_project.domain.JoinPlayer.repository.JoinPlayerRepository;
import com.gamecrew.gamecrew_project.domain.post.dto.request.PostRequestDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PageNationResponseDto;
import com.gamecrew.gamecrew_project.domain.post.dto.response.PostResponseDto;
import com.gamecrew.gamecrew_project.domain.post.entity.Post;
import com.gamecrew.gamecrew_project.domain.post.entity.PostImg;
import com.gamecrew.gamecrew_project.domain.post.repository.PostImgRepository;
import com.gamecrew.gamecrew_project.domain.post.repository.PostRepository;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostS3Service postS3Service;
    private final PostImgRepository postImgRepository;


    public void createPost(PostRequestDto requestDto, User user, List<MultipartFile> photos) throws IOException {
        List<PostImg> postImgList = null;
        if (photos != null && !photos.isEmpty()) {
            postImgList = postS3Service.uploadPhotosToS3AndCreatePostImages(photos);
        }

        Post post = new Post(requestDto, user);
        postRepository.save(post);

        if (postImgList != null) {
            associatePostImagesWithPost(post, postImgList);
        }
    }

    //PostImg 객체와 Post를 연결하고 DB에 저장
    private void associatePostImagesWithPost(Post post, List<PostImg> postImgList) {
        for (PostImg postImg : postImgList) {
            postImg.setPost(post);
            postImgRepository.save(postImg);
        }
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        if (!(post.getUser().getEmail().equals(user.getEmail()))){
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");}
        // DB수정
        post.update(requestDto);
    }
    public void deletePost(Long postId, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        if (!(post.getUser().getEmail().equals(user.getEmail()))){
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");}

        postRepository.delete(post);
    }
    @Transactional
    public void updateView(Long postId) {
        Post post = postRepository.findByPostId(postId);
        post.update();
    }

    public PostResponseDto getPost(Long postId, User user) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));
        PostResponseDto postResponseDto;
        if(Objects.isNull(user)){
            postResponseDto = new PostResponseDto(post);
        } else if (post.getUser().getEmail().equals(user.getEmail())) {
            postResponseDto = new PostResponseDto(post);
            postResponseDto.checkOwner();
        } else {
            postResponseDto = new PostResponseDto(post);
        }
        return postResponseDto;
    }

    public Map<String, Object> getCategoryPost(String category, int page, int size) {
        // 페이징 처리
        boolean isAsc = true;
        String sortBy = "postId";
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy,"title");
        Pageable pageable = PageRequest.of(page, size, sort);

        if(("all".equals(category))){
            Page<Post> postList = postRepository.findAll(pageable);

            List<PageNationResponseDto> pageNationResponseDto = postList.stream()
                    .map(PageNationResponseDto::new)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("postList", pageNationResponseDto);
            response.put("totalPages", postList.getTotalPages());

            return response;
        }
        else {
            Page<Post> postListCategory = postRepository.findAllByCategory(category, pageable);

//            if (postListCategory.getContent().isEmpty()) {
//                throw new RuntimeException("존재하지 않는 카테고리입니다."); // 커스텀 익셉션이라 그냥 런타입 익셉션 씀
//            }

            Map<String, Object> response = new HashMap<>();
            List<PageNationResponseDto> pageNationResponseDto = postListCategory.stream()
                    .map(PageNationResponseDto::new)
                    .collect(Collectors.toList());

            response.put("postList", pageNationResponseDto);
            response.put("totalPages", postListCategory.getTotalPages());

            return response;
        }
    }

//    public JoinPlayerResponseDto createJoinApply(Long postId, User user) throws MessagingException {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
//        );
//
//        List<JoinPlayer> joinPlayer = joinPlayerRepository.findByPost(post);
//
//        long count = joinPlayer.stream().filter(x-> x.getUserEMail().equals(user.getEmail())).count();
//
//        if(post.getUser().getEmail().equals(user.getEmail())||count!=0){
//            throw new IllegalArgumentException("중복 신청은 불가능합니다");
//        }else if(!post.isFullJoin()){
//            String nickname = user.getNickname();
//            String userEmail = user.getEmail();
//            post.recruitCount();
//            postRepository.save(post);
//            JoinPlayer saveJoinCrew = joinPlayerRepository.save(new JoinPlayer(post,user,nickname, userEmail));
//        }else {
//            throw new IllegalArgumentException("인원이 가득찼습니다");
//        }
//        return new JoinPlayerResponseDto(postId,"신청 완료하였습니다");
//    }


//    public Map<String, Object> getAllPost(int page, int size, String sortBy, boolean isAsc) {
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy,"title");
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<Post> postList = postRepository.findAll(pageable);
//
//        List<PostResponseDto> postResponseDtoList = postList.stream()
//                .map(PostResponseDto::new)
//                .collect(Collectors.toList());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("postList", postResponseDtoList);
//        response.put("totalPages", postList.getTotalPages());
//
//        return response;
//    }
}
