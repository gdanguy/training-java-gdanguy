package service.user;

import core.model.User;
import dao.login.LoginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by ebiz on 23/05/17.
 */
@Service("userService")
public class UserServiceImpl implements UserDetailsService {
    private LoginDAO loginDao;

    /**
     * .
     * @param loginDao .
     */
    @Autowired
    public UserServiceImpl(LoginDAO loginDao) {
        this.loginDao = loginDao;
    }

    /**
     * .
     * @param name .
     * @return .
     * @throws UsernameNotFoundException .
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = loginDao.getByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(name);
        }
        return user;
    }
}
