package service.dto;

import model.Pages;
import model.company.Company;
import model.computer.Computer;
import model.dto.company.CompanyDTO;
import model.dto.computer.ComputerDTO;

import java.util.ArrayList;

public interface DTOService {

    /**
     * Convert Pages<Computer> to Pages<ComputerDTO>.
     * @param pages Pages of Computer
     * @return the Pages<ComputerDTO> generated
     */
    Pages<ComputerDTO> convertComputerToComputerDTO(Pages<Computer> pages);

    /**
     * Convert ArrayList<Computer> to ArrayList<ComputerDTO>.
     * @param list ArrayList of Computer
     * @return the ArrayList<ComputerDTO> generated
     */
    ArrayList<ComputerDTO> convertComputerToComputerDTO(ArrayList<Computer> list);


    /**
     * Convert Pages<Company> to Pages<CompanyDTO>.
     * @param pages Pages of Company
     * @return the Pages<CompanyDTO> generated
     */
    Pages<CompanyDTO> convertCompanyToCompanyDTO(Pages<Company> pages);

    /**
     * Convert ArrayList<Company> to ArrayList<CompanyDTO>.
     * @param list ArrayList of Company
     * @return the ArrayList<CompanyDTO> generated
     */
    ArrayList<CompanyDTO> convertCompanyToCompanyDTO(ArrayList<Company> list);
}