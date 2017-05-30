package map;

import map.computer.ComputerDTO;
import core.model.Company;
import core.model.Computer;
import org.springframework.context.annotation.Configuration;
import core.utils.GenericBuilder;
import core.validator.Validateur;

@Configuration
public class ComputerMapperDTO implements Mapper<Computer, ComputerDTO> {
    /**
     * model.Computer from ComputerDTO.
     * @param computerDTO in
     * @return model.Computer
     */
    @Override
    public Computer from(ComputerDTO computerDTO) {
        return GenericBuilder.of(Computer::new)
                .with(Computer::setId, computerDTO.getId())
                .with(Computer::setName, computerDTO.getName())
                .with(Computer::setIntroduced, Validateur.parseString(computerDTO.getIntroduced()))
                .with(Computer::setDiscontinued, Validateur.parseString(computerDTO.getDiscontinued()))
                .with(Computer::setCompany, (computerDTO.getCompanyId() == null || computerDTO.getCompanyId() < 1) ? null :
                        GenericBuilder.of(Company::new)
                        .with(Company::setId, computerDTO.getCompanyId())
                        .with(Company::setName, computerDTO.getCompanyName())
                        .build())
                .build();
    }


    /**
     * model.Computer to ComputerDTO.
     * @param computer in
     * @return ComputerDTO
     */
    @Override
    public ComputerDTO to(Computer computer) {
        return GenericBuilder.of(ComputerDTO::new)
                .with(ComputerDTO::setId, computer.getId())
                .with(ComputerDTO::setName, computer.getName())
                .with(ComputerDTO::setIntroduced, computer.getIntroduced())
                .with(ComputerDTO::setDiscontinued, computer.getDiscontinued())
                .with(ComputerDTO::setCompanyId, computer.getCompany() == null ? null : computer.getCompany().getId())
                .with(ComputerDTO::setCompanyName, computer.getCompany() == null ? null : computer.getCompany().getName())
                .build();
    }
}