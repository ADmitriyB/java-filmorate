package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<MpaDto> getAllMpa() {
        return mpaService.getAllMpa();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MpaDto getDtoById(@PathVariable("id") Integer id) {
        return mpaService.getMpaById(id);
    }
}
