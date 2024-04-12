package dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DTOMapper {
	public static <T> T mapResultSetToDTO(ResultSet rs, Class<T> dtoClass) throws SQLException {
        T dto = null;
        try {
            dto = dtoClass.getDeclaredConstructor().newInstance();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                String columnName = rsmd.getColumnName(i);
                Object columnValue = rs.getObject(i);
                Field field = dtoClass.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(dto, columnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
}
