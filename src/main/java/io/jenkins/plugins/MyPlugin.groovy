package io.jenkins.plugins

import hudson.Extension
import hudson.FilePath
import hudson.Launcher
import hudson.model.AbstractProject
import hudson.model.Run
import hudson.model.TaskListener
import hudson.tasks.BuildWrapperDescriptor
import hudson.tasks.Builder
import jenkins.YesNoMaybe
import jenkins.tasks.SimpleBuildStep
import org.jenkinsci.Symbol
import org.kohsuke.stapler.DataBoundConstructor

import javax.annotation.Nonnull

class MyPlugin extends Builder implements SimpleBuildStep {
    private final String theParameter

    @DataBoundConstructor
    MyPlugin(String theParameter) {
        this.theParameter = theParameter
    }

    String getTheParameter() {
        this.theParameter
    }

    @Override
    void perform(@Nonnull Run<?, ?> run,
                 @Nonnull FilePath workspace,
                 @Nonnull Launcher launcher,
                 @Nonnull TaskListener listener) throws InterruptedException, IOException {
        listener.logger.println('we ran with parameter '+this.theParameter)
    }

    @Symbol('doStuff')
    @Extension(dynamicLoadable = YesNoMaybe.YES)
    static class DescriptorImpl extends BuildWrapperDescriptor {
        @Override
        boolean isApplicable(AbstractProject<?, ?> item) {
            true
        }

        @Override
        String getDisplayName() {
            'The display name'
        }
    }
}
