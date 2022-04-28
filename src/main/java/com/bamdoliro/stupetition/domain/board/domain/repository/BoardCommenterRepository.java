package com.bamdoliro.stupetition.domain.board.domain.repository;

import com.bamdoliro.stupetition.domain.board.domain.Board;
import com.bamdoliro.stupetition.domain.board.domain.BoardCommenter;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface BoardCommenterRepository extends CrudRepository<BoardCommenter, Long> {

    Boolean existsBoardCommenterByUserAndBoard(User user, Board board);
}
