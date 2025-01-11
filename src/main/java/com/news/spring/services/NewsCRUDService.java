package com.news.spring.services;

import com.news.spring.dto.NewsDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NewsCRUDService implements CRUDService<NewsDto> {

    @Value("${title.length.max}")
    private Integer maxTitleLength;

    @Value("${text.length.max}")
    private Integer maxTextLength;

    private static final Logger log = LogManager.getLogger(NewsCRUDService.class);
    private final Map<Long, NewsDto> newsStorage = new ConcurrentHashMap<>();

    @Override
    public NewsDto getById(Long id) {
        if (!newsStorage.containsKey(id)) {
            log.info("Новость с ID {} не найдена", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Новость с ID " + id + " не найдена");
        }
        log.info("Показана новость с ID: {}", id);
        return newsStorage.get(id);
    }

    @Override
    public List<NewsDto> getAll() {
        log.info("Выведен полный список новостей");
        return new ArrayList<>(newsStorage.values());
    }

    @Override
    public void create(NewsDto newsDto) {
        Long nextId = (long) ((newsStorage.isEmpty() ? 0 : newsStorage.size())+ 1);
        newsDto.setId(nextId);
        if (newsDto.getTitle().length() > maxTitleLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок слишком длинный");
        }
        if (newsDto.getText().length() > maxTextLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Текст слишком длинный");
        }
        newsDto.setDate(Instant.now());
        newsStorage.put(nextId, newsDto);
        log.info("Добавлена новость с ID: {}", nextId);
    }

    @Override
    public void update(Long id, NewsDto newsDto) {
        if (!newsStorage.containsKey(id)) {
            log.info("Не удалось обновить новость с ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Новость с ID " + id + " не найдена");
        }
        if (newsDto.getTitle().length() > maxTitleLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок слишком длинный");
        }
        if (newsDto.getText().length() > maxTextLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Текст слишком длинный");
        }
        log.info("Новость с ID {} обновлена", id);
        newsDto.setId(id);
        newsDto.setDate(Instant.now());
        newsStorage.put(id, newsDto);
    }

    @Override
    public void deleteById(Long id) {
        if (!newsStorage.containsKey(id)) {
            log.info("Не удалось удалить новость с ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Новость с ID " + id + " не найдена");
        }
        log.info("Новость с ID {} удалена", id);
        newsStorage.remove(id);
    }
}
