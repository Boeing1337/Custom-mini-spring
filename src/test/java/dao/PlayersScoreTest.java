package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayersScoreTest {

    KeywordsRepository abc = new KeywordsRepository();

    @Test
    void addPlayersScoreTest() {
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
    void EditPlayerScoreTest() {
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

    @Test
    void readePlayerScoreTest() {
        List<String> result = abc.readKeywords();
        List<String> expected = List.of("ВЕНИК",
                "ДОКЛАД",
                "ШУФЛЯДКА",
                "ЛЕПРЕКОН",
                "ФОТОАРХИВ",
                "АБСОЛЮТИЗМ");
        Assertions.assertIterableEquals(expected, result, "Изначальный файл имеет неправильный список счёта");
    }
}