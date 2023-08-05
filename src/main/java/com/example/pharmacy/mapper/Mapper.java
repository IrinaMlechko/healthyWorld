//package com.example.pharmacy.mapper;
//
//import com.example.pharmacy.entity.Credentials;
//import com.example.pharmacy.entity.Medicine;
//import com.example.pharmacy.entity.User;
//import com.example.pharmacy.util.Role;
//
//import java.math.BigDecimal;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//public class Mapper {
//
//    private Mapper(){}
//    public static User mapUserFromResultSet(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt(DBFields.USER_ID);
//        String firstName = resultSet.getString(DBFields.USER_FIRST_NAME);
//        String lastName = resultSet.getString(DBFields.USER_LAST_NAME);
//        LocalDate dateOfBirth = resultSet.getDate(DBFields.USER_DATE_OF_BIRTH).toLocalDate();
//        return User.newBuilder()
//                .setId(id)
//                .setFirstName(firstName)
//                .setLastName(lastName)
//                .setDateOfBirth(dateOfBirth)
//                .build();
//    }
//
//    public static Credentials mapCredentialsFromResultSet(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt(DBFields.CREDENTIALS_ID);
//        String login = resultSet.getString(DBFields.CREDENTIALS_LOGIN);
//        String password = resultSet.getString(DBFields.CREDENTIALS_PASSWORD);
//        Role role = Role.valueOf(resultSet.getString(DBFields.CREDENTIALS_ROLE));
//        int userId = resultSet.getInt(DBFields.CREDENTIALS_USER_ID);
//        return Credentials.newBuilder()
//                .setId(id)
//                .setLogin(login)
//                .setPassword(password)
//                .setRole(role)
//                .setUserId(userId)
//                .build();
//    }
//
//    public static void mapUserToResultSet(User user, ResultSet resultSet) throws SQLException {
//        resultSet.updateInt(DBFields.USER_ID, user.getId());
//        resultSet.updateString(DBFields.USER_FIRST_NAME, user.getFirstName());
//        resultSet.updateString(DBFields.USER_LAST_NAME, user.getLastName());
//        resultSet.updateDate(DBFields.USER_DATE_OF_BIRTH, java.sql.Date.valueOf(user.getDateOfBirth()));
//    }
//
//    public static void mapCredentialsToResultSet(Credentials credentials, ResultSet resultSet) throws SQLException {
//        resultSet.updateInt(DBFields.CREDENTIALS_ID, credentials.getId());
//        resultSet.updateString(DBFields.CREDENTIALS_LOGIN, credentials.getLogin());
//        resultSet.updateString(DBFields.CREDENTIALS_PASSWORD, credentials.getPassword());
//        resultSet.updateString(DBFields.CREDENTIALS_ROLE, credentials.getRole().toString());
//        resultSet.updateInt(DBFields.CREDENTIALS_USER_ID, credentials.getUserId());
//    }
//
//    public static void mapUserToStatement(User user, PreparedStatement statement) throws SQLException {
//        statement.setString(1, user.getFirstName());
//        statement.setString(2, user.getLastName());
//        statement.setDate(3, java.sql.Date.valueOf(user.getDateOfBirth()));
//    }
//
//    public static void mapCredentialsToStatement(Credentials credentials, PreparedStatement statement) throws SQLException {
//        statement.setString(1, credentials.getLogin());
//        statement.setString(2, credentials.getPassword());
//        statement.setObject(3, credentials.getRole(), Types.OTHER);
//        statement.setInt(4, credentials.getUserId());
//    }
//
//    public static Medicine mapMedicineFromResultSet(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt(DBFields.MEDICINE_ID);
//        String name = resultSet.getString(DBFields.MEDICINE_NAME);
//        String manufacturer = resultSet.getString(DBFields.MEDICINE_MANUFACTURER);
//        BigDecimal price = resultSet.getBigDecimal(DBFields.MEDICINE_PRICE);
//        String description = resultSet.getString(DBFields.MEDICINE_DESCRIPTION);
//        LocalDateTime createdAt = resultSet.getTimestamp(DBFields.MEDICINE_CREATED_AT).toLocalDateTime();
//        boolean prescriptionRequired = resultSet.getBoolean(DBFields.MEDICINE_PRESCRIPTION_REQUIRED);
//        return Medicine.newBuilder()
//                .setId(id)
//                .setName(name)
//                .setManufacturer(manufacturer)
//                .setPrice(price)
//                .setDescription(description)
//                .setCreatedAt(createdAt)
//                .setPrescriptionRequired(prescriptionRequired)
//                .build();
//    }
//
//    public static void mapMedicineToResultSet(Medicine medicine, ResultSet resultSet) throws SQLException {
//        resultSet.updateInt(DBFields.MEDICINE_ID, medicine.getId());
//        resultSet.updateString(DBFields.MEDICINE_NAME, medicine.getName());
//        resultSet.updateString(DBFields.MEDICINE_MANUFACTURER, medicine.getManufacturer());
//        resultSet.updateBigDecimal(DBFields.MEDICINE_PRICE, medicine.getPrice());
//        resultSet.updateString(DBFields.MEDICINE_DESCRIPTION, medicine.getDescription());
//        resultSet.updateTimestamp(DBFields.MEDICINE_CREATED_AT, java.sql.Timestamp.valueOf(medicine.getCreatedAt()));
//        resultSet.updateBoolean(DBFields.MEDICINE_PRESCRIPTION_REQUIRED, medicine.isPrescriptionRequired());
//    }
//
//    public static void mapMedicineToStatement(Medicine medicine, PreparedStatement statement) throws SQLException {
//        statement.setString(1, medicine.getName());
//        statement.setString(2, medicine.getManufacturer());
//        statement.setBigDecimal(3, medicine.getPrice());
//        statement.setString(4, medicine.getDescription());
//        statement.setTimestamp(5, java.sql.Timestamp.valueOf(medicine.getCreatedAt()));
//        statement.setBoolean(6, medicine.isPrescriptionRequired());
//    }
//}
