@ApplicationModule(
        allowedDependencies = {
                "posting::posts",
                "authentication",
                "connections"
        }
)
package com.social.twitter.timeline;

import org.springframework.modulith.ApplicationModule;