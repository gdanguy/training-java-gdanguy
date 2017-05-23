package map;

import map.company.CompanyDTO;
import core.model.Company;
import org.springframework.context.annotation.Configuration;
import core.utils.GenericBuilder;

/**
 * Created by ebiz on 24/04/17.
 */
@Configuration
public class CompanyMapper implements Mapper<Company, CompanyDTO> {
    /**
     * model.Company from CompanyDTO.
     * @param companyDTO in
     * @return model.Company
     */
    @Override
    public Company from(CompanyDTO companyDTO) {
        return GenericBuilder.of(Company::new)
                .with(Company::setId, companyDTO.getId())
                .with(Company::setName, companyDTO.getName())
                .build();
    }

    /**
     * model.Company to CompanyDTO.
     * @param company in
     * @return CompanyDTO
     */
    @Override
    public CompanyDTO to(Company company) {
        return GenericBuilder.of(CompanyDTO::new)
                .with(CompanyDTO::setId, company.getId())
                .with(CompanyDTO::setName, company.getName())
                .build();
    }
}
