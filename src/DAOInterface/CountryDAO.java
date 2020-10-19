package DAOInterface;

import javafx.collections.ObservableList;
import model.Country;
import java.sql.SQLException;

public interface CountryDAO {
    Country getCountry(String countryName);
    Country getCountry(int countryID);
    ObservableList<Country> getAllCountries() throws SQLException;
    void addCountry(Country newCountry) throws SQLException;
    void updateCountry(Country toUpdate) throws SQLException;
    void deleteCountry(Country deleteTarget);
}
