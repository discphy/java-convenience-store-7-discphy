package store.file;

import org.junit.jupiter.api.Test;
import store.constant.FileName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileReaderTest {

    @Test
    void 프로모션_파일_읽기() {
        //when
        List<String> read = FileReader.read(FileName.PROMOTIONS);

        //then
        assertThat(read).contains("name,buy,get,start_date,end_date");
    }

    @Test
    void 상품_파일_읽기() {
        //when
        List<String> read = FileReader.read(FileName.PRODUCTS);

        //then
        assertThat(read).contains("name,price,quantity,promotion");
    }
}