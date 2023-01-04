package com.bkalika.socialnetwork.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author @bkalika
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceMessageGenerator")
    @GenericGenerator(
            name = "sequenceMessageGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "message_sequence"),
                    @Parameter(name = "initial_value", value = "1000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @Column(nullable = false)
    @Size(max = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", createdDate=" + createdDate +
                '}';
    }
}
