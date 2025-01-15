package org.imt.tournamentmaster.service.auth;

import org.imt.tournamentmaster.model.auth.User;
import org.imt.tournamentmaster.model.auth.UserDto;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto);
}
