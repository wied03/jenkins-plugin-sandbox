package io.jenkins.plugins

import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.BuildWatcher
import org.jvnet.hudson.test.JenkinsRule

import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertTrue

class MyPluginTest {
    @Rule
    public JenkinsRule j = new JenkinsRule()
    @ClassRule
    public static BuildWatcher bw = new BuildWatcher()

    @Test
    void foo() {
        // arrange
        def project = j.createProject(WorkflowJob)
        project.definition = new CpsFlowDefinition(new File('src/test/resources/Jenkinsfile').text)

        // act
        def build = j.buildAndAssertSuccess(project)

        // assert
        j.assertLogContains('we ran with parameter foobar',
                            build)
        assertTrue 'We expect to keep the build around',
                   build.isKeepLog()
        assertEquals("build for 1",
                     build.getDescription());
    }
}
