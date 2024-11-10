package store.file;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FileMapperTest {

    @Test
    void 읽은_프로모션_데이터를_맵으로_반환() {
        //given
        List<String> lines = List.of(
                "name,buy,get,start_date,end_date",
                "탄산2+1,2,1,2024-01-01,2024-12-31",
                "MD추천상품,1,1,2024-01-01,2024-12-31"
        );

        //when
        List<Map<String, String>> map = FileMapper.map(lines);

        //then
        assertThat(map.getFirst().keySet()).contains("name", "buy", "get", "start_date", "end_date");
    }

    @Test
    void 읽은_상품_데이터를_맵으로_반환() {
        //given
        List<String> lines = List.of(
                "name,price,quantity,promotion",
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null"
        );

        //when
        List<Map<String, String>> map = FileMapper.map(lines);

        //then
        assertThat(map.getFirst().keySet()).contains("name", "price", "quantity", "promotion");
    }
}