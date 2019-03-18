package io.jenkins.plugins;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;

public class MyPlugin extends Builder implements SimpleBuildStep {
    private final String theParameter;

    @DataBoundConstructor
    public MyPlugin(String theParameter) {
        this.theParameter = theParameter;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("we ran with parameter " + this.theParameter);
    }
}
