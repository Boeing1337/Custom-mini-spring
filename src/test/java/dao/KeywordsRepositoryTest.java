package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class KeywordsRepositoryTest {

    KeywordsRepository abc = new KeywordsRepository();

    @Test
    void testReadKeywords() {
        List<String> result = abc.readKeywords();
        List<String> expected = List.of("ВЕНИК",
                "ДОКЛАД",
                "ШУФЛЯДКА",
                "ЛЕПРЕКОН",
                "ФОТОАРХИВ",
                "АБСОЛЮТИЗМ");
        Assertions.assertIterableEquals(expected, result, "Изначальный файл имеет неправильный список слов");
    }

    @Test
    void testAddKeywords() {
        List<String> result = abc.addKeywords(List.of("ХУЙ"));
        List<String> expected = List.of("ВЕНИК",
                "ДОКЛАД",
                "ШУФЛЯДКА",
                "ЛЕПРЕКОН",
                "ФОТОАРХИВ",
                "АБСОЛЮТИЗМ",
                "ХУЙ");
        Assertions.assertIterableEquals(expected, result, "");
        abc.deleteKeyword("ХУЙ");
    }

    @Test
    void testEditKeywords() {
        List<String> result = abc.editKeywords("АБСОЛЮТИЗМ", "ХУЙ");
        List<String> expected = List.of("ВЕНИК",
                "ДОКЛАД",
                "ШУФЛЯДКА",
                "ЛЕПРЕКОН",
                "ФОТОАРХИВ",
                "ХУЙ");
        Assertions.assertIterableEquals(expected, result, "");
        abc.editKeywords("ХУЙ", "АБСОЛЮТИЗМ");
    }

}