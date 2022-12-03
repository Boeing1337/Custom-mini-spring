package tech.wg.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionRepository;
import tech.wg.tools.MockGrammar;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService = new AdminService();
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private KeywordsRepository keywordsRepository;
    @Spy
    private MockGrammar grammar;

    @Test
    void testMenuWords() {
        String input = "1\n1\n2\nФифА\n1\n3\nФифа\nФуфлО\n1\n4\nфуфло\n1\n0\n0";
        grammar.setInputContent(input);

        String result = grammar.getOut();
    }

    @Test
    void testMenuQuestsAnswers() {
        String input = "2\n1\nг\n2\nФифА\nГГГГ\n1\nг\n3\nг\n1\nФуфлО\n1\nг\n4\nг\n1\nгг\n1\nг\n5\nг\n1\n1\nг\n0\n0";
        grammar.setInputContent(input);

        String result = grammar.getOut();
    }
}