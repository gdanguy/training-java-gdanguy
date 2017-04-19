package service.mappy;

import model.Page;
import model.computer.Computer;
import service.mappy.computer.ComputerDTO;

import java.util.ArrayList;

public interface ComputerMapper {

    /**
     * Convert Page<Computer> to Page<ComputerDTO>.
     * @param page Page of Computer
     * @return the Page<ComputerDTO> generated
     */
    Page<ComputerDTO> toDTO(Page<Computer> page);

    /**
     * Convert ArrayList<Computer> to ArrayList<ComputerDTO>.
     * @param list ArrayList of Computer
     * @return the ArrayList<ComputerDTO> generated
     */
    ArrayList<ComputerDTO> toDTO(ArrayList<Computer> list);



}