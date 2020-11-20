package DAOInterface;

import javafx.collections.ObservableList;
import model.Division;

import java.sql.SQLException;

/**
 * Defines interface for working with Division objects.
 */
public interface DivisionDAO {
    Division getDivision(String divisionName);

    Division getDivision(int divisionID);

    ObservableList<Division> getAllDivisions() throws SQLException;

    void addDivision(Division newDivision) throws SQLException;

    void updateDivision(Division toUpdate) throws SQLException;

    void deleteDivision(Division deleteTarget);
}
