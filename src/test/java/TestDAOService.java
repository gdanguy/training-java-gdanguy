import model.Pages;
import model.computer.Computer;
import model.company.Company;
import model.dao.company.CompanyDAO;
import model.dao.computer.ComputerDAO;
import model.dao.company.CompanyDAOImpl;
import model.dao.computer.ComputerDAOImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.DAOService;
import service.DAOServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.sql.SQLException;

public class TestDAOService {

    private DAOService service = new DAOServiceImpl();
    private ComputerDAOImpl dbComputer = ComputerDAO.getInstance();
    private CompanyDAOImpl dbCompany = CompanyDAO.getInstance();
    private int id;
    private static final String NAME_COMPUTER_TEST = "TestComputer";

    /**
     * Create a TestComputer.
     * @throws SQLException if bug
     */
    @Before
    public void before() throws SQLException{
        id = Integer.parseInt(service.createComputer(new Computer(NAME_COMPUTER_TEST,null,null,null)).split("id=")[1].split(",")[0]);
    }

    /**
     * Delete the TestComputer.
     * @throws SQLException if bug
     */
    @After
    public void after() throws SQLException{
        service.deleteComputer(id);
    }

    /**
     * Test.
     * service.countComputers();
     * @throws SQLException if bug
     */
    @Test
    public void testCountComputers() throws SQLException{
        int countService = service.countComputers();
        assertTrue("The number of computers returned by the Service corresponds to the base number", countService == dbComputer.countComputers());
    }

    /**
     * Test.
     * service.listComputers(int page)
     * service.listComputers(int page, int sizePage)
     * service.listComputers(String search)
     * @throws SQLException if bug
     */
    @Test
    public void testListComputers() throws SQLException {
        //Test sizePage default
        Pages<Computer> pages1 = service.listComputers(0);
        Pages<Computer> pages2 = service.listComputers(0, Pages.PAGE_SIZE);
        assertEquals("Test : The same return for both methods with the default sizes", pages1, pages2);

        //Test simple research
        Pages<Computer> result = service.listComputers(NAME_COMPUTER_TEST);
        assertTrue("Test : The computer is found ", result.getListPage().size() > 0);

        //Test found all computer
        int count = service.countComputers();
        assertTrue("Test : Found all computer", service.listComputers(0,count).getListPage().size() == count);
    }

    /**
     * Test.
     * service.listCompanies(int page)
     * @throws SQLException if bug
     */
    @Test
    public void listCompanies() throws SQLException {
        //Test found all companies.
        int count = dbCompany.countCompanies();
        assertTrue("Test found all companies", service.listAllCompanies().size() == count);
    }
    /*
    @Test
    public void showComputerdetails() throws SQLException {

    }

    @Test
    public void getComputer() throws SQLException {

    }

    @Test
    public void createComputer() throws SQLException, NumberFormatException {

    }

    @Test
    public void updateComputer() throws SQLException, NumberFormatException {

    }

    @Test
    public void deleteComputer() throws SQLException {

    }

    @Test
    public void getCompany() throws SQLException {

    }

    @Test
    public void listAllCompanies() throws SQLException {

    }
    */
}
