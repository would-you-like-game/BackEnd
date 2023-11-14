package com.gamecrew.gamecrew_project.domain.post.dto.response;

import com.gamecrew.gamecrew_project.global.response.CustomPageable;
import lombok.Getter;

import java.util.List;

@Getter
public class PostsResponseDto {
    private String msg;
    private CustomPageable pageable;
    private List<PostResultDto> result;


    public PostsResponseDto(
            String msg,
            int totalPages,
            long totalElements,
            int size,
            List<PostResultDto> postResultDto
    ){
        this.msg = msg;
        this.pageable = new CustomPageable(totalPages, totalElements, size);
        this.result = postResultDto;
    }
}
