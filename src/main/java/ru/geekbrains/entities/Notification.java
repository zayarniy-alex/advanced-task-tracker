package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "notifications")
public class Notification {
    public enum Status {
        UNCHECKED, CHECKED
    }

    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "comment_id")
    private Long comment_id;

    @Column (name = "receiver_id")
    private Long receiver_id;

    @Column (name = "status")
    private Status status;
}
