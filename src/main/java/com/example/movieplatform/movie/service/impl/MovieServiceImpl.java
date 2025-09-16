package com.example.movieplatform.movie.service.impl;

import com.example.movieplatform.client.domain.response.MovieResponseDto;
import com.example.movieplatform.genre.domain.Genre;
import com.example.movieplatform.genre.repository.GenreRepository;
import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.movie.domain.response.SimpleMovieResponse;
import com.example.movieplatform.movie.exception.MovieAlreadyExistsException;
import com.example.movieplatform.movie.repository.MovieRepository;
import com.example.movieplatform.movie.service.MovieService;
import com.example.movieplatform.moviegenre.domain.MovieGenre;
import com.example.movieplatform.moviegenre.repository.MovieGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieGenreRepository movieGenreRepository;

    // 영화 전체 리스트 (페이징 처리)
    @Override
    @Transactional(readOnly = true)
    public Page<SimpleMovieResponse> allMovies(Pageable pageable) {
        return movieRepository.findAllMovies(pageable);
    }

    @Override
    public void registerMovie(MovieResponseDto response) {
        // 영화 존재 체크
        existsMovieByDocid(response.docid());

        Movie movie = Movie.fromDto(response);
        Movie savedMovie = movieRepository.save(movie);

        // 장르 연결
        saveMovieGenre(savedMovie, response.genre());
    }

    public void existsMovieByDocid(String docid) {
        if (movieRepository.existsByDocid(docid)) {
            throw new MovieAlreadyExistsException();
        }
    }

    public void saveMovieGenre(Movie movie, String genreString) {
        if (genreString == null || genreString.isEmpty()) return;

        String[] genreNames = genreString.split(",");
        for (String genreName : genreNames) {
            genreName = genreName.trim();

            // 장르가 DB에 있는지 확인
            Optional<Genre> genre = genreRepository.findByName(genreName);
            Genre genreToUse;

            // 없으면 새로 생성 후 저장
            if (genre.isEmpty()) {
                Genre newGenre = new Genre(genreName);
                genreToUse = genreRepository.save(newGenre);
            } else {
                genreToUse = genre.get();
            }

            // 영화와 장르가 이미 연결되어 있는지 확인
            Optional<MovieGenre> existingMovieGenre = movieGenreRepository.findByMovieAndGenre(movie, genreToUse);

            // 연결되어 있지 않으면 새로 연결 정보 생성 후 저장
            if (existingMovieGenre.isEmpty()) {
                MovieGenre movieGenre = MovieGenre.valueOf(movie, genreToUse);
                movieGenreRepository.save(movieGenre);
            }
        }
    }
}
