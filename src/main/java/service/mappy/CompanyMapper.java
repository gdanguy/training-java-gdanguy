package service.mappy;

import model.Page;
import model.company.Company;
import service.mappy.company.CompanyDTO;

import java.util.ArrayList;

public interface CompanyMapper {

    /**
     * Convert Page<Company> to Page<CompanyDTO>.
     * @param page Page of Company
     * @return the Page<CompanyDTO> generated
     */
    Page<CompanyDTO> toDTO(Page<Company> page);

    /**
     * Convert ArrayList<Company> to ArrayList<CompanyDTO>.
     * @param list ArrayList of Company
     * @return the ArrayList<CompanyDTO> generated
     */
    ArrayList<CompanyDTO> toDTO(ArrayList<Company> list);

}