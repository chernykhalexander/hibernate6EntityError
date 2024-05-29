package org.hibernate.bugs;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Embeddable
public class ChatRoomUserId implements Serializable
{
    private String chatRoomId;
    private String userId;

    protected ChatRoomUserId()
    {
    }

    public ChatRoomUserId(String chatRoomId, String userId)
    {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ChatRoomUserId)) return false;
        ChatRoomUserId that = (ChatRoomUserId) o;
        return Objects.equals(chatRoomId, that.chatRoomId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(chatRoomId, userId);
    }

    public String getChatRoomId()
    {
        return chatRoomId;
    }

    public String getUserId()
    {
        return userId;
    }
}
