import model.Pages;
import model.dao.DAOException;
import model.computer.Computer;
import model.dao.company.CompanyDAO;
import model.dao.computer.ComputerDAO;
import model.dao.company.CompanyDAOImpl;
import model.dao.computer.ComputerDAOImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.dao.DAOService;
import service.dao.DAOServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestDAOService {

    private DAOService service = new DAOServiceImpl();
    private ComputerDAOImpl dbComputer = ComputerDAO.getInstance();
    private CompanyDAOImpl dbCompany = CompanyDAO.getInstance();
    private int id;
    private static final String NAME_COMPUTER_TEST = "TestComputer";
    private static final String NAME_COMPUTER_TEST_2 = "TestComputer2";
    private static final String NAME_COMPUTER_TEST_3 = "TestComputer3";

    /**
     * Create a TestComputer.
     * @throws DAOException if bug
     */
    @Before
    public void before() throws DAOException{
        id = service.createComputer(new Computer(NAME_COMPUTER_TEST,null,null,null));
    }

    /**
     * Delete the TestComputer.
     * @throws DAOException if bug
     */
    @After
    public void after() throws DAOException{
        service.deleteComputer(id);
    }

    //Computer

    /**
     * Test.
     * service.countComputers();
     * @throws DAOException if bug
     */
    @Test
    public void testCountComputers() throws DAOException{
        int countService = service.countComputers();
        assertTrue("The number of computers returned by the Service corresponds to the base number", countService == dbComputer.countComputers());
    }

    /**
     * Test.
     * service.listComputers(int page)
     * service.listComputers(int page, int sizePage)
     * service.listComputers(String search)
     * @throws DAOException if bug
     */
    @Test
    public void testListComputers() throws DAOException {
        //Test sizePage default
        Pages<Computer> pages1 = service.listComputers(0);
        Pages<Computer> pages2 = service.listComputers(0, Pages.PAGE_SIZE);
        assertEquals("Test : The same return for both methods with the default sizes", pages1, pages2);

        //Test simple research
        Pages<Computer> result = service.listComputers(NAME_COMPUTER_TEST);
        assertTrue("Test : The computer is found ", result.getListPage().size() > 0);

        //Test found all computer
        int count = service.countComputers();
        assertTrue("Test : Found all computer", service.listComputers(0, count).getListPage().size() == count);
    }

    @Test
    public void testGetComputer() throws DAOException {
       Computer search1 = service.listComputers(0).getListPage().get(0);
       assertEquals("Test : Search by id a Computer", search1, service.getComputer(search1.getId()));
    }

    @Test
    public void testShowComputerdetails() throws DAOException {
        Computer search1 = service.listComputers(0).getListPage().get(0);
        assertEquals("Test : Search by id a Computer, compare String details", search1.toStringDetails(), service.showComputerdetails(search1.getId()));
    }

    @Test
    public void testCreateDeleteUpdateComputer() throws DAOException, NumberFormatException {
        Pages<Computer> result = service.listComputers(NAME_COMPUTER_TEST_2);
        int exist = result.getListPage().size();

        int idComputer = service.createComputer(new Computer(NAME_COMPUTER_TEST_2,null,null,null));

        assertTrue("Test : The computer is create, id > 0 expected, but " + idComputer, idComputer != DAOService.ECHEC_FLAG);

        result = service.listComputers(NAME_COMPUTER_TEST_2);
        assertTrue("Test : The computer is add ", result.getListPage().size() == (exist + 1));

        result = service.listComputers(NAME_COMPUTER_TEST_3);
        exist = result.getListPage().size();

        service.updateComputer(new Computer(idComputer,NAME_COMPUTER_TEST_3,null,null,null));

        result = service.listComputers(NAME_COMPUTER_TEST_3);
        assertTrue("Test : The computer is update ", result.getListPage().size() == (exist + 1));

        service.deleteComputer(idComputer);

        result = service.listComputers(NAME_COMPUTER_TEST_3);
        assertTrue("Test : The computer is delete ", result.getListPage().size() == exist);
    }

    //Company

    @Test
    public void testListAllCompanies() throws DAOException {
        //Test found all companies.
        int count = dbCompany.countCompanies();
        assertTrue("Test found all companies", service.listAllCompanies().size() == count);
    }

}
