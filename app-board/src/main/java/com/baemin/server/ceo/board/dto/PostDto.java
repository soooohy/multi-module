package com.baemin.server.ceo.board.dto;

import lombok.Getter;
import lombok.Setter;

public class PostDto {

    @Getter
    @Setter
    public static class getAllReq {
        private int page;
        private int size;
    }

    @Getter
    @Setter
    public static class putReq {
        private long boardId;
        private String title;
        private String contents;
        private long userId;
    }

    @Getter
    @Setter
    public static class updateReq {
        private long postId;
        private long userId;
        private String title;
        private String contents;
    }

    @Getter
    @Setter
    public static class hideReq {
        private long userId;
        private long postId;
    }

}
