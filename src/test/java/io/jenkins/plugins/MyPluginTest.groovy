package io.jenkins.plugins

import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.BuildWatcher
import org.jvnet.hudson.test.JenkinsRule

class MyPluginTest {
    @Rule
    public JenkinsRule j = new JenkinsRule()
    @ClassRule
    public static BuildWatcher bw = new BuildWatcher()

    @Test
    void foo() {
        // arrange
        def project = j.createProject(WorkflowJob)
        project.definition = new CpsFlowDefinition('node { step([$class: "io.jenkins.plugins.MyPlugin", theParameter: "foobar"]) }')

        // act
        def build = j.buildAndAssertSuccess(project)

        // assert
        j.assertLogContains('we ran with parameter foobar',
                            build)
    }
}
