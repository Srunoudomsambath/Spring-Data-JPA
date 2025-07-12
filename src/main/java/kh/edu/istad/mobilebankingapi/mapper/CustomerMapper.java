package kh.edu.istad.mobilebankingapi.mapper;


import kh.edu.istad.mobilebankingapi.domain.Customer;
import kh.edu.istad.mobilebankingapi.dto.CreateCustomerRequest;
import kh.edu.istad.mobilebankingapi.dto.CustomerResponse;
import kh.edu.istad.mobilebankingapi.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // DTO -> Model
    // Model -> DTO
    // return type is converted data
    // parameter
    CustomerResponse fromCustomer(Customer customer);

    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    // Partially update -> find those 3 attributes by using mapper (map the which is not null) (MAP LOGIC)
    // source and target is (Customer) reference happen when it checks in repo
    // if it null no update no map
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);

}
