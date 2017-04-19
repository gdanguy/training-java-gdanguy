package service.mappy;

import model.GenericBuilder;
import model.Page;
import model.computer.Computer;
import service.mappy.computer.ComputerDTO;

import java.util.ArrayList;

/**
 * Created by ebiz on 23/03/17.
 */
public class ComputerMapperImpl implements ComputerMapper {

    /**
     * Convert Page<Computer> to Page<ComputerDTO>.
     * @param page Page of Computer
     * @return the Page<ComputerDTO> generated
     */
    public Page<ComputerDTO> toDTO(Page<Computer> page) {
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
            Computer c = list.get(i);
            result.add(GenericBuilder.of(ComputerDTO::new)
                    .with(ComputerDTO::setId, c.getId())
                    .with(ComputerDTO::setName, c.getName())
                    .with(ComputerDTO::setIntroduced, c.getIntroduced())
                    .with(ComputerDTO::setDiscontinued, c.getDiscontinued())
                    .with(ComputerDTO::setCompany, c.getCompany())
                    .build());
        }
        return result;
    }
}
