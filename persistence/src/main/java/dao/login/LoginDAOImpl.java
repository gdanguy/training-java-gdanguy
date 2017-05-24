package dao.login;

import core.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by ebiz on 23/05/17.
 */
@Repository
@Transactional
public class LoginDAOImpl implements LoginDAO {
    private SessionFactory sessionFactory;

    /**
     * .
     * @param sessionFactory .
     */
    @Autowired
    public LoginDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get a User in the Database with this username.
     * @param username the username of the user
     * @return User
     */
    @Override
    public User getByName(String username) {
        return (User) sessionFactory.getCurrentSession().createQuery("FROM User WHERE name = :name")
                .setParameter("name", username)
                .list().get(0);
    }
}
