package com.example.movieplatform.genre.service.impl;

import com.example.movieplatform.genre.domain.Genre;
import com.example.movieplatform.genre.domain.request.GenreCreateRequest;
import com.example.movieplatform.genre.domain.request.GenreDeleteRequest;
import com.example.movieplatform.genre.domain.response.GenreResponse;
import com.example.movieplatform.genre.exception.GenreAlreadyExistsException;
import com.example.movieplatform.genre.exception.GenreNotExistsException;
import com.example.movieplatform.genre.repository.GenreRepository;
import com.example.movieplatform.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public void createGenre(GenreCreateRequest request) {
        if(genreRepository.existsByName(request.name())){
            throw new GenreAlreadyExistsException();
        }

        Genre genre = new Genre(request.name());
        genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(GenreNotExistsException::new);
        genreRepository.delete(genre);
    }

    @Override
    public List<GenreResponse> listGenres() {
        return genreRepository.findAll()
                .stream()
                .map(genre -> new GenreResponse(genre.getId(), genre.getName()))
                .toList();
    }
}
