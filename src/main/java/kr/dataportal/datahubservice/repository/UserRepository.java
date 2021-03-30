package kr.dataportal.datahubservice.repository;

import kr.dataportal.datahubservice.domain.user.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserRepository {
    private final EntityManager em;

    public int signup(User user) {
        em.persist(user);
        return user.getSeq();
    }

    public User findByEmail(String email) {
        List<User> user = em.createQuery("" +
                " SELECT user FROM User user" +
                " WHERE user.email =: email", User.class)
                .setParameter("email", email)
                .getResultList();

        if (user.size() > 0) {
            return user.get(0);
        } else {
            return null;
        }
    }

}
