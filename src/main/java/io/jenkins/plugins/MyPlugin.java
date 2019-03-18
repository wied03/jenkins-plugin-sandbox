package io.jenkins.plugins;

import hudson.Extension;
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
        return new Execution(context, this);
    }

    public static final class Execution extends SynchronousNonBlockingStepExecution {
        // TODO: Is this the right way to do this? It's serializable (the step isn't so we can't put that here)
        private String theParameter;

        protected Execution(@Nonnull StepContext context,
                            MyPlugin step) {
            super(context);
            this.theParameter = step.theParameter;
        }

        @Override
        protected Void run() throws Exception {
            System.out.println("hi "+ theParameter);
            return null;
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
    }
}
