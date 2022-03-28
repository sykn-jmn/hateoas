package com.jeman.hateoas.message;

import com.jeman.hateoas.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private UUID uuid;
    @ManyToOne
    private User from;
    @ManyToOne
    private User to;
    private LocalDateTime timeAndDate;
    @Column(columnDefinition = "VARCHAR")
    private String message;

    public Message(User from, User to, LocalDateTime timeAndDate, String message) {
        this.from = from;
        this.to = to;
        this.timeAndDate = timeAndDate;
        this.message = message;
        this.uuid = UUID.randomUUID();
    }
}
