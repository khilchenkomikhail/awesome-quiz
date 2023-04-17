package ru.edu.spbstu.quizproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.edu.spbstu.quizproject.user.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "results")
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
    private Quiz quiz;
    private Integer percent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
}
