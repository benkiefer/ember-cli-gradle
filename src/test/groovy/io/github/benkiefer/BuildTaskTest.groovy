package io.github.benkiefer

import org.testng.annotations.Test

class BuildTaskTest extends EmberCliPluginSupport {

    @Test
    void tasksAreRegistered() {
        assert project.tasks.build
    }

    @Test
    void buildDependsOn() {
        assert project.tasks.build.dependsOn.contains('assemble')
        assert project.tasks.build.dependsOn.contains('check')
    }

}