package ru.feolex.ServiceLayer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Dao.CatColorRepository;
import ru.feolex.DaoLayer.Entities.CatColor;

@Component
public class ColorService implements ColorServiceInterface {

    @Autowired
    private CatColorRepository colorRep;

    @Override
    public boolean existsColorByName(String colorName) {
        return colorRep.existsByColorName(colorName);
    }

    @Override
    public boolean existsColorById(Long along) {
        return colorRep.existsById(along);
    }

    @Override
    public void initColors() {
        if (!colorRep.existsById(1L)) {
            colorRep.save(new CatColor(1L, "WHITE"));
        }
        if (!colorRep.existsById(2L)) {
            colorRep.save(new CatColor(2L, "GREY"));
        }
        if (!colorRep.existsById(3L)) {
            colorRep.save(new CatColor(3L, "RED"));
        }
        if (!colorRep.existsById(4L)) {
            colorRep.save(new CatColor(4L, "ANOTHER"));
        }
    }
}
