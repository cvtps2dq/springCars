package ru.cv2.springcars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cv2.springcars.models.User;
import ru.cv2.springcars.repos.UserRespository;

import java.text.MessageFormat;

@Service("userDetailsServiceImpl")
public class UserDetalServiceImpl implements UserDetailsService {

        private final UserRespository userRepository;

        @Autowired
        public UserDetalServiceImpl(UserRespository userRepository){
            this.userRepository=userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(nickname);
            if( user == null) {
                throw new UsernameNotFoundException(MessageFormat.format("User {0} doesn't exist", nickname));
            }
            return SecurityUser.fromUser(user);
        }
}
