package com.bamdoliro.stupetition.domain.board.domain.repository;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
