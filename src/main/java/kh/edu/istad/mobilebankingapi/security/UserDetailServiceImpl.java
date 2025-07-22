package kh.edu.istad.mobilebankingapi.security;

import kh.edu.istad.mobilebankingapi.domain.User;
import kh.edu.istad.mobilebankingapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        CustomerUserDetail customerUserDetail = new CustomerUserDetail();
        customerUserDetail.setUser(user);
        return customerUserDetail;
    }
}
