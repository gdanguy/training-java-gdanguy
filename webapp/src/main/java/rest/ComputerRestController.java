package rest;

import core.model.Computer;
import core.utils.Constant;
import core.utils.Page;
import core.validator.Validateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.computer.ComputerService;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by ebiz on 29/05/17.
 */
@RestController
@RequestMapping("/rest/computer")
public class ComputerRestController {
    private final ComputerService serviceComputer;

    /**
     * .
     * @param serviceComputer .
     */
    @Autowired
    public ComputerRestController(ComputerService serviceComputer) {
        this.serviceComputer = serviceComputer;
    }

    /**
     * Get a computer by id.
     * @param id Integer
     * @return Computer
     */
    @GetMapping("/{" + Constant.ID + "}")
    public Computer get(@PathVariable(value = Constant.ID) Integer id) {
        if (id == null || id < 1) {
            throw new InvalidParameterException("Invalid ID");
        }
        return serviceComputer.get(id);
    }

    /**
     * Get all.
     * @return List<Computer>
     */
    @GetMapping()
    public List<Computer> get() {
        return serviceComputer.getAll();
    }

    /**
     * Get page.
     * @param page Integer
     * @return Page<Computer>
     */
    @GetMapping("/page")
    public Page<Computer> getPage(@RequestParam(value = Constant.PAGE) Integer page) {
        if (page == null || page < 0) {
            throw new InvalidParameterException("Invalid page");
        }
        return serviceComputer.list(page);
    }

    /**
     * Get page.
     * @param page Integer
     * @param pageSize Integer
     * @return Page<Computer>
     */
    @GetMapping("/page2")
    public Page<Computer> getPage(@RequestParam(value = Constant.PAGE) Integer page,
                                  @RequestParam(value = Constant.SIZE_PAGE) Integer pageSize) {
        if (page == null || pageSize == null || page < 0 || pageSize < 1) {
            throw new InvalidParameterException("Invalid page");
        }
        return serviceComputer.list(page, pageSize);
    }

    /**
     * Get page.
     * @param page Integer
     * @param pageSize Integer
     * @param order String
     * @return Page<Computer>
     */
    @GetMapping("/page3")
    public Page<Computer> getPage(@RequestParam(value = Constant.PAGE) Integer page,
                                  @RequestParam(value = Constant.SIZE_PAGE) Integer pageSize,
                                  @RequestParam(value = Constant.ORDER) String order) {
        if (page == null || pageSize == null || order == null || page < 0 || pageSize < 1 || order.isEmpty()) {
            throw new InvalidParameterException("Invalid page");
        }
        return serviceComputer.list(page, pageSize, order);
    }

    /**
     * Create a computer.
     * @param computer Computer
     * @return id of the new Computer
     */
    @PostMapping("/add")
    public int add(@RequestBody Computer computer) {
        String[] errors = Validateur.validComputer(computer);
        if (errors.length > 0) {
            String message = "";
            for (String error : errors) {
                message += error;
            }
            throw new InvalidParameterException(message);
        }
        return serviceComputer.create(computer);
    }

    /**
     * Update a computer, get the old version with the computer.id and update other variable.
     * @param computer Computer
     */
    @PostMapping("/edit")
    public void edit(@RequestBody Computer computer) {
        String[] errors = Validateur.validComputer(computer);
        if (errors.length > 0) {
            String message = "";
            for (String error : errors) {
                message += error;
            }
            throw new InvalidParameterException(message);
        }
        serviceComputer.update(computer);
    }

    /**
     * Delete a computer.
     * @param id Integer
     */
    @PostMapping("/delete/{" + Constant.ID + "}")
    public void delete(@PathVariable(value = Constant.ID) Integer id) {
        if (id == null || id < 1) {
            throw new InvalidParameterException("Invalid ID");
        }
        serviceComputer.delete(id);
    }

    /**
     * Delete a List of computer.
     * @param computers List<Integer>
     */
    @PostMapping("/delete")
    public void delete(@RequestBody List<Integer> computers) {
        for (Integer id : computers) {
            if (id == null || id < 1) {
                throw new InvalidParameterException("Invalid ID");
            }
        }
        serviceComputer.delete(computers);
    }
}
