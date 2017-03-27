package service.mappy;

import model.Pages;
import model.company.Company;
import service.mappy.company.CompanyDTO;

import java.util.ArrayList;

public interface CompanyMapper {

    /**
     * Convert Pages<Company> to Pages<CompanyDTO>.
     * @param pages Pages of Company
     * @return the Pages<CompanyDTO> generated
     */
    Pages<CompanyDTO> toDTO(Pages<Company> pages);

    /**
     * Convert ArrayList<Company> to ArrayList<CompanyDTO>.
     * @param list ArrayList of Company
     * @return the ArrayList<CompanyDTO> generated
     */
    ArrayList<CompanyDTO> toDTO(ArrayList<Company> list);

    /**
     * Return the Instance of ComputerServiceImpl.
     * @return the Instance of ComputerServiceImpl
     */
    static CompanyMapperImpl getInstance() {
        return CompanyMapperImpl.INSTANCE;
    }
}