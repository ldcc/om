package com.ldcstorm.applet.om2.dbm;

import com.ldcstorm.applet.om2.dto.DTO;
import com.ldcstorm.applet.om2.dto.Product;

import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class DTProduct<O extends DTO> extends DTM<O> {
    private DTProduct(Class<O> tClass, String base) {
        super(tClass, base);
    }

    private static final DTM<Product> dtm = new DTProduct<>(Product.class, Product.PRODUCT_BASE);

    public static DTM<Product> getDtm() {
        return dtm;
    }

    @Override
    protected List<O> getAll(Statement statement) {
        List<O> os= super.getAll(statement);
        Collections.reverse(os);
        return os;
    }

}
