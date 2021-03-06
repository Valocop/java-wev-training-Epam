package by.training.machine.monitoring.user;

import by.training.machine.monitoring.dao.CRUDDao;
import by.training.machine.monitoring.dao.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao extends CRUDDao<UserDto, Long> {
    Optional<UserDto> findByLogin(String login) throws DaoException;
    List<UserDto> getUsersByMachineId(Long machineId) throws DaoException;
    boolean assignUserMachine(Long userId, Long machineId) throws DaoException;
    boolean deleteAssignUserMachine(Long userId) throws DaoException;
    boolean deleteAssignUserMachine(Long userId, Long machineId) throws DaoException;
    boolean assignUserMachineIsPresent(Long userId, Long machineId) throws DaoException;
}
