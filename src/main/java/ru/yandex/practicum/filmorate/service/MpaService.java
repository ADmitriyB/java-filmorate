package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MpaService {

    private final MpaRepository mpaRepository;

    public MpaService(MpaRepository mpaRepository) {
        this.mpaRepository = mpaRepository;
    }



    public MpaDto getMpaById(Integer mpaId) {
        return mpaRepository.findById(mpaId)
                .map(MpaMapper::mapToMpaDto)
                .orElseThrow(() -> new NotFoundException("Mpa не найден с ID: " + mpaId));
    }


    public Collection<MpaDto> getAllMpa() {
        return mpaRepository.findAll().stream()
                .map(MpaMapper::mapToMpaDto)
                .collect(Collectors.toList());
    }


}
