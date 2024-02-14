package com.sparta.team2newsfeed.entity;

import com.sparta.team2newsfeed.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "board")
public class Board extends Timestemped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int cookLevel;

    //1:N 양방향
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    // BoardService 에서 보드Dto 와 유저 ID를 받기 위한 생성자
    public Board(BoardRequestDto boardRequestDto, User user) {
        this.title = boardRequestDto.getTitle();
        this.body = boardRequestDto.getBody();
        this.category = boardRequestDto.getCategory();
        this.cookLevel = Integer.parseInt(boardRequestDto.getCookLevel());
        this.user = user;
    }

    public void update(BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getTitle() != null) {
            this.title = boardRequestDto.getTitle();
        }
        if (boardRequestDto.getBody() != null) {
            this.title = boardRequestDto.getBody();
        }
        if (boardRequestDto.getCategory() != null) {
            this.title = boardRequestDto.getCategory();
        }
        if (boardRequestDto.getCookLevel() != null) {
            this.cookLevel = Integer.parseInt(boardRequestDto.getCookLevel());
        }
    }

}
