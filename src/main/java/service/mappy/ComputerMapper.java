package service.mappy;

import model.GenericBuilder;
import model.computer.Computer;
import service.mappy.computer.ComputerDTO;

public class ComputerMapper implements Mapper<Computer, ComputerDTO> {
    /**
     * Computer from ComputerDTO.
     * @param computerDTO in
     * @return Computer
     */
    @Override
    public Computer from(ComputerDTO computerDTO) {
        return GenericBuilder.of(Computer::new)
                .with(Computer::setId, computerDTO.getId())
                .with(Computer::setName, computerDTO.getName())
                .build();
    }

    /**
     * Computer to ComputerDTO.
     * @param computer in
     * @return ComputerDTO
     */
    @Override
    public ComputerDTO to(Computer computer) {
        return GenericBuilder.of(ComputerDTO::new)
                .with(ComputerDTO::setId, computer.getId())
                .with(ComputerDTO::setName, computer.getName())
                .build();
    }
}