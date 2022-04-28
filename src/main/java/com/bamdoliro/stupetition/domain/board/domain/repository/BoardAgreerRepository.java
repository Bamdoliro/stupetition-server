package com.bamdoliro.stupetition.domain.board.domain.repository;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardAgreer;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface BoardAgreerRepository extends CrudRepository<BoardAgreer, Long> {

    Boolean existsBoardAgreerByUserAndBoard(User user, Board board);
}
