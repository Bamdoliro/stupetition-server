package com.bamdoliro.stupetition.domain.board.domain.repository;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardJoiner;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface BoardJoinerRepository extends CrudRepository<BoardJoiner, Long> {

    Boolean existsBoardJoinerByUserAndBoard(User user, Board board);
}
