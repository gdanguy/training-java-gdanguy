package service.mappy;

import model.GenericBuilder;
import model.company.Company;
import service.mappy.company.CompanyDTO;

/**
 * Created by ebiz on 24/04/17.
 */
public class CompanyMapper implements Mapper<Company, CompanyDTO> {
    /**
     * Company from CompanyDTO.
     * @param companyDTO in
     * @return Company
     */
    @Override
    public Company from(CompanyDTO companyDTO) {
        return GenericBuilder.of(Company::new)
                .with(Company::setId, companyDTO.getId())
                .with(Company::setName, companyDTO.getName())
                .build();
    }

    /**
     * Company to CompanyDTO.
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
