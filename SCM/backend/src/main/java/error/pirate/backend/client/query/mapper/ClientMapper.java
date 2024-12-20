package error.pirate.backend.client.query.mapper;

import error.pirate.backend.client.query.dto.ClientResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClientMapper {

    List<ClientResponse> findClientListByFilter(
            @Param("clientName") String clientName,
            @Param("clientRegistrationNo") String clientRegistrationNo,
            @Param("sortBy") String sortBy,
            @Param("sortDirection") String sortDirection,
            @Param("offset") int offset,
            @Param("size") int size
    );
}
