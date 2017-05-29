package rest;

import core.model.Company;
import core.utils.Constant;
import core.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.company.CompanyService;

import java.util.List;

/**
 * Created by ebiz on 29/05/17.
 */
@RestController
@RequestMapping("/rest/company")
public class CompanyRestController {

    private final CompanyService serviceCompany;

    //TODO faire les retours erreurs json

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
     * @return Company
     */
    @GetMapping("/{id}")
    public Company get(@PathVariable(value = Constant.ID) Integer id) {
        return serviceCompany.get(id);
    }

    /**
     * Get all.
     * @return List<Company>
     */
    @GetMapping()
    public List<Company> get() {
        return serviceCompany.listAll();
    }

    /**
     * Get a Page<Company>.
     * @param page Integer
     * @return Page<Company>
     */
    @GetMapping("/page/{" + Constant.PAGE + "}")
    public Page<Company> getPage(@PathVariable(value = Constant.PAGE) Integer page) {
        return serviceCompany.list(page);
    }

    /**
     * Create a company.
     * @param company Company
     * @return id of the new Company
     */
    @PostMapping("/add")
    public int add(@RequestBody Company company) {
        return serviceCompany.create(company);
    }

    /**
     * Delete a Company.
     * @param id Integer
     */
    @PostMapping("/delete/{" + Constant.ID + "}")
    public void delete(@PathVariable(value = Constant.ID) Integer id) {
        serviceCompany.delete(id);
    }
}
