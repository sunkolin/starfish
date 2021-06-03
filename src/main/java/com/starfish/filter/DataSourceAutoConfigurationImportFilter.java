//package com.starfish.filter;
//
//import com.google.common.collect.Sets;
//import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
//import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
//
//import java.util.Set;
//
///**
// * DataSourceAutoConfigurationImportFilter
// *
// * @author sunny
// * @version 1.0.0
// * @since 2021-06-03
// */
//public class DataSourceAutoConfigurationImportFilter implements AutoConfigurationImportFilter {
//
//    private static final Set<String> EXCLUDE_CLASSES = Sets.newHashSet(
//            "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
//            "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration");
//
//    @Override
//    public boolean[] match(String[] classNames, AutoConfigurationMetadata autoConfigurationMetadata) {
//        boolean[] matches = new boolean[classNames.length];
//
//        for (int i = 0; i < classNames.length; i++) {
//            matches[i] = !EXCLUDE_CLASSES.contains(classNames[i]);
//        }
//        return matches;
//    }
//
//}
