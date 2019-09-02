package com.cybernation.convert;

import com.cybernation.convert.config.ResponseConvertConfig;
import com.cybernation.convert.models.CommonModelOne;
import com.cybernation.convert.models.CommonModelTwo;
import com.cybernation.convert.models.TestResponseDto;
import io.extremum.common.dto.converters.ConversionConfig;
import io.extremum.common.dto.converters.services.DtoConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ResponseConvertConfig.class)
class ResponseConvertTest {
    @Autowired
    private DtoConversionService service;

    @Test
    void convertModels_To_CommonResponse() {
        CommonModelOne modelOne = new CommonModelOne("test");
        CommonModelTwo modelTwo = new CommonModelTwo("test");
        ConversionConfig config = ConversionConfig.builder().build();

        long count = Stream.of(modelOne, modelTwo)
                .map(model -> service.convertUnknownToResponseDto(model, config))
                .filter(responseDto -> responseDto.getClass() == TestResponseDto.class)
                .count();
        assertEquals(count, 2);
    }
}
