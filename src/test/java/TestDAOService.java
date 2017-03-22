import org.junit.Test;
import service.DAOService;
import service.DAOServiceImpl;

import javax.validation.constraints.AssertTrue;
import java.sql.SQLException;

public class TestServiceDAO {

    @Test
    public void countComputers() throws SQLException{
        DAOService service = new DAOServiceImpl();
        int count = service.countComputers();
        AssertTrue(count == 1);
    }
}
