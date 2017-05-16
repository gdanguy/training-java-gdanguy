import exception.CDBException;
import model.GenericBuilder;
import model.Page;
import model.company.Company;
import model.computer.Computer;
import model.dao.company.CompanyDAO;
import model.dao.computer.ComputerDAO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.CompanyService;
import service.ComputerService;
import service.ComputerServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class TestService {
    @Resource
    private ComputerService serviceComputer;
    @Resource
    private CompanyService serviceCompany;
    @Resource
    private ComputerDAO dbComputer;
    @Resource
    private CompanyDAO dbCompany;

    static final String NAME_COMPUTER_TEST = "TestComputer";
    static final String NAME_COMPUTER_TEST_2 = "TestComputer2";
    static final String NAME_COMPANY = "TestCompany";

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    //Computer
    @Test
    public void testCountComputers() throws CDBException {
        int countService = serviceComputer.count();
        assertTrue("The number of computers returned by the Service corresponds to the base number", countService == dbComputer.count());
    }

    @Test
    public void testListComputers() {
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
    public void testGetComputer() {
       Computer search1 = serviceComputer.list(0).getListPage().get(0);
       assertEquals("Test : Search by id a Computer", search1, serviceComputer.get(search1.getId()));
    }

    @Test
    public void testShowComputerdetails() {
        Computer search1 = serviceComputer.list(0).getListPage().get(0);
        assertEquals("Test : Search by id a Computer, compare String details", search1.toStringDetails(), serviceComputer.get(search1.getId()).toStringDetails());
    }

    @Test
    public void testCreateComputer() {
        int before = serviceComputer.count();

        int idComputer = serviceComputer.create(
                GenericBuilder.of(Computer::new)
                        .with(Computer::setId, -1)
                        .with(Computer::setName, NAME_COMPUTER_TEST)
                        .build());
        int after = serviceComputer.count();

        assertTrue("Test : The computer is create, id > 0 expected, but " + idComputer, idComputer != ComputerService.ECHEC_FLAG);
        assertTrue("Test : The computer is create, one more computer in base", before + 1 == after);
    }

    @Test(expected = CDBException.class)
    public void testCreateComputerNull() {
        serviceComputer.create(null);
    }

    @Test(expected = CDBException.class)
    public void testCreateComputerInvalid() {
        serviceComputer.create(GenericBuilder.of(Computer::new)
                            .with(Computer::setId, -1)
                            .with(Computer::setName, null)
                            .build());

        serviceComputer.create(GenericBuilder.of(Computer::new)
                        .with(Computer::setId, -1)
                        .with(Computer::setName, "")
                        .build());
    }

    @Test
    public void testUpdateComputer() {
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
        assertTrue("Test : The computer is update, one more computer in the search field ", before + 1 == after);
    }

    @Test
    public void testUpdateComputerNull() {
        assertTrue("Test : UpdateComputer(null) return false", !serviceComputer.update(null));
    }

    @Test
    public void testDeleteComputer() {
        int before = serviceComputer.count();
        serviceComputer.deleteLast();
        int after = serviceComputer.count();
        assertTrue("Test : the computer is delete, one less computer in base", before - 1 == after);
    }

    @Test
    public void testDeleteComputerInvalid() {
        assertTrue("Test : DeleteComputer(-1) return false", serviceComputer.delete(-1).equals(ComputerServiceImpl.INVALID_ID));
    }

    @Test
    public void testDeleteListComputer() {
        int before = serviceComputer.count();
        serviceComputer.deleteMultiLast();
        int after = serviceComputer.count();
        assertTrue("Test : the computer is delete, one less computer in base", before - 2 == after);
    }

    @Test(expected = CDBException.class)
    public void testDeleteListComputerNull() {
        serviceComputer.delete(null);
    }

    @Test(expected = CDBException.class)
    public void testDeleteListComputerEmpty() {
        serviceComputer.delete(new ArrayList<Integer>());
    }

    //Company

    @Test
    public void testListAllCompanies() throws CDBException {
        //Test found all companies.
        int count = dbCompany.count();
        assertTrue("Test found all companies", serviceCompany.listAll().size() == count);
    }

    @Test
    public void testCreateCompanies() {
        //Create a company
        int id = serviceCompany.create(
                GenericBuilder.of(Company::new)
                        .with(Company::setId, -1)
                        .with(Company::setName, NAME_COMPANY)
                        .build());
        assertTrue("Test Create a company", id >= 0);
    }

    @Test
    public void testCreateCompaniesNull() {
        assertTrue("Test : CreateCompany(null) return false", serviceCompany.create(null) == serviceCompany.ECHEC_FLAG);
    }

    @Test
    public void testCreateCompaniesInvalid() {
        assertTrue("Test : CreateComputer(invalid ComputerName (name is null)) return -1",
                serviceCompany.create(GenericBuilder.of(Company::new)
                        .with(Company::setId, -1)
                        .with(Company::setName, null)
                        .build()
                ) == serviceCompany.ECHEC_FLAG);

        assertTrue("Test : CreateComputer(invalid ComputerName (name is empty)) return -1",
                serviceCompany.create(GenericBuilder.of(Company::new)
                        .with(Company::setId, -1)
                        .with(Company::setName, "")
                        .build()
                ) == serviceCompany.ECHEC_FLAG);
    }

    @Test
    @Ignore
    public void testDeleteCompanies() {
        //delete this company
        boolean succes = serviceCompany.deleteLast();
        assertTrue("Test Delete a company", succes);
    }

    @Test
    public void testDeleteCompaniesInvalid() {
        assertTrue("Test : DeleteCompany(null) return false", !serviceCompany.delete(-1));
    }
}
