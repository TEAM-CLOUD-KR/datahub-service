package kr.dataportal.datahubservice.service;

import kr.dataportal.datahubservice.domain.user.User;
import kr.dataportal.datahubservice.dto.UserSignupDto;
import kr.dataportal.datahubservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserService {
    private final UserRepository userRepository;

    public int signup(User user) {
        return userRepository.signup(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
