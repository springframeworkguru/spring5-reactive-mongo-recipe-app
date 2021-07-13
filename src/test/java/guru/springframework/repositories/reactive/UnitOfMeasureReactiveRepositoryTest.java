package guru.springframework.repositories.reactive;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    public static final String EACH = "Each";
    @Autowired
    UnitOfMeasureReactiveRepository reactiveRepository;

    @Before
    public void setUp() throws Exception {
        reactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() throws Exception {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(EACH);

        reactiveRepository.save(unitOfMeasure).block();

        Long count = reactiveRepository.count().block();

        assertEquals(Long.valueOf(1), count);
    }

    @Test
    public void testFindByDescription() throws Exception {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(EACH);

        reactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure savedUom = reactiveRepository.findByDescription(EACH).block();

        assertEquals(EACH, savedUom.getDescription());
    }
}