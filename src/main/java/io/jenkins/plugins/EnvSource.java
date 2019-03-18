package io.jenkins.plugins;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.steps.*;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class EnvSource extends Step {

    @DataBoundConstructor
    public EnvSource() {

    }

    @Override
    public StepExecution start(StepContext context) {
        return new Execution(context);
    }

    public static final class Execution extends SynchronousNonBlockingStepExecution {


        protected Execution(@Nonnull StepContext context) {
            super(context);
        }

        @Override
        protected String run() throws Exception {
            return "989";
        }
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            HashSet<Class<?>> set = new HashSet<>();
            set.add(WorkflowRun.class);
            set.add(TaskListener.class);
            set.add(FilePath.class);
            set.add(Launcher.class);
            set.add(EnvVars.class);
            return set;
        }

        @Override
        public String getFunctionName() {
            return "populateBuildNumber";
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Will execute some stuff";
        }
    }
}
