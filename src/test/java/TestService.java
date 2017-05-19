import exception.CDBException;
import model.GenericBuilder;
import model.company.Company;
import model.computer.Computer;
import model.dao.company.CompanyDAO;
import model.dao.computer.ComputerDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.CompanyService;
import service.ComputerService;
import service.ComputerServiceImpl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
@Transactional
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

    @Test(expected = CDBException.class)
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

    @Test(expected = CDBException.class)
    public void testDeleteComputerInvalid() {
        assertTrue("Test : DeleteComputer(-1) return false", serviceComputer.delete(-1).equals(ComputerServiceImpl.INVALID_ID));
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

    @Test(expected = CDBException.class)
    public void testCreateCompaniesNull() {
        assertTrue("Test : CreateCompany(null) return false", serviceCompany.create(null) == serviceCompany.ECHEC_FLAG);
    }

    @Test(expected = CDBException.class)
    public void testCreateCompaniesInvalid() {
        serviceCompany.create(GenericBuilder.of(Company::new)
                .with(Company::setId, -1)
                .with(Company::setName, null)
                .build());
        serviceCompany.create(GenericBuilder.of(Company::new)
                .with(Company::setId, -1)
                .with(Company::setName, "")
                .build());
    }

    @Test
    @Ignore
    public void testDeleteCompanies() {
        //delete this company
        boolean succes = serviceCompany.deleteLast();
        assertTrue("Test Delete a company", succes);
    }

    @Test(expected = CDBException.class)
    public void testDeleteCompaniesInvalid() {
        assertTrue("Test : DeleteCompany(null) return false", !serviceCompany.delete(-1));
    }
}
