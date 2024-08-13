package com.andriusha.task.management.system.comment;

import com.andriusha.task.management.system.task.Task;
import com.andriusha.task.management.system.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "task_id")
    Task task;
    String content;
    User author;
}
