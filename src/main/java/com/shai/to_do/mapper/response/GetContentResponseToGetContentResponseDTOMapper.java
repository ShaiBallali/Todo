package com.shai.to_do.mapper.response;

import com.shai.to_do.constants.Queries;
import com.shai.to_do.dto.response.ContentDTO;
import com.shai.to_do.dto.response.GetContentResponseDTO;
import com.shai.to_do.dto.response.ResponseDTOFactory;
import com.shai.to_do.entity.Todo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetContentResponseToGetContentResponseDTOMapper {
    private final ResponseDTOFactory responseDTOFactory;

    public GetContentResponseToGetContentResponseDTOMapper(ResponseDTOFactory responseDTOFactory) {
        this.responseDTOFactory = responseDTOFactory;
    }

    public GetContentResponseDTO map(List<Todo> todos) {
        GetContentResponseDTO getContentResponseDTO = (GetContentResponseDTO) responseDTOFactory.getResponseDTO(Queries.GET);
        List<ContentDTO> contents = new ArrayList<>();
        for (Todo todo : todos) {
            ContentDTO contentDTO = new ContentDTO(todo.getRawid(),
                    todo.getTitle(),
                    todo.getContent(),
                    todo.getState(),
                    todo.getDuedate());
            contents.add(contentDTO);
        }
        getContentResponseDTO.setResult(contents);
        return getContentResponseDTO;
    }
}
