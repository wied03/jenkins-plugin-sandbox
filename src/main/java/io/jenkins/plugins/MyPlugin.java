package io.jenkins.plugins;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.flow.StashManager;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.steps.*;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.util.HashSet;
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
                            String theParameter) {
            super(context);
            this.theParameter = theParameter;
        }

        @Override
        protected String run() throws Exception {
            StepContext context = getContext();
            WorkflowRun run = context.get(WorkflowRun.class);
            TaskListener listener = context.get(TaskListener.class);

            run.keepLog(true);

//            StashManager.stash((Run)this.getContext().get(Run.class),
//                               "theStash",
//                               (FilePath)this.getContext().get(FilePath.class),
//                               (Launcher)this.getContext().get(Launcher.class),
//                               (EnvVars)this.getContext().get(EnvVars.class),
//                               (TaskListener)this.getContext().get(TaskListener.class),
//                               null,
//                               null,
//                               false, false);
            listener.getLogger().println("changeset size " + run.getChangeSets().size());
            listener.getLogger().println("we ran with parameter " + theParameter);
            return "DEV";
        }
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            HashSet<Class<?>> set = new HashSet<>();
            set.add(WorkflowRun.class);
            set.add(TaskListener.class);
            set.add(EnvVars.class);
            return set;
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
