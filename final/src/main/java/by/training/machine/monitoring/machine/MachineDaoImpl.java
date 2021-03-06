package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;
import by.training.machine.monitoring.entity.MachineEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class MachineDaoImpl implements MachineDao {
    //language=PostgreSQL
    private static final String INSERT_MACHINE = "INSERT INTO machine_monitoring_schema.machine (uniq_code, model_id, characteristic_id, manufacture_id) VALUES (?, ?, ?, ?)";
    //language=PostgreSQL
    private static final String DELETE_MACHINE = "DELETE FROM machine_monitoring_schema.machine WHERE id = ?";
    //language=PostgreSQL
    private static final String DELETE_MACHINE_BY_MANUFACTURE_AND_MACHINE = "DELETE FROM machine_monitoring_schema.machine WHERE id = ? AND manufacture_id = ?";
    //language=PostgreSQL
    private static final String DELETE_MACHINE_AND_USER_ASSIGN = "DELETE FROM machine_monitoring_schema.user_machine WHERE machine_id = ?";
    //language=PostgreSQL
    private static final String UPDATE_MACHINE = "UPDATE machine_monitoring_schema.machine SET uniq_code = ?, model_id = ?, characteristic_id = ?, manufacture_id = ? WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINE_BY_ID = "SELECT id, uniq_code, model_id, characteristic_id, manufacture_id FROM machine_monitoring_schema.machine WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINE_BY_MANUFACTURE_ID = "SELECT id, uniq_code, model_id, characteristic_id, manufacture_id FROM machine_monitoring_schema.machine WHERE manufacture_id = ?";
    //language=PostgreSQL
    private static final String SELECT_ALL_MACHINES = "SELECT id, uniq_code, model_id, characteristic_id, manufacture_id FROM machine_monitoring_schema.machine";
    //language=PostgreSQL
    private static final String SELECT_USERS_BY_MACHINE_ID = "SELECT id, login, password, email, name, address, tel, picture FROM machine_monitoring_schema.user_account " +
            "JOIN machine_monitoring_schema.user_machine ON user_account.id = user_machine.user_id WHERE machine_id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINES_BY_USER_ID = "SELECT id, uniq_code, model_id, characteristic_id, manufacture_id FROM machine_monitoring_schema.machine " +
            "JOIN machine_monitoring_schema.user_machine ON machine.id = user_machine.machine_id WHERE user_id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINE_BY_UNIQ_CODE = "SELECT id, uniq_code, model_id, characteristic_id, manufacture_id FROM machine_monitoring_schema.machine WHERE uniq_code = ?";
    private ConnectionManager connectionManager;

    @Override
    public Long save(MachineDto machineDto) throws DaoException {
        MachineEntity machineEntity = fromDto(machineDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_MACHINE, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            stmt.setString(++i, machineEntity.getUniqCode());
            stmt.setLong(++i, machineEntity.getModelId());
            stmt.setLong(++i, machineEntity.getCharacteristicId());
            stmt.setLong(++i, machineEntity.getManufactureId());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                machineEntity.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to save machine", e);
        }
        return machineEntity.getId();
    }

    @Override
    public boolean update(MachineDto machineDto) throws DaoException {
        MachineEntity machineEntity = fromDto(machineDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_MACHINE)) {
            int i = 0;
            stmt.setString(++i, machineEntity.getUniqCode());
            stmt.setLong(++i, machineEntity.getModelId());
            stmt.setLong(++i, machineEntity.getCharacteristicId());
            stmt.setLong(++i, machineEntity.getId());
            stmt.setLong(++i, machineEntity.getManufactureId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to update machine", e);
        }
    }

    @Override
    public boolean delete(MachineDto machineDto) throws DaoException {
        MachineEntity machineEntity = fromDto(machineDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_MACHINE)) {
            stmt.setLong(1, machineEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete machine", e);
        }
    }

    @Override
    public MachineDto getById(Long id) throws DaoException {
        List<Object> list = new ArrayList<>();
        list.add(1);
        List<MachineEntity> machineEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINE_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                machineEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get machine by id", e);
        }
        return machineEntities.stream()
                .map(this::fromEntity)
                .findFirst()
                .orElseThrow(() -> new DaoException("Failed to get manufacture by id"));
    }

    @Override
    public List<MachineDto> getByManufacture(Long manufactureId) throws DaoException {
        List<MachineEntity> machineEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINE_BY_MANUFACTURE_ID)) {
            stmt.setLong(1, manufactureId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                machineEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get machine by manufacture id", e);
        }
        return machineEntities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delByManufactureAndMachine(Long manufactureId, Long machineId) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_MACHINE_BY_MANUFACTURE_AND_MACHINE)) {
            stmt.setLong(1, machineId);
            stmt.setLong(2, manufactureId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete machine", e);
        }
    }

    @Override
    public boolean delAssignUserMachine(Long machineId) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_MACHINE_AND_USER_ASSIGN)) {
            stmt.setLong(1, machineId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete machine and user assign", e);
        }
    }

    @Override
    public Optional<MachineDto> getByUniqCode(String uniqCode) throws DaoException {
        List<MachineEntity> machineEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINE_BY_UNIQ_CODE)) {
            stmt.setString(1, uniqCode);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                machineEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get machine by uniq code", e);
        }
        return machineEntities.stream()
                .map(this::fromEntity)
                .findFirst();
    }

    @Override
    public List<MachineDto> getAssignMachineByUser(Long userId) throws DaoException {
        List<MachineEntity> machineEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINES_BY_USER_ID)) {
            stmt.setLong(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                MachineEntity machineEntity = parseResultSet(resultSet);
                machineEntities.add(machineEntity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to find machines by user id", e);
        }
        return machineEntities.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<MachineDto> findAll() throws DaoException {
        List<MachineEntity> machineEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_MACHINES)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                MachineEntity machineEntity = parseResultSet(resultSet);
                machineEntities.add(machineEntity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to find all machines", e);
        }
        return machineEntities.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private MachineEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String uniqNumber = resultSet.getString("uniq_code");
        Long modelId = resultSet.getLong("model_id");
        Long characteristicId = resultSet.getLong("characteristic_id");
        Long manufactureId = resultSet.getLong("manufacture_id");
        return MachineEntity.builder()
                .id(id)
                .uniqCode(uniqNumber)
                .modelId(modelId)
                .characteristicId(characteristicId)
                .manufactureId(manufactureId)
                .build();
    }

    private MachineEntity fromDto(MachineDto machineDto) {
        return MachineEntity.builder()
                .id(machineDto.getId())
                .uniqCode(machineDto.getUniqNumber())
                .modelId(machineDto.getModelId())
                .characteristicId(machineDto.getCharacteristicId())
                .manufactureId(machineDto.getManufactureId())
                .build();
    }

    private MachineDto fromEntity(MachineEntity entity) {
        return MachineDto.builder()
                .id(entity.getId())
                .uniqNumber(entity.getUniqCode())
                .modelId(entity.getModelId())
                .characteristicId(entity.getCharacteristicId())
                .manufactureId(entity.getManufactureId())
                .build();
    }
}
