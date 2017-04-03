import model.GenericBuilder;
import model.Page;
import model.company.Company;
import model.dao.DAOException;
import model.computer.Computer;
import model.dao.DAOFactory;
import model.dao.company.CompanyDAO;
import model.dao.computer.ComputerDAO;
import model.dao.company.CompanyDAOImpl;
import model.dao.computer.ComputerDAOImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.CompanyService;
import service.ComputerService;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private ComputerService serviceComputer = ComputerService.getInstance();
    private CompanyService serviceCompany = CompanyService.getInstance();
    private ComputerDAOImpl dbComputer = ComputerDAO.getInstance();
    private CompanyDAOImpl dbCompany = CompanyDAO.getInstance();
    public static final String NAME_COMPUTER_TEST = "TestComputer";
    public static final String NAME_COMPUTER_TEST_2 = "TestComputer2";

    public static final String NAME_COMPANY = "TestCompany";

    /**
     * Create a TestComputer.
     * @throws DAOException if bug
     */
    @Before
    public void before() throws DAOException{
        //daoFactory.open();
        //on ne commit jamais les tests
        //daoFactory.startTransaction();
    }

    /**
     * Delete the TestComputer.
     * @throws DAOException if bug
     */
    @After
    public void after() throws DAOException{
        //daoFactory.roolback();
        //daoFactory.close();
    }

    //Computer

    /**
     * Test.
     * serviceComputer.count();
     * @throws DAOException if bug
     */
    @Test
    public void testCountComputers() throws DAOException{
        int countService = serviceComputer.count();
        assertTrue("The number of computers returned by the Service corresponds to the base number", countService == dbComputer.count());
    }

    /**
     * Test.
     * serviceComputer.list(int page)
     * serviceComputer.list(int page, int sizePage)
     * serviceComputer.list(String search)
     * @throws DAOException if bug
     */
    @Test
    public void testListComputers() throws DAOException {
        //Test sizePage default
        Page<Computer> page1 = serviceComputer.list(0);
        Page<Computer> page2 = serviceComputer.list(0, Page.PAGE_SIZE);
        assertEquals("Test : The same return for both methods with the default sizes", page1, page2);

        //Test simple research
        Page<Computer> result = serviceComputer.list(NAME_COMPUTER_TEST);
        assertTrue("Test : The computer is found ", result.getListPage().size() > 0);

        //Test found all computer
        int count = serviceComputer.count();
        assertTrue("Test : Found all computer", serviceComputer.list(0, count).getListPage().size() == count);
    }

    @Test
    public void testGetComputer() throws DAOException {
       Computer search1 = serviceComputer.list(0).getListPage().get(0);
       assertEquals("Test : Search by id a Computer", search1, serviceComputer.get(search1.getId()));
    }

    @Test
    public void testShowComputerdetails() throws DAOException {
        Computer search1 = serviceComputer.list(0).getListPage().get(0);
        assertEquals("Test : Search by id a Computer, compare String details", search1.toStringDetails(), serviceComputer.get(search1.getId()).toStringDetails());
    }

    @Test
    public void testCreateComputer() throws DAOException {
        int before = serviceComputer.count();

        int idComputer = serviceComputer.create(
                GenericBuilder.of(Computer::new)
                        .with(Computer::setId, -1)
                        .with(Computer::setName, NAME_COMPUTER_TEST)
                        .build());
        int after = serviceComputer.count();

        assertTrue("Test : The computer is create, id > 0 expected, but " + idComputer, idComputer != CompanyService.ECHEC_FLAG);
        assertTrue("Test : The computer is create, one more computer in base", before +1 == after);
    }

    @Test
    public void testUpdateComputer() throws DAOException {
        ArrayList<Computer> c = serviceComputer.list(0,serviceComputer.count()).getListPage();
        int cpt = 0;
        //We search a computer who not have the name at NAME_COMPUTER_TEST_2
        while (c.get(cpt).getName().equals(NAME_COMPUTER_TEST_2)) {
            cpt++;
        }
        int idComputer = c.get(cpt).getId();
        int before = serviceComputer.list(NAME_COMPUTER_TEST_2).getListPage().size();
        serviceComputer.update(
                GenericBuilder.of(Computer::new)
                        .with(Computer::setId, idComputer)
                        .with(Computer::setName, NAME_COMPUTER_TEST_2)
                        .build());

        int after = serviceComputer.list(NAME_COMPUTER_TEST_2).getListPage().size();
        assertTrue("Test : The computer is update, one more computer in the search field ", before +1 == after);
    }

    @Test
    public void testDeleteComputer() throws DAOException {
        int before = serviceComputer.count();
        serviceComputer.deleteLast();
        int after = serviceComputer.count();
        assertTrue("Test : the computer is delete, one less computer in base", before -1 == after);
    }

    //Company

    @Test
    public void testListAllCompanies() throws DAOException {
        //Test found all companies.
        int count = dbCompany.count();
        assertTrue("Test found all companies", serviceCompany.listAll().size() == count);
    }

    @Test
    public void testCreateCompanies() throws DAOException {
        //Create a company
        int id = dbCompany.create(
                GenericBuilder.of(Company::new)
                        .with(Company::setId, -1)
                        .with(Company::setName, NAME_COMPANY)
                        .build());
        assertTrue("Test Create a company", id>=0);
    }

    @Test
    public void testDeleteCompanies() throws DAOException {
        //delete this company
        boolean succes = serviceCompany.delete(serviceCompany.listAll().get(0).getId());
        assertTrue("Test Delete a company", succes);
    }


}
