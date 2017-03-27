package service.mappy;

import model.Pages;
import model.computer.Computer;
import service.mappy.computer.ComputerDTO;

import java.util.ArrayList;

public interface ComputerMapper {

    /**
     * Convert Pages<Computer> to Pages<ComputerDTO>.
     * @param pages Pages of Computer
     * @return the Pages<ComputerDTO> generated
     */
    Pages<ComputerDTO> toDTO(Pages<Computer> pages);

    /**
     * Convert ArrayList<Computer> to ArrayList<ComputerDTO>.
     * @param list ArrayList of Computer
     * @return the ArrayList<ComputerDTO> generated
     */
    ArrayList<ComputerDTO> toDTO(ArrayList<Computer> list);

    /**
     * Return the Instance of ComputerServiceImpl.
     * @return the Instance of ComputerServiceImpl
     */
    static ComputerMapperImpl getInstance() {
        return ComputerMapperImpl.INSTANCE;
    }
}