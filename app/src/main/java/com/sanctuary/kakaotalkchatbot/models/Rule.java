package com.sanctuary.kakaotalkchatbot.models;

import androidx.annotation.Nullable;

public class Rule {
    public static final String MATCH_TYPE_EXACT = "정확히 일치";
    public static final String MATCH_TYPE_INCLUDING = "포함";

    public static final String ROOM_TYPE_ALL = "전체";
    public static final String ROOM_TYPE_GROUP = "단체";
    public static final String ROOM_TYPE_DIRECT = "개인";

    private String roomName;
    private String matchType;
    private String roomType;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Rule o = (Rule) obj;
        String a = roomName + matchType + roomType;
        String b = o.roomName + o.matchType + o.roomType;

        return a.equals(b);
    }
}
