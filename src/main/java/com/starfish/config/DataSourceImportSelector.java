package com.starfish.config;

import com.starfish.context.PropertiesContext;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * DataSourceImportSelector
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-02
 */
public class DataSourceImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableDataSource.class.getName());
        Boolean enableDataSource = (Boolean) (attributes != null ? attributes.get("value") : null);
        if (enableDataSource == null || enableDataSource) {
            PropertiesContext.set("DataSourceAutoConfiguration", "true");
            return new String[]{
                    "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
                    "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
            };
        } else {
            PropertiesContext.set("DataSourceAutoConfiguration", "false");
            return new String[]{};
        }
    }


}
