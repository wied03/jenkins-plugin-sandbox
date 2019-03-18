package io.jenkins.plugins;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.steps.*;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MyPlugin extends Step {
    private final String theParameter;

    @DataBoundConstructor
    public MyPlugin(String theParameter) {
        this.theParameter = theParameter;
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new Execution(context,
                             this.theParameter);
    }

    public static final class Execution extends SynchronousNonBlockingStepExecution {
        // TODO: Is this the right way to do this? It's serializable (the step isn't so we can't put that here)
        private String theParameter;

        protected Execution(@Nonnull StepContext context,
                            String theParameter) throws Exception {
            super(context);
            this.theParameter = theParameter;
        }

        @Override
        protected String run() throws Exception {
            StepContext context = getContext();
            WorkflowRun run = context.get(WorkflowRun.class);
            TaskListener listener = context.get(TaskListener.class);
            run.keepLog(true);
            listener.getLogger().println("changeset size " + run.getChangeSets().size());
            listener.getLogger().println("we ran with parameter " + theParameter);
            return "DEV";
        }
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return new HashSet<>();
        }

        @Override
        public String getFunctionName() {
            return "doStuff";
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Will execute some stuff";
        }
    }
}
