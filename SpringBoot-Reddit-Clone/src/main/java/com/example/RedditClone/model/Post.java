package com.example.RedditClone.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;

    @NotBlank(message = "Post name can not be empty or null")
    private String postName;

    @Nullable
    private String url;

    //Large chunk of data can be in Description, therefore Large Object = Lob
    @Nullable
    @Lob
    private String description;

    private Integer voteCount;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="id", referencedColumnName = "id")
    @JoinColumn
    private User user;

    private Instant createdDate;

    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name= "id", referencedColumnName= "id")
    @JoinColumn
    private Subreddit subreddit;


}
