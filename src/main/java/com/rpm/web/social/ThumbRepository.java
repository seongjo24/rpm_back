package com.rpm.web.social;

import com.rpm.web.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbRepository extends CrudRepository<Thumb, Long> {
    Thumb findByBoardSeqAndUserSeq(Social social, User user);
}
