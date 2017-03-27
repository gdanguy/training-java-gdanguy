package service.mappy;

import model.Pages;
import model.computer.Computer;
import service.mappy.computer.ComputerDTO;

import java.util.ArrayList;

/**
 * Created by ebiz on 23/03/17.
 */
public enum ComputerMapperImpl implements ComputerMapper {

    INSTANCE;

    /**
     * Convert Pages<Computer> to Pages<ComputerDTO>.
     * @param pages Pages of Computer
     * @return the Pages<ComputerDTO> generated
     */
    public Pages<ComputerDTO> toDTO(Pages<Computer> pages)  {
        return new Pages<ComputerDTO>(toDTO(pages.getListPage()), pages.getPageSize());
    }

    /**
     * Convert ArrayList<Computer> to ArrayList<ComputerDTO>.
     * @param list ArrayList of Computer
     * @return the ArrayList<ComputerDTO> generated
     */
    public ArrayList<ComputerDTO> toDTO(ArrayList<Computer> list) {
        ArrayList<ComputerDTO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(new ComputerDTO(list.get(i)));
        }
        return result;
    }
}
