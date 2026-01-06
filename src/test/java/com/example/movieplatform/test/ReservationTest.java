package com.example.movieplatform.test;

import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.movie.repository.MovieRepository;
import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.reservation.repository.ReservationRepository;
import com.example.movieplatform.reservation.service.impl.ReservationServiceImpl;
import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.screen.domain.Seat;
import com.example.movieplatform.screen.repository.ScreenRepository;
import com.example.movieplatform.screen.repository.SeatRepository;
import com.example.movieplatform.showinginfo.domain.ShowingInfo;
import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import com.example.movieplatform.showinginfo.repository.ShowingInfoRepository;
import com.example.movieplatform.ticket.repository.TicketRepository;
import com.example.movieplatform.user.domain.User;
import com.example.movieplatform.user.domain.request.UserCreateRequest;
import com.example.movieplatform.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // 데이터를 세팅하기 위한 추가 리포지토리들 (프로젝트에 맞게 주입 필요)
    @Autowired private ShowingInfoRepository showingInfoRepository;
    @Autowired private SeatRepository seatRepository;
    @Autowired private ScreenRepository screenRepository;
    @Autowired private MovieRepository movieRepository;

    private List<User> users = new ArrayList<>(); // 3명의 유저를 담을 리스트
    private Long showingInfoId;
    private List<Long> seatIds;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
        reservationRepository.deleteAll();
        seatRepository.deleteAll();
        showingInfoRepository.deleteAll();
        screenRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();

        // 테스트 유저 세팅
        users.clear();
        for (int i = 1; i <= 3; i++) {
            UserCreateRequest req = new UserCreateRequest(
                    "password",
                    "user" + i + "@test.com",
                    "User" + i,
                    "010-0000-000" + i,
                    LocalDate.now()
            );
            User user = userRepository.save(User.of(req, "encodedPassword"));
            users.add(user);
        }

        // 테스트 상영관 세팅
        Screen screen = screenRepository.save(new Screen("1관"));

        // 테스트 영화 세팅
        Movie movie = movieRepository.save(new Movie(
                null,                   // id (DB 자동 생성)
                "DOC-TEST-001",         // docid
                "테스트 영화",            // title
                "Test Movie",           // title_en
                "테스트 감독",            // director
                "테스트 줄거리입니다.",     // plot
                "http://test.com/img.jpg", // posterUrl
                "테스트 제작사",          // company
                LocalDate.now(),        // releaseDate
                "120",                  // runtime (String 타입임에 주의!)
                "15세 관람가",            // grade
                new ArrayList<>()       // movieGenres
        ));

        LocalTime startTime = LocalTime.of(12, 0);
        int runningTime = Integer.parseInt(movie.getRuntime());
        LocalTime endTime = startTime.plusMinutes(runningTime);

        // 상영정보 세팅
        ShowingInfoCreateRequest infoReq = new ShowingInfoCreateRequest(
                movie.getId(),      // movieId
                screen.getId(),     // screenId
                LocalDate.now().plusDays(1), // showingDate (내일)
                startTime,          // startTime
                10000               // price
        );

        ShowingInfo showingInfo = showingInfoRepository.save(
                ShowingInfo.create(infoReq, movie, screen, endTime)
        );

        this.showingInfoId = showingInfo.getId();

        // 좌석 세팅
        Seat seat = seatRepository.save(
                new Seat("1-1", screen, 1, 1)
        );

        // 테스트할 좌석 ID 리스트 초기화
        this.seatIds = List.of(seat.getId());
    }

    @Test
    @DisplayName("유저 3명이 동시에 같은 좌석을 예매하면 1명만 성공해야 한다.")
    void createReservation_Concurrency_3Users() throws InterruptedException {
        // Given
        int threadCount = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        CountDownLatch readyLatch = new CountDownLatch(1);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // 시간 포맷 (시:분:초.밀리초)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        ReservationRequest request = new ReservationRequest(showingInfoId, seatIds);

        System.out.println("========== 테스트 시작 ==========");

        // When
        for (int i = 0; i < threadCount; i++) {
            int userIndex = i;
            executorService.submit(() -> {
                long startTimeMs = 0;
                try {
                    readyLatch.await(); // 출발 신호 대기

                    // 1. 시작 시간 기록
                    startTimeMs = System.currentTimeMillis();
                    String startLog = LocalDateTime.now().format(formatter);
                    System.out.printf("[User %d] 요청 시작 \t| 시간: %s%n", userIndex, startLog);

                    User currentUser = users.get(userIndex);

                    // 서비스 호출 (여기서 비관적 락으로 인해 대기가 발생할 수 있음)
                    reservationService.createReservation(request, currentUser);

                    // 2. 성공 시간 기록
                    long endTimeMs = System.currentTimeMillis();
                    String endLog = LocalDateTime.now().format(formatter);
                    long duration = endTimeMs - startTimeMs;

                    System.out.printf("[User %d] 예매 성공 \t| 시간: %s | 소요시간: %dms%n", userIndex, endLog, duration);
                    successCount.incrementAndGet();

                } catch (Exception e) {
                    // 3. 실패 시간 기록
                    long endTimeMs = System.currentTimeMillis();
                    String endLog = LocalDateTime.now().format(formatter);
                    long duration = endTimeMs - startTimeMs;

                    System.out.printf("[User %d] 예매 실패 \t| 시간: %s | 소요시간: %dms | 사유: %s%n", userIndex, endLog, duration, e.getMessage());
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        Thread.sleep(100);
        readyLatch.countDown(); // 동시 시작!
        latch.await();
        System.out.println("========== 테스트 종료 ==========");

        // Then
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(2);
    }
}