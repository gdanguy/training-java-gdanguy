package rest;

import core.model.Computer;
import core.utils.Constant;
import core.utils.Page;
import core.validator.Validateur;
import map.ComputerMapperDTO;
import map.computer.ComputerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@CrossOrigin
@RequestMapping("/api/computers")
public class ComputerRestController {
    private final ComputerService serviceComputer;
    private final ComputerMapperDTO computerMapperDTO = new ComputerMapperDTO();

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
     * @return ComputerDTO
     */
    @GetMapping("/{" + Constant.ID + "}")
    public ComputerDTO get(@PathVariable(value = Constant.ID) Integer id) {
        if (id == null || id < 1) {
            throw new InvalidParameterException("Invalid ID");
        }
        return computerMapperDTO.to(serviceComputer.get(id));
    }

    /**
     * Get all.
     * @return List<ComputerDTO>
     */
    @GetMapping()
    public List<ComputerDTO> get() {
        return (List) computerMapperDTO.toList(serviceComputer.getAll());
    }

    /**
     * Get page.
     * @param page Integer
     * @param pageSize Integer
     * @param order String
     * @return Page<ComputerDTO>
     */
    @GetMapping("/page")
    public Page<ComputerDTO> getPage(@RequestParam(value = Constant.PAGE) Integer page,
                                  @RequestParam(value = Constant.SIZE_PAGE, required = false) Integer pageSize,
                                  @RequestParam(value = Constant.ORDER, required = false) String order) {
        if (page == null || page < 0) {
            throw new InvalidParameterException("Invalid page");
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = Page.PAGE_SIZE;
        }
        if (order == null || order.isEmpty()) {
            order = Constant.ORDER_NAME_ASC;
        }
        return computerMapperDTO.toPage(serviceComputer.list(page, pageSize, order));
    }

    /**
     * Create a computer.
     * @param computerDTO ComputerDTO
     * @return id of the new Computer
     */
    @PostMapping()
    public ResponseEntity<?> add(@RequestBody ComputerDTO computerDTO) {
        Computer computer = computerMapperDTO.from(computerDTO);
        String[] errors = Validateur.validComputer(computer);
        if (errors != null && errors.length > 0) {
            String message = "";
            for (String error : errors) {
                message += error;
            }
            throw new InvalidParameterException(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceComputer.create(computer));
    }

    /**
     * Update a computer, get the old version with the computer.id and update other variable.
     * @param computerDTO Computer
     */
    @PutMapping()
    public void edit(@RequestBody ComputerDTO computerDTO) {
        Computer computer = computerMapperDTO.from(computerDTO);
        String[] errors = Validateur.validComputer(computer);
        if (errors != null && errors.length > 0) {
            String message = "";
            for (String error : errors) {
                message += error;
            }
            throw new InvalidParameterException(message);
        }
        serviceComputer.update(computer);
    }

    /**
     * Delete a List of computer.
     * @param computers List<Integer>
     */
    @DeleteMapping()
    public void delete(@RequestBody List<Integer> computers) {
        serviceComputer.delete(computers);
    }
}
