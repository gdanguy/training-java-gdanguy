package service.mappy;

import model.GenericBuilder;
import model.Page;
import model.company.Company;
import service.mappy.company.CompanyDTO;

import java.util.ArrayList;

/**
 * Created by ebiz on 23/03/17.
 */
public class CompanyMapperImpl implements CompanyMapper {

    /**
     * Convert Page<Company> to Page<CompanyDTO>.
     * @param page Page of Company
     * @return the Page<CompanyDTO> generated
     */
    public Page<CompanyDTO> toDTO(Page<Company> page) {
        return new Page<>(toDTO(page.getListPage()), page.getPageSize());
    }

    /**
     * Convert ArrayList<Company> to ArrayList<CompanyDTO>.
     * @param list ArrayList of Company
     * @return the ArrayList<CompanyDTO> generated
     */
    public ArrayList<CompanyDTO> toDTO(ArrayList<Company> list) {
        ArrayList<CompanyDTO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Company c = list.get(i);
            result.add(GenericBuilder.of(CompanyDTO::new)
                    .with(CompanyDTO::setId, c.getId())
                    .with(CompanyDTO::setName, c.getName())
                    .build());
        }
        return result;
    }
}
