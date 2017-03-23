package service.dto;

import model.Pages;
import model.company.Company;
import model.computer.Computer;
import model.dto.company.CompanyDTO;
import model.dto.computer.ComputerDTO;

import java.util.ArrayList;

/**
 * Created by ebiz on 23/03/17.
 */
public class DTOServiceImpl implements DTOService {

    /**
     * Convert Pages<Computer> to Pages<ComputerDTO>.
     * @param pages Pages of Computer
     * @return the Pages<ComputerDTO> generated
     */
    public Pages<ComputerDTO> convertComputerToComputerDTO(Pages<Computer> pages)  {
        return new Pages<>(convertComputerToComputerDTO(pages.getListPage()), pages.getPageSize());
    }

    /**
     * Convert ArrayList<Computer> to ArrayList<ComputerDTO>.
     * @param list ArrayList of Computer
     * @return the ArrayList<ComputerDTO> generated
     */
    public ArrayList<ComputerDTO> convertComputerToComputerDTO(ArrayList<Computer> list) {
        ArrayList<ComputerDTO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(new ComputerDTO(list.get(i)));
        }
        return result;
    }


    /**
     * Convert Pages<Company> to Pages<CompanyDTO>.
     * @param pages Pages of Company
     * @return the Pages<CompanyDTO> generated
     */
    public Pages<CompanyDTO> convertCompanyToCompanyDTO(Pages<Company> pages) {
        return new Pages<>(convertCompanyToCompanyDTO(pages.getListPage()), pages.getPageSize());
    }

    /**
     * Convert ArrayList<Company> to ArrayList<CompanyDTO>.
     * @param list ArrayList of Company
     * @return the ArrayList<CompanyDTO> generated
     */
    public ArrayList<CompanyDTO> convertCompanyToCompanyDTO(ArrayList<Company> list) {
        ArrayList<CompanyDTO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(new CompanyDTO(list.get(i)));
        }
        return result;
    }
}
