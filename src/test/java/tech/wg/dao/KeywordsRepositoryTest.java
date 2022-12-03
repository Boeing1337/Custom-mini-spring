package tech.wg.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.extention.RepositoryExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ExtendWith(RepositoryExtension.class)
class KeywordsRepositoryTest {

    private final KeywordsRepository repository = new KeywordsRepository();

    @BeforeAll
    static void createTestFile() {
        try (FileWriter writer = new FileWriter("keywordsFileForTest")) {
            writer.write("ВЕНИК\n" +
                    "ДОКЛАД\n" +
                    "ШУФЛЯДКА\n" +
                    "ЛЕПРЕКОН\n" +
                    "ФОТОАРХИВ\n" +
                    "АБСОЛЮТИЗМ\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void deleteTestFile() {
        try {
            Files.deleteIfExists(Path.of("keywordsFileForTest"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddKeywords() {
        List<String> result = repository.addKeywords(List.of("ХУЙ"));
        List<String> expected = List.of("ВЕНИК",
                "ДОКЛАД",
                "ШУФЛЯДКА",
                "ЛЕПРЕКОН",
                "ФОТОАРХИВ",
                "АБСОЛЮТИЗМ",
                "ХУЙ");
        Assertions.assertIterableEquals(expected, result, "");
        repository.deleteKeyword("ХУЙ");
    }

    @Test
    void testEditKeywords() {
        List<String> result = repository.editKeywords("АБСОЛЮТИЗМ", "ХУЙ");
        List<String> expected = List.of("ВЕНИК",
                "ДОКЛАД",
                "ШУФЛЯДКА",
                "ЛЕПРЕКОН",
                "ФОТОАРХИВ",
                "ХУЙ");
        Assertions.assertIterableEquals(expected, result, "");
        repository.editKeywords("ХУЙ", "АБСОЛЮТИЗМ");
    }

}