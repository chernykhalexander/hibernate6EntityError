package org.hibernate.bugs;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "chatRoom")
public class ChatRoom
{
    @Id
    private String id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chatRoom",
            orphanRemoval = true)
    private Set<ChatRoomUser> chatRoomUsers;

    private String info;

    public ChatRoom()
    {
        this.id = UUID.randomUUID().toString();
    }

    public String getId()
    {
        return id;
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
