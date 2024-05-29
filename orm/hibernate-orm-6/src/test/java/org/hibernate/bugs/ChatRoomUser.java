package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_room_user")
public class ChatRoomUser
{
    @EmbeddedId
    private ChatRoomUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", referencedColumnName = "id")
    @MapsId("chatRoomId")
    private ChatRoom chatRoom;

    @Column(name = "info")
    private String info;

    public void setId(ChatRoomUserId id)
    {
        this.id = id;
    }

    public ChatRoomUser()
    {
    }

    public ChatRoomUserId getId()
    {
        return id;
    }

    public ChatRoom getChatRoom()
    {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom)
    {
        this.chatRoom = chatRoom;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }
}
