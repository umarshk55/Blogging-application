package com.shaik.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();

    // ✅ NEW: store which users liked the post
    private Set<UserDto> likedBy = new HashSet<>();

    // ✅ OPTIONAL: if you just want a like count instead of user details
     private int likeCount;
}
