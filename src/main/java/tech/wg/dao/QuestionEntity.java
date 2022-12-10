package tech.wg.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class QuestionEntity {
    private String question;
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return question.equals(that.question) && answer.equals(that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }
}
