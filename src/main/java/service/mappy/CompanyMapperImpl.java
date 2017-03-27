package service.mappy;

import model.Pages;
import model.company.Company;
import service.mappy.company.CompanyDTO;

import java.util.ArrayList;

/**
 * Created by ebiz on 23/03/17.
 */
public enum CompanyMapperImpl implements CompanyMapper {
    INSTANCE;

    /**
     * Convert Pages<Company> to Pages<CompanyDTO>.
     * @param pages Pages of Company
     * @return the Pages<CompanyDTO> generated
     */
    public Pages<CompanyDTO> toDTO(Pages<Company> pages) {
        return new Pages<>(toDTO(pages.getListPage()), pages.getPageSize());
    }

    /**
     * Convert ArrayList<Company> to ArrayList<CompanyDTO>.
     * @param list ArrayList of Company
     * @return the ArrayList<CompanyDTO> generated
     */
    public ArrayList<CompanyDTO> toDTO(ArrayList<Company> list) {
        ArrayList<CompanyDTO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(new CompanyDTO(list.get(i)));
        }
        return result;
    }
}
