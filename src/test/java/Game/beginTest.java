package Game;

import dao.UserGameStateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


@ExtendWith(MockitoExtension.class)
class beginTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @InjectMocks
    begin service;

    @Mock
    UserGameStateRepository userGameStateRepository;


    @Test
    void theGameContinue() {

    }

    @Test
    void theGameNew() {
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream("2\nе\n1\n0\n0".getBytes()));
        service.theGameNew();
        System.setOut(originalOut);
        System.out.println(outContent);

    }
}