package dao.login;

import core.model.User;

/**
 * Created by ebiz on 23/05/17.
 */
public interface LoginDAO {

    /**
     * Get a User in the Database with this username.
     * @param username the username of the user
     * @return User
     */
    User getByName(String username);
}
