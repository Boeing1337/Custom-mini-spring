package Game;

import dao.KeywordsRepository;
import dao.QuestionRepository;
import dao.UserGameStateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class BeginTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @InjectMocks
    begin game;

    @Mock
    UserGameStateRepository userGameStateRepository;
    @Mock
    KeywordsRepository words;
    @Mock
    QuestionRepository base;


    @Test
    void theGameContinue() {

    }

    @Test
    void theGameNew() {
        System.setOut(new PrintStream(outContent));

        Mockito.when(userGameStateRepository.writeProgress(any(), any(), any())).thenReturn(true);
        Mockito.when(words.readKeywords()).thenReturn(List.of("ДОКЛАД"));

        System.setIn(new ByteArrayInputStream("1\nд\n3\nа\nк\n0\n0".getBytes()));
        game.theGameNew();
        System.setOut(originalOut);
        System.out.println(outContent);

    }
}