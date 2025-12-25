package com.starfish.core.condition;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

/**
 * OnLinuxCondition
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-08
 */
public class OnProfileCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 获取当前生效的环境
        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();

        // 获取注解中配置的环境
        String[] annotationProfiles = new String[]{};
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnProfile.class.getName());
        if (attributes != null) {
            annotationProfiles = (String[]) attributes.get("value");
        }

        // 判断当前生效的环境中包含注解中的环境
        Set<String> set1 = Sets.newHashSet(activeProfiles);
        Set<String> set2 = Sets.newHashSet(annotationProfiles);
        Set<String> intersectionResult = Sets.intersection(set1, set2);
        boolean match = CollectionUtils.isEmpty(intersectionResult);
        String activeProfilesString = Joiner.on(",").skipNulls().join(activeProfiles);
        String annotationProfilesString = Joiner.on(",").skipNulls().join(annotationProfiles);
        String message = "ConditionalOnProfile 环境验证结果是" + match + ",生效的环境是" + activeProfilesString + ",注解中环境是" + annotationProfilesString;

        return new ConditionOutcome(match, message);
    }

}
