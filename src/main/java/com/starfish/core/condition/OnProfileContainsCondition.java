package com.starfish.core.condition;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

class OnProfileContainsCondition implements Condition {

    @Override
    @SuppressWarnings("NullAway") // Reflection
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();

        MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(ProfileContains.class.getName());
        if (attrs != null) {
            for (Object value : attrs.get("value")) {
                // 使用包含逻辑处理
                String[] annotationValue = (String[]) value;
                for (String activeProfile : activeProfiles) {
                    for (String annotationValueItem : annotationValue) {
                        if (activeProfile.contains(annotationValueItem)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

}
