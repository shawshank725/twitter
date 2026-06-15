@ApplicationModule(
        allowedDependencies = {
                "posting::posts",
                "authentication",
                "connections",
                "posting::dto"
        }
)
package com.social.twitter.timeline;

import org.springframework.modulith.ApplicationModule;