package rest;

import core.model.Company;
import core.utils.Constant;
import core.utils.Page;
import core.validator.Validateur;
import map.CompanyMapperDTO;
import map.company.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.company.CompanyService;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by ebiz on 29/05/17.
 */
@RestController
@RequestMapping("/rest/company")
public class CompanyRestController {
    private final CompanyService serviceCompany;
    private final CompanyMapperDTO companyMapperDTO = new CompanyMapperDTO();

    /**
     * .
     * @param serviceCompany  .
     */
    @Autowired
    public CompanyRestController(CompanyService serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    /**
     * Get a company by id.
     * @param id Integer
     * @return CompanyDTO
     */
    @GetMapping("/{id}")
    public CompanyDTO get(@PathVariable(value = Constant.ID) Integer id) {
        if (id == null || id < 1) {
            throw new InvalidParameterException("Invalid ID");
        }
        return companyMapperDTO.to(serviceCompany.get(id));
    }

    /**
     * Get all.
     * @return List<CompanyDTO>
     */
    @GetMapping()
    public List<CompanyDTO> get() {
        return (List) companyMapperDTO.toList(serviceCompany.listAll());
    }

    /**
     * Get a Page<CompanyDTO>.
     * @param page Integer
     * @return Page<CompanyDTO>
     */
    @GetMapping("/page/{" + Constant.PAGE + "}")
    public Page<CompanyDTO> getPage(@PathVariable(value = Constant.PAGE) Integer page) {
        if (page == null || page < 0) {
            throw new InvalidParameterException("Invalid page");
        }
        return companyMapperDTO.toPage(serviceCompany.list(page));
    }

    /**
     * Create a company.
     * @param companyDTO CompanyDTO
     * @return id of the new Company
     */
    @PostMapping()
    public int add(@RequestBody CompanyDTO companyDTO) {
        Company company = companyMapperDTO.from(companyDTO);
        String errors = Validateur.validCompany(company);
        if (errors != null && !errors.isEmpty()) {
            throw new InvalidParameterException(errors);
        }
        return serviceCompany.create(company);
    }

    /**
     * Delete a Company.
     * @param id Integer
     */
    @DeleteMapping("/{" + Constant.ID + "}")
    public void delete(@PathVariable(value = Constant.ID) Integer id) {
        if (id == null || id < 1) {
            throw new InvalidParameterException("Invalid ID");
        }
        serviceCompany.delete(id);
    }
}
