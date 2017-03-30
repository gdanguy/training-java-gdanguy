package service.mappy;

import model.Page;
import model.computer.Computer;
import service.mappy.computer.ComputerDTO;

import java.util.ArrayList;

/**
 * Created by ebiz on 23/03/17.
 */
public enum ComputerMapperImpl implements ComputerMapper {

    INSTANCE;

    /**
     * Convert Page<Computer> to Page<ComputerDTO>.
     * @param page Page of Computer
     * @return the Page<ComputerDTO> generated
     */
    public Page<ComputerDTO> toDTO(Page<Computer> page)  {
        return new Page<ComputerDTO>(toDTO(page.getListPage()), page.getPageSize());
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
